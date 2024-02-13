package dml.majiang.core.entity.action.gang;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.fenzu.GangType;

public class GangAction extends PanPlayerAction {
    private String dachupaiPlayerId;
    private MajiangPai pai;
    private GangType gangType;

    private boolean blockedByHigherPriorityAction = false;

    public GangAction() {
    }

    public GangAction(String actionPlayerId, String dachupaiPlayerId, MajiangPai pai, GangType gangType) {
        super(actionPlayerId);
        this.dachupaiPlayerId = dachupaiPlayerId;
        this.pai = pai;
        this.gangType = gangType;
    }

    public String getDachupaiPlayerId() {
        return dachupaiPlayerId;
    }

    public void setDachupaiPlayerId(String dachupaiPlayerId) {
        this.dachupaiPlayerId = dachupaiPlayerId;
    }

    public MajiangPai getPai() {
        return pai;
    }

    public void setPai(MajiangPai pai) {
        this.pai = pai;
    }

    public GangType getGangType() {
        return gangType;
    }

    public void setGangType(GangType gangType) {
        this.gangType = gangType;
    }

    public boolean isBlockedByHigherPriorityAction() {
        return blockedByHigherPriorityAction;
    }

    public void setBlockedByHigherPriorityAction(boolean blockedByHigherPriorityAction) {
        this.blockedByHigherPriorityAction = blockedByHigherPriorityAction;
    }
}
