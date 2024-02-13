package dml.majiang.core.entity.action.hu;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
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
    public void process(HuAction huAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        Hu hu = huAction.getHu();
        PanPlayer huPlayer = pan.findPlayerById(huAction.getActionPlayerId());
        huPlayer.setHu(hu);
    }

}
