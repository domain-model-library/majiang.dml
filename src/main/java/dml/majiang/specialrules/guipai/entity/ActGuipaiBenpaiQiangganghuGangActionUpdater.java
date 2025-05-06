package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.action.gang.GangActionUpdater;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.mo.GanghouBupai;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.fenzu.GangType;
import dml.majiang.core.entity.shoupai.ShoupaiBiaoZhunPanHu;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

import java.util.ArrayList;
import java.util.List;

public abstract class ActGuipaiBenpaiQiangganghuGangActionUpdater implements GangActionUpdater {

    @Override
    public void updateActions(GangAction gangAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        PanPlayer player = pan.findPlayerById(gangAction.getActionPlayerId());
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        ActGuipaiBenpaiState actGuipaiBenpaiState = panSpecialRulesState.findSpecialRuleState(ActGuipaiBenpaiState.class);
        MajiangPai guipaiType = guipaiState.getGuipaiType();
        MajiangPai actGuipaiBenpaiPaiType = actGuipaiBenpaiState.getActGuipaiBenpaiPaiType();
        List<MajiangPai> guipaiActPaiTypeList = new ArrayList<>(guipaiState.getGuipaiActPaiTypes());
        if (!guipaiType.equals(actGuipaiBenpaiPaiType)) {
            guipaiActPaiTypeList.remove(actGuipaiBenpaiPaiType);
        }
        //抢杠胡
        boolean qiangganghu = false;
        if (gangAction.getGangType().equals(GangType.kezigangmo)
                || gangAction.getGangType().equals(GangType.kezigangshoupai)
                || gangAction.getGangType().equals(GangType.shoupaigangmo)) {
            PanPlayer currentPlayer = player;
            Pai gangpai = player.findGangchuPai(gangAction.getGangPaiId());
            while (true) {
                PanPlayer xiajia = pan.findNextMenFengPlayer(currentPlayer);
                if (xiajia.getId().equals(gangAction.getActionPlayerId())) {
                    break;
                }
                //胡
                boolean getHu = tryAndGenerateHuCandidateAction(gangAction, gangpai, pan, panFrames, panSpecialRulesState, xiajia,
                        guipaiType, guipaiActPaiTypeList, actGuipaiBenpaiPaiType);
                if (getHu) {
                    qiangganghu = true;
                }
                currentPlayer = xiajia;
            }
        }
        if (qiangganghu) {
            return;
        }
        // 杠完之后要摸牌
        player.addActionCandidate(new MoAction(player.getId(), new GanghouBupai(gangAction.getId())));
    }

    private boolean tryAndGenerateHuCandidateAction(GangAction gangAction, Pai gangpai, Pan pan, PanFrames panFrames,
                                                    PanSpecialRulesState panSpecialRulesState, PanPlayer hupaiPlayer,
                                                    MajiangPai guipaiType, List<MajiangPai> guipaiActPaiTypeList,
                                                    MajiangPai actGuipaiBenpaiPaiType) {
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
        shoupaiList.add(gangpai);
        if (guipaiList.isEmpty()) {
            List<ShoupaiPaiXing> hupaiShoupaiPaiXingList = ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
            if (hupaiShoupaiPaiXingList != null) {
                //把ShoupaiPaiXing中的扮演鬼牌本牌的牌的花色还原为其本花色
                for (ShoupaiPaiXing shoupaiPaiXing : hupaiShoupaiPaiXingList) {
                    shoupaiPaiXing.replacePaiType(guipaiType, actGuipaiBenpaiPaiType);
                }
                Hu hu = makeHuWithoutGuipai(gangAction, gangpai, pan, panFrames, hupaiPlayer.getId(), hupaiShoupaiPaiXingList, panSpecialRulesState,
                        guipaiType, actGuipaiBenpaiPaiType);
                if (hu != null) {
                    hupaiPlayer.addActionCandidate(new HuAction(hupaiPlayer.getId(), hu));
                    return true;
                }
                return false;
            } else {
                //没有胡牌
                return false;
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
            Hu hu = makeHuWithGuipai(gangAction, gangpai, pan, panFrames, hupaiPlayer.getId(), hupaiShoupaiPaiXingListWithGuipaiActList, panSpecialRulesState,
                    guipaiType, actGuipaiBenpaiPaiType);
            if (hu != null) {
                hupaiPlayer.addActionCandidate(new HuAction(hupaiPlayer.getId(), hu));
                return true;
            }
            return false;
        }
    }

    protected abstract Hu makeHuWithGuipai(GangAction gangAction, Pai gangpai, Pan pan, PanFrames panFrames, String huPlayerId,
                                           List<HupaiShoupaiPaiXingListWithGuipaiAct> hupaiShoupaiPaiXingListWithGuipaiActList,
                                           PanSpecialRulesState panSpecialRulesState, MajiangPai guipaiType, MajiangPai actGuipaiBenpaiPaiType);

    protected abstract Hu makeHuWithoutGuipai(GangAction gangAction, Pai gangpai, Pan pan, PanFrames panFrames, String huPlayerId,
                                              List<ShoupaiPaiXing> hupaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState,
                                              MajiangPai guipaiType, MajiangPai actGuipaiBenpaiPaiType);
}
