package dml.majiang.specialrules.genfeng.entity;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.da.DaActionProcessor;

public class GenfengDaActionProcessor implements DaActionProcessor {
    private long panId;
    private DaActionProcessor daActionProcessor;

    public GenfengDaActionProcessor(DaActionProcessor daActionProcessor) {
        this.daActionProcessor = daActionProcessor;
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
    public void process(DaAction action, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        daActionProcessor.process(action, pan, panFrames, panSpecialRulesState);
        GenfengState genfengState = panSpecialRulesState.findSpecialRuleState(GenfengState.class);
        PanPlayer daPlayer = pan.findPlayerById(action.getActionPlayerId());
        Pai daPai = daPlayer.findDachupai(action.getPaiId());
        genfengState.updateGenfengState(daPai.getPaiType());
    }
}
