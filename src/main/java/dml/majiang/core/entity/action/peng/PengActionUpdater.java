package dml.majiang.core.entity.action.peng;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;

public interface PengActionUpdater {
    public void setPanId(long panId);

    public long getPanId();

    public void updateActions(PengAction pengAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState);
}
