package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.chi.ChiAction;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.shoupai.ShoupaiShunziCalculator;

import java.util.ArrayList;
import java.util.List;

public abstract class ActGuipaiBenpaiDaActionUpdater implements DaActionUpdater {
    public void updateActions(DaAction daAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        PanPlayer daPlayer = pan.findPlayerById(daAction.getActionPlayerId());
        PanPlayer xiajiaPlayer = pan.findNextMenFengPlayer(daPlayer);
        int daPaiId = daAction.getPaiId();

        // 下家可以吃
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        ActGuipaiBenpaiState actGuipaiBenpaiState = panSpecialRulesState.findSpecialRuleState(ActGuipaiBenpaiState.class);
        //把扮演鬼牌本牌的牌的花色设置为鬼牌本牌的花色，和其他手牌一起放入shoupaiList
        List<Pai> shoupaiList = new ArrayList<>();
        for (Pai pai : xiajiaPlayer.getFangruShoupai().values()) {
            if (pai.getPaiType().equals(actGuipaiBenpaiState.getActGuipaiBenpaiPai())) {
                Pai newPai = new Pai(pai.getId(), guipaiState.getGuipaiType());
                shoupaiList.add(newPai);
            } else {
                shoupaiList.add(pai);
            }
        }
        Pai daPai = daPlayer.findDachupai(daPaiId);
        Pai paiToAdd;
        if (daPai.getPaiType().equals(actGuipaiBenpaiState.getActGuipaiBenpaiPai())) {
            paiToAdd = new Pai(daPai.getId(), guipaiState.getGuipaiType());
        } else {
            paiToAdd = daPai;
        }
        List<int[]> shunziPaiIdList = ShoupaiShunziCalculator.tryAndMakeShunziWithPai(shoupaiList, paiToAdd);
        if (shunziPaiIdList != null) {
            for (int[] shunziPaiId : shunziPaiIdList) {
                xiajiaPlayer.addActionCandidate(new ChiAction(xiajiaPlayer.getId(), daAction.getActionPlayerId(), daPaiId, shunziPaiId));
            }
        }

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