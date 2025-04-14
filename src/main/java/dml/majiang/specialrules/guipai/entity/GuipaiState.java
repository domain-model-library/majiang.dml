package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.SpecialRuleState;

import java.util.List;

public class GuipaiState implements SpecialRuleState {
    /**
     * 目前只支持一种鬼牌
     */
    private MajiangPai guipaiType;

    /**
     * 鬼牌可以替代的牌
     */
    private List<MajiangPai> guipaiActPaiTypes;

    public void setGuipaiType(MajiangPai guipaiType) {
        this.guipaiType = guipaiType;
    }

    public MajiangPai getGuipaiType() {
        return guipaiType;
    }

    public List<MajiangPai> getGuipaiActPaiTypes() {
        return guipaiActPaiTypes;
    }

    public void setGuipaiActPaiTypes(List<MajiangPai> guipaiActPaiTypes) {
        this.guipaiActPaiTypes = guipaiActPaiTypes;
    }
}

