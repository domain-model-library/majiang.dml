package dml.majiang.core.entity.action.da;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;

/**
 * 打之后能吃碰杠胡，最常见的打之后的动作
 */
public abstract class ChiPengGangHuDaActionUpdater implements DaActionUpdater {
    public void updateActions(DaAction daAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        PanPlayer daPlayer = pan.findPlayerById(daAction.getActionPlayerId());
        PanPlayer xiajiaPlayer = pan.findNextMenFengPlayer(daPlayer);
        int daPaiId = daAction.getPaiId();

        // 下家可以吃
        xiajiaPlayer.tryChiAndGenerateCandidateActions(daPlayer, daPaiId);

        // 其他的可以碰杠胡
        while (true) {
            if (xiajiaPlayer.getId().equals(daAction.getActionPlayerId())) {
                break;
            }
            //碰
            xiajiaPlayer.tryPengAndGenerateCandidateAction(daPlayer, daAction.getPaiId());

            //杠
            xiajiaPlayer.tryGangdachuAndGenerateCandidateAction(daPlayer, daAction.getPaiId());

            //胡
            tryAndGenerateHuCandidateAction(daAction, pan, panSpecialRulesState, xiajiaPlayer);

            // 需要有“过”
            xiajiaPlayer.checkAndGenerateGuoCandidateAction(daAction);

            xiajiaPlayer = pan.findNextMenFengPlayer(xiajiaPlayer);
        }

        // 如果所有玩家啥也做不了,那就下家摸牌
        if (pan.allPlayerHasNoActionCandidates()) {
            xiajiaPlayer = pan.findNextMenFengPlayer(daPlayer);
            xiajiaPlayer.addActionCandidate(new MoAction(xiajiaPlayer.getId(), new LundaoMopai()));
        }
    }

    protected abstract void tryAndGenerateHuCandidateAction(DaAction daAction, Pan pan,
                                                            PanSpecialRulesState panSpecialRulesState, PanPlayer panPlayer);
}
