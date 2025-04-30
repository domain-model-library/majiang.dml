package dml.majiang.simulator.base.entity;

public class SelectFirstPai implements PaiExchangeState {
    private int selectedPaiId;

    public SelectFirstPai() {

    }

    public SelectFirstPai(int selectedPaiId) {
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
        return new InitalPaiExchangeState();
    }
}
