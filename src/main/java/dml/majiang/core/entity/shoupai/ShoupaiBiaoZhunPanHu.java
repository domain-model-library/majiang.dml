package dml.majiang.core.entity.shoupai;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 标准的判胡算法，既，4面子（或多于4面子）+1将子
 */
public class ShoupaiBiaoZhunPanHu {

    private static Map<Integer, List<DuliPaiGroupPaiXingCombination>> duliPaiGroupPaiXingCombinationsPatternCache = new ConcurrentHashMap<>();
    private static Map<String, List<LianxuPaiGroupPaiXingCombination>> lianxuPaiGroupPaiXingCombinationsPatternCache = new ConcurrentHashMap<>();

    public static List<ShoupaiPaiXing> getAllHuPaiShoupaiPaiXing(List<Pai> shoupaiList) {
        //把shoupaiList按照牌的类型分组
        Map<MajiangPai, Set<Pai>> paiTypeMap = new HashMap<>();
        for (Pai pai : shoupaiList) {
            MajiangPai paiType = pai.getPaiType();
            Set<Pai> paiSet = paiTypeMap.get(paiType);
            if (paiSet == null) {
                paiSet = new HashSet<>();
                paiTypeMap.put(paiType, paiSet);
            }
            paiSet.add(pai);
        }
        //记录每组牌的数量
        int[] paiAmounts = new int[MajiangPai.count];
        for (Map.Entry<MajiangPai, Set<Pai>> entry : paiTypeMap.entrySet()) {
            MajiangPai paiType = entry.getKey();
            Set<Pai> paiSet = entry.getValue();
            int amount = paiSet.size();
            paiAmounts[paiType.ordinal()] = amount;
        }

        //遍历paiAmounts，解析出独立牌组和连续牌组
        List<DuliPaiAmountGroup> duliPaiAmountGroups = new ArrayList<>();
        List<LianxuPaiAmountGroup> lianxuPaiAmountGroups = new ArrayList<>();
        int lianxuCount = 0;
        for (int paiTypeOrdinal = 0; paiTypeOrdinal < MajiangPai.count; paiTypeOrdinal++) {
            int amount = paiAmounts[paiTypeOrdinal];
            if (amount == 0 || paiTypeOrdinal == (MajiangPai.count - 1)) {
                if (lianxuCount == 0) {
                    //前面没有未处理的牌，过
                    continue;
                }
                //看下之前遍历的是否形成连续牌组
                if (lianxuCount >= 3) {
                    int[] lianxupaizu = new int[lianxuCount];
                    System.arraycopy(paiAmounts, paiTypeOrdinal - lianxuCount, lianxupaizu, 0, lianxuCount);
                    lianxuPaiAmountGroups.add(new LianxuPaiAmountGroup(lianxupaizu, MajiangPai.valueOf(paiTypeOrdinal - lianxuCount)));
                    //断连
                    lianxuCount = 0;
                    continue;
                }
                //之前遍历的既然没有形成连续牌组，那就挨个放入独立牌组
                for (int i = paiTypeOrdinal - lianxuCount; i < paiTypeOrdinal; i++) {
                    duliPaiAmountGroups.add(new DuliPaiAmountGroup(paiAmounts[i], MajiangPai.valueOf(i)));
                }
                //断连
                lianxuCount = 0;
                continue;
            }
            lianxuCount++;
        }

        //计算每个独立牌组的所有牌型组合，比如对于独立牌组“6个一万”，可以是 牌型组合：“两个一万刻子” 或者 牌型组合：“一个一万杠子+一个一万对子”
        List<List<DuliPaiGroupPaiXingCombination>> duliPaiGroupPaiXingCombinations = new ArrayList<>();
        for (DuliPaiAmountGroup amountGroup : duliPaiAmountGroups) {
            int amount = amountGroup.getAmount();
            MajiangPai paiType = amountGroup.getPaiType();
            //有单牌不能胡
            if (amount == 1) {
                return null;
            }
            List<DuliPaiGroupPaiXingCombination> duliPaiGroupPaiXingCombinationList = new ArrayList();
            duliPaiGroupPaiXingCombinations.add(duliPaiGroupPaiXingCombinationList);
            generateDuliPaiGroupPaiXingCombinations(amount, paiType, duliPaiGroupPaiXingCombinationList);
        }

        //计算每个连续牌组的所有牌型组合
        List<List<LianxuPaiGroupPaiXingCombination>> lianxuPaiGroupPaiXingCombinations = new ArrayList<>();
        for (LianxuPaiAmountGroup amountGroup : lianxuPaiAmountGroups) {
            int[] amountArray = amountGroup.getAmountArray();
            MajiangPai startPaiType = amountGroup.getStartPaiType();
            List<LianxuPaiGroupPaiXingCombination> lianxuPaiGroupPaiXingCombinationList = new ArrayList();
            lianxuPaiGroupPaiXingCombinations.add(lianxuPaiGroupPaiXingCombinationList);
            generateLianxuPaiGroupPaiXingCombinations(amountArray, startPaiType, lianxuPaiGroupPaiXingCombinationList);
        }

        //组合独立牌组和连续牌组的所有牌型组合，过滤掉不能胡的，形成最终的牌型组合
        //计算独立牌组能形成的所有牌型组合
        List<PaiXingCombination> paiXingCombinationsForDuli = new ArrayList<>();
        if (!duliPaiGroupPaiXingCombinations.isEmpty()) {
            int totalpaiXingCombinationsForDuli = 1;
            int[] modArrayForDuli = new int[duliPaiGroupPaiXingCombinations.size()];
            for (int i = 0; i < modArrayForDuli.length; i++) {
                if (i == 0) {
                    modArrayForDuli[i] = 1;
                } else {
                    modArrayForDuli[i] = totalpaiXingCombinationsForDuli;
                }
                int duliPaiGroupPaiXingCombinationCount = duliPaiGroupPaiXingCombinations.get(i).size();
                totalpaiXingCombinationsForDuli *= duliPaiGroupPaiXingCombinationCount;
            }
            for (int combinationCode = 0; combinationCode < totalpaiXingCombinationsForDuli; combinationCode++) {
                PaiXingCombination paiXingCombination = new PaiXingCombination();
                //反向遍历modArrayForDuli
                int subCode = combinationCode;
                for (int i = modArrayForDuli.length - 1; i >= 0; i--) {
                    int mod = modArrayForDuli[i];
                    int combinationsIndex = subCode / mod;
                    subCode = subCode % mod;
                    DuliPaiGroupPaiXingCombination combination = duliPaiGroupPaiXingCombinations.get(i).get(combinationsIndex);
                    paiXingCombination.acceptDuliPaiGroupPaiXingCombination(combination);
                }
                if (paiXingCombination.countDuizi() > 1) {
                    //对子数量超过1个，不能胡
                    continue;
                }
                paiXingCombinationsForDuli.add(paiXingCombination);
            }
        }

        //TODO 计算连续牌组能形成的所有牌型组合
    }

