package test.dml.majiang.simulator.base.entity;

import dml.majiang.core.entity.Pai;

public abstract class PaiExchanger {
    public void exchange(PaiExchanger other) {
        Pai otherPai = other.takePai();
        Pai thisPai = exchangePai(otherPai);
        other.putPai(thisPai);
    }

    protected abstract void putPai(Pai thisPai);

    protected abstract Pai exchangePai(Pai otherPai);

    protected abstract Pai takePai();
}
