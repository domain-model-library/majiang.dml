package test.dml.majiang.simulator.base.entity;

public class SecondPaiSelected implements PaiExchangeState {
    private int firstPaiId;
    private int secondPaiId;

    public SecondPaiSelected() {
    }

    public SecondPaiSelected(int firstPaiId, int secondPaiId) {
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PaiExchangeState mousePressedOnPai(int paiId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PaiExchangeState mouseReleased() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PaiExchangeState mouseExitedFromPai(int paiId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
