package dml.majiang.core.entity.action.gang;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.mo.GanghouBupai;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.fenzu.GangType;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

import java.util.List;

/**
 * 杠了之后会被别人抢杠胡
 */
public abstract class QiangganghuGangActionUpdater implements GangActionUpdater {

    @Override
    public void updateActions(GangAction gangAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        PanPlayer player = pan.findPlayerById(gangAction.getActionPlayerId());
        //抢杠胡
        boolean qiangganghu = false;
        if (gangAction.getGangType().equals(GangType.kezigangmo)
                || gangAction.getGangType().equals(GangType.kezigangshoupai)
                || gangAction.getGangType().equals(GangType.shoupaigangmo)) {
            PanPlayer currentPlayer = player;
            Pai gangpai = player.findGangchuPai(gangAction.getGangPaiId());
            while (true) {
                PanPlayer xiajia = pan.findNextMenFengPlayer(currentPlayer);
                if (xiajia.getId().equals(gangAction.getActionPlayerId())) {
                    break;
                }
                List<ShoupaiPaiXing> huPaiShoupaiPaiXingList = xiajia.calculateAllHuPaiShoupaiPaiXingForQianggangHu(gangpai);
                if (huPaiShoupaiPaiXingList != null && !huPaiShoupaiPaiXingList.isEmpty()) {
                    Hu hu = makeHu(gangAction, gangpai, pan, panFrames, huPaiShoupaiPaiXingList, panSpecialRulesState);
                    if (hu != null) {
                        xiajia.addActionCandidate(new HuAction(xiajia.getId(), hu));
                    }
                    qiangganghu = true;
                }
                currentPlayer = xiajia;
            }
        }
        if (qiangganghu) {
            return;
        }
        // 杠完之后要摸牌
        player.addActionCandidate(new MoAction(player.getId(), new GanghouBupai(gangAction.getId())));
    }

    protected abstract Hu makeHu(GangAction gangAction, Pai gangpai, Pan pan, PanFrames panFrames,
                                 List<ShoupaiPaiXing> huPaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState);
}