    private static void generateLianxuPaiGroupPaiXingCombinations(int[] amountArray, MajiangPai startPaiType,
                                                                  List<LianxuPaiGroupPaiXingCombination> combinationStore) {
        String key = Arrays.toString(amountArray);
        //从缓存取模式
        List<LianxuPaiGroupPaiXingCombination> combinationsPattern = lianxuPaiGroupPaiXingCombinationsPatternCache.get(key);
        if (combinationsPattern == null) {//缓存没取到，计算模式
            combinationsPattern = new ArrayList<>();

            //先计算出amountArray所有可能的PaiXingPattern
            List<PaiXingPattern> paiXingPatterns = new ArrayList<>();
            for (int i = 0; i < amountArray.length; i++) {
                int amount = amountArray[i];
                if (amount >= 2) {
                    paiXingPatterns.add(new DuiziPaiXingPattern(i));
                }
                if (amount >= 3) {
                    paiXingPatterns.add(new KeziPaiXingPattern(i));
                }
                if (amount >= 4) {
                    paiXingPatterns.add(new GangziPaiXingPattern(i));
                }
                if (i < amountArray.length - 2) {
                    paiXingPatterns.add(new ShunziPaiXingPattern(i));
                }
            }

            //遍历paiXingPatterns，从amountArray取每个PaiXingPattern可能的数量，对子最多只能取1个
            int totalAmountLeft = 0;
            for (int amount : amountArray) {
                totalAmountLeft += amount;
            }
            caculateLianxuPaiGroupPaiXingCombinationsPattern(amountArray, totalAmountLeft,
                    paiXingPatterns, 0,
                    new LianxuPaiGroupPaiXingCombination(), combinationsPattern);

            lianxuPaiGroupPaiXingCombinationsPatternCache.putIfAbsent(key, combinationsPattern);
        }
        //combinationsPattern生成实际combinations
        int paiTypeShift = startPaiType.ordinal();
        for (LianxuPaiGroupPaiXingCombination pattern : combinationsPattern) {
            LianxuPaiGroupPaiXingCombination combination = new LianxuPaiGroupPaiXingCombination();
            Map<MajiangPai, Integer> shunziMapInPattern = pattern.getShunziMap();
            for (Map.Entry<MajiangPai, Integer> entry : shunziMapInPattern.entrySet()) {
                MajiangPai paiTypeInPattern = entry.getKey();
                int amount = entry.getValue();
                MajiangPai paiType = MajiangPai.valueOf(paiTypeInPattern.ordinal() + paiTypeShift);
                combination.getShunziMap().put(paiType, amount);
            }
            Map<MajiangPai, Integer> duiziMapInPattern = pattern.getDuiziMap();
            for (Map.Entry<MajiangPai, Integer> entry : duiziMapInPattern.entrySet()) {
                MajiangPai paiTypeInPattern = entry.getKey();
                int amount = entry.getValue();
                MajiangPai paiType = MajiangPai.valueOf(paiTypeInPattern.ordinal() + paiTypeShift);
                combination.getDuiziMap().put(paiType, amount);
            }
            Map<MajiangPai, Integer> keziMapInPattern = pattern.getKeziMap();
            for (Map.Entry<MajiangPai, Integer> entry : keziMapInPattern.entrySet()) {
                MajiangPai paiTypeInPattern = entry.getKey();
                int amount = entry.getValue();
                MajiangPai paiType = MajiangPai.valueOf(paiTypeInPattern.ordinal() + paiTypeShift);
                combination.getKeziMap().put(paiType, amount);
            }
            Map<MajiangPai, Integer> gangziMapInPattern = pattern.getGangziMap();
            for (Map.Entry<MajiangPai, Integer> entry : gangziMapInPattern.entrySet()) {
                MajiangPai paiTypeInPattern = entry.getKey();
                int amount = entry.getValue();
                MajiangPai paiType = MajiangPai.valueOf(paiTypeInPattern.ordinal() + paiTypeShift);
                combination.getGangziMap().put(paiType, amount);
            }
            combinationStore.add(combination);
        }
    }

