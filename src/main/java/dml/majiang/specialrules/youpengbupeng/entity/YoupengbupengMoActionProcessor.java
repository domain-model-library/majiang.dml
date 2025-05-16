package dml.majiang.specialrules.youpengbupeng.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.action.mo.MoActionProcessor;

public class YoupengbupengMoActionProcessor implements MoActionProcessor {
    private long panId;
    private MoActionProcessor moActionProcessor;

    public YoupengbupengMoActionProcessor() {
    }

    public YoupengbupengMoActionProcessor(MoActionProcessor moActionProcessor) {
        this.panId = moActionProcessor.getPanId();
        this.moActionProcessor = moActionProcessor;
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
    public void process(MoAction moAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        moActionProcessor.process(moAction, pan, panFrames, panSpecialRulesState);
        YoupengbupengState youpengbupengState = panSpecialRulesState.findSpecialRuleState(YoupengbupengState.class);
        youpengbupengState.clearYoupengbupengState(moAction.getActionPlayerId());
    }

    public MoActionProcessor getMoActionProcessor() {
        return moActionProcessor;
    }

    public void setMoActionProcessor(MoActionProcessor moActionProcessor) {
        this.moActionProcessor = moActionProcessor;
    }
}
