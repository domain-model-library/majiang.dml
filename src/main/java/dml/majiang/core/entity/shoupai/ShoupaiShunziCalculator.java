package dml.majiang.core.entity.shoupai;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;

import java.util.*;

public class ShoupaiShunziCalculator {
    public static List<int[]> tryAndMakeShunziWithPai(List<Pai> shoupaiList, Pai paiToAdd) {
        // 只有两张手牌时不能吃
        if (shoupaiList.size() == 2) {
            return null;
        }
        if (!MajiangPai.isXushupai(paiToAdd.getPaiType())) {
            return null;
        }

        Map<MajiangPai, Set<Pai>> paiMap = makePaiTypeShoupaiMap(shoupaiList);

        MajiangPai previousPaiType = null;
        MajiangPai previousPreviousPaiType = null;
        MajiangPai nextPaiType = null;
        MajiangPai nextNextPaiType = null;
        previousPaiType = MajiangPai.previous(paiToAdd.getPaiType());
        if (previousPaiType != null) {
            previousPreviousPaiType = MajiangPai.previous(previousPaiType);
        }
        nextPaiType = MajiangPai.next(paiToAdd.getPaiType());
        if (nextPaiType != null) {
            nextNextPaiType = MajiangPai.next(nextPaiType);
        }
        List<int[]> shunziList = null;
        if (nextPaiType != null && nextNextPaiType != null) {
            Set<Pai> nextPaiSet = paiMap.get(nextPaiType);
            Set<Pai> nextNextPaiSet = paiMap.get(nextNextPaiType);
            if (nextPaiSet != null && nextNextPaiSet != null && !nextPaiSet.isEmpty() && !nextNextPaiSet.isEmpty()) {
                int[] shunziPaiIds = new int[3];
                shunziPaiIds[0] = paiToAdd.getId();
                shunziPaiIds[1] = nextPaiSet.iterator().next().getId();
                shunziPaiIds[2] = nextNextPaiSet.iterator().next().getId();
                shunziList = new ArrayList<>();
                shunziList.add(shunziPaiIds);
            }
        }

        if (previousPaiType != null && nextPaiType != null) {
            Set<Pai> previousPaiSet = paiMap.get(previousPaiType);
            Set<Pai> nextPaiSet = paiMap.get(nextPaiType);
            if (previousPaiSet != null && nextPaiSet != null && !previousPaiSet.isEmpty() && !nextPaiSet.isEmpty()) {
                int[] shunziPaiIds = new int[3];
                shunziPaiIds[0] = previousPaiSet.iterator().next().getId();
                shunziPaiIds[1] = paiToAdd.getId();
                shunziPaiIds[2] = nextPaiSet.iterator().next().getId();
                if (shunziList == null) {
                    shunziList = new ArrayList<>();
                }
                shunziList.add(shunziPaiIds);
            }
        }

        if (previousPreviousPaiType != null && previousPaiType != null) {
            Set<Pai> previousPreviousPaiSet = paiMap.get(previousPreviousPaiType);
            Set<Pai> previousPaiSet = paiMap.get(previousPaiType);
            if (previousPreviousPaiSet != null && previousPaiSet != null && !previousPreviousPaiSet.isEmpty() && !previousPaiSet.isEmpty()) {
                int[] shunziPaiIds = new int[3];
                shunziPaiIds[0] = previousPreviousPaiSet.iterator().next().getId();
                shunziPaiIds[1] = previousPaiSet.iterator().next().getId();
                shunziPaiIds[2] = paiToAdd.getId();
                if (shunziList == null) {
                    shunziList = new ArrayList<>();
                }
                shunziList.add(shunziPaiIds);
            }
        }
        return shunziList;
    }

    private static Map<MajiangPai, Set<Pai>> makePaiTypeShoupaiMap(List<Pai> shoupaiList) {
        Map<MajiangPai, Set<Pai>> paiMap = new HashMap<>();
        shoupaiList.forEach(pai -> {
            if (paiMap.containsKey(pai.getPaiType())) {
                paiMap.get(pai.getPaiType()).add(pai);
            } else {
                Set<Pai> paiSet = new HashSet<>();
                paiSet.add(pai);
                paiMap.put(pai.getPaiType(), paiSet);
            }
        });
        return paiMap;
    }
}
