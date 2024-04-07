package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.chi.ChiAction;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.fenzu.Shunzi;
import dml.majiang.core.entity.shoupai.ShoupaiCalculator;

public abstract class ActGuipaiBenpaiDaActionUpdater implements DaActionUpdater {
    public void updateActions(DaAction daAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        PanPlayer daPlayer = pan.findPlayerById(daAction.getActionPlayerId());
        PanPlayer xiajiaPlayer = pan.findNextMenFengPlayer(daPlayer);
        MajiangPai daPai = daAction.getPai();

        // 下家可以吃
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        ActGuipaiBenpaiState actGuipaiBenpaiState = panSpecialRulesState.findSpecialRuleState(ActGuipaiBenpaiState.class);
        ShoupaiCalculator shoupaiCalculator = xiajiaPlayer.getShoupaiCalculator();
        Shunzi shunzi1 = shoupaiCalculator.tryAndMakeShunziWithPai1(daPai,
                guipaiState.getGuipaiType(), actGuipaiBenpaiState.getActGuipaiBenpaiPai());
        if (shunzi1 != null) {
            shunzi1.setPai1(daPai);
            xiajiaPlayer.addActionCandidate(
                    new ChiAction(xiajiaPlayer.getId(), daAction.getActionPlayerId(), daPai, shunzi1));
        }

        Shunzi shunzi2 = shoupaiCalculator.tryAndMakeShunziWithPai2(daPai,
                guipaiState.getGuipaiType(), actGuipaiBenpaiState.getActGuipaiBenpaiPai());
        if (shunzi2 != null) {
            shunzi2.setPai2(daPai);
            xiajiaPlayer.addActionCandidate(
                    new ChiAction(xiajiaPlayer.getId(), daAction.getActionPlayerId(), daPai, shunzi2));
        }

        Shunzi shunzi3 = shoupaiCalculator.tryAndMakeShunziWithPai3(daPai,
                guipaiState.getGuipaiType(), actGuipaiBenpaiState.getActGuipaiBenpaiPai());
        if (shunzi3 != null) {
            shunzi3.setPai3(daPai);
            xiajiaPlayer.addActionCandidate(
                    new ChiAction(xiajiaPlayer.getId(), daAction.getActionPlayerId(), daPai, shunzi3));
        }

        // 其他的可以碰杠胡
        while (true) {
            if (xiajiaPlayer.getId().equals(daAction.getActionPlayerId())) {
                break;
            }
            //碰
            xiajiaPlayer.tryPengAndGenerateCandidateAction(daAction.getActionPlayerId(), daAction.getPai());

            //杠
            xiajiaPlayer.tryGangdachuAndGenerateCandidateAction(daAction.getActionPlayerId(), daAction.getPai());

            //胡
            tryAndGenerateHuCandidateAction(daAction, pan, panSpecialRulesState, xiajiaPlayer);

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

    protected abstract void tryAndGenerateHuCandidateAction(DaAction daAction, Pan pan,
                                                            PanSpecialRulesState panSpecialRulesState, PanPlayer panPlayer);
}