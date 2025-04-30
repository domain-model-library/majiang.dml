package dml.majiang.simulator.base.entity;

public class FirstPaiSelected implements PaiExchangeState {
    private int selectedPaiId;

    public FirstPaiSelected() {
    }

    public FirstPaiSelected(int selectedPaiId) {
        this.selectedPaiId = selectedPaiId;
    }

    public int getSelectedPaiId() {
        return selectedPaiId;
    }

    public void setSelectedPaiId(int selectedPaiId) {
        this.selectedPaiId = selectedPaiId;
    }

    @Override
    public PaiExchangeState mouseEnteredInPai(int paiId) {
        if (paiId != this.selectedPaiId) {
            return new SelectSecondPai(selectedPaiId, paiId);
        } else {
            return this;
        }
    }

    @Override
    public PaiExchangeState mousePressedOnPai(int paiId) {
        return new FirstPaiSelected(paiId);
    }

    @Override
    public PaiExchangeState mouseReleased() {
        return new SelectFirstPai(selectedPaiId);
    }

    @Override
    public PaiExchangeState mouseExitedFromPai(int paiId) {
        return new FirstPaiSelectedAndExit(paiId);
    }
}
