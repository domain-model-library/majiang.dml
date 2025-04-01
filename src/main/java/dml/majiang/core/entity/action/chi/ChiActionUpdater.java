package dml.majiang.core.entity.action.chi;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;


public interface ChiActionUpdater {

    public void setPanId(long panId);

    public long getPanId();

    public void updateActions(ChiAction chiAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState);
}