    private static void caculateLianxuPaiGroupPaiXingCombinationsPattern(int[] amountArray,
                                                                         int totalAmountLeft,
                                                                         List<PaiXingPattern> paiXingPatterns, int paiXingPatternsIndex,
                                                                         LianxuPaiGroupPaiXingCombination combinationPattern,
                                                                         List<LianxuPaiGroupPaiXingCombination> combinationPatternStore) {
        PaiXingPattern paiXingPattern = paiXingPatterns.get(paiXingPatternsIndex);
        int amountTaken = paiXingPattern.addToCombination(combinationPattern, amountArray);
        totalAmountLeft -= amountTaken;
        if (totalAmountLeft == 0) {
            //组合完成
            combinationPatternStore.add(combinationPattern);
            return;
        }
        if (paiXingPattern.canAddToCombination(combinationPattern, amountArray)) {
            //递推同一个PaiXingPattern
            int[] newAmountArray = Arrays.copyOf(amountArray, amountArray.length);
            LianxuPaiGroupPaiXingCombination newCombinationPattern = combinationPattern.copy();
            caculateLianxuPaiGroupPaiXingCombinationsPattern(newAmountArray, totalAmountLeft,
                    paiXingPatterns, paiXingPatternsIndex,
                    newCombinationPattern, combinationPatternStore);
        } else {
            if (paiXingPatternsIndex == paiXingPatterns.size() - 1) {
                //已经是最后一个了，不能再递推了
                return;
            } else {
                //递推下一个PaiXingPattern
                int nextPaiXingPatternsIndex = paiXingPatternsIndex + 1;
                int[] newAmountArray = Arrays.copyOf(amountArray, amountArray.length);
                LianxuPaiGroupPaiXingCombination newCombinationPattern = combinationPattern.copy();
                caculateLianxuPaiGroupPaiXingCombinationsPattern(newAmountArray, totalAmountLeft,
                        paiXingPatterns, nextPaiXingPatternsIndex,
                        newCombinationPattern, combinationPatternStore);
            }
        }
    }

