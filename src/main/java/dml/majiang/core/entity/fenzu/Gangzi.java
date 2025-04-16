package dml.majiang.core.entity.fenzu;


import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;

public class Gangzi {
    private Pai pai1;
    private Pai pai2;
    private Pai pai3;
    private Pai pai4;

    public Gangzi() {
    }

    public Gangzi(Pai pai1, Pai pai2, Pai pai3, Pai pai4) {
        this.pai1 = pai1;
        this.pai2 = pai2;
        this.pai3 = pai3;
        this.pai4 = pai4;
    }

    public void replacePaiType(MajiangPai paiType, MajiangPai toReplaceType) {
        pai1.replacePaiType(paiType, toReplaceType);
        pai2.replacePaiType(paiType, toReplaceType);
        pai3.replacePaiType(paiType, toReplaceType);
        pai4.replacePaiType(paiType, toReplaceType);
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

    public Pai getPai4() {
        return pai4;
    }

    public void setPai4(Pai pai4) {
        this.pai4 = pai4;
    }


}
