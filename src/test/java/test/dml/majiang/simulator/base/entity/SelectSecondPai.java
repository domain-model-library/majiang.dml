package test.dml.majiang.simulator.base.entity;

public class SelectSecondPai implements PaiExchangeState {
    private int firstPaiId;
    private int secondPaiId;

    public SelectSecondPai() {
    }

    public SelectSecondPai(int firstPaiId, int secondPaiId) {
        this.firstPaiId = firstPaiId;
        this.secondPaiId = secondPaiId;
    }

    public int getFirstPaiId() {
        return firstPaiId;
    }

    public void setFirstPaiId(int firstPaiId) {
        this.firstPaiId = firstPaiId;
    }

    public int getSecondPaiId() {
        return secondPaiId;
    }

    public void setSecondPaiId(int secondPaiId) {
        this.secondPaiId = secondPaiId;
    }

    @Override
    public PaiExchangeState mouseEnteredInPai(int paiId) {
        if (paiId == this.firstPaiId) {
            return new FirstPaiSelected(paiId);
        } else if (paiId == this.secondPaiId) {
            return this;
        } else {
            return new SelectSecondPai(this.firstPaiId, paiId);
        }
    }

    @Override
    public PaiExchangeState mousePressedOnPai(int paiId) {
        return new FirstPaiSelected(paiId);
    }

    @Override
    public PaiExchangeState mouseReleased() {
        return new SecondPaiSelected(this.firstPaiId, this.secondPaiId);
    }

    @Override
    public PaiExchangeState mouseExitedFromPai(int paiId) {
        return new FirstPaiSelectedAndExit(this.firstPaiId);
    }
}
