package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.chi.ChiAction;
import dml.majiang.core.entity.action.chi.ChiActionUpdater;

public class GuipaiChiActionUpdater implements ChiActionUpdater {

    private long panId;

    @Override
    public void setPanId(long panId) {
        this.panId = panId;

    }

    @Override
    public long getPanId() {
        return this.panId;
    }

    @Override
    public void updateActions(ChiAction chiAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        //如果吃的动作阻塞了，就不用更新动作了
        if (chiAction.isBlockedByHigherPriorityAction()) {
            return;
        }

        pan.clearAllPlayersActionCandidates();
        PanPlayer player = pan.findPlayerById(chiAction.getActionPlayerId());

        // 吃的那个人要打出牌
        player.generateDaActions();
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        player.removeDaActionCandidateForPaiType(guipaiState.getGuipaiType());
    }

}