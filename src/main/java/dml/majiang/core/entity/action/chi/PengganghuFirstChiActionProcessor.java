package dml.majiang.core.entity.action.chi;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;

/**
 * 碰杠胡优先的吃，最普遍的吃。在其他玩家有碰杠胡的机会的情况下不能吃。
 *
 * @author Neo
 */
public class PengganghuFirstChiActionProcessor implements ChiActionProcessor {

    private long panId;

    @Override
    public void setPanId(long panId) {
        this.panId = panId;
    }

    @Override
    public long getPanId() {
        return panId;
    }

    @Override
    public void process(ChiAction chiAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        PanPlayer player = pan.findPlayerById(chiAction.getActionPlayerId());
        PanPlayer xiajia = pan.findXiajia(player);
        while (true) {
            if (!xiajia.getId().equals(player.getId())) {
                if (xiajia.hasPengActionCandidate()
                        || xiajia.hasGangActionCandidate()
                        || xiajia.hasHuActionCandidate()) {
                    chiAction.setBlockedByHigherPriorityAction(true);
                    return;
                }
            } else {
                break;
            }
            xiajia = pan.findXiajia(xiajia);
        }

        pan.playerChiPai(chiAction.getActionPlayerId(), chiAction.getDachupaiPlayerId(), chiAction.getChijinPai(),
                chiAction.getShunzi());
    }

}
