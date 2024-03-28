package dml.majiang.core.entity.shoupai;


import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.fenzu.Shunzi;
import dml.majiang.core.entity.shoupai.gouxing.*;

import java.util.*;

/**
 * 手牌计算器
 *
 * @author Neo
 */
public class ShoupaiCalculator {

    /**
     * 0-8万,9-17筒,18-26条,27东风,28南风,29西风,30北风,31红中,32发财,33白板
     */
    private int[] paiQuantityArray = new int[34];

    public List<PaiXing> calculateAllPaiXingFromGouXing(GouXing gouXing) {
        List<JutihuaLianXuPaiZu> jutihuaLianXuPaiZuList = new ArrayList<>();
        List<Integer> duLiPaiIdxList = new ArrayList<>();
        parseXuShuPai(0, 8, jutihuaLianXuPaiZuList, duLiPaiIdxList);
        parseXuShuPai(9, 17, jutihuaLianXuPaiZuList, duLiPaiIdxList);
        parseXuShuPai(18, 26, jutihuaLianXuPaiZuList, duLiPaiIdxList);
        parseZiPai(duLiPaiIdxList);
        jutihuaLianXuPaiZuList.forEach((jutiZu) -> jutiZu.getLianXuPaiZu().calculateCode());
        Collections.sort(jutihuaLianXuPaiZuList);
        List<MajiangPai[]> jutiLianXuPaiTypesArrayList = new ArrayList<>();
        jutihuaLianXuPaiZuList.forEach((jutihuaLianXuPaiZu) -> jutiLianXuPaiTypesArrayList
                .add(jutihuaLianXuPaiZu.getJutiLianXuPaiTypesArray()));
        return gouXing.calculateAllPaiXing(paiQuantityArray, jutiLianXuPaiTypesArrayList, duLiPaiIdxList);
    }

    public List<GouXing> calculateAllGouXing() {
        List<GouXing> result = new ArrayList<>();
        List<JutihuaLianXuPaiZu> jutihuaLianXuPaiZuList = new ArrayList<>();
        List<Integer> duLiPaiIdxList = new ArrayList<>();
        parseXuShuPai(0, 8, jutihuaLianXuPaiZuList, duLiPaiIdxList);
        parseXuShuPai(9, 17, jutihuaLianXuPaiZuList, duLiPaiIdxList);
        parseXuShuPai(18, 26, jutihuaLianXuPaiZuList, duLiPaiIdxList);
        parseZiPai(duLiPaiIdxList);
        List<LianXuPaiZu> lianXuPaiZuList = new ArrayList<>();
        jutihuaLianXuPaiZuList.forEach((jutihuaLianXuPaiZu) -> {
            LianXuPaiZu lianXuPaiZu = jutihuaLianXuPaiZu.getLianXuPaiZu();
            lianXuPaiZu.calculateCode();
            lianXuPaiZuList.add(lianXuPaiZu);
        });
        Collections.sort(lianXuPaiZuList);

        // 开始分情况查询构型
        boolean hasLianXuPaiZu = !lianXuPaiZuList.isEmpty();
        boolean hasDuLiPaiZu = !duLiPaiIdxList.isEmpty();
        if (hasLianXuPaiZu && !hasDuLiPaiZu) {// 手牌全由连续牌组组成
            if (lianXuPaiZuList.size() == 1) {// 只有一个连续牌组
                int idx = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(0));
                LianXuPaiZuGouXing[] lianXuPaiZuGouXingArray = GouXingCalculatorHelper.gouXingCalculator.yiLianXuPaiZuGouXingsArray[idx];
                for (int i = 0; i < lianXuPaiZuGouXingArray.length; i++) {
                    result.add(lianXuPaiZuGouXingArray[i]);
                }
            } else if (lianXuPaiZuList.size() == 2) {// 由两个连续牌组组成
                int idx1 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(0));

                int idx2 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(1));

                LianXuPaiZuZuHeGouXing[] lianXuPaiZuZuHeGouXingArray = GouXingCalculatorHelper.gouXingCalculator.erLianXuPaiZuGouXingsArray[idx1
                        * GouXingCalculatorHelper.gouXingCalculator.erLianXuPaiZuGouXingsArrayIdx1Mod + idx2];

