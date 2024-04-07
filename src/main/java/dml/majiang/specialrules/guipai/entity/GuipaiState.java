package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.SpecialRuleState;

public class GuipaiState implements SpecialRuleState {
    /**
     * 目前只支持一种鬼牌
     */
    private MajiangPai guipaiType;

    public void setGuipaiType(MajiangPai guipaiType) {
        this.guipaiType = guipaiType;
    }

    public MajiangPai getGuipaiType() {
        return guipaiType;
    }
}

