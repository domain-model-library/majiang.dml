package dml.majiang.core.entity;

import dml.majiang.core.entity.action.PanPlayerAction;

public class PanFrame {
    private PanPlayerAction action;
    private Pan panAfterAction;

    public PanPlayerAction getAction() {
        return action;
    }

    public void setAction(PanPlayerAction action) {
        this.action = action;
    }

    public Pan getPanAfterAction() {
        return panAfterAction;
    }

    public void setPanAfterAction(Pan panAfterAction) {
        this.panAfterAction = panAfterAction;
    }
}
