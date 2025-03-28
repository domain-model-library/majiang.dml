package dml.majiang.core.entity.action.da;

import dml.majiang.core.entity.action.PanPlayerAction;

public class DaAction extends PanPlayerAction {

    private int paiId;

    public DaAction() {

    }

    public DaAction(String actionPlayerId, int paiId) {
        super(actionPlayerId);
        this.paiId = paiId;
    }

    public int getPaiId() {
        return paiId;
    }

    public void setPaiId(int paiId) {
        this.paiId = paiId;
    }
}
