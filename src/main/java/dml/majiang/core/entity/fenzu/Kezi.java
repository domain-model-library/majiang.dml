package dml.majiang.core.entity.fenzu;


import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.shoupai.ShoupaiKeziZu;

public class Kezi implements MajiangPaiFenZu {

    private Pai pai1;
    private Pai pai2;
    private Pai pai3;

    public Kezi() {
    }

    public Kezi(Pai pai1, Pai pai2, Pai pai3) {
        this.pai1 = pai1;
        this.pai2 = pai2;
        this.pai3 = pai3;
    }

    @Override
    public MajiangPai[] toPaiArray() {
        return new MajiangPai[]{pai1.getPaiType(), pai2.getPaiType(), pai3.getPaiType()};
    }

    @Override
    public ShoupaiKeziZu generateShoupaiMajiangPaiFenZuSkeleton() {
        ShoupaiKeziZu shoupaiKeziZu = new ShoupaiKeziZu();
        shoupaiKeziZu.setKezi(this);
        return shoupaiKeziZu;
    }

    @Override
    public int countPai(MajiangPai paiType) {
        if (paiType.equals(pai1.getPaiType())) {
            return 3;
        } else {
            return 0;
        }
    }

    public MajiangPai getPaiType() {
        return pai1.getPaiType();
    }

    public Pai getPai1() {
        return pai1;
    }

    public void setPai1(Pai pai1) {
        this.pai1 = pai1;
    }

    public Pai getPai2() {
        return pai2;
    }

    public void setPai2(Pai pai2) {
        this.pai2 = pai2;
    }

    public Pai getPai3() {
        return pai3;
    }

    public void setPai3(Pai pai3) {
        this.pai3 = pai3;
    }


}
