package dml.majiang.specialrules.youpengbupeng.entity;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.guo.GuoAction;
import dml.majiang.core.entity.action.guo.GuoActionProcessor;
import dml.majiang.core.entity.action.peng.PengAction;

public class YoupengbupengGuoActionProcessor implements GuoActionProcessor {
    private long panId;
    private GuoActionProcessor guoActionProcessor;

    public YoupengbupengGuoActionProcessor() {
    }

    public YoupengbupengGuoActionProcessor(GuoActionProcessor guoActionProcessor) {
        this.panId = guoActionProcessor.getPanId();
        this.guoActionProcessor = guoActionProcessor;
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
    public void process(GuoAction guoAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        guoActionProcessor.process(guoAction, pan, panFrames, panSpecialRulesState);
        //看看有没有peng的action
        PanPlayer guoPlayer = pan.findPlayerById(guoAction.getActionPlayerId());
        PengAction pengAction = guoPlayer.getPengCandidateAction();
        if (pengAction != null) {
            PanPlayer dachuPlayer = pan.findPlayerById(pengAction.getDachupaiPlayerId());
            Pai dachuPai = dachuPlayer.findDachupai(pengAction.getPengPaiId());
            YoupengbupengState youpengbupengState = panSpecialRulesState.findSpecialRuleState(YoupengbupengState.class);
            youpengbupengState.recordPlayerYoupengbupeng(guoPlayer.getId(), dachuPai.getPaiType());
        }
    }

    public GuoActionProcessor getGuoActionProcessor() {
        return guoActionProcessor;
    }

    public void setGuoActionProcessor(GuoActionProcessor guoActionProcessor) {
        this.guoActionProcessor = guoActionProcessor;
    }
}
