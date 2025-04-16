package dml.majiang.core.entity.fenzu;


import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;

public class Shunzi {

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

    public void replacePaiType(MajiangPai paiType, MajiangPai toReplaceType) {
        pai1.replacePaiType(paiType, toReplaceType);
        pai2.replacePaiType(paiType, toReplaceType);
        pai3.replacePaiType(paiType, toReplaceType);
    }

    public int[] getShunziPaiIds() {
        return new int[]{pai1.getId(), pai2.getId(), pai3.getId()};
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
