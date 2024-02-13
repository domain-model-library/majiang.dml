package dml.majiang.core.entity.action.guo;

import dml.majiang.core.entity.action.PanPlayerAction;

/**
 * 过动作
 */
public class GuoAction extends PanPlayerAction {

    /**
     * 过动作是因为哪个动作执行之后导致的
     */
    private PanPlayerAction causedByAction;

    public GuoAction() {
    }

    public GuoAction(String actionPlayerId, PanPlayerAction causedByAction) {
        super(actionPlayerId);
        this.causedByAction = causedByAction;
    }

    public PanPlayerAction getCausedByAction() {
        return causedByAction;
    }

    public void setCausedByAction(PanPlayerAction causedByAction) {
        this.causedByAction = causedByAction;
    }
}
