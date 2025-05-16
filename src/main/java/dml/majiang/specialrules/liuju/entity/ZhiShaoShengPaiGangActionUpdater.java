package dml.majiang.specialrules.liuju.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.action.gang.GangActionUpdater;

public class ZhiShaoShengPaiGangActionUpdater implements GangActionUpdater {
    private long panId;

    private GangActionUpdater gangActionUpdater;

    public ZhiShaoShengPaiGangActionUpdater() {
    }

    public ZhiShaoShengPaiGangActionUpdater(GangActionUpdater gangActionUpdater) {
        this.panId = gangActionUpdater.getPanId();
        this.gangActionUpdater = gangActionUpdater;
    }

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
        gangActionUpdater.updateActions(gangAction, pan, panFrames, panSpecialRulesState);
        ZhiShaoShengPaiRuleState zhiShaoShengPaiRuleState = panSpecialRulesState.findSpecialRuleState(ZhiShaoShengPaiRuleState.class);
        if (pan.countAvaliablePai() <= zhiShaoShengPaiRuleState.getZhiShaoShengPai()) {
            //删除mo的action
            pan.removeMoActionCandidate();
        }
    }

    public GangActionUpdater getGangActionUpdater() {
        return gangActionUpdater;
    }

    public void setGangActionUpdater(GangActionUpdater gangActionUpdater) {
        this.gangActionUpdater = gangActionUpdater;
    }
}
