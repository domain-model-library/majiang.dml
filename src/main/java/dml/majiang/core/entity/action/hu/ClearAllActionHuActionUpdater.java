package dml.majiang.core.entity.action.hu;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;

/**
 * 玩家胡了之后清除玩家所有动作
 *
 * @author lsc
 */
public class ClearAllActionHuActionUpdater implements HuActionUpdater {

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
    public void updateActions(HuAction huAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
    }

}
