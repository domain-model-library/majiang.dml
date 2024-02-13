package dml.majiang.core.entity.action.guo;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;

public interface GuoActionUpdater {

    public void setPanId(long panId);

    public long getPanId();

    public void updateActions(GuoAction guoAction, Pan pan, PanSpecialRulesState panSpecialRulesState);
}
