package dml.majiang.core.entity.action.chi;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;


public interface ChiActionProcessor {

    public void setPanId(long panId);

    public long getPanId();

    public void process(ChiAction chiAction, Pan pan, PanSpecialRulesState panSpecialRulesState);
}
