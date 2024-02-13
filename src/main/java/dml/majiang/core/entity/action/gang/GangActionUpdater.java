package dml.majiang.core.entity.action.gang;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;

public interface GangActionUpdater {
    public void setPanId(long panId);

    public long getPanId();

    public void updateActions(GangAction gangAction, Pan pan, PanSpecialRulesState panSpecialRulesState);
}