    private static void generateDuliPaiGroupPaiXingCombinations(int amount, MajiangPai paiType,
                                                                List<DuliPaiGroupPaiXingCombination> combinationStore) {
        if (amount == 2) {
            //取对子
            DuliPaiGroupPaiXingCombination combination = new DuliPaiGroupPaiXingCombination(paiType);
            combination.addDuizi();
            combinationStore.add(combination);
        } else if (amount == 3) {
            //取刻子
            DuliPaiGroupPaiXingCombination combination = new DuliPaiGroupPaiXingCombination(paiType);
            combination.addKezi();
            combinationStore.add(combination);
        } else if (amount == 4) {
            //取杠子
            DuliPaiGroupPaiXingCombination combination = new DuliPaiGroupPaiXingCombination(paiType);
            combination.addGangzi();
            combinationStore.add(combination);
        } else {
            //从缓存取模式
            List<DuliPaiGroupPaiXingCombination> combinationsPattern = duliPaiGroupPaiXingCombinationsPatternCache.get(amount);
            if (combinationsPattern == null) {//缓存没取到，计算模式
                combinationsPattern = new ArrayList<>();
                //先取对子，尝试取0个到1个对子
                for (int i = 0; i <= 1; i++) {
                    DuliPaiGroupPaiXingCombination combinationPattern = new DuliPaiGroupPaiXingCombination(null);
                    combinationPattern.addDuizi(i);
                    amount -= i * 2;
                    //再取刻子，先计算最大的刻子数量
                    int maxKeziAmount = amount / 3;
                    //尝试取0个到maxKeziAmount个刻子
                    for (int j = 0; j <= maxKeziAmount; j++) {
                        DuliPaiGroupPaiXingCombination combinationPattern2 = combinationPattern.copy();
                        combinationPattern2.addKezi(j);
                        amount -= j * 3;
                        //再取杠子，先计算最大的杠子数量
                        int maxGangziAmount = amount / 4;

                        //尝试取maxGangziAmount个杠子
                        DuliPaiGroupPaiXingCombination combinationPattern3 = combinationPattern2.copy();
                        combinationPattern3.addGangzi(maxGangziAmount);
                        amount -= maxGangziAmount * 4;
                        if (amount == 0) {
                            combinationsPattern.add(combinationPattern3);
                        }
                        amount += maxGangziAmount * 4;

                        amount += j * 3;
                    }
                    amount += i * 2;
                }
                duliPaiGroupPaiXingCombinationsPatternCache.putIfAbsent(amount, combinationsPattern);
            }
            for (DuliPaiGroupPaiXingCombination pattern : combinationsPattern) {
                DuliPaiGroupPaiXingCombination combination = pattern.copy();
                combination.setPaiType(paiType);
                combinationStore.add(combination);
            }
        }
    }

    private static class DuliPaiAmountGroup {
        private int amount;
        private MajiangPai paiType;

        private DuliPaiAmountGroup(int amount, MajiangPai paiType) {
            this.amount = amount;
            this.paiType = paiType;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public MajiangPai getPaiType() {
            return paiType;
        }

        public void setPaiType(MajiangPai paiType) {
            this.paiType = paiType;
        }
    }

    private static class LianxuPaiAmountGroup {
        private int[] amountArray;
        private MajiangPai startPaiType;

        private LianxuPaiAmountGroup(int[] amountArray, MajiangPai startPaiType) {
            this.amountArray = amountArray;
            this.startPaiType = startPaiType;
        }

        public int[] getAmountArray() {
            return amountArray;
        }

        public void setAmountArray(int[] amountArray) {
            this.amountArray = amountArray;
        }

        public MajiangPai getStartPaiType() {
            return startPaiType;
        }

        public void setStartPaiType(MajiangPai startPaiType) {
            this.startPaiType = startPaiType;
        }
    }


