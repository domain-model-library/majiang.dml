package dml.majiang.core.entity.action.hu;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;

public class PlayerSetHuHuActionProcessor implements HuActionProcessor {

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
    public void process(HuAction huAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        Hu hu = huAction.getHu();
        pan.setPlayerHu(huAction.getActionPlayerId(), hu);
    }

}
