package dml.majiang.core.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 一盘麻将的特殊规则状态，比如能碰不碰，飘财神等地方规则
 */
public class PanSpecialRulesState {
    private long panId;
    private Map<Class<?>, SpecialRuleState> specialRuleStateMap = new HashMap<>();

    public long getPanId() {
        return panId;
    }

    public void setPanId(long panId) {
        this.panId = panId;
    }

    public Map<Class<?>, SpecialRuleState> getSpecialRuleStateMap() {
        return specialRuleStateMap;
    }

    public void setSpecialRuleStateMap(Map<Class<?>, SpecialRuleState> specialRuleStateMap) {
        this.specialRuleStateMap = specialRuleStateMap;
    }

    public void addSpecialRuleState(SpecialRuleState specialRuleState) {
        specialRuleStateMap.put(specialRuleState.getClass(), specialRuleState);
    }

    public <T extends SpecialRuleState> T findSpecialRuleState(Class<T> specialRuleStateClass) {
        return (T) specialRuleStateMap.get(specialRuleStateClass);
    }
}
