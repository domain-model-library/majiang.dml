package dml.majiang.core.entity;

import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.action.chi.ChiAction;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.action.guo.GuoAction;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.peng.PengAction;
import dml.majiang.core.entity.chupaizu.ChichuPaiZu;
import dml.majiang.core.entity.chupaizu.GangchuPaiZu;
import dml.majiang.core.entity.chupaizu.PengchuPaiZu;
import dml.majiang.core.entity.fenzu.GangType;
import dml.majiang.core.entity.fenzu.Gangzi;
import dml.majiang.core.entity.fenzu.Kezi;
import dml.majiang.core.entity.fenzu.Shunzi;
import dml.majiang.core.entity.shoupai.ShoupaiCalculator;

import java.util.*;

public class PanPlayer {
    private String id;
    private MenFeng menFeng;

    /**
     * 已放入的手牌列表
     */
    private List<MajiangPai> fangruShoupaiList = new ArrayList<>();

    /**
     * 刚摸进待处理的手牌（未放入）
     */
    private MajiangPai gangmoShoupai;

    private Map<Integer, PanPlayerAction> actionCandidates = new HashMap<>();

    private ShoupaiCalculator shoupaiCalculator = new ShoupaiCalculator();

    /**
     * 打出的牌列表
     */
    private List<MajiangPai> dachupaiList = new ArrayList<>();

    private List<ChichuPaiZu> chichupaiZuList = new ArrayList<>();
    private List<PengchuPaiZu> pengchupaiZuList = new ArrayList<>();
    private List<GangchuPaiZu> gangchupaiZuList = new ArrayList<>();

    private Hu hu;


    public void addShoupai(MajiangPai pai) {
        fangruShoupaiList.add(pai);
        shoupaiCalculator.addPai(pai);
    }

