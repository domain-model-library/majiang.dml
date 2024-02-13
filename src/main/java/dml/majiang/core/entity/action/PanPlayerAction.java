package dml.majiang.core.entity.action;

public abstract class PanPlayerAction {
    private int id;
    private String actionPlayerId;

    protected PanPlayerAction() {
    }

    protected PanPlayerAction(String actionPlayerId) {
        this.actionPlayerId = actionPlayerId;
    }


    public String getActionPlayerId() {
        return actionPlayerId;
    }

    public void setActionPlayerId(String actionPlayerId) {
        this.actionPlayerId = actionPlayerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
