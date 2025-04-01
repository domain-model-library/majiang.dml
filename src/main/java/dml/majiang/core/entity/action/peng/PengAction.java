package dml.majiang.core.entity.action.peng;

import dml.majiang.core.entity.action.PanPlayerAction;

public class PengAction extends PanPlayerAction {
    private String dachupaiPlayerId;
    private int pengPaiId;
    private int[] paiIds;

    private boolean blockedByHigherPriorityAction = false;

    public PengAction() {
    }

    public PengAction(String actionPlayerId, String dachupaiPlayerId, int pengPaiId, int[] paiIds) {
        super(actionPlayerId);
        this.dachupaiPlayerId = dachupaiPlayerId;
        this.pengPaiId = pengPaiId;
        this.paiIds = paiIds;
    }

    public String getDachupaiPlayerId() {
        return dachupaiPlayerId;
    }

    public void setDachupaiPlayerId(String dachupaiPlayerId) {
        this.dachupaiPlayerId = dachupaiPlayerId;
    }

    public int getPengPaiId() {
        return pengPaiId;
    }

    public void setPengPaiId(int pengPaiId) {
        this.pengPaiId = pengPaiId;
    }

    public int[] getPaiIds() {
        return paiIds;
    }

    public void setPaiIds(int[] paiIds) {
        this.paiIds = paiIds;
    }

    public boolean isBlockedByHigherPriorityAction() {
        return blockedByHigherPriorityAction;
    }

    public void setBlockedByHigherPriorityAction(boolean blockedByHigherPriorityAction) {
        this.blockedByHigherPriorityAction = blockedByHigherPriorityAction;
    }
}
