package dml.majiang.core.entity.action.gang;

import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.fenzu.GangType;

public class GangAction extends PanPlayerAction {
    private String dachupaiPlayerId;
    private int gangPaiId;
    private int[] paiIds;
    private GangType gangType;


    public GangAction() {
    }

    public GangAction(String actionPlayerId, String dachupaiPlayerId, int gangPaiId, int[] paiIds, GangType gangType) {
        super(actionPlayerId);
        this.dachupaiPlayerId = dachupaiPlayerId;
        this.gangPaiId = gangPaiId;
        this.paiIds = paiIds;
        this.gangType = gangType;
    }

    public String getDachupaiPlayerId() {
        return dachupaiPlayerId;
    }

    public void setDachupaiPlayerId(String dachupaiPlayerId) {
        this.dachupaiPlayerId = dachupaiPlayerId;
    }

    public int getGangPaiId() {
        return gangPaiId;
    }

    public void setGangPaiId(int gangPaiId) {
        this.gangPaiId = gangPaiId;
    }

    public int[] getPaiIds() {
        return paiIds;
    }

    public void setPaiIds(int[] paiIds) {
        this.paiIds = paiIds;
    }

    public GangType getGangType() {
        return gangType;
    }

    public void setGangType(GangType gangType) {
        this.gangType = gangType;
    }

}
