package dml.majiang.core.entity.shoupai;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.fenzu.Shunzi;

import java.util.*;

public class ShoupaiShunziCalculator {
    public static List<Shunzi> tryAndMakeShunziWithPai(List<Pai> shoupaiList, Pai paiToAdd) {
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
        List<Shunzi> shunziList = null;
        if (nextPaiType != null && nextNextPaiType != null) {
            Set<Pai> nextPaiSet = paiMap.get(nextPaiType);
            Set<Pai> nextNextPaiSet = paiMap.get(nextNextPaiType);
            if (nextPaiSet != null && nextNextPaiSet != null && !nextPaiSet.isEmpty() && !nextNextPaiSet.isEmpty()) {
                Shunzi shunzi = new Shunzi();
                shunzi.setPai1(paiToAdd);
                shunzi.setPai2(nextPaiSet.iterator().next());
                shunzi.setPai3(nextNextPaiSet.iterator().next());
                shunziList = new ArrayList<>();
                shunziList.add(shunzi);
            }
        }

        if (previousPaiType != null && nextPaiType != null) {
            Set<Pai> previousPaiSet = paiMap.get(previousPaiType);
            Set<Pai> nextPaiSet = paiMap.get(nextPaiType);
            if (previousPaiSet != null && nextPaiSet != null && !previousPaiSet.isEmpty() && !nextPaiSet.isEmpty()) {
                Shunzi shunzi = new Shunzi();
                shunzi.setPai1(previousPaiSet.iterator().next());
                shunzi.setPai2(paiToAdd);
                shunzi.setPai3(nextPaiSet.iterator().next());
                if (shunziList == null) {
                    shunziList = new ArrayList<>();
                }
                shunziList.add(shunzi);
            }
        }

        if (previousPreviousPaiType != null && previousPaiType != null) {
            Set<Pai> previousPreviousPaiSet = paiMap.get(previousPreviousPaiType);
            Set<Pai> previousPaiSet = paiMap.get(previousPaiType);
            if (previousPreviousPaiSet != null && previousPaiSet != null && !previousPreviousPaiSet.isEmpty() && !previousPaiSet.isEmpty()) {
                Shunzi shunzi = new Shunzi();
                shunzi.setPai1(previousPreviousPaiSet.iterator().next());
                shunzi.setPai2(previousPaiSet.iterator().next());
                shunzi.setPai3(paiToAdd);
                if (shunziList == null) {
                    shunziList = new ArrayList<>();
                }
                shunziList.add(shunzi);
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
