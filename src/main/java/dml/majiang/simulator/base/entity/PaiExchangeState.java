package dml.majiang.simulator.base.entity;

public interface PaiExchangeState {
    PaiExchangeState mouseEnteredInPai(int paiId);

    PaiExchangeState mousePressedOnPai(int paiId);

    PaiExchangeState mouseReleased();

    PaiExchangeState mouseExitedFromPai(int paiId);
}
