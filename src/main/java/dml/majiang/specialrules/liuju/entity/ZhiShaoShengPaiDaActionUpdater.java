package dml.majiang.specialrules.liuju.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.da.DaActionUpdater;

public class ZhiShaoShengPaiDaActionUpdater implements DaActionUpdater {
    private long panId;

    private DaActionUpdater daActionUpdater;

    public ZhiShaoShengPaiDaActionUpdater(DaActionUpdater daActionUpdater) {
        this.panId = daActionUpdater.getPanId();
        this.daActionUpdater = daActionUpdater;
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
    public void updateActions(DaAction daAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        daActionUpdater.updateActions(daAction, pan, panSpecialRulesState);
        ZhiShaoShengPaiRuleState zhiShaoShengPaiRuleState = panSpecialRulesState.findSpecialRuleState(ZhiShaoShengPaiRuleState.class);
        if (pan.countAvaliablePai() <= zhiShaoShengPaiRuleState.getZhiShaoShengPai()) {
            //删除mo的action
            pan.removeMoActionCandidate();
        }
    }
}
