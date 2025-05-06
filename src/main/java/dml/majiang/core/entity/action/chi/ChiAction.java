package dml.majiang.core.entity.action.chi;

import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.fenzu.Shunzi;

public class ChiAction extends PanPlayerAction {
    private String dachupaiPlayerId;
    private int chijinPaiId;
    private Shunzi shunzi;


    public ChiAction() {
    }

    public ChiAction(String actionPlayerId, String dachupaiPlayerId, int chijinPaiId, Shunzi shunzi) {
        super(actionPlayerId);
        this.dachupaiPlayerId = dachupaiPlayerId;
        this.chijinPaiId = chijinPaiId;
        this.shunzi = shunzi;
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

    public Shunzi getShunzi() {
        return shunzi;
    }

    public void setShunzi(Shunzi shunzi) {
        this.shunzi = shunzi;
    }

}