                for (int i = 0; i < lianXuPaiZuZuHeGouXingArray.length; i++) {
                    result.add(lianXuPaiZuZuHeGouXingArray[i]);
                }
            } else if (lianXuPaiZuList.size() == 3) {// 由三个连续牌组组成
                int idx1 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(0));

                int idx2 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(1));

                int idx3 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(2));

                LianXuPaiZuZuHeGouXing[] lianXuPaiZuZuHeGouXingArray = GouXingCalculatorHelper.gouXingCalculator.sanLianXuPaiZuGouXingsArray[idx1
                        * GouXingCalculatorHelper.gouXingCalculator.sanLianXuPaiZuGouXingsArrayIdx1Mod
                        + idx2 * GouXingCalculatorHelper.gouXingCalculator.sanLianXuPaiZuGouXingsArrayIdx2Mod + idx3];

                for (int i = 0; i < lianXuPaiZuZuHeGouXingArray.length; i++) {
                    result.add(lianXuPaiZuZuHeGouXingArray[i]);
                }
            } else if (lianXuPaiZuList.size() == 4) {// 由四个连续牌组组成
                int idx1 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(0));

                int idx2 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(1));

                int idx3 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(2));

                int idx4 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(3));

                LianXuPaiZuZuHeGouXing[] lianXuPaiZuZuHeGouXingArray = GouXingCalculatorHelper.gouXingCalculator.siLianXuPaiZuGouXingsArray[idx1
                        * GouXingCalculatorHelper.gouXingCalculator.siLianXuPaiZuGouXingsArrayIdx1Mod
                        + idx2 * GouXingCalculatorHelper.gouXingCalculator.siLianXuPaiZuGouXingsArrayIdx2Mod
                        + idx3 * GouXingCalculatorHelper.gouXingCalculator.siLianXuPaiZuGouXingsArrayIdx3Mod + idx4];

                for (int i = 0; i < lianXuPaiZuZuHeGouXingArray.length; i++) {
                    result.add(lianXuPaiZuZuHeGouXingArray[i]);
                }
            } else if (lianXuPaiZuList.size() == 5) {// 由五个连续牌组组成

                int idx1 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(0));

                int idx2 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(1));

                int idx3 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(2));

                int idx4 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(3));

                int idx5 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(4));

                LianXuPaiZuZuHeGouXing[] lianXuPaiZuZuHeGouXingArray = GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuGouXingsArray[idx1
                        * GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuGouXingsArrayIdx1Mod
                        + idx2 * GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuGouXingsArrayIdx2Mod
                        + idx3 * GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuGouXingsArrayIdx3Mod
                        + idx4 * GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuGouXingsArrayIdx4Mod + idx5];

                for (int i = 0; i < lianXuPaiZuZuHeGouXingArray.length; i++) {
                    result.add(lianXuPaiZuZuHeGouXingArray[i]);
                }
            }
        } else if (!hasLianXuPaiZu && hasDuLiPaiZu) {// 手牌全由独立牌组成
            int idx = calculateDuLiPaiZuGouXingIdx(duLiPaiIdxList);
            DuLiPaiZuGouXing[] duLiPaiZuGouXingArray = GouXingCalculatorHelper.gouXingCalculator.duLiPaiZuGouXingsArray[idx];
            for (int i = 0; i < duLiPaiZuGouXingArray.length; i++) {
                result.add(duLiPaiZuGouXingArray[i]);
            }
        } else {// 手牌由连续牌组和独立牌组组成
            if (lianXuPaiZuList.size() == 1) {// 有一个连续牌组
                int lianXuIdx = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(0));

                int duLiIdx = calculateDuLiPaiZuGouXingIdx(duLiPaiIdxList);

                LianXuPaiZuDuLiPaiZuZuHeGouXing[] lianXuPaiZuDuLiPaiZuZuHeGouXingArray = GouXingCalculatorHelper.gouXingCalculator.yiLianXuPaiZuAndDuLiPaiZuGouXingsArray[lianXuIdx
                        * GouXingCalculatorHelper.gouXingCalculator.yiLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx1Mod
                        + duLiIdx];
                for (int i = 0; i < lianXuPaiZuDuLiPaiZuZuHeGouXingArray.length; i++) {
                    result.add(lianXuPaiZuDuLiPaiZuZuHeGouXingArray[i]);
                }
            } else if (lianXuPaiZuList.size() == 2) {// 有两个连续牌组
                int lianXuIdx1 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(0));

                int lianXuIdx2 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(1));

                int duLiIdx = calculateDuLiPaiZuGouXingIdx(duLiPaiIdxList);

                LianXuPaiZuDuLiPaiZuZuHeGouXing[] lianXuPaiZuDuLiPaiZuZuHeGouXingArray = GouXingCalculatorHelper.gouXingCalculator.erLianXuPaiZuAndDuLiPaiZuGouXingsArray[lianXuIdx1
                        * GouXingCalculatorHelper.gouXingCalculator.erLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx1Mod
                        + lianXuIdx2
                        * GouXingCalculatorHelper.gouXingCalculator.erLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx2Mod
                        + duLiIdx];
                for (int i = 0; i < lianXuPaiZuDuLiPaiZuZuHeGouXingArray.length; i++) {
                    result.add(lianXuPaiZuDuLiPaiZuZuHeGouXingArray[i]);
                }
            } else if (lianXuPaiZuList.size() == 3) {// 有三个连续牌组
                int lianXuIdx1 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(0));

                int lianXuIdx2 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(1));

                int lianXuIdx3 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(2));

                int duLiIdx = calculateDuLiPaiZuGouXingIdx(duLiPaiIdxList);

                LianXuPaiZuDuLiPaiZuZuHeGouXing[] lianXuPaiZuDuLiPaiZuZuHeGouXingArray = GouXingCalculatorHelper.gouXingCalculator.sanLianXuPaiZuAndDuLiPaiZuGouXingsArray[lianXuIdx1
                        * GouXingCalculatorHelper.gouXingCalculator.sanLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx1Mod
                        + lianXuIdx2
                        * GouXingCalculatorHelper.gouXingCalculator.sanLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx2Mod
                        + lianXuIdx3
                        * GouXingCalculatorHelper.gouXingCalculator.sanLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx3Mod
                        + duLiIdx];
                for (int i = 0; i < lianXuPaiZuDuLiPaiZuZuHeGouXingArray.length; i++) {
                    result.add(lianXuPaiZuDuLiPaiZuZuHeGouXingArray[i]);
                }
            } else if (lianXuPaiZuList.size() == 4) {// 有四个连续牌组
                int lianXuIdx1 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(0));

                int lianXuIdx2 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(1));

                int lianXuIdx3 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(2));

                int lianXuIdx4 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(3));

                int duLiIdx = calculateDuLiPaiZuGouXingIdx(duLiPaiIdxList);

                LianXuPaiZuDuLiPaiZuZuHeGouXing[] lianXuPaiZuDuLiPaiZuZuHeGouXingArray = GouXingCalculatorHelper.gouXingCalculator.siLianXuPaiZuAndDuLiPaiZuGouXingsArray[lianXuIdx1
                        * GouXingCalculatorHelper.gouXingCalculator.siLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx1Mod
                        + lianXuIdx2
                        * GouXingCalculatorHelper.gouXingCalculator.siLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx2Mod
                        + lianXuIdx3
                        * GouXingCalculatorHelper.gouXingCalculator.siLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx3Mod
                        + lianXuIdx4
                        * GouXingCalculatorHelper.gouXingCalculator.siLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx4Mod
                        + duLiIdx];
                for (int i = 0; i < lianXuPaiZuDuLiPaiZuZuHeGouXingArray.length; i++) {
                    result.add(lianXuPaiZuDuLiPaiZuZuHeGouXingArray[i]);
                }
            } else if (lianXuPaiZuList.size() == 5) {// 有五个连续牌组
                int lianXuIdx1 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(0));

                int lianXuIdx2 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(1));

                int lianXuIdx3 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(2));

                int lianXuIdx4 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(3));

                int lianXuIdx5 = calculateLianXuPaiZuGouXingIdx(lianXuPaiZuList.get(4));

                int duLiIdx = calculateDuLiPaiZuGouXingIdx(duLiPaiIdxList);

                LianXuPaiZuDuLiPaiZuZuHeGouXing[] lianXuPaiZuDuLiPaiZuZuHeGouXingArray = GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuAndDuLiPaiZuGouXingsArray[lianXuIdx1
                        * GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx1Mod
                        + lianXuIdx2
                        * GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx2Mod
                        + lianXuIdx3
                        * GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx3Mod
                        + lianXuIdx4
                        * GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx4Mod
                        + lianXuIdx5
                        * GouXingCalculatorHelper.gouXingCalculator.wuLianXuPaiZuAndDuLiPaiZuGouXingsArrayIdx5Mod
                        + duLiIdx];
                for (int i = 0; i < lianXuPaiZuDuLiPaiZuZuHeGouXingArray.length; i++) {
                    result.add(lianXuPaiZuDuLiPaiZuZuHeGouXingArray[i]);
                }
            }
        }
        return result;
    }

    private int calculateLianXuPaiZuGouXingIdx(LianXuPaiZu lianXuPaiZu) {
        if (!lianXuPaiZu.isBigCodeMode()) {
            return GouXingCalculatorHelper.gouXingCalculator.lianXuPaiZuGouXingsIdxArray[lianXuPaiZu.getSmallCode()];
        } else {
            return GouXingCalculatorHelper.gouXingCalculator.lianXuPaiZuGouXingsIdxMap.get(lianXuPaiZu.getBigCode());
        }
    }

    private int calculateDuLiPaiZuGouXingIdx(List<Integer> duLiPaiIdxList) {
        int[] duLiPaiCountArray = new int[10];
        int totalPai = 0;
        for (int duLiPaiIdx : duLiPaiIdxList) {
            int duLiPaiQuantity = paiQuantityArray[duLiPaiIdx];
            totalPai += duLiPaiQuantity;
            duLiPaiCountArray[duLiPaiQuantity - 1]++;
        }
        DuLiPaiZu duLiPaiZu = new DuLiPaiZu(0, duLiPaiCountArray, totalPai, 0);
        duLiPaiZu.calculateCode();
        int idx = GouXingCalculatorHelper.gouXingCalculator.duLiPaiZuGouXingsIdxArray[duLiPaiZu.getCode()];
        return idx;
    }

    private void parseZiPai(List<Integer> duLiPaiIdxList) {
        for (int i = 27; i <= 33; i++) {
            if (paiQuantityArray[i] > 0) {
                duLiPaiIdxList.add(i);
            }
        }
    }

    private void parseXuShuPai(int startIdx, int endIdx, List<JutihuaLianXuPaiZu> jutihuaLianXuPaiZuList,
                               List<Integer> duLiPaiIdxList) {
        MajiangPai[] allPaiTypeArray = MajiangPai.values();
        int lian = 0;
        int totalPai = 0;
        boolean bigCodeMode = false;
        for (int i = startIdx; i <= endIdx; i++) {
            int paiQuantity = paiQuantityArray[i];
            if (paiQuantity > 0) {
                lian++;
                totalPai += paiQuantity;
                if (paiQuantity > 7) {
                    bigCodeMode = true;
                }
            } else {
                if (lian >= 3) {
                    int[] lianXuPaiZuPaiQuantityArray = new int[lian];
                    System.arraycopy(paiQuantityArray, i - lian, lianXuPaiZuPaiQuantityArray, 0, lian);

                    MajiangPai[] jutiLianXuPaiTypesArray = new MajiangPai[lian];
                    System.arraycopy(allPaiTypeArray, i - lian, jutiLianXuPaiTypesArray, 0, lian);

                    jutihuaLianXuPaiZuList.add(new JutihuaLianXuPaiZu(
                            new LianXuPaiZu(lianXuPaiZuPaiQuantityArray, totalPai, 0, bigCodeMode),
                            jutiLianXuPaiTypesArray));
                } else {
                    for (int j = lian; j > 0; j--) {
                        duLiPaiIdxList.add(i - j);
                    }
                }
                lian = 0;
                totalPai = 0;
            }
        }
        // 收尾
        if (lian >= 3) {
            int[] lianXuPaiZuPaiQuantityArray = new int[lian];
            System.arraycopy(paiQuantityArray, (endIdx + 1) - lian, lianXuPaiZuPaiQuantityArray, 0, lian);

            MajiangPai[] jutiLianXuPaiTypesArray = new MajiangPai[lian];
            System.arraycopy(allPaiTypeArray, (endIdx + 1) - lian, jutiLianXuPaiTypesArray, 0, lian);

            jutihuaLianXuPaiZuList.add(new JutihuaLianXuPaiZu(
                    new LianXuPaiZu(lianXuPaiZuPaiQuantityArray, totalPai, 0, bigCodeMode), jutiLianXuPaiTypesArray));
        } else {
            for (int j = lian; j > 0; j--) {
                duLiPaiIdxList.add((endIdx + 1) - j);
            }
        }
    }

    public void addPai(MajiangPai pai) {
        int paiOrdinal = pai.ordinal();
        paiQuantityArray[paiOrdinal]++;
    }

    public void removePai(MajiangPai pai) {
        int paiOrdinal = pai.ordinal();
        paiQuantityArray[paiOrdinal]--;
    }

    public void removePai(MajiangPai pai, int zhangShu) {
        int paiOrdinal = pai.ordinal();
        paiQuantityArray[paiOrdinal] -= zhangShu;
    }

    public Shunzi tryAndMakeShunziWithPai1(MajiangPai pai1) {
        int paiOrdinal = pai1.ordinal();
        if (paiOrdinal >= 0 && paiOrdinal <= 8) {// 万
            if (paiOrdinal <= 6) {
                if (paiQuantityArray[paiOrdinal + 1] > 0 && paiQuantityArray[paiOrdinal + 2] > 0) {
                    Shunzi shunzi = new Shunzi(pai1, MajiangPai.valueOf(paiOrdinal + 1),
                            MajiangPai.valueOf(paiOrdinal + 2));
                    return shunzi;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else if (paiOrdinal >= 9 && paiOrdinal <= 17) {// 筒
            if (paiOrdinal <= 15) {
                if (paiQuantityArray[paiOrdinal + 1] > 0 && paiQuantityArray[paiOrdinal + 2] > 0) {
                    Shunzi shunzi = new Shunzi(pai1, MajiangPai.valueOf(paiOrdinal + 1),
                            MajiangPai.valueOf(paiOrdinal + 2));
                    return shunzi;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else if (paiOrdinal >= 18 && paiOrdinal <= 26) {// 条
            if (paiOrdinal <= 24) {
                if (paiQuantityArray[paiOrdinal + 1] > 0 && paiQuantityArray[paiOrdinal + 2] > 0) {
                    Shunzi shunzi = new Shunzi(pai1, MajiangPai.valueOf(paiOrdinal + 1),
                            MajiangPai.valueOf(paiOrdinal + 2));
                    return shunzi;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Shunzi tryAndMakeShunziWithPai2(MajiangPai pai2) {
        int paiOrdinal = pai2.ordinal();
        if (paiOrdinal >= 0 && paiOrdinal <= 8) {// 万
            if (paiOrdinal >= 1 && paiOrdinal <= 7) {
                if (paiQuantityArray[paiOrdinal - 1] > 0 && paiQuantityArray[paiOrdinal + 1] > 0) {
                    Shunzi shunzi = new Shunzi(MajiangPai.valueOf(paiOrdinal - 1), pai2,
                            MajiangPai.valueOf(paiOrdinal + 1));
                    return shunzi;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else if (paiOrdinal >= 9 && paiOrdinal <= 17) {// 筒
            if (paiOrdinal >= 10 && paiOrdinal <= 16) {
                if (paiQuantityArray[paiOrdinal - 1] > 0 && paiQuantityArray[paiOrdinal + 1] > 0) {
                    Shunzi shunzi = new Shunzi(MajiangPai.valueOf(paiOrdinal - 1), pai2,
                            MajiangPai.valueOf(paiOrdinal + 1));
                    return shunzi;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else if (paiOrdinal >= 18 && paiOrdinal <= 26) {// 条
            if (paiOrdinal >= 19 && paiOrdinal <= 25) {
                if (paiQuantityArray[paiOrdinal - 1] > 0 && paiQuantityArray[paiOrdinal + 1] > 0) {
                    Shunzi shunzi = new Shunzi(MajiangPai.valueOf(paiOrdinal - 1), pai2,
                            MajiangPai.valueOf(paiOrdinal + 1));
                    return shunzi;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Shunzi tryAndMakeShunziWithPai3(MajiangPai pai3) {
        int paiOrdinal = pai3.ordinal();
        if (paiOrdinal >= 0 && paiOrdinal <= 8) {// 万
            if (paiOrdinal >= 2) {
                if (paiQuantityArray[paiOrdinal - 1] > 0 && paiQuantityArray[paiOrdinal - 2] > 0) {
                    Shunzi shunzi = new Shunzi(MajiangPai.valueOf(paiOrdinal - 2), MajiangPai.valueOf(paiOrdinal - 1),
                            pai3);
                    return shunzi;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else if (paiOrdinal >= 9 && paiOrdinal <= 17) {// 筒
            if (paiOrdinal >= 11) {
                if (paiQuantityArray[paiOrdinal - 1] > 0 && paiQuantityArray[paiOrdinal - 2] > 0) {
                    Shunzi shunzi = new Shunzi(MajiangPai.valueOf(paiOrdinal - 2), MajiangPai.valueOf(paiOrdinal - 1),
                            pai3);
                    return shunzi;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else if (paiOrdinal >= 18 && paiOrdinal <= 26) {// 条
            if (paiOrdinal >= 20) {
                if (paiQuantityArray[paiOrdinal - 1] > 0 && paiQuantityArray[paiOrdinal - 2] > 0) {
                    Shunzi shunzi = new Shunzi(MajiangPai.valueOf(paiOrdinal - 2), MajiangPai.valueOf(paiOrdinal - 1),
                            pai3);
                    return shunzi;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<MajiangPai> findAllPaiQuantityIsFour() {
        List<MajiangPai> allGangzi = new ArrayList<>();
        for (int i = 0; i < paiQuantityArray.length; i++) {
            if (paiQuantityArray[i] == 4) {
                allGangzi.add(MajiangPai.valueOf(i));
            }
        }
        return allGangzi;
    }

    public int count(MajiangPai pai) {
        if (pai == null) {
            return 0;
        }
        int paiOrdinal = pai.ordinal();
        return paiQuantityArray[paiOrdinal];
    }

    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForZimoHu(PanPlayer player,
                                                                         GouXingPanHu gouXingPanHu) {
        return calculateAllHuPaiShoupaiPaiXingForZimoHu(
                new HashSet<>(), new MajiangPai[0], new ArrayList<>(), null, player, gouXingPanHu);
    }

    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForZimoHu(Set<MajiangPai> guipaiTypes,
                                                                         MajiangPai[] paiTypesForGuipaiAct,
                                                                         List<MajiangPai> guipaiList,
                                                                         PanPlayer player,
                                                                         GouXingPanHu gouXingPanHu) {
        return calculateAllHuPaiShoupaiPaiXingForZimoHu(
                guipaiTypes, paiTypesForGuipaiAct, guipaiList, null, player, gouXingPanHu);

    }


    /**
     * @param guipaiTypes          哪些牌是鬼牌
     * @param paiTypesForGuipaiAct 鬼牌可以扮演的牌类
     * @param guipaiList           玩家拥有的鬼牌
     * @param actGuipaiBenpaiPai   用于替换鬼牌本牌的牌，比如财神是三万有的地方用白板替换三万
     * @param player
     * @param gouXingPanHu
     * @return
     */
    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForZimoHu(Set<MajiangPai> guipaiTypes,
                                                                         MajiangPai[] paiTypesForGuipaiAct,
                                                                         List<MajiangPai> guipaiList,
                                                                         MajiangPai actGuipaiBenpaiPai,
                                                                         PanPlayer player,
                                                                         GouXingPanHu gouXingPanHu) {
        MajiangPai gangmoShoupai = player.getGangmoShoupai();
        boolean gangmoGuipai = gangmoShoupai != null && guipaiTypes.contains(gangmoShoupai);
        if (!gangmoGuipai) {
            addPai(player.getGangmoShoupai());
        }
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = calculateHuPaiShoupaiPaiXingList(guipaiTypes, paiTypesForGuipaiAct,
                guipaiList, player,
                actGuipaiBenpaiPai, gouXingPanHu, player.getGangmoShoupai());
        if (!gangmoGuipai) {
            removePai(player.getGangmoShoupai());
        }
        return huPaiShoupaiPaiXingList;
    }


    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForDianpaoHu(PanPlayer player,
                                                                            MajiangPai daChuPai,
                                                                            GouXingPanHu gouXingPanHu) {
        return calculateAllHuPaiShoupaiPaiXingForDianpaoHu(
                new HashSet<>(), new MajiangPai[0], new ArrayList<>(), null, player, daChuPai, gouXingPanHu);

    }

    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForDianpaoHu(Set<MajiangPai> guipaiTypes,
                                                                            MajiangPai[] paiTypesForGuipaiAct,
                                                                            List<MajiangPai> guipaiList,
                                                                            PanPlayer player,
                                                                            MajiangPai daChuPai,
                                                                            GouXingPanHu gouXingPanHu) {
        return calculateAllHuPaiShoupaiPaiXingForDianpaoHu(
                guipaiTypes, paiTypesForGuipaiAct, guipaiList, null, player, daChuPai, gouXingPanHu);
    }

    /**
     * @param guipaiTypes          哪些牌是鬼牌
     * @param paiTypesForGuipaiAct 鬼牌可以扮演的牌类
     * @param guipaiList           玩家拥有的鬼牌
     * @param actGuipaiBenpaiPai   用于替换鬼牌本牌的牌，比如财神是三万有的地方用白板替换三万
     * @param player
     * @param gouXingPanHu
     * @return
     */
    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForDianpaoHu(Set<MajiangPai> guipaiTypes,
                                                                            MajiangPai[] paiTypesForGuipaiAct,
                                                                            List<MajiangPai> guipaiList,
                                                                            MajiangPai actGuipaiBenpaiPai,
                                                                            PanPlayer player,
                                                                            MajiangPai daChuPai,
                                                                            GouXingPanHu gouXingPanHu) {
        // 先把这张牌放入计算器
        addPai(daChuPai);
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = calculateHuPaiShoupaiPaiXingList(guipaiTypes, paiTypesForGuipaiAct,
                guipaiList, player,
                actGuipaiBenpaiPai, gouXingPanHu, daChuPai);
        // 再把这张牌拿出计算器
        removePai(daChuPai);
        return huPaiShoupaiPaiXingList;
    }

    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForQianggangHu(PanPlayer player,
                                                                              MajiangPai gangPai,
                                                                              GouXingPanHu gouXingPanHu) {
        return calculateAllHuPaiShoupaiPaiXingForQianggangHu(
                new HashSet<>(), new MajiangPai[0], new ArrayList<>(), null, player, gangPai, gouXingPanHu);
    }

    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForQianggangHu(Set<MajiangPai> guipaiTypes,
                                                                              MajiangPai[] paiTypesForGuipaiAct,
                                                                              List<MajiangPai> guipaiList,
                                                                              PanPlayer player,
                                                                              MajiangPai gangPai,
                                                                              GouXingPanHu gouXingPanHu) {
        return calculateAllHuPaiShoupaiPaiXingForQianggangHu(
                guipaiTypes, paiTypesForGuipaiAct, guipaiList, null, player, gangPai, gouXingPanHu);
    }

    /**
     * @param guipaiTypes          哪些牌是鬼牌
     * @param paiTypesForGuipaiAct 鬼牌可以扮演的牌类
     * @param guipaiList           玩家拥有的鬼牌
     * @param actGuipaiBenpaiPai   用于替换鬼牌本牌的牌，比如财神是三万有的地方用白板替换三万
     * @param player
     * @param gouXingPanHu
     * @return
     */
    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForQianggangHu(Set<MajiangPai> guipaiTypes,
                                                                              MajiangPai[] paiTypesForGuipaiAct,
                                                                              List<MajiangPai> guipaiList,
                                                                              MajiangPai actGuipaiBenpaiPai,
                                                                              PanPlayer player,
                                                                              MajiangPai gangPai,
                                                                              GouXingPanHu gouXingPanHu) {

        addPai(gangPai);
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = calculateHuPaiShoupaiPaiXingList(guipaiTypes, paiTypesForGuipaiAct,
                guipaiList, player,
                actGuipaiBenpaiPai, gouXingPanHu, gangPai);
        removePai(gangPai);
        return huPaiShoupaiPaiXingList;
    }


    private List<ShoupaiPaiXing> calculateHuPaiShoupaiPaiXingList(Set<MajiangPai> guipaiTypes, MajiangPai[] paiTypesForGuipaiAct,
                                                                  List<MajiangPai> guipaiList,
                                                                  PanPlayer player, MajiangPai actGuipaiBenpaiPai,
                                                                  GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        List<ShoupaiPaiXing> shoupaiPaiXingList = new ArrayList<>();
        int actGuipaiBenpaiPaiCount = count(actGuipaiBenpaiPai);
        if (!guipaiTypes.contains(actGuipaiBenpaiPai) && actGuipaiBenpaiPaiCount > 0) {
            shoupaiPaiXingList.addAll(calculateHuPaiShoupaiPaiXingListWithActGuipaiBenpaiPai(
                    actGuipaiBenpaiPai, actGuipaiBenpaiPaiCount,
                    guipaiTypes, paiTypesForGuipaiAct, guipaiList,
                    player, gouXingPanHu, huPai));
        } else {
            shoupaiPaiXingList.addAll(calculateHuPaiShoupaiPaiXingListWithoutActGuipaiBenpaiPai(
                    guipaiTypes, paiTypesForGuipaiAct, guipaiList, player, gouXingPanHu, huPai));
        }
        return shoupaiPaiXingList;
    }

    private Collection<? extends ShoupaiPaiXing> calculateHuPaiShoupaiPaiXingListWithoutActGuipaiBenpaiPai(
            Set<MajiangPai> guipaiTypes, MajiangPai[] paiTypesForGuipaiAct, List<MajiangPai> guipaiList,
            PanPlayer player, GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        if (!guipaiList.isEmpty()) {// 有鬼牌
            List<ShoupaiPaiXing> shoupaiPaiXingList = calculateHuPaiShoupaiPaiXingListWithGuipai(
                    new ActGuipaiBenpaiPaiDangPai[0], paiTypesForGuipaiAct, guipaiList, player, gouXingPanHu, huPai);
            return shoupaiPaiXingList;
        } else {// 没鬼牌
            List<ShoupaiPaiXing> shoupaiPaiXingList = calculateHuPaiShoupaiPaiXingListWithoutGuipai(
                    new ActGuipaiBenpaiPaiDangPai[0], player, gouXingPanHu, huPai);
            return shoupaiPaiXingList;
        }
    }


    private Collection<? extends ShoupaiPaiXing> calculateHuPaiShoupaiPaiXingListWithActGuipaiBenpaiPai(
            MajiangPai actGuipaiBenpaiPai, int actGuipaiBenpaiPaiCount,
            Set<MajiangPai> guipaiTypes, MajiangPai[] paiTypesForGuipaiAct, List<MajiangPai> guipaiList,
            PanPlayer player, GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        List<ShoupaiPaiXing> shoupaiPaiXingList = new ArrayList<>();
        // 移除用于替换鬼牌本牌的牌
        for (int i = 0; i < actGuipaiBenpaiPaiCount; i++) {
            removePai(actGuipaiBenpaiPai);
        }
        MajiangPai[] paiTypesForActGuipaiBenpaiPaiAct = calculatePaiTypesForActGuipaiBenpaiPaiAct(actGuipaiBenpaiPai, guipaiTypes);
        int maxZuheCode = (int) Math.pow(paiTypesForActGuipaiBenpaiPaiAct.length, actGuipaiBenpaiPaiCount);
        int[] modArray = new int[actGuipaiBenpaiPaiCount];
        for (int i = 0; i < actGuipaiBenpaiPaiCount; i++) {
            modArray[i] = (int) Math.pow(paiTypesForActGuipaiBenpaiPaiAct.length, actGuipaiBenpaiPaiCount - 1 - i);
        }
        for (int zuheCode = 0; zuheCode < maxZuheCode; zuheCode++) {
            ActGuipaiBenpaiPaiDangPai[] actGuipaiBenpaiPaiDangPaiArray = new ActGuipaiBenpaiPaiDangPai[actGuipaiBenpaiPaiCount];
            int temp = zuheCode;
            int previousGuipaiDangIdx = 0;
            for (int i = 0; i < actGuipaiBenpaiPaiCount; i++) {
                int mod = modArray[i];
                int shang = temp / mod;
                if (shang >= previousGuipaiDangIdx) {
                    int yu = temp % mod;
                    actGuipaiBenpaiPaiDangPaiArray[i] = new ActGuipaiBenpaiPaiDangPai(actGuipaiBenpaiPai,
                            paiTypesForActGuipaiBenpaiPaiAct[shang]);
                    temp = yu;
                    previousGuipaiDangIdx = shang;
                } else {
                    actGuipaiBenpaiPaiDangPaiArray = null;
                    break;
                }
            }
            if (actGuipaiBenpaiPaiDangPaiArray != null) {
                // 先把所有当的替换鬼牌本牌的牌加入计算器
                for (int i = 0; i < actGuipaiBenpaiPaiDangPaiArray.length; i++) {
                    addPai(actGuipaiBenpaiPaiDangPaiArray[i].getDangpai());
                }
                if (!guipaiList.isEmpty()) {// 有鬼牌
                    shoupaiPaiXingList.addAll(calculateHuPaiShoupaiPaiXingListWithGuipai(actGuipaiBenpaiPaiDangPaiArray,
                            paiTypesForGuipaiAct, guipaiList, player, gouXingPanHu, huPai));
                } else {// 没鬼牌
                    shoupaiPaiXingList.addAll(calculateHuPaiShoupaiPaiXingListWithoutGuipai(actGuipaiBenpaiPaiDangPaiArray,
                            player, gouXingPanHu, huPai));

                }

                // 再把所有当的替换鬼牌本牌的牌移出计算器
                for (int i = 0; i < actGuipaiBenpaiPaiDangPaiArray.length; i++) {
                    removePai(actGuipaiBenpaiPaiDangPaiArray[i].getDangpai());
                }
            }
        }
        // 加入用于替换鬼牌本牌的牌
        for (int i = 0; i < actGuipaiBenpaiPaiCount; i++) {
            addPai(actGuipaiBenpaiPai);
        }
        return shoupaiPaiXingList;
    }

    private List<ShoupaiPaiXing> calculateHuPaiShoupaiPaiXingListWithoutGuipai(
            ActGuipaiBenpaiPaiDangPai[] actGuipaiBenpaiPaiDangPaiArray, PanPlayer player, GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        int chichuShunziCount = player.countChichupaiZu();
        int pengchuKeziCount = player.countPengchupaiZu();
        int gangchuGangziCount = player.countGangchupaiZu();
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = new ArrayList<>();

        ShoupaiJiesuanPai[] dangPaiArray = new ShoupaiJiesuanPai[actGuipaiBenpaiPaiDangPaiArray.length];
        System.arraycopy(actGuipaiBenpaiPaiDangPaiArray, 0, dangPaiArray, 0, actGuipaiBenpaiPaiDangPaiArray.length);
        // 计算构型
        List<GouXing> gouXingList = calculateAllGouXing();
        for (GouXing gouXing : gouXingList) {
            boolean hu = gouXingPanHu.panHu(gouXing.getGouXingCode(), chichuShunziCount, pengchuKeziCount,
                    gangchuGangziCount);
            if (hu) {
                // 计算牌型
                huPaiShoupaiPaiXingList.addAll(calculateAllShoupaiPaiXingForGouXingWithHupai(gouXing,
                        dangPaiArray, huPai));
            }
        }
        return huPaiShoupaiPaiXingList;
    }

    private List<ShoupaiPaiXing> calculateHuPaiShoupaiPaiXingListWithGuipai(
            ActGuipaiBenpaiPaiDangPai[] actGuipaiBenpaiPaiDangPaiArray,
            MajiangPai[] paiTypesForGuipaiAct, List<MajiangPai> guipaiList, PanPlayer player,
            GouXingPanHu gouXingPanHu, MajiangPai huPai) {
        int chichuShunziCount = player.countChichupaiZu();
        int pengchuKeziCount = player.countPengchupaiZu();
        int gangchuGangziCount = player.countGangchupaiZu();
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = new ArrayList<>();
        // 开始循环财神各种当法，算构型
        List<ShoupaiWithGuipaiDangGouXingZu> shoupaiWithGuipaiDangGouXingZuList = calculateShoupaiWithGuipaiDangGouXingZuList(
                guipaiList, paiTypesForGuipaiAct);
        // 对于可胡的构型，计算出所有牌型
        for (ShoupaiWithGuipaiDangGouXingZu shoupaiWithGuipaiDangGouXingZu : shoupaiWithGuipaiDangGouXingZuList) {
            GuipaiDangPai[] guipaiDangPaiArray = shoupaiWithGuipaiDangGouXingZu.getGuipaiDangPaiArray();
            ShoupaiJiesuanPai[] dangPaiArray = new ShoupaiJiesuanPai[actGuipaiBenpaiPaiDangPaiArray.length
                    + guipaiDangPaiArray.length];
            System.arraycopy(actGuipaiBenpaiPaiDangPaiArray, 0, dangPaiArray, 0, actGuipaiBenpaiPaiDangPaiArray.length);
            System.arraycopy(guipaiDangPaiArray, 0, dangPaiArray, actGuipaiBenpaiPaiDangPaiArray.length, guipaiDangPaiArray.length);
            List<GouXing> gouXingList = shoupaiWithGuipaiDangGouXingZu.getGouXingList();
            for (GouXing gouXing : gouXingList) {
                boolean hu = gouXingPanHu.panHu(gouXing.getGouXingCode(), chichuShunziCount, pengchuKeziCount,
                        gangchuGangziCount);
                if (hu) {
                    // 从计算器中移除所有鬼牌本牌,把所有当的鬼牌加入计算器
                    for (int i = 0; i < guipaiDangPaiArray.length; i++) {
                        removePai(guipaiDangPaiArray[i].getGuipai());
                        addPai(guipaiDangPaiArray[i].getDangpai());
                    }
                    // 计算牌型
                    huPaiShoupaiPaiXingList.addAll(calculateAllShoupaiPaiXingForGouXingWithHupai(gouXing,
                            dangPaiArray, huPai));
                    // 把所有当的鬼牌移出计算器,把所有鬼牌本牌加回计算器
                    for (int i = 0; i < guipaiDangPaiArray.length; i++) {
                        removePai(guipaiDangPaiArray[i].getDangpai());
                        addPai(guipaiDangPaiArray[i].getGuipai());
                    }
                }

            }
        }
        return huPaiShoupaiPaiXingList;
    }

    private List<ShoupaiPaiXing> calculateAllShoupaiPaiXingForGouXingWithHupai(
            GouXing gouXing, ShoupaiJiesuanPai[] dangPaiArray, MajiangPai huPai) {
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = new ArrayList<>();
        // 计算牌型
        List<PaiXing> paiXingList = calculateAllPaiXingFromGouXing(gouXing);
        for (PaiXing paiXing : paiXingList) {
            List<ShoupaiPaiXing> shoupaiPaiXingList = paiXing.generateShoupaiPaiXingByDangPai(dangPaiArray);

            // 对于每一个ShoupaiPaiXing还要变换最后弄进的牌
            for (ShoupaiPaiXing shoupaiPaiXing : shoupaiPaiXingList) {
                List<ShoupaiPaiXing> shoupaiPaiXingListWithDifftentLastActionPaiInZu = shoupaiPaiXing
                        .differentiateShoupaiPaiXingByLastActionPai(huPai);
                huPaiShoupaiPaiXingList.addAll(shoupaiPaiXingListWithDifftentLastActionPaiInZu);
            }

        }
        return huPaiShoupaiPaiXingList;
    }

    private List<ShoupaiWithGuipaiDangGouXingZu> calculateShoupaiWithGuipaiDangGouXingZuList(
            List<MajiangPai> guipaiList, MajiangPai[] paiTypesForGuipaiAct) {
        List<ShoupaiWithGuipaiDangGouXingZu> shoupaiWithGuipaiDangGouXingZuList = new ArrayList<>();

        int guipaiCount = guipaiList.size();
        int maxZuheCode = (int) Math.pow(paiTypesForGuipaiAct.length, guipaiCount);
        int[] modArray = new int[guipaiCount];
        for (int i = 0; i < guipaiCount; i++) {
            modArray[i] = (int) Math.pow(paiTypesForGuipaiAct.length, guipaiCount - 1 - i);
        }
        for (int zuheCode = 0; zuheCode < maxZuheCode; zuheCode++) {
            GuipaiDangPai[] guipaiDangPaiArray = new GuipaiDangPai[guipaiCount];
            int temp = zuheCode;
            int previousGuipaiDangIdx = 0;
            for (int i = 0; i < guipaiCount; i++) {
                int mod = modArray[i];
                int shang = temp / mod;
                if (shang >= previousGuipaiDangIdx) {
                    int yu = temp % mod;
                    guipaiDangPaiArray[i] = new GuipaiDangPai(guipaiList.get(i), paiTypesForGuipaiAct[shang]);
                    temp = yu;
                    previousGuipaiDangIdx = shang;
                } else {
                    guipaiDangPaiArray = null;
                    break;
                }
            }
            if (guipaiDangPaiArray != null) {
                // 从计算器中移除所有鬼牌本牌,把所有当的鬼牌加入计算器
                for (int i = 0; i < guipaiDangPaiArray.length; i++) {
                    removePai(guipaiDangPaiArray[i].getGuipai());
                    addPai(guipaiDangPaiArray[i].getDangpai());
                }
                // 计算构型
                List<GouXing> gouXingList = calculateAllGouXing();
                // 把所有当的鬼牌移出计算器,把所有鬼牌本牌加回计算器
                for (int i = 0; i < guipaiDangPaiArray.length; i++) {
                    removePai(guipaiDangPaiArray[i].getDangpai());
                    addPai(guipaiDangPaiArray[i].getGuipai());
                }
                ShoupaiWithGuipaiDangGouXingZu shoupaiWithGuipaiDangGouXingZu = new ShoupaiWithGuipaiDangGouXingZu();
                shoupaiWithGuipaiDangGouXingZu.setGouXingList(gouXingList);
                shoupaiWithGuipaiDangGouXingZu.setGuipaiDangPaiArray(guipaiDangPaiArray);
                shoupaiWithGuipaiDangGouXingZuList.add(shoupaiWithGuipaiDangGouXingZu);
            }
        }
        return shoupaiWithGuipaiDangGouXingZuList;
    }

    private MajiangPai[] calculatePaiTypesForActGuipaiBenpaiPaiAct(MajiangPai actGuipaiBenpaiPai, Set<MajiangPai> guipaiTypes) {
        MajiangPai[] paiTypesForActGuipaiBenpaiPaiAct = new MajiangPai[guipaiTypes.size() + 1];
        guipaiTypes.toArray(paiTypesForActGuipaiBenpaiPaiAct);
        paiTypesForActGuipaiBenpaiPaiAct[guipaiTypes.size()] = actGuipaiBenpaiPai;
        return paiTypesForActGuipaiBenpaiPaiAct;
    }

    public int[] getPaiQuantityArray() {
        return paiQuantityArray;
    }

    public void setPaiQuantityArray(int[] paiQuantityArray) {
        this.paiQuantityArray = paiQuantityArray;
    }

}
