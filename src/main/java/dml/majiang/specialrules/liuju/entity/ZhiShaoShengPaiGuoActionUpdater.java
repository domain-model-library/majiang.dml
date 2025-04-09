package dml.majiang.specialrules.liuju.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.guo.GuoAction;
import dml.majiang.core.entity.action.guo.GuoActionUpdater;

public class ZhiShaoShengPaiGuoActionUpdater implements GuoActionUpdater {
    private long panId;

    private GuoActionUpdater guoActionUpdater;

    public ZhiShaoShengPaiGuoActionUpdater(GuoActionUpdater guoActionUpdater) {
        this.panId = guoActionUpdater.getPanId();
        this.guoActionUpdater = guoActionUpdater;
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
    public void updateActions(GuoAction guoAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        guoActionUpdater.updateActions(guoAction, pan, panFrames, panSpecialRulesState);
        ZhiShaoShengPaiRuleState zhiShaoShengPaiRuleState = panSpecialRulesState.findSpecialRuleState(ZhiShaoShengPaiRuleState.class);
        if (pan.countAvaliablePai() <= zhiShaoShengPaiRuleState.getZhiShaoShengPai()) {
            //删除mo的action
            pan.removeMoActionCandidate();
        }
    }
}
