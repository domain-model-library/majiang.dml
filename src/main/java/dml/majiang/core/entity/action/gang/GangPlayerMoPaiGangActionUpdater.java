package dml.majiang.core.entity.action.gang;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.mo.GanghouBupai;
import dml.majiang.core.entity.action.mo.MoAction;

public class GangPlayerMoPaiGangActionUpdater implements GangActionUpdater {

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
    public void updateActions(GangAction gangAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        //如果杠的动作阻塞了，就不用更新动作了
        if (gangAction.isBlockedByHigherPriorityAction()) {
            return;
        }

        pan.clearAllPlayersActionCandidates();
        PanPlayer player = pan.findPlayerById(gangAction.getActionPlayerId());

        // 杠完之后要摸牌
        player.addActionCandidate(new MoAction(player.getId(), new GanghouBupai(gangAction.getId())));
    }

}
