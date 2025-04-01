package dml.majiang.core.entity.action.chi;

import dml.majiang.core.entity.action.PanPlayerAction;

public class ChiAction extends PanPlayerAction {
    private String dachupaiPlayerId;
    private int chijinPaiId;
    private int[] shunziPaiIds;

    private boolean blockedByHigherPriorityAction = false;

    public ChiAction() {
    }

    public ChiAction(String actionPlayerId, String dachupaiPlayerId, int chijinPaiId, int[] shunziPaiIds) {
        super(actionPlayerId);
        this.dachupaiPlayerId = dachupaiPlayerId;
        this.chijinPaiId = chijinPaiId;
        this.shunziPaiIds = shunziPaiIds;
    }

    public String getDachupaiPlayerId() {
        return dachupaiPlayerId;
    }

    public void setDachupaiPlayerId(String dachupaiPlayerId) {
        this.dachupaiPlayerId = dachupaiPlayerId;
    }

    public int getChijinPaiId() {
        return chijinPaiId;
    }

    public void setChijinPaiId(int chijinPaiId) {
        this.chijinPaiId = chijinPaiId;
    }

    public int[] getShunziPaiIds() {
        return shunziPaiIds;
    }

    public void setShunziPaiIds(int[] shunziPaiIds) {
        this.shunziPaiIds = shunziPaiIds;
    }

    public boolean isBlockedByHigherPriorityAction() {
        return blockedByHigherPriorityAction;
    }

    public void setBlockedByHigherPriorityAction(boolean blockedByHigherPriorityAction) {
        this.blockedByHigherPriorityAction = blockedByHigherPriorityAction;
    }
}
