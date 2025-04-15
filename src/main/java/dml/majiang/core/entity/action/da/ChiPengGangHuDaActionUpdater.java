package dml.majiang.core.entity.action.da;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

import java.util.List;

/**
 * 打之后能吃碰杠胡，最常见的打之后的动作
 */
public abstract class ChiPengGangHuDaActionUpdater implements DaActionUpdater {
    public void updateActions(DaAction daAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        PanPlayer daPlayer = pan.findPlayerById(daAction.getActionPlayerId());
        PanPlayer xiajiaPlayer = pan.findNextMenFengPlayer(daPlayer);
        int daPaiId = daAction.getPaiId();
        Pai daPai = daPlayer.findDachupai(daPaiId);

        // 下家可以吃
        xiajiaPlayer.tryChiAndGenerateCandidateActions(daPlayer, daPaiId);

        // 其他的可以碰杠胡
        while (true) {
            if (xiajiaPlayer.getId().equals(daAction.getActionPlayerId())) {
                break;
            }
            //碰
            xiajiaPlayer.tryPengAndGenerateCandidateAction(daPlayer, daAction.getPaiId());

            //杠
            xiajiaPlayer.tryGangdachuAndGenerateCandidateAction(daPlayer, daAction.getPaiId());

            //胡
            tryAndGenerateHuCandidateAction(daAction, daPai, daPlayer, daPaiId, pan, panFrames, panSpecialRulesState, xiajiaPlayer);

            // 需要有“过”
            xiajiaPlayer.checkAndGenerateGuoCandidateAction(daAction);

            xiajiaPlayer = pan.findNextMenFengPlayer(xiajiaPlayer);
        }

        // 如果所有玩家啥也做不了,那就下家摸牌
        if (pan.allPlayerHasNoActionCandidates()) {
            xiajiaPlayer = pan.findNextMenFengPlayer(daPlayer);
            xiajiaPlayer.addActionCandidate(new MoAction(xiajiaPlayer.getId(), new LundaoMopai()));
        }
    }

    private void tryAndGenerateHuCandidateAction(DaAction daAction, Pai daPai, PanPlayer daPlayer, int daPaiId, Pan pan, PanFrames panFrames,
                                                 PanSpecialRulesState panSpecialRulesState, PanPlayer panPlayer) {
        List<ShoupaiPaiXing> hupaiShoupaiPaiXingList = panPlayer.calculateAllHuPaiShoupaiPaiXingForDianpaoHu(daPlayer.findDachupai(daPaiId));
        Hu hu = makeHu(daAction, daPai, pan, panFrames, panPlayer.getId(), hupaiShoupaiPaiXingList, panSpecialRulesState);
        if (hu != null) {
            panPlayer.addActionCandidate(new HuAction(panPlayer.getId(), hu));
        }
    }

    protected abstract Hu makeHu(DaAction daAction, Pai daPai, Pan pan, PanFrames panFrames, String huPlayerId,
                                 List<ShoupaiPaiXing> hupaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState);
}
