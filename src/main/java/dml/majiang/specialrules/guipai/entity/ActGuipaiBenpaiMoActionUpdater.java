package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.action.mo.MoActionUpdater;
import dml.majiang.core.entity.shoupai.ShoupaiBiaoZhunPanHu;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

import java.util.ArrayList;
import java.util.List;

public abstract class ActGuipaiBenpaiMoActionUpdater implements MoActionUpdater {
    @Override
    public void updateActions(MoAction moAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        ActGuipaiBenpaiState actGuipaiBenpaiState = panSpecialRulesState.findSpecialRuleState(ActGuipaiBenpaiState.class);
        MajiangPai guipaiType = guipaiState.getGuipaiType();
        MajiangPai actGuipaiBenpaiPaiType = actGuipaiBenpaiState.getActGuipaiBenpaiPaiType();
        List<MajiangPai> guipaiActPaiTypeList = new ArrayList<>(guipaiState.getGuipaiActPaiTypes());
        if (!guipaiType.equals(actGuipaiBenpaiPaiType)) {
            guipaiActPaiTypeList.remove(actGuipaiBenpaiPaiType);
        }

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
        tryAndGenerateHuCandidateAction(moAction, pan, panFrames, player, panSpecialRulesState, guipaiType, guipaiActPaiTypeList,
                actGuipaiBenpaiPaiType);

        // 需要有“过”
        player.checkAndGenerateGuoCandidateAction(moAction);

        // 没有动作，那就只能打了
        if (player.getActionCandidates().isEmpty()) {
            player.generateDaActions();
            //不能打财神，过滤掉
            player.removeDaActionCandidateForPaiType(guipaiState.getGuipaiType());
        }
    }

    private void tryAndGenerateHuCandidateAction(MoAction moAction, Pan pan, PanFrames panFrames, PanPlayer player,
                                                 PanSpecialRulesState panSpecialRulesState,
                                                 MajiangPai guipaiType, List<MajiangPai> guipaiActPaiTypeList,
                                                 MajiangPai actGuipaiBenpaiPaiType) {
        List<Pai> shoupaiList = new ArrayList<>();
        List<Pai> guipaiList = new ArrayList<>();
        if (!guipaiType.equals(actGuipaiBenpaiPaiType)) {
            for (Pai pai : player.getFangruShoupaiList()) {
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
            if (player.getGangmoShoupai().getPaiType().equals(guipaiType)) {
                Pai paiCopy = new Pai(player.getGangmoShoupai().getId(), player.getGangmoShoupai().getPaiType());
                shoupaiList.add(paiCopy);
                guipaiList.add(paiCopy);
            } else if (player.getGangmoShoupai().getPaiType().equals(actGuipaiBenpaiPaiType)) {
                shoupaiList.add(new Pai(player.getGangmoShoupai().getId(), guipaiType));
            } else {
                shoupaiList.add(player.getGangmoShoupai());
            }
        } else {
            for (Pai pai : player.getFangruShoupaiList()) {
                if (pai.getPaiType().equals(guipaiType)) {
                    Pai paiCopy = new Pai(pai.getId(), pai.getPaiType());
                    shoupaiList.add(paiCopy);
                    guipaiList.add(paiCopy);
                } else {
                    shoupaiList.add(pai);
                }
            }
            if (player.getGangmoShoupai().getPaiType().equals(guipaiType)) {
                Pai paiCopy = new Pai(player.getGangmoShoupai().getId(), player.getGangmoShoupai().getPaiType());
                shoupaiList.add(paiCopy);
                guipaiList.add(paiCopy);
            } else {
                shoupaiList.add(player.getGangmoShoupai());
            }
        }
        if (guipaiList.isEmpty()) {
            List<ShoupaiPaiXing> hupaiShoupaiPaiXingList = ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
            if (hupaiShoupaiPaiXingList != null) {
                //把ShoupaiPaiXing中的扮演鬼牌本牌的牌的花色还原为其本花色
                for (ShoupaiPaiXing shoupaiPaiXing : hupaiShoupaiPaiXingList) {
                    shoupaiPaiXing.replacePaiType(guipaiType, actGuipaiBenpaiPaiType);
                }
                Hu hu = makeHuWithoutGuipai(moAction, player.getGangmoShoupai(), pan, panFrames, hupaiShoupaiPaiXingList, panSpecialRulesState,
                        guipaiType, actGuipaiBenpaiPaiType);
                if (hu != null) {
                    player.addActionCandidate(new HuAction(player.getId(), hu));
                }
            }
        } else {
            //生成所有鬼牌当的组合
            List<MajiangPai[]> guipaiActPaiTypeCombinationList = GuipaiActPaiTypeCombinationCaculator.get(guipaiList.size(), guipaiActPaiTypeList);
            List<HupaiShoupaiPaiXingListWithGuipaiAct> hupaiShoupaiPaiXingListWithGuipaiActList = new ArrayList<>();
            for (MajiangPai[] guipaiActPaiTypeCombination : guipaiActPaiTypeCombinationList) {
                HupaiShoupaiPaiXingListWithGuipaiAct hupaiShoupaiPaiXingListWithGuipaiAct = new HupaiShoupaiPaiXingListWithGuipaiAct();
                for (int i = 0; i < guipaiList.size(); i++) {
                    Pai guipai = guipaiList.get(i);
                    MajiangPai actPaiType = guipaiActPaiTypeCombination[i];
                    guipai.setPaiType(actPaiType);
                    hupaiShoupaiPaiXingListWithGuipaiAct.addGuipaiAct(guipai.getId(), actPaiType);
                }
                List<ShoupaiPaiXing> hupaiShoupaiPaiXingList = ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
                if (hupaiShoupaiPaiXingList != null) {
                    for (ShoupaiPaiXing shoupaiPaiXing : hupaiShoupaiPaiXingList) {
                        //还原鬼牌为其本花色
                        for (Pai guipai : guipaiList) {
                            shoupaiPaiXing.setPaiType(guipai.getId(), guipaiType);
                        }
                        //把ShoupaiPaiXing中的扮演鬼牌本牌的牌的花色还原为其本花色
                        shoupaiPaiXing.replacePaiType(guipaiType, actGuipaiBenpaiPaiType);
                    }
                    hupaiShoupaiPaiXingListWithGuipaiAct.setHupaiShoupaiPaiXingList(hupaiShoupaiPaiXingList);
                    hupaiShoupaiPaiXingListWithGuipaiActList.add(hupaiShoupaiPaiXingListWithGuipaiAct);
                }
            }
            Hu hu = makeHuWithGuipai(moAction, player.getGangmoShoupai(), pan, panFrames, hupaiShoupaiPaiXingListWithGuipaiActList, panSpecialRulesState,
                    guipaiType, actGuipaiBenpaiPaiType);
            if (hu != null) {
                player.addActionCandidate(new HuAction(player.getId(), hu));
            }
        }
    }

    protected abstract Hu makeHuWithGuipai(MoAction moAction, Pai moPai, Pan pan, PanFrames panFrames,
                                           List<HupaiShoupaiPaiXingListWithGuipaiAct> hupaiShoupaiPaiXingListWithGuipaiActList,
                                           PanSpecialRulesState panSpecialRulesState, MajiangPai guipaiType, MajiangPai actGuipaiBenpaiPaiType);

    protected abstract Hu makeHuWithoutGuipai(MoAction moAction, Pai moPai, Pan pan, PanFrames panFrames,
                                              List<ShoupaiPaiXing> hupaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState,
                                              MajiangPai guipaiType, MajiangPai actGuipaiBenpaiPaiType);
}