package dml.majiang.core.entity.action.guo;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;

public class DoNothingGuoActionProcessor implements GuoActionProcessor {

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
    public void process(GuoAction guoAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        // 过的话就是啥也不做
    }

}
