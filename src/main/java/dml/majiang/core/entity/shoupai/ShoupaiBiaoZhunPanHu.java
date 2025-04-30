package dml.majiang.core.entity.shoupai;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.fenzu.Duizi;
import dml.majiang.core.entity.fenzu.Gangzi;
import dml.majiang.core.entity.fenzu.Kezi;
import dml.majiang.core.entity.fenzu.Shunzi;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 标准的判胡算法，既，4面子（或多于4面子）+1将子
 */
public class ShoupaiBiaoZhunPanHu {

    private static Map<Integer, List<DuliPaiGroupPaiXingCombination>> duliPaiGroupPaiXingCombinationsPatternCache = new ConcurrentHashMap<>();
    private static Map<String, List<LianxuPaiGroupPaiXingCombination>> lianxuPaiGroupPaiXingCombinationsPatternCache = new ConcurrentHashMap<>();

    public static List<ShoupaiPaiXing> getAllHuPaiShoupaiPaiXing(List<Pai> inputShoupaiList) {
        //把shoupaiList按照牌的类型分组
        //先复制输入list
        List<Pai> shoupaiList = new ArrayList<>(inputShoupaiList.size());
        for (Pai pai : inputShoupaiList) {
            shoupaiList.add(new Pai(pai.getId(), pai.getPaiType()));
        }
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
        parseXushuPaiAmountGroup(paiAmounts, duliPaiAmountGroups, lianxuPaiAmountGroups, MajiangPai.yiwan, MajiangPai.jiuwan);
        parseXushuPaiAmountGroup(paiAmounts, duliPaiAmountGroups, lianxuPaiAmountGroups, MajiangPai.yitong, MajiangPai.jiutong);
        parseXushuPaiAmountGroup(paiAmounts, duliPaiAmountGroups, lianxuPaiAmountGroups, MajiangPai.yitiao, MajiangPai.jiutiao);
        for (int i = MajiangPai.dongfeng.ordinal(); i < MajiangPai.count; i++) {
            int amount = paiAmounts[i];
            if (amount == 0) {
                continue;
            }
            duliPaiAmountGroups.add(new DuliPaiAmountGroup(amount, MajiangPai.valueOf(i)));
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
            generateLianxuPaiGroupPaiXingCombinations(amountArray, startPaiType, lianxuPaiGroupPaiXingCombinationList);
            //对应的LianxuPaiAmountGroup如果没有生成任何牌型组合，说明这个牌组不能组合出任何一种合法胡的一部分（比如必定有单牌或者必定有两个对子等），总体结论是不能胡
            if (lianxuPaiGroupPaiXingCombinationList.isEmpty()) {
                return null;
            }
            lianxuPaiGroupPaiXingCombinations.add(lianxuPaiGroupPaiXingCombinationList);
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
            if (paiXingCombinationsForDuli.isEmpty()) {
                //独立牌组组合没有合法的胡牌方式
                return null;
            }
        }

        //计算连续牌组能形成的所有牌型组合
        List<PaiXingCombination> paiXingCombinationsForLianxu = new ArrayList<>();
        if (!lianxuPaiGroupPaiXingCombinations.isEmpty()) {
            int totalpaiXingCombinationsForLianxu = 1;
            int[] modArrayForLianxu = new int[lianxuPaiGroupPaiXingCombinations.size()];
            for (int i = 0; i < modArrayForLianxu.length; i++) {
                if (i == 0) {
                    modArrayForLianxu[i] = 1;
                } else {
                    modArrayForLianxu[i] = totalpaiXingCombinationsForLianxu;
                }
                int lianxuPaiGroupPaiXingCombinationCount = lianxuPaiGroupPaiXingCombinations.get(i).size();
                totalpaiXingCombinationsForLianxu *= lianxuPaiGroupPaiXingCombinationCount;
            }
            for (int combinationCode = 0; combinationCode < totalpaiXingCombinationsForLianxu; combinationCode++) {
                PaiXingCombination paiXingCombination = new PaiXingCombination();
                //反向遍历modArrayForLianxu
                int subCode = combinationCode;
                for (int i = modArrayForLianxu.length - 1; i >= 0; i--) {
                    int mod = modArrayForLianxu[i];
                    int combinationsIndex = subCode / mod;
                    subCode = subCode % mod;
                    LianxuPaiGroupPaiXingCombination combination = lianxuPaiGroupPaiXingCombinations.get(i).get(combinationsIndex);
                    paiXingCombination.acceptLianxuPaiGroupPaiXingCombination(combination);
                }
                if (paiXingCombination.countDuizi() > 1) {
                    //对子数量超过1个，不能胡
                    continue;
                }
                paiXingCombinationsForLianxu.add(paiXingCombination);
            }
            if (paiXingCombinationsForLianxu.isEmpty()) {
                //连续牌组组合没有合法的胡牌方式
                return null;
            }
        }
        //组合独立牌组和连续牌组的所有牌型组合
        List<PaiXingCombination> paiXingCombinations;
        if (paiXingCombinationsForDuli.isEmpty() && paiXingCombinationsForLianxu.isEmpty()) {
            return null;
        } else if (paiXingCombinationsForDuli.isEmpty()) {
            paiXingCombinations = paiXingCombinationsForLianxu;
        } else if (paiXingCombinationsForLianxu.isEmpty()) {
            paiXingCombinations = paiXingCombinationsForDuli;
        } else {
            paiXingCombinations = new ArrayList<>();
            for (PaiXingCombination duliCombination : paiXingCombinationsForDuli) {
                for (PaiXingCombination lianxuCombination : paiXingCombinationsForLianxu) {
                    PaiXingCombination combination = duliCombination.copy();
                    combination.combine(lianxuCombination);
                    if (combination.countDuizi() > 1) {
                        //对子数量超过1个，不能胡
                        continue;
                    }
                    paiXingCombinations.add(combination);
                }
            }
        }

        //牌型组合生成手牌型
        List<ShoupaiPaiXing> shoupaiPaiXingList = new ArrayList<>();
        for (PaiXingCombination paiXingCombination : paiXingCombinations) {
            Map<MajiangPai, List<Pai>> paiTypeListMap = new HashMap<>();
            for (Map.Entry<MajiangPai, Set<Pai>> entry : paiTypeMap.entrySet()) {
                MajiangPai paiType = entry.getKey();
                Set<Pai> paiSet = entry.getValue();
                List<Pai> paiList = new ArrayList<>(paiSet);
                paiTypeListMap.put(paiType, paiList);
            }
            ShoupaiPaiXing shoupaiPaiXing = new ShoupaiPaiXing();
            Map<MajiangPai, Integer> shunziMap = paiXingCombination.getShunziMap();
            for (Map.Entry<MajiangPai, Integer> entry : shunziMap.entrySet()) {
                MajiangPai paiType = entry.getKey();
                int amount = entry.getValue();
                for (int i = 0; i < amount; i++) {
                    List<Pai> paiList1 = paiTypeListMap.get(paiType);
                    Pai pai1 = paiList1.remove(0);
                    List<Pai> paiList2 = paiTypeListMap.get(MajiangPai.valueOf(paiType.ordinal() + 1));
                    Pai pai2 = paiList2.remove(0);
                    List<Pai> paiList3 = paiTypeListMap.get(MajiangPai.valueOf(paiType.ordinal() + 2));
                    Pai pai3 = paiList3.remove(0);
                    shoupaiPaiXing.addShunzi(new Shunzi(pai1, pai2, pai3));
                }
            }
            Map<MajiangPai, Integer> duiziMap = paiXingCombination.getDuiziMap();
            for (Map.Entry<MajiangPai, Integer> entry : duiziMap.entrySet()) {
                MajiangPai paiType = entry.getKey();
                int amount = entry.getValue();
                for (int i = 0; i < amount; i++) {
                    List<Pai> paiList = paiTypeListMap.get(paiType);
                    Pai pai1 = paiList.remove(0);
                    Pai pai2 = paiList.remove(0);
                    shoupaiPaiXing.addDuizi(new Duizi(pai1, pai2));
                }
            }
            Map<MajiangPai, Integer> keziMap = paiXingCombination.getKeziMap();
            for (Map.Entry<MajiangPai, Integer> entry : keziMap.entrySet()) {
                MajiangPai paiType = entry.getKey();
                int amount = entry.getValue();
                for (int i = 0; i < amount; i++) {
                    List<Pai> paiList = paiTypeListMap.get(paiType);
                    Pai pai1 = paiList.remove(0);
                    Pai pai2 = paiList.remove(0);
                    Pai pai3 = paiList.remove(0);
                    shoupaiPaiXing.addKezi(new Kezi(pai1, pai2, pai3));
                }
            }
            Map<MajiangPai, Integer> gangziMap = paiXingCombination.getGangziMap();
            for (Map.Entry<MajiangPai, Integer> entry : gangziMap.entrySet()) {
                MajiangPai paiType = entry.getKey();
                int amount = entry.getValue();
                for (int i = 0; i < amount; i++) {
                    List<Pai> paiList = paiTypeListMap.get(paiType);
                    Pai pai1 = paiList.remove(0);
                    Pai pai2 = paiList.remove(0);
                    Pai pai3 = paiList.remove(0);
                    Pai pai4 = paiList.remove(0);
                    shoupaiPaiXing.addGangzi(new Gangzi(pai1, pai2, pai3, pai4));
                }
            }
            shoupaiPaiXingList.add(shoupaiPaiXing);
        }
        return shoupaiPaiXingList.isEmpty() ? null : shoupaiPaiXingList;
    }

