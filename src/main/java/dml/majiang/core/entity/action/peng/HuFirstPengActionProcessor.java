package dml.majiang.core.entity.action.peng;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;

/**
 * 胡优先的碰，最普遍的碰。在其他玩家有胡的机会的情况下不能碰。
 *
 * @author Neo
 */
public class HuFirstPengActionProcessor implements PengActionProcessor {

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
    public void process(PengAction pengAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        PanPlayer player = pan.findPlayerById(pengAction.getActionPlayerId());
        PanPlayer xiajia = pan.findNextMenFengPlayer(player);
        while (true) {
            if (!xiajia.getId().equals(player.getId())) {
                if (xiajia.hasHuActionCandidate()) {
                    pengAction.setBlockedByHigherPriorityAction(true);
                    return;
                }
            } else {
                break;
            }
            xiajia = pan.findNextMenFengPlayer(xiajia);
        }

        pan.playerPengPai(pengAction.getActionPlayerId(), pengAction.getDachupaiPlayerId(), pengAction.getPai());
    }

}
