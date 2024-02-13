package dml.majiang.core.entity.action.peng;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;

/**
 * 碰完之后的刻子可以杠手牌，要不就打牌。这是最常见的
 */
public class KezigangshoupaiPengActionUpdater implements PengActionUpdater {

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
    public void updateActions(PengAction pengAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
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
        }

    }
}
