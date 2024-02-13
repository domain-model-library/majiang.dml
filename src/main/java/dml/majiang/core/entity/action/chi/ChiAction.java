package dml.majiang.core.entity.action.chi;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.fenzu.Shunzi;

public class ChiAction extends PanPlayerAction {
    private String dachupaiPlayerId;
    private MajiangPai chijinPai;
    private Shunzi shunzi;

    private boolean blockedByHigherPriorityAction = false;

    public ChiAction() {
    }

    public ChiAction(String actionPlayerId, String dachupaiPlayerId, MajiangPai chijinPai, Shunzi shunzi) {
        super(actionPlayerId);
        this.dachupaiPlayerId = dachupaiPlayerId;
        this.chijinPai = chijinPai;
        this.shunzi = shunzi;
    }

    public String getDachupaiPlayerId() {
        return dachupaiPlayerId;
    }

    public void setDachupaiPlayerId(String dachupaiPlayerId) {
        this.dachupaiPlayerId = dachupaiPlayerId;
    }

    public MajiangPai getChijinPai() {
        return chijinPai;
    }

    public void setChijinPai(MajiangPai chijinPai) {
        this.chijinPai = chijinPai;
    }

    public Shunzi getShunzi() {
        return shunzi;
    }

    public void setShunzi(Shunzi shunzi) {
        this.shunzi = shunzi;
    }

    public boolean isBlockedByHigherPriorityAction() {
        return blockedByHigherPriorityAction;
    }

    public void setBlockedByHigherPriorityAction(boolean blockedByHigherPriorityAction) {
        this.blockedByHigherPriorityAction = blockedByHigherPriorityAction;
    }
}