    /**
     * 独立牌组牌型组合。就是给定一组独立牌牌的拆解，比如给了6个一万，拆解成2个一万刻子 或者 1个一万杠子+1个一万对子，这就是两个组合。
     */
    private static class DuliPaiGroupPaiXingCombination {

        private MajiangPai paiType;
        private int duiziAmount;
        private int keziAmount;
        private int gangziAmount;

        private DuliPaiGroupPaiXingCombination(MajiangPai paiType) {
            this.paiType = paiType;
        }


        public void addDuizi() {
            duiziAmount++;
        }

        public void addDuizi(int amount) {
            duiziAmount += amount;
        }


        public void addKezi() {
            keziAmount++;
        }

        public void addKezi(int amount) {
            keziAmount += amount;
        }

        public void addGangzi() {
            gangziAmount++;
        }

        public void addGangzi(int amount) {
            gangziAmount += amount;
        }

        public MajiangPai getPaiType() {
            return paiType;
        }

        public void setPaiType(MajiangPai paiType) {
            this.paiType = paiType;
        }

        public int getDuiziAmount() {
            return duiziAmount;
        }

        public int getKeziAmount() {
            return keziAmount;
        }

        public int getGangziAmount() {
            return gangziAmount;
        }

        public void setDuiziAmount(int duiziAmount) {
            this.duiziAmount = duiziAmount;
        }

        public void setKeziAmount(int keziAmount) {
            this.keziAmount = keziAmount;
        }

        public void setGangziAmount(int gangziAmount) {
            this.gangziAmount = gangziAmount;
        }

        public DuliPaiGroupPaiXingCombination copy() {
            DuliPaiGroupPaiXingCombination newCombination = new DuliPaiGroupPaiXingCombination(paiType);
            newCombination.setDuiziAmount(duiziAmount);
            newCombination.setKeziAmount(keziAmount);
            newCombination.setGangziAmount(gangziAmount);
            return newCombination;
        }
    }

    /**
     * 连续牌组牌型组合。就是给定一组连续牌牌的拆解，比如给了6万6万6万7万8万9万9万9万，拆解成 6万对子+678万顺子+9万刻子 或者 6万刻子+789万顺子+9万对子，这就是两个组合。
     */
    private static class LianxuPaiGroupPaiXingCombination {

        /**
         * key是顺子的起始牌类型，value是该种顺子个数
         */
        private Map<MajiangPai, Integer> shunziMap = new HashMap<>();

        private Map<MajiangPai, Integer> duiziMap = new HashMap<>();
        private Map<MajiangPai, Integer> keziMap = new HashMap<>();
        private Map<MajiangPai, Integer> gangziMap = new HashMap<>();

        public void addDuizi(MajiangPai paiType) {
            Integer amount = duiziMap.get(paiType);
            if (amount == null) {
                amount = 0;
            }
            amount++;
            duiziMap.put(paiType, amount);
        }

        public void addKezi(MajiangPai paiType) {
            Integer amount = keziMap.get(paiType);
            if (amount == null) {
                amount = 0;
            }
            amount++;
            keziMap.put(paiType, amount);
        }

        public void addGangzi(MajiangPai paiType) {
            Integer amount = gangziMap.get(paiType);
            if (amount == null) {
                amount = 0;
            }
            amount++;
            gangziMap.put(paiType, amount);
        }

        public void addShunzi(MajiangPai paiType) {
            Integer amount = shunziMap.get(paiType);
            if (amount == null) {
                amount = 0;
            }
            amount++;
            shunziMap.put(paiType, amount);
        }

        public boolean hasDuizi() {
            return !duiziMap.isEmpty();
        }

        public Map<MajiangPai, Integer> getShunziMap() {
            return shunziMap;
        }

        public void setShunziMap(Map<MajiangPai, Integer> shunziMap) {
            this.shunziMap = shunziMap;
        }

        public Map<MajiangPai, Integer> getDuiziMap() {
            return duiziMap;
        }

        public void setDuiziMap(Map<MajiangPai, Integer> duiziMap) {
            this.duiziMap = duiziMap;
        }

        public Map<MajiangPai, Integer> getKeziMap() {
            return keziMap;
        }

        public void setKeziMap(Map<MajiangPai, Integer> keziMap) {
            this.keziMap = keziMap;
        }

