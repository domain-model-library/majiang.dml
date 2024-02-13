package dml.majiang.core.entity.action.gang;

import dml.majiang.core.entity.Pan;
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
    public void updateActions(GangAction gangAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        PanPlayer player = pan.findPlayerById(gangAction.getActionPlayerId());

        // 杠完之后要摸牌
        player.addActionCandidate(new MoAction(player.getId(), new GanghouBupai(gangAction.getPai(), gangAction.getGangType())));
    }

}
