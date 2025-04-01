package dml.majiang.core.entity.action.mo;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;


public interface MoActionProcessor {

    public void setPanId(long panId);

    public long getPanId();

    public void process(MoAction moAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState);
}
