package dml.majiang.core.entity.action.da;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;


public interface DaActionProcessor {
    public void setPanId(long panId);

    public long getPanId();

    public void process(DaAction daAction, Pan pan, PanSpecialRulesState panSpecialRulesState);
}
