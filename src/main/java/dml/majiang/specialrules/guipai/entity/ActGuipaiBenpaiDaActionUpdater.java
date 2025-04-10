package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.chi.ChiAction;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.shoupai.ShoupaiBiaoZhunPanHu;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;
import dml.majiang.core.entity.shoupai.ShoupaiShunziCalculator;

import java.util.ArrayList;
import java.util.List;

public abstract class ActGuipaiBenpaiDaActionUpdater implements DaActionUpdater {
    public void updateActions(DaAction daAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        PanPlayer daPlayer = pan.findPlayerById(daAction.getActionPlayerId());
        PanPlayer xiajiaPlayer = pan.findNextMenFengPlayer(daPlayer);
        int daPaiId = daAction.getPaiId();
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        ActGuipaiBenpaiState actGuipaiBenpaiState = panSpecialRulesState.findSpecialRuleState(ActGuipaiBenpaiState.class);
        MajiangPai guipaiType = guipaiState.getGuipaiType();
        MajiangPai actGuipaiBenpaiPaiType = actGuipaiBenpaiState.getActGuipaiBenpaiPaiType();

        // 下家可以吃
        //把扮演鬼牌本牌的牌的花色设置为鬼牌本牌的花色，和其他手牌一起放入shoupaiList
        List<Pai> shoupaiList = new ArrayList<>();
        for (Pai pai : xiajiaPlayer.getFangruShoupai().values()) {
            if (pai.getPaiType().equals(actGuipaiBenpaiPaiType)) {
                Pai newPai = new Pai(pai.getId(), guipaiType);
                shoupaiList.add(newPai);
            } else {
                shoupaiList.add(pai);
            }
        }
        Pai daPai = daPlayer.findDachupai(daPaiId);
        Pai paiToAdd;
        if (daPai.getPaiType().equals(actGuipaiBenpaiPaiType)) {
            paiToAdd = new Pai(daPai.getId(), guipaiType);
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
            tryAndGenerateHuCandidateAction(daAction, daPai, pan, panFrames, panSpecialRulesState, xiajiaPlayer,
                    guipaiType, actGuipaiBenpaiPaiType);

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

    private void tryAndGenerateHuCandidateAction(DaAction daAction, Pai daPai, Pan pan, PanFrames panFrames,
                                                 PanSpecialRulesState panSpecialRulesState, PanPlayer hupaiPlayer,
                                                 MajiangPai guipaiType, MajiangPai actGuipaiBenpaiPaiType) {
        List<Pai> shoupaiList = new ArrayList<>();
        List<Pai> guipaiList = new ArrayList<>();
        if (!guipaiType.equals(actGuipaiBenpaiPaiType)) {
            for (Pai pai : hupaiPlayer.getFangruShoupaiList()) {
                if (pai.getPaiType().equals(guipaiType)) {
                    Pai paiCopy = new Pai(pai.getId(), pai.getPaiType());
                    shoupaiList.add(paiCopy);
                    guipaiList.add(paiCopy);
                } else if (pai.getPaiType().equals(actGuipaiBenpaiPaiType)) {
                    shoupaiList.add(new Pai(pai.getId(), guipaiType));
                } else {
                    shoupaiList.add(pai);
                }
            }
        } else {
            for (Pai pai : hupaiPlayer.getFangruShoupaiList()) {
                if (pai.getPaiType().equals(guipaiType)) {
                    Pai paiCopy = new Pai(pai.getId(), pai.getPaiType());
                    shoupaiList.add(paiCopy);
                    guipaiList.add(paiCopy);
                } else {
                    shoupaiList.add(pai);
                }
            }
        }
        if (guipaiList.isEmpty()) {
            shoupaiList.add(daPai);
            List<ShoupaiPaiXing> hupaiShoupaiPaiXingList = ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
            //把ShoupaiPaiXing中的扮演鬼牌本牌的牌的花色还原为其本花色
            for (ShoupaiPaiXing shoupaiPaiXing : hupaiShoupaiPaiXingList) {
                shoupaiPaiXing.replacePaiType(guipaiType, actGuipaiBenpaiPaiType);
            }
            Hu hu = makeHuWithoutGuipai(daAction, pan, panFrames, hupaiPlayer.getId(), hupaiShoupaiPaiXingList, panSpecialRulesState,
                    guipaiType, actGuipaiBenpaiPaiType);
            if (hu != null) {
                hupaiPlayer.addActionCandidate(new HuAction(hupaiPlayer.getId(), hu));
            }
        } else {
            List<MajiangPai> guipaiActPaiTypeList = getGuipaiActPaiTypes();
            if (!guipaiType.equals(actGuipaiBenpaiPaiType)) {
                guipaiActPaiTypeList.remove(actGuipaiBenpaiPaiType);
            }
            //生成所有鬼牌当的组合
        }
    }

    protected abstract Hu makeHuWithoutGuipai(DaAction daAction, Pan pan, PanFrames panFrames, String huPlayerId,
                                              List<ShoupaiPaiXing> hupaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState,
                                              MajiangPai guipaiType, MajiangPai actGuipaiBenpaiPaiType);

    /**
     * 鬼牌可以替代的牌
     *
     * @return
     */
    protected List<MajiangPai> getGuipaiActPaiTypes() {
        MajiangPai[] xushupaiAndZipaiArray = MajiangPai.xushupaiAndZipaiArray();
        List<MajiangPai> guipaiActPaiTypeList = new ArrayList<>();
        for (MajiangPai pai : xushupaiAndZipaiArray) {
            guipaiActPaiTypeList.add(pai);
        }
        return guipaiActPaiTypeList;
    }
}