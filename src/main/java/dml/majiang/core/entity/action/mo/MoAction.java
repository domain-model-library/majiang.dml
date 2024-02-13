package dml.majiang.core.entity.action.mo;

import dml.majiang.core.entity.action.PanPlayerAction;

/**
 * 摸牌动作
 */
public class MoAction extends PanPlayerAction {

    private MopaiReason reason;

    public MoAction() {
    }

    public MoAction(String actionPlayerId, MopaiReason reason) {
        super(actionPlayerId);
        this.reason = reason;
    }
}
