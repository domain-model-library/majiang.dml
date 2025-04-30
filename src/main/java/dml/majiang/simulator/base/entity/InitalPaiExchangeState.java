package dml.majiang.simulator.base.entity;

public class InitalPaiExchangeState implements PaiExchangeState {

    @Override
    public PaiExchangeState mouseEnteredInPai(int paiId) {
        return new SelectFirstPai(paiId);
    }

    @Override
    public PaiExchangeState mousePressedOnPai(int paiId) {
        return new FirstPaiSelected(paiId);
    }

    @Override
    public PaiExchangeState mouseReleased() {
        return this;
    }

    @Override
    public PaiExchangeState mouseExitedFromPai(int paiId) {
        return this;
    }
}
