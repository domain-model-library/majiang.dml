package dml.majiang.core.service;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.WaitingPlayerCursor;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.repository.WaitingPlayerCursorRepository;
import dml.majiang.core.service.repositoryset.WaitingPlayerCursorServiceRepositorySet;

import java.util.List;

/**
 * 用于桌子中间显示等待哪个玩家操作的光标
 */
public class WaitingPlayerCursorService {
    public static void updateWaitingPlayerIdToDaPlayer(long panId,
                                                       WaitingPlayerCursorServiceRepositorySet waitingPlayerCursorServiceRepositorySet) {
        PanRepository panRepository = waitingPlayerCursorServiceRepositorySet.getPanRepository();
        WaitingPlayerCursorRepository waitingPlayerCursorRepository = waitingPlayerCursorServiceRepositorySet.getWaitingPlayerCursorRepository();

        Pan pan = panRepository.find(panId);
        WaitingPlayerCursor waitingPlayerCursor = waitingPlayerCursorRepository.takeOrPutIfAbsent(pan.getId(),
                new WaitingPlayerCursor(pan.getId()));
        List<PanPlayer> players = pan.allPlayers();
        for (PanPlayer player : players) {
            if (player.hasDaActionCandidate()) {
                waitingPlayerCursor.setWaitingPlayerId(player.getId());
                return;
            }
        }
        waitingPlayerCursor.setWaitingPlayerId(null);
    }

    public static WaitingPlayerCursor findWaitingPlayerCursor(long panId,
                                                              WaitingPlayerCursorServiceRepositorySet waitingPlayerCursorServiceRepositorySet) {
        WaitingPlayerCursorRepository waitingPlayerCursorRepository = waitingPlayerCursorServiceRepositorySet.getWaitingPlayerCursorRepository();
        return waitingPlayerCursorRepository.find(panId);
    }

}
