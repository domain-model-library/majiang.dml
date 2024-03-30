package dml.majiang.core.entity.action.da;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;

/**
 * 打之后能吃碰杠(还有胡，但是各地规则不同，就不包含了)，最常见的打之后的动作
 */
public class ChiPengGangDaActionUpdater implements DaActionUpdater {

    private long panId;

    public ChiPengGangDaActionUpdater() {
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
