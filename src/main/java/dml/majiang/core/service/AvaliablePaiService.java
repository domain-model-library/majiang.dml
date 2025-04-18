package dml.majiang.core.service;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.service.repositoryset.AvaliablePaiServiceRepositorySet;

import java.util.*;

public class AvaliablePaiService {
    public static void fillAvaliablePaiWithNoHuapai(long panId, AvaliablePaiServiceRepositorySet avaliablePaiServiceRepositorySet,
                                                    MajiangPai... excludedPaiTypes) {
        PanRepository panRepository = avaliablePaiServiceRepositorySet.getPanRepository();

        Pan pan = panRepository.take(panId);
        Set<MajiangPai> notPlaySet = new HashSet<>();
        notPlaySet.add(MajiangPai.chun);
        notPlaySet.add(MajiangPai.xia);
        notPlaySet.add(MajiangPai.qiu);
        notPlaySet.add(MajiangPai.dong);
        notPlaySet.add(MajiangPai.mei);
        notPlaySet.add(MajiangPai.lan);
        notPlaySet.add(MajiangPai.zhu);
        notPlaySet.add(MajiangPai.ju);
        if (excludedPaiTypes != null) {
            for (MajiangPai excludedPaiType : excludedPaiTypes) {
                notPlaySet.add(excludedPaiType);
            }
        }
        MajiangPai[] allMajiangPaiArray = MajiangPai.values();
        List<MajiangPai> playPaiTypeList = new ArrayList<>();
        for (int i = 0; i < allMajiangPaiArray.length; i++) {
            MajiangPai pai = allMajiangPaiArray[i];
            if (!notPlaySet.contains(pai)) {
                playPaiTypeList.add(pai);
            }
        }

        List<Pai> allPaiList = new ArrayList<>();
        int paiId = 1;
        for (MajiangPai paiType : playPaiTypeList) {
            for (int i = 0; i < 4; i++) {
                Pai pai = new Pai();
                pai.setId(paiId);
                pai.setPaiType(paiType);
                allPaiList.add(pai);
                paiId++;
            }
        }
        Random r = new Random(panId);
        Collections.shuffle(allPaiList, r);
        pan.setAvaliablePaiList(allPaiList);
        pan.setPlayPaiTypeList(playPaiTypeList);
    }
}
