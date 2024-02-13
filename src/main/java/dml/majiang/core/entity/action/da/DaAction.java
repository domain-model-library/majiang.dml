package dml.majiang.core.entity.action.da;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.action.PanPlayerAction;

public class DaAction extends PanPlayerAction {

    private MajiangPai pai;

    public DaAction() {

    }

    public DaAction(String actionPlayerId, MajiangPai pai) {
        super(actionPlayerId);
        this.pai = pai;
    }

    public MajiangPai getPai() {
        return pai;
    }

    public void setPai(MajiangPai pai) {
        this.pai = pai;
    }
}
