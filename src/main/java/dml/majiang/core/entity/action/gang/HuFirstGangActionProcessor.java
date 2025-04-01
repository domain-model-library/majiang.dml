package dml.majiang.core.entity.action.gang;


import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.fenzu.GangType;


/**
 * 胡优先的杠，最普遍的杠。在其他玩家有胡的机会的情况下不能杠。
 *
 * @author Neo
 */
public class HuFirstGangActionProcessor implements GangActionProcessor {

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
    public void process(GangAction gangAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        PanPlayer player = pan.findPlayerById(gangAction.getActionPlayerId());
        PanPlayer xiajia = pan.findNextMenFengPlayer(player);
        while (true) {
            if (!xiajia.getId().equals(player.getId())) {
                if (xiajia.hasHuActionCandidate()) {
                    gangAction.setBlockedByHigherPriorityAction(true);
                    return;
                }
            } else {
                break;
            }
            xiajia = pan.findNextMenFengPlayer(xiajia);
        }

        GangType gangType = gangAction.getGangType();
        if (gangType.equals(GangType.gangdachu)) {
            pan.playerGangDachupai(gangAction.getActionPlayerId(), gangAction.getDachupaiPlayerId(), gangAction.getGangPaiId());
        } else if (gangType.equals(GangType.shoupaigangmo)) {
            pan.playerShoupaiGangMo(gangAction.getActionPlayerId());
        } else if (gangType.equals(GangType.gangsigeshoupai)) {
            pan.playerGangSigeshoupai(gangAction.getActionPlayerId(), gangAction.getPaiIds());
        } else if (gangType.equals(GangType.kezigangmo)) {
            pan.playerKeziGangMo(gangAction.getActionPlayerId());
        } else if (gangType.equals(GangType.kezigangshoupai)) {
            pan.playerKeziGangShoupai(gangAction.getActionPlayerId(), gangAction.getGangPaiId());
        } else {
        }

    }

}
