package dml.majiang.core.entity.action.mo;


import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.fenzu.GangType;

/**
 * 杠后补牌
 *
 * @author Neo
 */
public class GanghouBupai implements MopaiReason {

    private MajiangPai gangPai;

    private GangType gangType;

    public GanghouBupai() {
    }

    public GanghouBupai(MajiangPai gangPai, GangType gangType) {
        this.gangPai = gangPai;
        this.gangType = gangType;
    }

    public MajiangPai getGangPai() {
        return gangPai;
    }

    public void setGangPai(MajiangPai gangPai) {
        this.gangPai = gangPai;
    }

    public GangType getGangType() {
        return gangType;
    }

    public void setGangType(GangType gangType) {
        this.gangType = gangType;
    }

}
