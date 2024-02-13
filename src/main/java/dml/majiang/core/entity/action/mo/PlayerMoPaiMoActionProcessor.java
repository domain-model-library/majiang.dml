package dml.majiang.core.entity.action.mo;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;

/**
 * 最常见的摸牌处理，玩家摸一张牌
 */
public class PlayerMoPaiMoActionProcessor implements MoActionProcessor {

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
    public void process(MoAction moAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        pan.playerMoPai(moAction.getActionPlayerId());
    }
}
