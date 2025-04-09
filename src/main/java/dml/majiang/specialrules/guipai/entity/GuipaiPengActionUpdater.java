package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.peng.PengAction;
import dml.majiang.core.entity.action.peng.PengActionUpdater;

public class GuipaiPengActionUpdater implements PengActionUpdater {

    private long panId;

    @Override
    public void setPanId(long panId) {
        this.panId = panId;
    }

    @Override
    public long getPanId() {
        return panId;
    }

    @Override
    public void updateActions(PengAction pengAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        if (pengAction.isBlockedByHigherPriorityAction()) {
            return;
        }
        pan.clearAllPlayersActionCandidates();

        // 刻子杠手牌
        PanPlayer player = pan.findPlayerById(pengAction.getActionPlayerId());
        player.tryKezigangshoupaiAndGenerateCandidateAction();

        // 需要有“过”
        player.checkAndGenerateGuoCandidateAction(pengAction);

        //打牌
        if (player.getActionCandidates().isEmpty()) {
            player.generateDaActions();
            GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
            player.removeDaActionCandidateForPaiType(guipaiState.getGuipaiType());
        }

    }
}
