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
        List<PanPlayer> players = pan.allPlayers();
        for (PanPlayer player : players) {
            if (player.hasDaActionCandidate()) {
                WaitingPlayerCursor waitingPlayerCursor = waitingPlayerCursorRepository.takeOrPutIfAbsent(pan.getId(),
                        new WaitingPlayerCursor(pan.getId()));
                waitingPlayerCursor.setWaitingPlayerId(player.getId());
                return;
            }
        }
    }

    public static void clearWaitingPlayerIdIfAnyPengGangHu(long panId,
                                                           WaitingPlayerCursorServiceRepositorySet waitingPlayerCursorServiceRepositorySet) {
        PanRepository panRepository = waitingPlayerCursorServiceRepositorySet.getPanRepository();
        WaitingPlayerCursorRepository waitingPlayerCursorRepository = waitingPlayerCursorServiceRepositorySet.getWaitingPlayerCursorRepository();

        Pan pan = panRepository.find(panId);
        List<PanPlayer> players = pan.allPlayers();
        for (PanPlayer player : players) {
            if (player.hasPengActionCandidate() || player.hasGangActionCandidate() || player.hasHuActionCandidate()) {
                WaitingPlayerCursor waitingPlayerCursor = waitingPlayerCursorRepository.takeOrPutIfAbsent(pan.getId(),
                        new WaitingPlayerCursor(pan.getId()));
                waitingPlayerCursor.setWaitingPlayerId(null);
                return;
            }
        }
    }

    public static WaitingPlayerCursor findWaitingPlayerCursor(long panId,
                                                              WaitingPlayerCursorServiceRepositorySet waitingPlayerCursorServiceRepositorySet) {
        WaitingPlayerCursorRepository waitingPlayerCursorRepository = waitingPlayerCursorServiceRepositorySet.getWaitingPlayerCursorRepository();
        return waitingPlayerCursorRepository.find(panId);
    }

}
