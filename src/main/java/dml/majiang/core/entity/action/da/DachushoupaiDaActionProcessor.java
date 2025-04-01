package dml.majiang.core.entity.action.da;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;

/**
 * 最常见的打出手牌
 *
 * @author Neo
 */
public class DachushoupaiDaActionProcessor implements DaActionProcessor {

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
    public void process(DaAction action, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        pan.playerDaChuPai(action.getActionPlayerId(), action.getPaiId());
    }

}
