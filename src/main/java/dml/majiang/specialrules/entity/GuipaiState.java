package dml.majiang.specialrules.entity;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.SpecialRuleState;

public class GuipaiState implements SpecialRuleState {
    private MajiangPai guipaiType;

    public void setGuipaiType(MajiangPai guipaiType) {
        this.guipaiType = guipaiType;
    }

    public MajiangPai getGuipaiType() {
        return guipaiType;
    }
}

