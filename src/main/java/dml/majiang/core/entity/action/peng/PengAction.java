package dml.majiang.core.entity.action.peng;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.action.PanPlayerAction;

public class PengAction extends PanPlayerAction {
    private String dachupaiPlayerId;
    private MajiangPai pai;

    private boolean blockedByHigherPriorityAction = false;

    public PengAction() {
    }

    public PengAction(String actionPlayerId, String dachupaiPlayerId, MajiangPai pai) {
        super(actionPlayerId);
        this.dachupaiPlayerId = dachupaiPlayerId;
        this.pai = pai;
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

    public boolean isBlockedByHigherPriorityAction() {
        return blockedByHigherPriorityAction;
    }

    public void setBlockedByHigherPriorityAction(boolean blockedByHigherPriorityAction) {
        this.blockedByHigherPriorityAction = blockedByHigherPriorityAction;
    }
}
