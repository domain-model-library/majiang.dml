package dml.majiang.core.entity.fenzu;


import dml.majiang.core.entity.Pai;

public class Duizi {

    private Pai pai1;
    private Pai pai2;

    public Duizi() {
    }

    public Duizi(Pai pai1, Pai pai2) {
        this.pai1 = pai1;
        this.pai2 = pai2;
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
}
