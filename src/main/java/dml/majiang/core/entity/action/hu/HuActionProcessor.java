package dml.majiang.core.entity.action.hu;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;


public interface HuActionProcessor {
    public void setPanId(long panId);

    public long getPanId();

    public void process(HuAction huAction, Pan pan, PanSpecialRulesState panSpecialRulesState);
}
