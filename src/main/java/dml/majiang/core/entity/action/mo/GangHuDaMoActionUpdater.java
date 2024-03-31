package dml.majiang.core.entity.action.mo;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;

/**
 * 摸完之后可以杠胡，也可以过，什么也没有那就打了。最常见的摸后操作
 */
public abstract class GangHuDaMoActionUpdater implements MoActionUpdater {
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

        // 胡
        tryAndGenerateHuCandidateAction(moAction, pan, panSpecialRulesState);

        // 需要有“过”
        player.checkAndGenerateGuoCandidateAction(moAction);

        // 没有动作，那就只能打了
        if (player.getActionCandidates().isEmpty()) {
            player.generateDaActions();
        }
    }

    protected abstract void tryAndGenerateHuCandidateAction(MoAction moAction, Pan pan, PanSpecialRulesState panSpecialRulesState);
}
