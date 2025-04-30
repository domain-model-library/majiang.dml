package dml.majiang.simulator.base.entity;

public class PaiExchangeStateContainer {
    private PaiExchangeState state;

    public PaiExchangeStateContainer() {
        this.state = new InitalPaiExchangeState();
    }

    public void mouseEnteredInPai(int paiId) {
        this.state = this.state.mouseEnteredInPai(paiId);
    }

    public void mousePressedOnPai(int paiId) {
        this.state = this.state.mousePressedOnPai(paiId);
    }

    public void mouseReleased() {
        this.state = this.state.mouseReleased();
    }

    public void mouseExitedFromPai(int paiId) {
        this.state = this.state.mouseExitedFromPai(paiId);
    }

    public PaiExchangeState getState() {
        return state;
    }

    public void setState(PaiExchangeState state) {
        this.state = state;
    }
}
