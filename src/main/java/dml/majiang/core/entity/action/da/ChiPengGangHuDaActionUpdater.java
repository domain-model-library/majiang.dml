package dml.majiang.core.entity.action.da;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.hu.DianpaoHu;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;
import dml.majiang.core.entity.shoupai.gouxing.GouXingPanHu;

import java.util.List;

/**
 * 打之后能吃碰杠胡，最常见的打之后的动作
 */
public class ChiPengGangHuDaActionUpdater implements DaActionUpdater {

    private long panId;

    private GouXingPanHu gouXingPanHu;

    public ChiPengGangHuDaActionUpdater() {
    }

    public ChiPengGangHuDaActionUpdater(GouXingPanHu gouXingPanHu) {
        this.gouXingPanHu = gouXingPanHu;
    }

    @Override
    public void setPanId(long panId) {
        this.panId = panId;
    }

    @Override
    public long getPanId() {
        return panId;
    }

    @Override
    public void updateActions(DaAction daAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        PanPlayer daPlayer = pan.findPlayerById(daAction.getActionPlayerId());
        PanPlayer xiajiaPlayer = pan.findNextMenFengPlayer(daPlayer);
        MajiangPai daPai = daAction.getPai();

        // 下家可以吃
        xiajiaPlayer.tryChiAndGenerateCandidateActions(daPlayer.getId(), daPai);

        // 其他的可以碰杠胡
        while (true) {
            if (xiajiaPlayer.getId().equals(daAction.getActionPlayerId())) {
                break;
            }
            //碰
            xiajiaPlayer.tryPengAndGenerateCandidateAction(daAction.getActionPlayerId(), daAction.getPai());

            //杠
            xiajiaPlayer.tryGangdachuAndGenerateCandidateAction(daAction.getActionPlayerId(), daAction.getPai());

            //点炮胡
            // 各地方的胡牌规则不一样，这里只是演示
            List<ShoupaiPaiXing> huPaiShoupaiPaiXingList =
                    xiajiaPlayer.getShoupaiCalculator().calculateAllHuPaiShoupaiPaiXingForDianpaoHu(
                            xiajiaPlayer, daAction.getPai(), gouXingPanHu);
            if (huPaiShoupaiPaiXingList != null && !huPaiShoupaiPaiXingList.isEmpty()) {
                xiajiaPlayer.addActionCandidate(new HuAction(xiajiaPlayer.getId(),
                        new DianpaoHu(huPaiShoupaiPaiXingList.get(0), daAction.getActionPlayerId())));
            }

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
}
