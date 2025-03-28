package dml.majiang.core.service;

import dml.majiang.core.entity.*;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.service.repositoryset.FaPaiServiceRepositorySet;

import java.util.Iterator;
import java.util.List;

public class FaPaiService {
    public static void faPai13Shoupai(long panId, FaPaiServiceRepositorySet faPaiServiceRepositorySet) {
        PanRepository panRepository = faPaiServiceRepositorySet.getPanRepository();

        Pan pan = panRepository.take(panId);
        List<Pai> avaliablePaiList = pan.getAvaliablePaiList();
        MenFeng zhuangPlayerMenFeng = pan.findMenFengForZhuang();
        for (int i = 0; i < 13; i++) {
            MenFeng playerMenFeng = zhuangPlayerMenFeng;
            for (int j = 0; j < 4; j++) {
                PanPlayer player = pan.findPlayerByMenFeng(playerMenFeng);
                if (player != null) {
                    faPai(avaliablePaiList, player);
                }
                playerMenFeng = playerMenFeng.next();
            }
        }
    }

    public static void faPai16Shoupai(long panId, FaPaiServiceRepositorySet faPaiServiceRepositorySet) {
        PanRepository panRepository = faPaiServiceRepositorySet.getPanRepository();

        Pan pan = panRepository.take(panId);
        List<Pai> avaliablePaiList = pan.getAvaliablePaiList();
        MenFeng zhuangPlayerMenFeng = pan.findMenFengForZhuang();
        for (int i = 0; i < 16; i++) {
            MenFeng playerMenFeng = zhuangPlayerMenFeng;
            for (int j = 0; j < 4; j++) {
                PanPlayer player = pan.findPlayerByMenFeng(playerMenFeng);
                if (player != null) {
                    if (player.countFangruShoupai() < 16) {
                        faPai(avaliablePaiList, player);
                    }
                }
                playerMenFeng = playerMenFeng.next();
            }
        }
    }

    private static void faPai(List<Pai> avaliablePaiList, PanPlayer player) {
        Pai pai = avaliablePaiList.remove(0);
        player.addShoupai(pai);
    }

    public static void faPai(long panId, MenFeng menFeng, MajiangPai paiType,
                             FaPaiServiceRepositorySet faPaiServiceRepositorySet) {
        PanRepository panRepository = faPaiServiceRepositorySet.getPanRepository();

        Pan pan = panRepository.take(panId);
        List<Pai> avaliablePaiList = pan.getAvaliablePaiList();
        //找到牌并移除
        Pai paiFound = null;
        Iterator<Pai> iterator = avaliablePaiList.iterator();
        while (iterator.hasNext()) {
            Pai pai = iterator.next();
            if (pai.getPaiType().equals(paiType)) {
                paiFound = pai;
                iterator.remove();
                break;
            }
        }
        if (paiFound != null) {
            PanPlayer player = pan.findPlayerByMenFeng(menFeng);
            player.addShoupai(paiFound);
        }
    }
}
