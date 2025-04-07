package dml.majiang.core.entity.shoupai;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;

import java.util.*;

/**
 * 标准的判胡算法，既，4面子（或多于4面子）+1将子
 */
public class ShoupaiBiaoZhunPanHu {

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
            generateDuliPaiGroupPaiXingCombinations(null, 2, amount, paiType, duliPaiGroupPaiXingCombinationList);
        }

        //计算每个连续牌组的所有牌型组合
        List<List<LianxuPaiGroupPaiXingCombination>> lianxuPaiGroupPaiXingCombinations = new ArrayList<>();
        for (LianxuPaiAmountGroup amountGroup : lianxuPaiAmountGroups) {
            int[] amountArray = amountGroup.getAmountArray();
            MajiangPai startPaiType = amountGroup.getStartPaiType();
            List<LianxuPaiGroupPaiXingCombination> lianxuPaiGroupPaiXingCombinationList = new ArrayList();
            lianxuPaiGroupPaiXingCombinations.add(lianxuPaiGroupPaiXingCombinationList);
            generateLianxuPaiGroupPaiXingCombinations(new LianxuPaiGroupPaiXingCombination(), 0, 1,
                    amountArray, startPaiType, lianxuPaiGroupPaiXingCombinationList);
        }
    }

    private static void generateLianxuPaiGroupPaiXingCombinations(LianxuPaiGroupPaiXingCombination combination,
                                                                  int checkIndex, int minAmount,
                                                                  int[] amountArray, MajiangPai startPaiType,
                                                                  List<LianxuPaiGroupPaiXingCombination> combinationStore) {
        //minAmount为1，代表要取顺子
        if (minAmount == 1) {
            //可以组成顺子
            if (amountArray[checkIndex] > 0 && amountArray[checkIndex + 1] > 0 && amountArray[checkIndex + 2] > 0) {
                combination.addShunzi(MajiangPai.valueOf(startPaiType.ordinal() + checkIndex));
                //取完顺子后，数量减1
                amountArray[checkIndex]--;
                amountArray[checkIndex + 1]--;
                amountArray[checkIndex + 2]--;
                //取完顺子后正好没有剩余牌了，成功记录一个组合
                if (amountArray[checkIndex] == 0 && amountArray[checkIndex + 1] == 0 && amountArray[checkIndex + 2] == 0) {
                    combinationStore.add(combination);
                } else {
                    //继续递推
                    LianxuPaiGroupPaiXingCombination newCombination = new LianxuPaiGroupPaiXingCombination();
                    newCombination.getDuiziMap().putAll(combination.getDuiziMap());
                    newCombination.getKeziMap().putAll(combination.getKeziMap());
                    newCombination.getGangziMap().putAll(combination.getGangziMap());
                    newCombination.getShunziMap().putAll(combination.getShunziMap());
                    int[] newAmountArray = new int[amountArray.length];
                    System.arraycopy(amountArray, 0, newAmountArray, 0, amountArray.length);
                    generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex, minAmount, newAmountArray, startPaiType, combinationStore);
                }
            } else {//不能组成顺子
                int amount = amountArray[checkIndex];
                if (amount == 0) {
                    //递推
                    LianxuPaiGroupPaiXingCombination newCombination = new LianxuPaiGroupPaiXingCombination();
                    newCombination.getDuiziMap().putAll(combination.getDuiziMap());
                    newCombination.getKeziMap().putAll(combination.getKeziMap());
                    newCombination.getGangziMap().putAll(combination.getGangziMap());
                    newCombination.getShunziMap().putAll(combination.getShunziMap());
                    int[] newAmountArray = new int[amountArray.length];
                    System.arraycopy(amountArray, 0, newAmountArray, 0, amountArray.length);
                    if (amountArray.length - (checkIndex + 1) >= 3) {
                        generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 1, newAmountArray, startPaiType, combinationStore);
                    } else {
                        generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 2, newAmountArray, startPaiType, combinationStore);
                    }
                } else if (amount == 1) {
                    //如果是单牌，放弃该组合
                    return;
                } else if (amount == 2) {
                    if (combination.hasDuizi()) {
                        //已经有对子了，放弃该组合
                        return;
                    } else {
                        //取对子
                        combination.addDuizi(MajiangPai.valueOf(startPaiType.ordinal() + checkIndex));
                        //取完对子后，清空数量
                        amountArray[checkIndex] = 0;
                        //继续递推
                        LianxuPaiGroupPaiXingCombination newCombination = new LianxuPaiGroupPaiXingCombination();
                        newCombination.getDuiziMap().putAll(combination.getDuiziMap());
                        newCombination.getKeziMap().putAll(combination.getKeziMap());
                        newCombination.getGangziMap().putAll(combination.getGangziMap());
                        newCombination.getShunziMap().putAll(combination.getShunziMap());
                        int[] newAmountArray = new int[amountArray.length];
                        System.arraycopy(amountArray, 0, newAmountArray, 0, amountArray.length);
                        if (amountArray.length - (checkIndex + 1) >= 3) {
                            generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 1, newAmountArray, startPaiType, combinationStore);
                        } else {
                            generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 2, newAmountArray, startPaiType, combinationStore);
                        }
                    }
                } else if (amount == 3) {
                    //取刻子
                    combination.addKezi(MajiangPai.valueOf(startPaiType.ordinal() + checkIndex));
                    //取完刻子后，清空数量
                    amountArray[checkIndex] = 0;
                    //继续递推
                    LianxuPaiGroupPaiXingCombination newCombination = new LianxuPaiGroupPaiXingCombination();
                    newCombination.getDuiziMap().putAll(combination.getDuiziMap());
                    newCombination.getKeziMap().putAll(combination.getKeziMap());
                    newCombination.getGangziMap().putAll(combination.getGangziMap());
                    newCombination.getShunziMap().putAll(combination.getShunziMap());
                    int[] newAmountArray = new int[amountArray.length];
                    System.arraycopy(amountArray, 0, newAmountArray, 0, amountArray.length);
                    if (amountArray.length - (checkIndex + 1) >= 3) {
                        generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 1, newAmountArray, startPaiType, combinationStore);
                    } else {
                        generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 2, newAmountArray, startPaiType, combinationStore);
                    }
                } else if (amount == 4) {
                    //取杠子
                    combination.addGangzi(MajiangPai.valueOf(startPaiType.ordinal() + checkIndex));
                    //取完杠子后，清空数量
                    amountArray[checkIndex] = 0;
                    //继续递推
                    LianxuPaiGroupPaiXingCombination newCombination = new LianxuPaiGroupPaiXingCombination();
                    newCombination.getDuiziMap().putAll(combination.getDuiziMap());
                    newCombination.getKeziMap().putAll(combination.getKeziMap());
                    newCombination.getGangziMap().putAll(combination.getGangziMap());
                    newCombination.getShunziMap().putAll(combination.getShunziMap());
                    int[] newAmountArray = new int[amountArray.length];
                    System.arraycopy(amountArray, 0, newAmountArray, 0, amountArray.length);
                    if (amountArray.length - (checkIndex + 1) >= 3) {
                        generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 1, newAmountArray, startPaiType, combinationStore);
                    } else {
                        generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 2, newAmountArray, startPaiType, combinationStore);
                    }
                } else {
                    //继续递推,从尝试吃掉对子开始
                    LianxuPaiGroupPaiXingCombination newCombination = new LianxuPaiGroupPaiXingCombination();
                    newCombination.getDuiziMap().putAll(combination.getDuiziMap());
                    newCombination.getKeziMap().putAll(combination.getKeziMap());
                    newCombination.getGangziMap().putAll(combination.getGangziMap());
                    newCombination.getShunziMap().putAll(combination.getShunziMap());
                    int[] newAmountArray = new int[amountArray.length];
                    System.arraycopy(amountArray, 0, newAmountArray, 0, amountArray.length);
                    generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex, 2, newAmountArray, startPaiType, combinationStore);
                }
            }
        } else if (minAmount == 2) {//minAmount为2,代表要取对子
            int amount = amountArray[checkIndex];
            if (amount == 0) {
                //到底了，保存组合
                if (checkIndex == amountArray.length - 1) {
                    //成功记录一个组合
                    combinationStore.add(combination);
                } else {
                    //继续递推
                    LianxuPaiGroupPaiXingCombination newCombination = new LianxuPaiGroupPaiXingCombination();
                    newCombination.getDuiziMap().putAll(combination.getDuiziMap());
                    newCombination.getKeziMap().putAll(combination.getKeziMap());
                    newCombination.getGangziMap().putAll(combination.getGangziMap());
                    newCombination.getShunziMap().putAll(combination.getShunziMap());
                    int[] newAmountArray = new int[amountArray.length];
                    System.arraycopy(amountArray, 0, newAmountArray, 0, amountArray.length);
                    if (amountArray.length - (checkIndex + 1) >= 3) {
                        generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 1, newAmountArray, startPaiType, combinationStore);
                    } else {
                        generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 2, newAmountArray, startPaiType, combinationStore);
                    }
                }
            } else if (amount == 1) {
                //单牌不能胡，放弃该组合
                return;
            } else if (amount == 2) {
                //取对子
                combination.addDuizi(MajiangPai.valueOf(startPaiType.ordinal() + checkIndex));
                //取完对子后，清空数量
                amountArray[checkIndex] = 0;
                //继续递推
                LianxuPaiGroupPaiXingCombination newCombination = new LianxuPaiGroupPaiXingCombination();
                newCombination.getDuiziMap().putAll(combination.getDuiziMap());
                newCombination.getKeziMap().putAll(combination.getKeziMap());
                newCombination.getGangziMap().putAll(combination.getGangziMap());
                newCombination.getShunziMap().putAll(combination.getShunziMap());
                int[] newAmountArray = new int[amountArray.length];
                System.arraycopy(amountArray, 0, newAmountArray, 0, amountArray.length);
                if (amountArray.length - (checkIndex + 1) >= 3) {
                    generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 1, newAmountArray, startPaiType, combinationStore);
                } else {
                    generateLianxuPaiGroupPaiXingCombinations(newCombination, checkIndex + 1, 2, newAmountArray, startPaiType, combinationStore);
                }
            } else if (amount == 3) {
                //取完对子就剩单牌了，放弃该组合
                return;
            } else if (amount == 4) {
                //取完对子又是对子，放弃该组合
                return;
            } else {
                //取对子，，直到杠子
            }
        }
    }

    private static void generateDuliPaiGroupPaiXingCombinations(DuliPaiGroupPaiXingCombination combination, int minAmount, int amount, MajiangPai paiType,
                                                                List<DuliPaiGroupPaiXingCombination> combinationStore) {
        if (combination != null) {
            if (amount == 1) {
                //单牌不能胡，放弃该组合
                return;
            } else if (amount == 2) {
                //combination不为null，说明至少已经有对子了，combination是从对子开始的嘛。
                //胡牌只能有一个对子，放弃该组合
                return;
            } else if (amount == 3) {
                combination.addKezi();
                //成功记录一个组合
                combinationStore.add(combination);
                return;
            } else if (amount == 4) {
                combination.addGangzi();
                //成功记录一个组合
                combinationStore.add(combination);
                return;
            } else {
                if (minAmount == 3) {
                    combination.addKezi();
                } else if (minAmount == 4) {
                    combination.addGangzi();
                }
                amount -= minAmount;
                //开始递推，从尝试吃掉同样的minAmount的牌开始直到最多吃掉4张牌也就是杠子
                for (int i = minAmount; i <= 4; i++) {
                    if ((amount - i) < 0) {
                        break;
                    }
                    DuliPaiGroupPaiXingCombination newCombination = new DuliPaiGroupPaiXingCombination(paiType);
                    newCombination.setDuiziAmount(combination.getDuiziAmount());
                    newCombination.setKeziAmount(combination.getKeziAmount());
                    newCombination.setGangziAmount(combination.getGangziAmount());
                    generateDuliPaiGroupPaiXingCombinations(newCombination, i, amount, paiType, combinationStore);
                }
            }
        } else {
            //刚开始，从对子开始
            DuliPaiGroupPaiXingCombination initCombination = new DuliPaiGroupPaiXingCombination(paiType);
            initCombination.addDuizi();
            amount -= minAmount;
            if (amount == 0) {
                //成功记录一个组合
                combinationStore.add(initCombination);
                return;
            } else {
                //开始递推，对子只能有一副所以从尝试吃掉刻子（minAmount+1）开始直到最多吃掉4张牌也就是杠子
                for (int i = (minAmount + 1); i <= 4; i++) {
                    if ((amount - i) < 0) {
                        break;
                    }
                    DuliPaiGroupPaiXingCombination newCombination = new DuliPaiGroupPaiXingCombination(paiType);
                    newCombination.setDuiziAmount(initCombination.getDuiziAmount());
                    newCombination.setKeziAmount(initCombination.getKeziAmount());
                    newCombination.setGangziAmount(initCombination.getGangziAmount());
                    generateDuliPaiGroupPaiXingCombinations(newCombination, i, amount, paiType, combinationStore);
                }
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


        public void addDuizi(int amount) {
            duiziAmount += amount;
        }

        public void addDuizi() {
            addDuizi(1);
        }

        public void addKezi(int amount) {
            keziAmount += amount;
        }

        public void addKezi() {
            addKezi(1);
        }

        public void addGangzi(int amount) {
            gangziAmount += amount;
        }

        public void addGangzi() {
            addGangzi(1);
        }

        public MajiangPai getPaiType() {
            return paiType;
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
}

