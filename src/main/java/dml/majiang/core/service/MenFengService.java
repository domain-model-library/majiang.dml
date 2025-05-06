package dml.majiang.core.service;

import dml.majiang.core.entity.MenFeng;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.service.repositoryset.MenFengServiceRepositorySet;

import java.util.List;
import java.util.Random;

public class MenFengService {
    public static void setPlayerMenFengRandomly(long panId, MenFengServiceRepositorySet menFengServiceRepositorySet) {
        setPlayerMenFengRandomly(panId, new MenFeng[]{MenFeng.dong, MenFeng.nan, MenFeng.xi, MenFeng.bei}, menFengServiceRepositorySet);
    }

    /**
     * 随机设置玩家的门风
     *
     * @param panId
     * @param menFengs                    不足4人时需自定义采用的门风列表
     * @param menFengServiceRepositorySet
     */
    public static void setPlayerMenFengRandomly(long panId, MenFeng[] menFengs,
                                                MenFengServiceRepositorySet menFengServiceRepositorySet) {
        PanRepository panRepository = menFengServiceRepositorySet.getPanRepository();
        Pan pan = panRepository.take(panId);
        List<String> playerIds = pan.allPlayerIds();
        Random random = new Random(panId);

        for (int i = 0; i < menFengs.length; i++) {
            int index = random.nextInt(playerIds.size());
            String playerId = playerIds.remove(index);
            pan.setPlayerMenFeng(playerId, menFengs[i]);
        }

    }
}