        public Map<MajiangPai, Integer> getGangziMap() {
            return gangziMap;
        }

        public void setGangziMap(Map<MajiangPai, Integer> gangziMap) {
            this.gangziMap = gangziMap;
        }

        public LianxuPaiGroupPaiXingCombination copy() {
            LianxuPaiGroupPaiXingCombination newCombination = new LianxuPaiGroupPaiXingCombination();
            newCombination.setShunziMap(new HashMap<>(shunziMap));
            newCombination.setDuiziMap(new HashMap<>(duiziMap));
            newCombination.setKeziMap(new HashMap<>(keziMap));
            newCombination.setGangziMap(new HashMap<>(gangziMap));
            return newCombination;
        }


    }

    private static class PaiXingCombination {

        /**
         * key是顺子的起始牌类型，value是该种顺子个数
         */
        private Map<MajiangPai, Integer> shunziMap = new HashMap<>();

        private Map<MajiangPai, Integer> duiziMap = new HashMap<>();
        private Map<MajiangPai, Integer> keziMap = new HashMap<>();
        private Map<MajiangPai, Integer> gangziMap = new HashMap<>();

        public void addDuizi(MajiangPai paiType) {
            Integer amount = duiziMap.get(paiType);
            if (amount == null) {
                amount = 0;
            }
            amount++;
            duiziMap.put(paiType, amount);
        }

        public void addKezi(MajiangPai paiType) {
            Integer amount = keziMap.get(paiType);
            if (amount == null) {
                amount = 0;
            }
            amount++;
            keziMap.put(paiType, amount);
        }

        public void addGangzi(MajiangPai paiType) {
            Integer amount = gangziMap.get(paiType);
            if (amount == null) {
                amount = 0;
            }
            amount++;
            gangziMap.put(paiType, amount);
        }

        public void addShunzi(MajiangPai paiType) {
            Integer amount = shunziMap.get(paiType);
            if (amount == null) {
                amount = 0;
            }
            amount++;
            shunziMap.put(paiType, amount);
        }

        public boolean hasDuizi() {
            return !duiziMap.isEmpty();
        }

        public Map<MajiangPai, Integer> getShunziMap() {
            return shunziMap;
        }

        public void setShunziMap(Map<MajiangPai, Integer> shunziMap) {
            this.shunziMap = shunziMap;
        }

        public Map<MajiangPai, Integer> getDuiziMap() {
            return duiziMap;
        }

        public void setDuiziMap(Map<MajiangPai, Integer> duiziMap) {
            this.duiziMap = duiziMap;
        }

        public Map<MajiangPai, Integer> getKeziMap() {
            return keziMap;
        }

        public void setKeziMap(Map<MajiangPai, Integer> keziMap) {
            this.keziMap = keziMap;
        }

        public Map<MajiangPai, Integer> getGangziMap() {
            return gangziMap;
        }

        public void setGangziMap(Map<MajiangPai, Integer> gangziMap) {
            this.gangziMap = gangziMap;
        }


    }

    /**
     * 牌型模式，用于连续牌组模式中牌型的抽象化表达，表达的还是对子，刻子，杠子，顺子这几种牌型
     */
    private interface PaiXingPattern {
        int addToCombination(LianxuPaiGroupPaiXingCombination combinationPattern, int[] amountArray);

        boolean canAddToCombination(LianxuPaiGroupPaiXingCombination combinationPattern, int[] amountArray);
    }

    private static class DuiziPaiXingPattern implements PaiXingPattern {
        private int indexInAmountArray;

        public DuiziPaiXingPattern(int indexInAmountArray) {
            this.indexInAmountArray = indexInAmountArray;
        }

        public int getIndexInAmountArray() {
            return indexInAmountArray;
        }

        public void setIndexInAmountArray(int indexInAmountArray) {
            this.indexInAmountArray = indexInAmountArray;
        }

        @Override
        public int addToCombination(LianxuPaiGroupPaiXingCombination combinationPattern, int[] amountArray) {
            if (combinationPattern.hasDuizi()) {
                return 0;
            }
            int amount = amountArray[indexInAmountArray];
            if (amount < 2) {
                return 0;
            }
            combinationPattern.addDuizi(MajiangPai.valueOf(indexInAmountArray));
            amountArray[indexInAmountArray] -= 2;
            return 2;
        }

