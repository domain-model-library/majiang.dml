package dml.majiang.core.entity.action;

public abstract class PanPlayerAction {
    protected int id;
    protected String actionPlayerId;
    protected boolean blockedByHigherPriorityAction;

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

    public boolean isBlockedByHigherPriorityAction() {
        return blockedByHigherPriorityAction;
    }

    public void setBlockedByHigherPriorityAction(boolean blockedByHigherPriorityAction) {
        this.blockedByHigherPriorityAction = blockedByHigherPriorityAction;
    }
}
