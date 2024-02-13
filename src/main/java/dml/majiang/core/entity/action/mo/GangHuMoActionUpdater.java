package dml.majiang.core.entity.action.mo;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.hu.ZimoHu;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;
import dml.majiang.core.entity.shoupai.gouxing.GouXingPanHu;

import java.util.List;

/**
 * 摸完之后可以杠、胡的操作，也可以过，过了之后就可以打了。最常见的摸后操作
 */
public class GangHuMoActionUpdater implements MoActionUpdater {
    private long panId;
    private GouXingPanHu gouXingPanHu;

    public GangHuMoActionUpdater() {
    }

    public GangHuMoActionUpdater(GouXingPanHu gouXingPanHu) {
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
    public void updateActions(MoAction moAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        int avaliablePaiLeft = pan.countAvaliablePai();
        if (avaliablePaiLeft <= 0) {// 没牌了
            return;
        }
        PanPlayer player = pan.findPlayerById(moAction.getActionPlayerId());
        // 有手牌或刻子可以杠这个摸来的牌
        player.tryShoupaigangmoAndGenerateCandidateAction();
        player.tryKezigangmoAndGenerateCandidateAction();

        // 杠四个手牌
        player.tryGangsigeshoupaiAndGenerateCandidateAction();

        // 刻子杠手牌
        player.tryKezigangshoupaiAndGenerateCandidateAction();

        // 胡，各地方的胡牌规则不一样，这里只是演示
        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList =
                player.getShoupaiCalculator().calculateAllHuPaiShoupaiPaiXingForZimoHu(player, this.gouXingPanHu);
        if (huPaiShoupaiPaiXingList != null && !huPaiShoupaiPaiXingList.isEmpty()) {
            player.addActionCandidate(new HuAction(player.getId(), new ZimoHu(huPaiShoupaiPaiXingList.get(0))));
        }

        // 需要有“过”
        player.checkAndGenerateGuoCandidateAction(moAction);

        // 没有杠、胡，那就只能打了
        if (player.getActionCandidates().isEmpty()) {
            player.generateDaActions();
        }
    }
}