        @Override
        public boolean canAddToCombination(LianxuPaiGroupPaiXingCombination combinationPattern, int[] amountArray) {
            if (combinationPattern.hasDuizi()) {
                return false;
            }
            int amount = amountArray[indexInAmountArray];
            if (amount < 2) {
                return false;
            }
            return true;
        }
    }

    private static class KeziPaiXingPattern implements PaiXingPattern {
        private int indexInAmountArray;

        public KeziPaiXingPattern(int indexInAmountArray) {
            this.indexInAmountArray = indexInAmountArray;
        }

        public int getIndexInAmountArray() {
            return indexInAmountArray;
        }

        public void setIndexInAmountArray(int indexInAmountArray) {
            this.indexInAmountArray = indexInAmountArray;
        }

        @Override
        public int addToCombination(LianxuPaiGroupPaiXingCombination combinationPattern, int[] amountArray) {
            int amount = amountArray[indexInAmountArray];
            if (amount < 3) {
                return 0;
            }
            combinationPattern.addKezi(MajiangPai.valueOf(indexInAmountArray));
            amountArray[indexInAmountArray] -= 3;
            return 3;
        }

        @Override
        public boolean canAddToCombination(LianxuPaiGroupPaiXingCombination combinationPattern, int[] amountArray) {
            int amount = amountArray[indexInAmountArray];
            if (amount < 3) {
                return false;
            }
            return true;
        }
    }

    private static class GangziPaiXingPattern implements PaiXingPattern {
        private int indexInAmountArray;

        public GangziPaiXingPattern(int indexInAmountArray) {
            this.indexInAmountArray = indexInAmountArray;
        }

        public int getIndexInAmountArray() {
            return indexInAmountArray;
        }

        public void setIndexInAmountArray(int indexInAmountArray) {
            this.indexInAmountArray = indexInAmountArray;
        }

        @Override
        public int addToCombination(LianxuPaiGroupPaiXingCombination combinationPattern, int[] amountArray) {
            int amount = amountArray[indexInAmountArray];
            if (amount < 4) {
                return 0;
            }
            combinationPattern.addGangzi(MajiangPai.valueOf(indexInAmountArray));
            amountArray[indexInAmountArray] -= 4;
            return 4;
        }

        @Override
        public boolean canAddToCombination(LianxuPaiGroupPaiXingCombination combinationPattern, int[] amountArray) {
            int amount = amountArray[indexInAmountArray];
            if (amount < 4) {
                return false;
            }
            return true;
        }
    }

    private static class ShunziPaiXingPattern implements PaiXingPattern {
        /**
         * 顺子中的起始牌的下标
         */
        private int indexInAmountArray;

        public ShunziPaiXingPattern(int indexInAmountArray) {
            this.indexInAmountArray = indexInAmountArray;
        }

        public int getIndexInAmountArray() {
            return indexInAmountArray;
        }

        public void setIndexInAmountArray(int indexInAmountArray) {
            this.indexInAmountArray = indexInAmountArray;
        }

        @Override
        public int addToCombination(LianxuPaiGroupPaiXingCombination combinationPattern, int[] amountArray) {
            int amount1 = amountArray[indexInAmountArray];
            int amount2 = amountArray[indexInAmountArray + 1];
            int amount3 = amountArray[indexInAmountArray + 2];
            if (amount1 < 1 || amount2 < 1 || amount3 < 1) {
                return 0;
            }
            combinationPattern.addShunzi(MajiangPai.valueOf(indexInAmountArray));
            amountArray[indexInAmountArray] -= 1;
            amountArray[indexInAmountArray + 1] -= 1;
            amountArray[indexInAmountArray + 2] -= 1;
            return 3;
        }

        @Override
        public boolean canAddToCombination(LianxuPaiGroupPaiXingCombination combinationPattern, int[] amountArray) {
            int amount1 = amountArray[indexInAmountArray];
            int amount2 = amountArray[indexInAmountArray + 1];
            int amount3 = amountArray[indexInAmountArray + 2];
            if (amount1 < 1 || amount2 < 1 || amount3 < 1) {
                return false;
            }
            return true;
        }
    }
}

