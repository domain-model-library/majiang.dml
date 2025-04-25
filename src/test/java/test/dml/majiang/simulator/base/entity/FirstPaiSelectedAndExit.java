package test.dml.majiang.simulator.base.entity;

public class FirstPaiSelectedAndExit implements PaiExchangeState {
    private int selectedPaiId;

    public FirstPaiSelectedAndExit() {

    }

    public FirstPaiSelectedAndExit(int selectedPaiId) {
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
        if (this.selectedPaiId == paiId) {
            return new FirstPaiSelected(this.selectedPaiId);
        } else {
            return new SelectSecondPai(this.selectedPaiId, paiId);
        }
    }

    @Override
    public PaiExchangeState mousePressedOnPai(int paiId) {
        return new FirstPaiSelected(paiId);
    }

    @Override
    public PaiExchangeState mouseReleased() {
        return new InitalPaiExchangeState();
    }

    @Override
    public PaiExchangeState mouseExitedFromPai(int paiId) {
        return this;
    }
}
