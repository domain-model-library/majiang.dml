package dml.majiang.core.entity.shoupai;


import dml.majiang.core.entity.MajiangPai;

/**
 * 用于替换鬼牌本牌的牌当其他牌
 *
 * @author lsc
 */
public class ActGuipaiBenpaiPaiDangPai extends ShoupaiJiesuanPai {

    public static final String dangType = "actguipaibenpaipaidang";

    private MajiangPai actGuipaiBenpaiPai;

    private MajiangPai dangpai;

    public ActGuipaiBenpaiPaiDangPai() {
    }

    public ActGuipaiBenpaiPaiDangPai(MajiangPai actGuipaiBenpaiPai, MajiangPai dangpai) {
        this.actGuipaiBenpaiPai = actGuipaiBenpaiPai;
        this.dangpai = dangpai;
    }

    @Override
    public String dangType() {
        return dangType;
    }

    @Override
    public MajiangPai getYuanPaiType() {
        return actGuipaiBenpaiPai;
    }

    @Override
    public MajiangPai getZuoyongPaiType() {
        return dangpai;
    }

    @Override
    public ShoupaiJiesuanPai copy() {
        ActGuipaiBenpaiPaiDangPai newActGuipaiBenpaiPaiDangPai = new ActGuipaiBenpaiPaiDangPai();
        newActGuipaiBenpaiPaiDangPai.setActGuipaiBenpaiPai(actGuipaiBenpaiPai);
        newActGuipaiBenpaiPaiDangPai.setDangpai(dangpai);
        newActGuipaiBenpaiPaiDangPai.setLastActionPai(isLastActionPai());
        return newActGuipaiBenpaiPaiDangPai;
    }

    @Override
    public boolean dangBenPai() {
        return actGuipaiBenpaiPai.equals(dangpai);
    }

    public MajiangPai getActGuipaiBenpaiPai() {
        return actGuipaiBenpaiPai;
    }

    public void setActGuipaiBenpaiPai(MajiangPai actGuipaiBenpaiPai) {
        this.actGuipaiBenpaiPai = actGuipaiBenpaiPai;
    }

    public MajiangPai getDangpai() {
        return dangpai;
    }

    public void setDangpai(MajiangPai dangpai) {
        this.dangpai = dangpai;
    }

}
