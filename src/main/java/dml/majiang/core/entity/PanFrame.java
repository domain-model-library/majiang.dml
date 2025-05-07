package dml.majiang.core.entity;

import dml.majiang.core.entity.action.PanPlayerAction;

public class PanFrame {
    private int number;
    private PanPlayerAction action;
    private Pan panAfterAction;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

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
