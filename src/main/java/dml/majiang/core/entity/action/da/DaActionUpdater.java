package dml.majiang.core.entity.action.da;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;

public interface DaActionUpdater {

    public void setPanId(long panId);

    public long getPanId();

    public void updateActions(DaAction daAction, Pan pan, PanSpecialRulesState panSpecialRulesState);
}
