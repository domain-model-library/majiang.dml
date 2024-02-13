package dml.majiang.core.entity.action.gang;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;


public interface GangActionProcessor {
    public void setPanId(long panId);

    public long getPanId();

    public void process(GangAction gangAction, Pan pan, PanSpecialRulesState panSpecialRulesState);
}
