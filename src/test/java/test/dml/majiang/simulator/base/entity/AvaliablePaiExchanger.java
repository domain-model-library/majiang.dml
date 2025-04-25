package test.dml.majiang.simulator.base.entity;

import dml.majiang.core.entity.Pai;

import java.util.List;

public class AvaliablePaiExchanger extends PaiExchanger {
    private Pai pai;
    private List<Pai> avaliablePaiList;
    private int paiIndex;

    public AvaliablePaiExchanger(Pai pai, List<Pai> avaliablePaiList) {
        this.pai = pai;
        this.avaliablePaiList = avaliablePaiList;
        paiIndex = avaliablePaiList.indexOf(pai);
    }

    @Override
    protected void putPai(Pai otherPai) {
        avaliablePaiList.add(paiIndex, otherPai);
    }

    @Override
    protected Pai exchangePai(Pai otherPai) {
        for (int i = 0; i < avaliablePaiList.size(); i++) {
            Pai pai = avaliablePaiList.get(i);
            if (pai.equals(this.pai)) {
                avaliablePaiList.remove(i);
                avaliablePaiList.add(i, otherPai);
                return pai;
            }
        }
        throw new RuntimeException("pai not found");
    }

    @Override
    protected Pai takePai() {
        return avaliablePaiList.remove(paiIndex);
    }
}
