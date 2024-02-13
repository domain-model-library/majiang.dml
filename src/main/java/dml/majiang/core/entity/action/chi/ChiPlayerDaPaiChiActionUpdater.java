package dml.majiang.core.entity.action.chi;


import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;

/**
 * 吃的那个人要打牌
 *
 * @author neo
 */
public class ChiPlayerDaPaiChiActionUpdater implements ChiActionUpdater {

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
    public void updateActions(ChiAction chiAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        //如果吃的动作阻塞了，就不用更新动作了
        if (chiAction.isBlockedByHigherPriorityAction()) {
            return;
        }

        pan.clearAllPlayersActionCandidates();
        PanPlayer player = pan.findPlayerById(chiAction.getActionPlayerId());

        // 吃的那个人要打出牌
        player.generateDaActions();
    }

}
