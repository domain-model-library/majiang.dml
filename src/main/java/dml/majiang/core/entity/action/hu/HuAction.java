package dml.majiang.core.entity.action.hu;

import dml.majiang.core.entity.action.PanPlayerAction;

public class HuAction extends PanPlayerAction {

    private Hu hu;

    public HuAction() {
    }

    public HuAction(String actionPlayerId, Hu hu) {
        super(actionPlayerId);
        this.hu = hu;
    }

    public Hu getHu() {
        return hu;
    }

    public void setHu(Hu hu) {
        this.hu = hu;
    }
}
