package dml.majiang.core.service;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.MenFeng;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.service.repositoryset.FaPaiServiceRepositorySet;

import java.util.List;

public class FaPaiService {
    public static void faPai13Shoupai(long panId, FaPaiServiceRepositorySet faPaiServiceRepositorySet) {
        PanRepository panRepository = faPaiServiceRepositorySet.getPanRepository();

        Pan pan = panRepository.take(panId);
        List<MajiangPai> avaliablePaiList = pan.getAvaliablePaiList();
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
        List<MajiangPai> avaliablePaiList = pan.getAvaliablePaiList();
        MenFeng zhuangPlayerMenFeng = pan.findMenFengForZhuang();
        for (int i = 0; i < 16; i++) {
            MenFeng playerMenFeng = zhuangPlayerMenFeng;
            for (int j = 0; j < 4; j++) {
                PanPlayer player = pan.findPlayerByMenFeng(playerMenFeng);
                if (player != null) {
                    if (player.countFangruShoupai() == 16) {
                        continue;
                    }
                    faPai(avaliablePaiList, player);
                }
                playerMenFeng = playerMenFeng.next();
            }
        }
    }

    private static void faPai(List<MajiangPai> avaliablePaiList, PanPlayer player) {
        MajiangPai pai = avaliablePaiList.remove(0);
        player.addShoupai(pai);
    }

    public static void faPai(List<MajiangPai> avaliablePaiList, PanPlayer player, MajiangPai pai) {
        boolean fapaiFound = avaliablePaiList.remove(pai);
        if (fapaiFound) {
            player.addShoupai(pai);
        }
    }
}
