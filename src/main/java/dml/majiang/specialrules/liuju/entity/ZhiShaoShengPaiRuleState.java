package dml.majiang.specialrules.liuju.entity;

import dml.majiang.core.entity.SpecialRuleState;

/**
 * 至少剩几张牌的规则
 */
public class ZhiShaoShengPaiRuleState implements SpecialRuleState {
    private int zhiShaoShengPai;

    public ZhiShaoShengPaiRuleState() {
    }

    public ZhiShaoShengPaiRuleState(int zhiShaoShengPai) {
        this.zhiShaoShengPai = zhiShaoShengPai;
    }

    public int getZhiShaoShengPai() {
        return zhiShaoShengPai;
    }

    public void setZhiShaoShengPai(int zhiShaoShengPai) {
        this.zhiShaoShengPai = zhiShaoShengPai;
    }
}
