package dml.majiang.specialrules.liuju.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.action.gang.GangActionUpdater;

public class ZhiShaoShengPaiGangActionUpdater implements GangActionUpdater {
    private long panId;

    private GangActionUpdater gangActionUpdater;

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
    public void updateActions(GangAction gangAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        gangActionUpdater.updateActions(gangAction, pan, panSpecialRulesState);
        ZhiShaoShengPaiRuleState zhiShaoShengPaiRuleState = panSpecialRulesState.findSpecialRuleState(ZhiShaoShengPaiRuleState.class);
        if (pan.countAvaliablePai() <= zhiShaoShengPaiRuleState.getZhiShaoShengPai()) {
            //删除mo的action
            pan.removeMoActionCandidate();
        }
    }
}
