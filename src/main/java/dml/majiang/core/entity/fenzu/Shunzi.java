package dml.majiang.core.entity.fenzu;


import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.shoupai.ShoupaiShunziZu;

public class Shunzi implements MajiangPaiFenZu {

    private Pai pai1;
    private Pai pai2;
    private Pai pai3;

    public Shunzi() {
    }

    public Shunzi(Pai pai1, Pai pai2, Pai pai3) {
        this.pai1 = pai1;
        this.pai2 = pai2;
        this.pai3 = pai3;
    }

    @Override
    public ShoupaiShunziZu generateShoupaiMajiangPaiFenZuSkeleton() {
        ShoupaiShunziZu shoupaiShunziZu = new ShoupaiShunziZu();
        shoupaiShunziZu.setShunzi(this);
        return shoupaiShunziZu;
    }

    @Override
    public int countPai(MajiangPai paiType) {
        if (paiType.equals(pai1) || paiType.equals(pai2) || paiType.equals(pai3)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public MajiangPai[] toPaiArray() {
        return new MajiangPai[]{pai1.getPaiType(), pai2.getPaiType(), pai3.getPaiType()};
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

    public boolean equals(Object o) {
        Shunzi s = (Shunzi) o;
        return (pai1.equals(s.pai1) && pai2.equals(s.pai2) && pai3.equals(s.pai3));
    }

    public int hashCode() {
        return pai1.hashCode() * 100 + pai2.hashCode() * 10 + pai3.hashCode();
    }

}