    public void addActionCandidate(PanPlayerAction action) {
        int idForNewAction = actionCandidates.size() + 1;
        action.setId(idForNewAction);
        actionCandidates.put(idForNewAction, action);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MenFeng getMenFeng() {
        return menFeng;
    }

    public void setMenFeng(MenFeng menFeng) {
        this.menFeng = menFeng;
    }

    public List<MajiangPai> getFangruShoupaiList() {
        return fangruShoupaiList;
    }

    public void setFangruShoupaiList(List<MajiangPai> fangruShoupaiList) {
        this.fangruShoupaiList = fangruShoupaiList;
    }

    public Map<Integer, PanPlayerAction> getActionCandidates() {
        return actionCandidates;
    }

    public PanPlayerAction findActionCandidate(int actionId) {
        return actionCandidates.get(actionId);
    }

    public void setGangmoShoupai(MajiangPai pai) {
        this.gangmoShoupai = pai;
    }

    public MajiangPai getGangmoShoupai() {
        return gangmoShoupai;
    }

    public void setActionCandidates(Map<Integer, PanPlayerAction> actionCandidates) {
        this.actionCandidates = actionCandidates;
    }

    public void tryShoupaigangmoAndGenerateCandidateAction() {
        int count = shoupaiCalculator.count(gangmoShoupai);
        if (count >= 3) {
            addActionCandidate(new GangAction(id, null, gangmoShoupai, GangType.shoupaigangmo));
        }
    }

    public void tryGangsigeshoupaiAndGenerateCandidateAction() {
        List<MajiangPai> gangpaiList = shoupaiCalculator.findAllPaiQuantityIsFour();
        gangpaiList.forEach(
                (gangpai) -> addActionCandidate(new GangAction(id, null, gangpai, GangType.gangsigeshoupai)));
    }

    public void tryKezigangshoupaiAndGenerateCandidateAction() {
        for (PengchuPaiZu pengchuPaiZu : pengchupaiZuList) {
            for (MajiangPai fangruShoupai : fangruShoupaiList) {
                if (pengchuPaiZu.getKezi().getPaiType().equals(fangruShoupai)) {
                    addActionCandidate(new GangAction(id, null, fangruShoupai, GangType.kezigangshoupai));
                    break;
                }
            }
        }
    }

    public void tryKezigangmoAndGenerateCandidateAction() {
        for (PengchuPaiZu pengchuPaiZu : pengchupaiZuList) {
            if (pengchuPaiZu.getKezi().getPaiType().equals(gangmoShoupai)) {
                addActionCandidate(new GangAction(id, null, gangmoShoupai, GangType.kezigangmo));
                return;
            }
        }
    }

    public ShoupaiCalculator getShoupaiCalculator() {
        return shoupaiCalculator;
    }

    public void setShoupaiCalculator(ShoupaiCalculator shoupaiCalculator) {
        this.shoupaiCalculator = shoupaiCalculator;
    }

    public List<MajiangPai> getDachupaiList() {
        return dachupaiList;
    }

    public void setDachupaiList(List<MajiangPai> dachupaiList) {
        this.dachupaiList = dachupaiList;
    }

    public List<ChichuPaiZu> getChichupaiZuList() {
        return chichupaiZuList;
    }

    public void setChichupaiZuList(List<ChichuPaiZu> chichupaiZuList) {
        this.chichupaiZuList = chichupaiZuList;
    }

    public List<PengchuPaiZu> getPengchupaiZuList() {
        return pengchupaiZuList;
    }

    public void setPengchupaiZuList(List<PengchuPaiZu> pengchupaiZuList) {
        this.pengchupaiZuList = pengchupaiZuList;
    }

    public List<GangchuPaiZu> getGangchupaiZuList() {
        return gangchupaiZuList;
    }

    public void setGangchupaiZuList(List<GangchuPaiZu> gangchupaiZuList) {
        this.gangchupaiZuList = gangchupaiZuList;
    }

    public void clearActionCandidates() {
        actionCandidates.clear();
    }

    public int countChichupaiZu() {
        return chichupaiZuList.size();
    }

    public int countPengchupaiZu() {
        return pengchupaiZuList.size();
    }

    public int countGangchupaiZu() {
        return gangchupaiZuList.size();
    }

    public void checkAndGenerateGuoCandidateAction(PanPlayerAction causedByAction) {
        for (int i = 1; i <= actionCandidates.size(); i++) {
            PanPlayerAction action = actionCandidates.get(i);
            if (action instanceof ChiAction
                    || action instanceof PengAction
                    || action instanceof GangAction
                    || action instanceof HuAction) {
                addActionCandidate(new GuoAction(id, causedByAction));
                return;
            }
        }
    }

    public void generateDaActions() {
        Set<MajiangPai> daPaiSet = new HashSet<>();
        fangruShoupaiList.forEach((shoupai) -> {
            if (!daPaiSet.contains(shoupai)) {
                addActionCandidate(new DaAction(id, shoupai));
                daPaiSet.add(shoupai);
            }
        });

        if (gangmoShoupai != null && !daPaiSet.contains(gangmoShoupai)) {
            addActionCandidate(new DaAction(id, gangmoShoupai));
        }
    }

    public void daChuPai(MajiangPai pai) {
        fangruShoupai();
        fangruShoupaiList.remove(pai);
        dachupaiList.add(pai);
        shoupaiCalculator.removePai(pai);
    }

    /**
     * 把刚摸的牌放入手牌
     */
    public void fangruShoupai() {
        if (gangmoShoupai != null) {
            addShoupai(gangmoShoupai);
            gangmoShoupai = null;
        }
    }

    public void tryChiAndGenerateCandidateActions(String dachupaiPlayerId, MajiangPai pai) {
        // 只有两张手牌时不能吃
        if (fangruShoupaiList.size() == 2) {
            return;
        }
        if (MajiangPai.isXushupai(pai)) {
            Shunzi shunzi1 = shoupaiCalculator.tryAndMakeShunziWithPai1(pai);
            if (shunzi1 != null) {
                addActionCandidate(new ChiAction(id, dachupaiPlayerId, pai, shunzi1));
            }

            Shunzi shunzi2 = shoupaiCalculator.tryAndMakeShunziWithPai2(pai);
            if (shunzi2 != null) {
                addActionCandidate(new ChiAction(id, dachupaiPlayerId, pai, shunzi2));
            }

            Shunzi shunzi3 = shoupaiCalculator.tryAndMakeShunziWithPai3(pai);
            if (shunzi3 != null) {
                addActionCandidate(new ChiAction(id, dachupaiPlayerId, pai, shunzi3));
            }

        }
    }

    public void tryPengAndGenerateCandidateAction(String dachupaiPlayerId, MajiangPai pai) {
        int count = shoupaiCalculator.count(pai);
        if (count >= 2) {
            addActionCandidate(new PengAction(id, dachupaiPlayerId, pai));
        }
    }

    public void tryGangdachuAndGenerateCandidateAction(String dachupaiPlayerId, MajiangPai pai) {
        int count = shoupaiCalculator.count(pai);
        if (count >= 3) {
            addActionCandidate(new GangAction(id, dachupaiPlayerId, pai, GangType.gangdachu));
        }
    }

    public boolean hasPengActionCandidate() {
        for (PanPlayerAction action : actionCandidates.values()) {
            if (action instanceof PengAction) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDaActionCandidate() {
        for (PanPlayerAction action : actionCandidates.values()) {
            if (action instanceof DaAction) {
                return true;
            }
        }
        return false;
    }

    public boolean hasGangActionCandidate() {
        for (PanPlayerAction action : actionCandidates.values()) {
            if (action instanceof GangAction) {
                return true;
            }
        }
        return false;
    }

    public boolean hasHuActionCandidate() {
        for (PanPlayerAction action : actionCandidates.values()) {
            if (action instanceof HuAction) {
                return true;
            }
        }
        return false;
    }

    public void chiPai(PanPlayer dachupaiPlayer, MajiangPai chijinpai, Shunzi chifaShunzi) {
        dachupaiPlayer.removeLatestDachupai();
        MajiangPai pai1 = chifaShunzi.getPai1();
        if (!pai1.equals(chijinpai)) {
            fangruShoupaiList.remove(pai1);
            shoupaiCalculator.removePai(pai1);
        }
        MajiangPai pai2 = chifaShunzi.getPai2();
        if (!pai2.equals(chijinpai)) {
            fangruShoupaiList.remove(pai2);
            shoupaiCalculator.removePai(pai2);
        }
        MajiangPai pai3 = chifaShunzi.getPai3();
        if (!pai3.equals(chijinpai)) {
            fangruShoupaiList.remove(pai3);
            shoupaiCalculator.removePai(pai3);
        }
        ChichuPaiZu chichuPaiZu = new ChichuPaiZu(chijinpai, chifaShunzi, dachupaiPlayer.getId(), id);
        chichupaiZuList.add(chichuPaiZu);
    }

    private void removeLatestDachupai() {
        dachupaiList.remove(dachupaiList.size() - 1);
    }

    public void pengPai(PanPlayer dachupaiPlayer, MajiangPai pai) {
        dachupaiPlayer.removeLatestDachupai();
        fangruShoupaiList.remove(pai);
        fangruShoupaiList.remove(pai);
        shoupaiCalculator.removePai(pai, 2);
        PengchuPaiZu pengchuPaiZu = new PengchuPaiZu(new Kezi(pai), dachupaiPlayer.getId(), id);
        pengchupaiZuList.add(pengchuPaiZu);
    }

    public void gangDachupai(PanPlayer dachupaiPlayer, MajiangPai pai) {
        dachupaiPlayer.removeLatestDachupai();
        fangruShoupaiList.remove(pai);
        fangruShoupaiList.remove(pai);
        fangruShoupaiList.remove(pai);
        shoupaiCalculator.removePai(pai, 3);
        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu(new Gangzi(pai), dachupaiPlayer.getId(), id, GangType.gangdachu);
        gangchupaiZuList.add(gangchuPaiZu);
    }

    public void gangMopai(MajiangPai pai) {
        fangruShoupaiList.remove(pai);
        fangruShoupaiList.remove(pai);
        fangruShoupaiList.remove(pai);
        shoupaiCalculator.removePai(pai, 3);
        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu(new Gangzi(pai), null, id, GangType.shoupaigangmo);
        gangchupaiZuList.add(gangchuPaiZu);
        gangmoShoupai = null;
    }

    public void gangSigeshoupai(MajiangPai pai) {
        fangruShoupaiList.remove(pai);
        fangruShoupaiList.remove(pai);
        fangruShoupaiList.remove(pai);
        fangruShoupaiList.remove(pai);
        shoupaiCalculator.removePai(pai, 4);
        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu(new Gangzi(pai), null, id, GangType.gangsigeshoupai);
        gangchupaiZuList.add(gangchuPaiZu);
        fangruShoupai();
    }

    public void keziGangMopai(MajiangPai pai) {
        Iterator<PengchuPaiZu> i = pengchupaiZuList.iterator();
        while (i.hasNext()) {
            PengchuPaiZu pengchuPai = i.next();
            if (pengchuPai.getKezi().getPaiType().equals(pai)) {
                i.remove();
                break;
            }
        }
        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu(new Gangzi(pai), null, id, GangType.kezigangmo);
        gangchupaiZuList.add(gangchuPaiZu);
        gangmoShoupai = null;
    }

    public void keziGangShoupai(MajiangPai pai) {
        Iterator<PengchuPaiZu> i = pengchupaiZuList.iterator();
        while (i.hasNext()) {
            PengchuPaiZu pengchuPai = i.next();
            if (pengchuPai.getKezi().getPaiType().equals(pai)) {
                i.remove();
                break;
            }
        }
        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu(new Gangzi(pai), null, id, GangType.kezigangshoupai);
        gangchupaiZuList.add(gangchuPaiZu);
        fangruShoupaiList.remove(pai);
        shoupaiCalculator.removePai(pai);
        fangruShoupai();
    }

    public Hu getHu() {
        return hu;
    }

    public void setHu(Hu hu) {
        this.hu = hu;
    }

    public List<MajiangPai> findShoupaiForPaiTypes(Set<MajiangPai> paiTypes) {
        List<MajiangPai> shoupaiList = new ArrayList<>();
        for (MajiangPai shoupai : fangruShoupaiList) {
            if (paiTypes.contains(shoupai)) {
                shoupaiList.add(shoupai);
            }
        }
        if (gangmoShoupai != null && paiTypes.contains(gangmoShoupai)) {
            shoupaiList.add(gangmoShoupai);
        }
        return shoupaiList;
    }


    public void removeDaActionCandidate(MajiangPai pai) {
        for (PanPlayerAction action : actionCandidates.values()) {
            if (action instanceof DaAction) {
                DaAction daAction = (DaAction) action;
                if (daAction.getPai().equals(pai)) {
                    actionCandidates.remove(action.getId());
                    return;
                }
            }
        }
    }

    public void releaseActionBlockedByHigherPriorityAction() {
        for (PanPlayerAction action : actionCandidates.values()) {
            if (action instanceof ChiAction) {
                ChiAction chiAction = (ChiAction) action;
                if (chiAction.isBlockedByHigherPriorityAction()) {
                    chiAction.setBlockedByHigherPriorityAction(false);
                }
            }
        }
    }
}
