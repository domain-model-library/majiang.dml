package dml.majiang.core.service;

import dml.majiang.core.entity.MenFeng;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.service.repositoryset.MenFengServiceRepositorySet;

import java.util.List;
import java.util.Random;

public class MenFengService {
    public static void setPlayerMenFengRandomly(long panId, MenFengServiceRepositorySet menFengServiceRepositorySet) {
        PanRepository panRepository = menFengServiceRepositorySet.getPanRepository();
        Pan pan = panRepository.take(panId);
        List<String> playerIds = pan.allPlayerIds();
        Random random = new Random(panId);

        //随机选一个玩家设为东
        int dongIndex = random.nextInt(playerIds.size());
        String dongPlayerId = playerIds.remove(dongIndex);
        pan.setPlayerMenFeng(dongPlayerId, MenFeng.dong);

        //随机选一个玩家设为南
        int nanIndex = random.nextInt(playerIds.size());
        String nanPlayerId = playerIds.remove(nanIndex);
        pan.setPlayerMenFeng(nanPlayerId, MenFeng.nan);

        //随机选一个玩家设为西
        int xiIndex = random.nextInt(playerIds.size());
        String xiPlayerId = playerIds.remove(xiIndex);
        pan.setPlayerMenFeng(xiPlayerId, MenFeng.xi);

        //剩下的玩家设为北
        String beiPlayerId = playerIds.get(0);
        pan.setPlayerMenFeng(beiPlayerId, MenFeng.bei);
    }
}
