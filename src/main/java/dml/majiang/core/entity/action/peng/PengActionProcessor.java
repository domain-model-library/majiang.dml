package dml.majiang.core.entity.action.peng;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;


public interface PengActionProcessor {

    public void setPanId(long panId);

    public long getPanId();

    public void process(PengAction pengAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState);
}
