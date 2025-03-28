package dml.majiang.core.entity;

import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.action.chi.ChiAction;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.action.guo.GuoAction;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.action.peng.PengAction;
import dml.majiang.core.entity.chupaizu.ChichuPaiZu;
import dml.majiang.core.entity.chupaizu.GangchuPaiZu;
import dml.majiang.core.entity.chupaizu.PengchuPaiZu;
import dml.majiang.core.entity.fenzu.GangType;
import dml.majiang.core.entity.fenzu.Gangzi;
import dml.majiang.core.entity.fenzu.Kezi;
import dml.majiang.core.entity.fenzu.Shunzi;

import java.util.*;

public class PanPlayer {
    private String id;
    private MenFeng menFeng;

    /**
     * 已放入的手牌
     */
    private Map<Integer, Pai> fangruShoupai = new HashMap<>();

    /**
     * 刚摸进待处理的手牌（未放入）
     */
    private Pai gangmoShoupai;

    private Map<Integer, PanPlayerAction> actionCandidates = new HashMap<>();


    /**
     * 打出的牌列表
     */
    private List<Pai> dachupaiList = new ArrayList<>();

    private List<ChichuPaiZu> chichupaiZuList = new ArrayList<>();
    private List<PengchuPaiZu> pengchupaiZuList = new ArrayList<>();
    private List<GangchuPaiZu> gangchupaiZuList = new ArrayList<>();


    public void addShoupai(Pai pai) {
        fangruShoupaiList.add(pai);
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

    public Map<Integer, Pai> getFangruShoupai() {
        return fangruShoupai;
    }

    public void setFangruShoupai(Map<Integer, Pai> fangruShoupai) {
        this.fangruShoupai = fangruShoupai;
    }

    public Map<Integer, PanPlayerAction> getActionCandidates() {
        return actionCandidates;
    }

    public PanPlayerAction findActionCandidate(int actionId) {
        return actionCandidates.get(actionId);
    }

    public void setGangmoShoupai(Pai pai) {
        this.gangmoShoupai = pai;
    }

    public Pai getGangmoShoupai() {
        return gangmoShoupai;
    }

    public void setActionCandidates(Map<Integer, PanPlayerAction> actionCandidates) {
        this.actionCandidates = actionCandidates;
    }

    public List<Pai> getDachupaiList() {
        return dachupaiList;
    }

    public void setDachupaiList(List<Pai> dachupaiList) {
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
        fangruShoupai.keySet().forEach(paiId -> addActionCandidate(new DaAction(id, paiId)));
        if (gangmoShoupai != null) {
            addActionCandidate(new DaAction(id, gangmoShoupai.getId()));
        }
    }

    public void daChuPai(int paiId) {
        fangruShoupai();
        Pai pai = fangruShoupai.remove(paiId);
        dachupaiList.add(pai);
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

    public void chiPai(PanPlayer dachupaiPlayer, int chijinpaiId, int[] chifaShunziPaiIds) {
        Pai pai1 = chifaShunziPaiIds[0] == chijinpaiId ? dachupaiPlayer.removeDachupai(chijinpaiId) : fangruShoupai.remove(chifaShunziPaiIds[0]);
        Pai pai2 = chifaShunziPaiIds[1] == chijinpaiId ? dachupaiPlayer.removeDachupai(chijinpaiId) : fangruShoupai.remove(chifaShunziPaiIds[1]);
        Pai pai3 = chifaShunziPaiIds[2] == chijinpaiId ? dachupaiPlayer.removeDachupai(chijinpaiId) : fangruShoupai.remove(chifaShunziPaiIds[2]);
        Shunzi shunzi = new Shunzi(pai1, pai2, pai3);
        ChichuPaiZu chichuPaiZu = new ChichuPaiZu(chijinpaiId, shunzi, dachupaiPlayer.getId(), id);
        chichupaiZuList.add(chichuPaiZu);
    }

    private Pai removeDachupai(int paiId) {
        Iterator<Pai> i = dachupaiList.iterator();
        while (i.hasNext()) {
            Pai pai = i.next();
            if (pai.getId() == paiId) {
                i.remove();
                return pai;
            }
        }
        return null;
    }

    public void pengPai(PanPlayer dachupaiPlayer, int paiId) {
        Pai pai1 = dachupaiPlayer.removeDachupai(paiId);
        Pai pai2 = removeShoupaiForType(pai1.getPaiType());
        Pai pai3 = removeShoupaiForType(pai1.getPaiType());
        PengchuPaiZu pengchuPaiZu = new PengchuPaiZu(new Kezi(pai1, pai2, pai3), dachupaiPlayer.getId(), id);
        pengchupaiZuList.add(pengchuPaiZu);
    }

    private Pai removeShoupaiForType(MajiangPai paiType) {
        for (Pai pai : fangruShoupai.values()) {
            if (pai.getPaiType().equals(paiType)) {
                fangruShoupai.remove(pai.getId());
                return pai;
            }
        }
        return null;
    }

    public void gangDachupai(PanPlayer dachupaiPlayer, int paiId) {
        Pai pai1 = dachupaiPlayer.removeDachupai(paiId);
        Pai pai2 = removeShoupaiForType(pai1.getPaiType());
        Pai pai3 = removeShoupaiForType(pai1.getPaiType());
        Pai pai4 = removeShoupaiForType(pai1.getPaiType());
        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu(new Gangzi(pai1, pai2, pai3, pai4), dachupaiPlayer.getId(), id, GangType.gangdachu);
        gangchupaiZuList.add(gangchuPaiZu);
    }

    public void gangMopai() {
        Pai pai1 = gangmoShoupai;
        Pai pai2 = removeShoupaiForType(pai1.getPaiType());
        Pai pai3 = removeShoupaiForType(pai1.getPaiType());
        Pai pai4 = removeShoupaiForType(pai1.getPaiType());
        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu(new Gangzi(pai1, pai2, pai3, pai4), null, id, GangType.shoupaigangmo);
        gangchupaiZuList.add(gangchuPaiZu);
        gangmoShoupai = null;
    }

    public void gangSigeshoupai(MajiangPai paiType) {
        Pai pai1 = removeShoupaiForType(paiType);
        Pai pai2 = removeShoupaiForType(paiType);
        Pai pai3 = removeShoupaiForType(paiType);
        Pai pai4 = removeShoupaiForType(paiType);
        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu(new Gangzi(pai1, pai2, pai3, pai4), null, id, GangType.gangsigeshoupai);
        gangchupaiZuList.add(gangchuPaiZu);
        fangruShoupai();
    }

    public void keziGangMopai() {
        Pai pai1 = gangmoShoupai;
        PengchuPaiZu pengchuPai = null;
        Iterator<PengchuPaiZu> i = pengchupaiZuList.iterator();
        while (i.hasNext()) {
            pengchuPai = i.next();
            if (pengchuPai.getKezi().getPaiType().equals(pai1.getPaiType())) {
                i.remove();
                break;
            }
        }
        Pai pai2 = pengchuPai.getKezi().getPai1();
        Pai pai3 = pengchuPai.getKezi().getPai2();
        Pai pai4 = pengchuPai.getKezi().getPai3();
        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu(new Gangzi(pai1, pai2, pai3, pai4), null, id, GangType.kezigangmo);
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

    public int countFangruShoupai() {
        return fangruShoupaiList.size();
    }

    public void removeMoActionCandidate() {
        for (PanPlayerAction action : actionCandidates.values()) {
            if (action instanceof MoAction) {
                actionCandidates.remove(action.getId());
                return;
            }
        }
    }
}
