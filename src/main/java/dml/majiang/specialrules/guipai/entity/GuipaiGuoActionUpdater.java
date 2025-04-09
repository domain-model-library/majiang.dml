package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.guo.GuoAction;
import dml.majiang.core.entity.action.guo.GuoActionUpdater;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.action.peng.PengAction;

public class GuipaiGuoActionUpdater implements GuoActionUpdater {

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
    public void updateActions(GuoAction guoAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        pan.playerClearActionCandidates(guoAction.getActionPlayerId());
        PanPlayer player = pan.findPlayerById(guoAction.getActionPlayerId());

        // 首先看一下,我过的是什么? 是我摸牌之后的胡,杠? 还是我碰之后的杠? 还是别人打出牌之后我可以吃碰杠胡
        PanPlayerAction causedByAction = guoAction.getCausedByAction();
        if (causedByAction instanceof MoAction) {// 过的是我摸牌之后的胡,杠
            // 那要我打牌
            player.generateDaActions();
            GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
            player.removeDaActionCandidateForPaiType(guipaiState.getGuipaiType());
        } else if (causedByAction instanceof DaAction) {// 过的是别人打出牌之后我可以吃碰杠胡
            if (pan.allPlayerHasNoActionCandidates()) {// 如果所有玩家啥也干不了
                // 打牌那家的下家摸牌 TODO 还没处理牌摸完
                // 打牌那家的下家摸牌
                PanPlayer xiajiaPlayer = pan
                        .findNextMenFengPlayer(pan.findPlayerById(causedByAction.getActionPlayerId()));
                xiajiaPlayer.addActionCandidate(new MoAction(xiajiaPlayer.getId(), new LundaoMopai()));
            } else {
                //释放被阻塞的吃动作
                pan.releasePlayerActionBlockedByHigherPriorityAction();
            }
        } else if (causedByAction instanceof PengAction) {// 过的是我碰之后的杠
            // 那要我打牌
            player.generateDaActions();
            GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
            player.removeDaActionCandidateForPaiType(guipaiState.getGuipaiType());
        } else {
        }
    }

}