    private static void parseXushuPaiAmountGroup(int[] paiAmounts,
                                                 List<DuliPaiAmountGroup> duliPaiAmountGroups,
                                                 List<LianxuPaiAmountGroup> lianxuPaiAmountGroups,
                                                 MajiangPai startXushuPai, MajiangPai endXushuPai) {
        int lianxuCount = 0;
        for (int paiTypeOrdinal = startXushuPai.ordinal(); paiTypeOrdinal <= (endXushuPai.ordinal() + 1); paiTypeOrdinal++) {
            int amount = 0;
            if (paiTypeOrdinal < endXushuPai.ordinal() + 1) {
                amount = paiAmounts[paiTypeOrdinal];
            }
            if (amount == 0) {
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
        //可以直接掠过当前的PaiXingPattern（既不选），选下一个
        if (paiXingPatternsIndex < paiXingPatterns.size() - 1) {
            //递推下一个PaiXingPattern
            int nextPaiXingPatternsIndex = paiXingPatternsIndex + 1;
            int[] newAmountArray = Arrays.copyOf(amountArray, amountArray.length);
            LianxuPaiGroupPaiXingCombination newCombinationPattern = combinationPattern.copy();
            caculateLianxuPaiGroupPaiXingCombinationsPattern(newAmountArray, totalAmountLeft,
                    paiXingPatterns, nextPaiXingPatternsIndex,
                    newCombinationPattern, combinationPatternStore);
        }

        PaiXingPattern paiXingPattern = paiXingPatterns.get(paiXingPatternsIndex);
        int amountTaken = paiXingPattern.addToCombination(combinationPattern, amountArray);
        //如果 amountTaken==0 就是paiXingPattern没干成，等同于不选跳下一个。那前面已经有了，这里直接返回
        if (amountTaken == 0) {
            return;
        }
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
        }
        if (paiXingPatternsIndex < paiXingPatterns.size() - 1) {
            //递推下一个PaiXingPattern
            int nextPaiXingPatternsIndex = paiXingPatternsIndex + 1;
            int[] newAmountArray = Arrays.copyOf(amountArray, amountArray.length);
            LianxuPaiGroupPaiXingCombination newCombinationPattern = combinationPattern.copy();
            caculateLianxuPaiGroupPaiXingCombinationsPattern(newAmountArray, totalAmountLeft,
                    paiXingPatterns, nextPaiXingPatternsIndex,
                    newCombinationPattern, combinationPatternStore);
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


        public void acceptDuliPaiGroupPaiXingCombination(DuliPaiGroupPaiXingCombination combination) {
            Integer duiziAmount = duiziMap.get(combination.getPaiType());
            if (duiziAmount == null) {
                duiziAmount = 0;
            }
            duiziAmount += combination.getDuiziAmount();
            duiziMap.put(combination.getPaiType(), duiziAmount);

            Integer keziAmount = keziMap.get(combination.getPaiType());
            if (keziAmount == null) {
                keziAmount = 0;
            }
            keziAmount += combination.getKeziAmount();
            keziMap.put(combination.getPaiType(), keziAmount);

            Integer gangziAmount = gangziMap.get(combination.getPaiType());
            if (gangziAmount == null) {
                gangziAmount = 0;
            }
            gangziAmount += combination.getGangziAmount();
            gangziMap.put(combination.getPaiType(), gangziAmount);
        }

        public int countDuizi() {
            int count = 0;
            for (Map.Entry<MajiangPai, Integer> entry : duiziMap.entrySet()) {
                count += entry.getValue();
            }
            return count;
        }

        public void acceptLianxuPaiGroupPaiXingCombination(LianxuPaiGroupPaiXingCombination combination) {
            Map<MajiangPai, Integer> shunziMapInCombination = combination.getShunziMap();
            for (Map.Entry<MajiangPai, Integer> entry : shunziMapInCombination.entrySet()) {
                MajiangPai paiType = entry.getKey();
                Integer amount = shunziMap.get(paiType);
                if (amount == null) {
                    amount = 0;
                }
                amount += entry.getValue();
                shunziMap.put(paiType, amount);
            }
            Map<MajiangPai, Integer> duiziMapInCombination = combination.getDuiziMap();
            for (Map.Entry<MajiangPai, Integer> entry : duiziMapInCombination.entrySet()) {
                MajiangPai paiType = entry.getKey();
                Integer amount = duiziMap.get(paiType);
                if (amount == null) {
                    amount = 0;
                }
                amount += entry.getValue();
                duiziMap.put(paiType, amount);
            }
            Map<MajiangPai, Integer> keziMapInCombination = combination.getKeziMap();
            for (Map.Entry<MajiangPai, Integer> entry : keziMapInCombination.entrySet()) {
                MajiangPai paiType = entry.getKey();
                Integer amount = keziMap.get(paiType);
                if (amount == null) {
                    amount = 0;
                }
                amount += entry.getValue();
                keziMap.put(paiType, amount);
            }
            Map<MajiangPai, Integer> gangziMapInCombination = combination.getGangziMap();
            for (Map.Entry<MajiangPai, Integer> entry : gangziMapInCombination.entrySet()) {
                MajiangPai paiType = entry.getKey();
                Integer amount = gangziMap.get(paiType);
                if (amount == null) {
                    amount = 0;
                }
                amount += entry.getValue();
                gangziMap.put(paiType, amount);
            }
        }

        public void combine(PaiXingCombination combination) {
            Map<MajiangPai, Integer> shunziMapInCombination = combination.getShunziMap();
            for (Map.Entry<MajiangPai, Integer> entry : shunziMapInCombination.entrySet()) {
                MajiangPai paiType = entry.getKey();
                Integer amount = shunziMap.get(paiType);
                if (amount == null) {
                    amount = 0;
                }
                amount += entry.getValue();
                shunziMap.put(paiType, amount);
            }
            Map<MajiangPai, Integer> duiziMapInCombination = combination.getDuiziMap();
            for (Map.Entry<MajiangPai, Integer> entry : duiziMapInCombination.entrySet()) {
                MajiangPai paiType = entry.getKey();
                Integer amount = duiziMap.get(paiType);
                if (amount == null) {
                    amount = 0;
                }
                amount += entry.getValue();
                duiziMap.put(paiType, amount);
            }
            Map<MajiangPai, Integer> keziMapInCombination = combination.getKeziMap();
            for (Map.Entry<MajiangPai, Integer> entry : keziMapInCombination.entrySet()) {
                MajiangPai paiType = entry.getKey();
                Integer amount = keziMap.get(paiType);
                if (amount == null) {
                    amount = 0;
                }
                amount += entry.getValue();
                keziMap.put(paiType, amount);
            }
            Map<MajiangPai, Integer> gangziMapInCombination = combination.getGangziMap();
            for (Map.Entry<MajiangPai, Integer> entry : gangziMapInCombination.entrySet()) {
                MajiangPai paiType = entry.getKey();
                Integer amount = gangziMap.get(paiType);
                if (amount == null) {
                    amount = 0;
                }
                amount += entry.getValue();
                gangziMap.put(paiType, amount);
            }
        }

        public PaiXingCombination copy() {
            PaiXingCombination newCombination = new PaiXingCombination();
            newCombination.setShunziMap(new HashMap<>(shunziMap));
            newCombination.setDuiziMap(new HashMap<>(duiziMap));
            newCombination.setKeziMap(new HashMap<>(keziMap));
            newCombination.setGangziMap(new HashMap<>(gangziMap));
            return newCombination;
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

