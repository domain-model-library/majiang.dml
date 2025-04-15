package dml.majiang.core.entity;

import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.action.chi.ChiAction;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.action.guo.GuoAction;
import dml.majiang.core.entity.action.hu.Hu;
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
import dml.majiang.core.entity.shoupai.ShoupaiBiaoZhunPanHu;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;
import dml.majiang.core.entity.shoupai.ShoupaiShunziCalculator;

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

    /**
     * 胡牌
     */
    private Hu hu;

    public void setHu(Hu hu) {
        this.hu = hu;
    }

    public Hu getHu() {
        return hu;
    }


    public void addShoupai(Pai pai) {
        fangruShoupai.put(pai.getId(), pai);
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

    public void tryShoupaigangmoAndGenerateCandidateAction() {
        int[] paiIds = new int[4];
        int paiIdsIdx = 0;
        paiIds[paiIdsIdx++] = gangmoShoupai.getId();
        for (Pai pai : fangruShoupai.values()) {
            if (pai.getPaiType().equals(gangmoShoupai.getPaiType())) {
                paiIds[paiIdsIdx++] = pai.getId();
            }
        }
        if (paiIdsIdx >= 4) {
            addActionCandidate(new GangAction(id, null, gangmoShoupai.getId(), paiIds, GangType.shoupaigangmo));
        }
    }

    public void tryKezigangmoAndGenerateCandidateAction() {
        int[] paiIds = new int[4];
        paiIds[0] = gangmoShoupai.getId();
        for (PengchuPaiZu pengchuPaiZu : pengchupaiZuList) {
            if (pengchuPaiZu.getKezi().getPaiType().equals(gangmoShoupai.getPaiType())) {
                paiIds[1] = pengchuPaiZu.getKezi().getPai1().getId();
                paiIds[2] = pengchuPaiZu.getKezi().getPai2().getId();
                paiIds[3] = pengchuPaiZu.getKezi().getPai3().getId();
                addActionCandidate(new GangAction(id, null, gangmoShoupai.getId(), paiIds, GangType.kezigangmo));
                return;
            }
        }
    }

    public void tryGangsigeshoupaiAndGenerateCandidateAction() {
        Map<MajiangPai, Set<Pai>> paiMap = makePaiTypeShoupaiMap();
        List<Set<Pai>> gangpaiList = new ArrayList<>();
        for (Set<Pai> paiSet : paiMap.values()) {
            if (paiSet.size() == 4) {
                gangpaiList.add(paiSet);
            }
        }
        for (Set<Pai> paiSet : gangpaiList) {
            int[] paiIds = new int[4];
            int paiIdsIdx = 0;
            for (Pai pai : paiSet) {
                paiIds[paiIdsIdx++] = pai.getId();
            }
            addActionCandidate(new GangAction(id, null, -1, paiIds, GangType.gangsigeshoupai));
        }
    }

    public void tryKezigangshoupaiAndGenerateCandidateAction() {
        for (PengchuPaiZu pengchuPaiZu : pengchupaiZuList) {
            for (Pai shoupai : fangruShoupai.values()) {
                if (pengchuPaiZu.getKezi().getPaiType().equals(shoupai.getPaiType())) {
                    int[] paiIds = new int[4];
                    paiIds[0] = shoupai.getId();
                    paiIds[1] = pengchuPaiZu.getKezi().getPai1().getId();
                    paiIds[2] = pengchuPaiZu.getKezi().getPai2().getId();
                    paiIds[3] = pengchuPaiZu.getKezi().getPai3().getId();
                    addActionCandidate(new GangAction(id, null, shoupai.getId(), paiIds, GangType.kezigangshoupai));
                    break;
                }
            }
        }
    }

    public void tryChiAndGenerateCandidateActions(PanPlayer dachupaiPlayer, int dapaiId) {
        List<Pai> shoupaiList = new ArrayList<>();
        for (Pai pai : fangruShoupai.values()) {
            shoupaiList.add(pai);
        }
        Pai daPai = dachupaiPlayer.findDachupai(dapaiId);
        List<int[]> shunziPaiIdList = ShoupaiShunziCalculator.tryAndMakeShunziWithPai(shoupaiList, daPai);
        if (shunziPaiIdList == null) {
            for (int[] shunziPaiId : shunziPaiIdList) {
                addActionCandidate(new ChiAction(id, dachupaiPlayer.getId(), dapaiId, shunziPaiId));
            }
        }
    }

    public void tryPengAndGenerateCandidateAction(PanPlayer dachupaiPlayer, int dapaiId) {
        Pai dapai = dachupaiPlayer.findDachupai(dapaiId);
        Map<MajiangPai, Set<Pai>> paiMap = makePaiTypeShoupaiMap();
        Set<Pai> paiSet = paiMap.get(dapai.getPaiType());
        if (paiSet != null && paiSet.size() >= 2) {
            int[] paiIds = new int[3];
            paiIds[0] = dapaiId;
            Iterator<Pai> i = paiSet.iterator();
            int idx = 1;
            while (idx < 3) {
                Pai pai = i.next();
                paiIds[idx++] = pai.getId();
            }
            addActionCandidate(new PengAction(id, dachupaiPlayer.getId(), dapaiId, paiIds));
        }
    }

    public void tryGangdachuAndGenerateCandidateAction(PanPlayer dachupaiPlayer, int dapaiId) {
        Pai dapai = dachupaiPlayer.findDachupai(dapaiId);
        Map<MajiangPai, Set<Pai>> paiMap = makePaiTypeShoupaiMap();
        Set<Pai> paiSet = paiMap.get(dapai.getPaiType());
        if (paiSet != null && paiSet.size() >= 3) {
            int[] paiIds = new int[4];
            paiIds[0] = dapaiId;
            Iterator<Pai> i = paiSet.iterator();
            int idx = 1;
            while (idx < 4) {
                Pai pai = i.next();
                paiIds[idx++] = pai.getId();
            }
            addActionCandidate(new GangAction(id, dachupaiPlayer.getId(), dapaiId, paiIds, GangType.gangdachu));
        }
    }

    public Pai findDachupai(int dapaiId) {
        for (Pai pai : dachupaiList) {
            if (pai.getId() == dapaiId) {
                return pai;
            }
        }
        return null;
    }

    private Map<MajiangPai, Set<Pai>> makePaiTypeShoupaiMap() {
        Map<MajiangPai, Set<Pai>> paiMap = new HashMap<>();
        fangruShoupai.values().forEach(pai -> {
            if (paiMap.containsKey(pai.getPaiType())) {
                paiMap.get(pai.getPaiType()).add(pai);
            } else {
                Set<Pai> paiSet = new HashSet<>();
                paiSet.add(pai);
                paiMap.put(pai.getPaiType(), paiSet);
            }
        });
        return paiMap;
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

    public void chiPai(PanPlayer dachupaiPlayer, int chijinpaiId, int[] shunziPaiIds) {
        Pai pai1 = shunziPaiIds[0] == chijinpaiId ? dachupaiPlayer.removeDachupai(chijinpaiId) : fangruShoupai.remove(shunziPaiIds[0]);
        Pai pai2 = shunziPaiIds[1] == chijinpaiId ? dachupaiPlayer.removeDachupai(chijinpaiId) : fangruShoupai.remove(shunziPaiIds[1]);
        Pai pai3 = shunziPaiIds[2] == chijinpaiId ? dachupaiPlayer.removeDachupai(chijinpaiId) : fangruShoupai.remove(shunziPaiIds[2]);
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

    public void gangSigeshoupai(int[] paiIds) {
        Pai pai1 = fangruShoupai.remove(paiIds[0]);
        Pai pai2 = fangruShoupai.remove(paiIds[1]);
        Pai pai3 = fangruShoupai.remove(paiIds[2]);
        Pai pai4 = fangruShoupai.remove(paiIds[3]);
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

    public void keziGangShoupai(int paiId) {
        Pai pai1 = fangruShoupai.remove(paiId);
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
        GangchuPaiZu gangchuPaiZu = new GangchuPaiZu(new Gangzi(pai1, pai2, pai3, pai4), null, id, GangType.kezigangshoupai);
        gangchupaiZuList.add(gangchuPaiZu);
        fangruShoupai();
    }

    public List<Pai> findShoupaiForPaiTypes(Set<MajiangPai> paiTypes) {
        List<Pai> shoupaiList = new ArrayList<>();
        fangruShoupai.values().forEach(pai -> {
            if (paiTypes.contains(pai.getPaiType())) {
                shoupaiList.add(pai);
            }
        });
        if (gangmoShoupai != null && paiTypes.contains(gangmoShoupai.getPaiType())) {
            shoupaiList.add(gangmoShoupai);
        }
        return shoupaiList;
    }


    public void removeDaActionCandidate(int paiId) {
        for (PanPlayerAction action : actionCandidates.values()) {
            if (action instanceof DaAction) {
                DaAction daAction = (DaAction) action;
                if (daAction.getPaiId() == paiId) {
                    actionCandidates.remove(action.getId());
                    return;
                }
            }
        }
    }

    public void removeDaActionCandidateForPaiType(MajiangPai paiType) {
        for (Pai shouPai : fangruShoupai.values()) {
            if (shouPai.getPaiType().equals(paiType)) {
                removeDaActionCandidate(shouPai.getId());
            }
        }
        if (gangmoShoupai != null && gangmoShoupai.getPaiType().equals(paiType)) {
            removeDaActionCandidate(gangmoShoupai.getId());
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
        return fangruShoupai.size();
    }

    public void removeMoActionCandidate() {
        for (PanPlayerAction action : actionCandidates.values()) {
            if (action instanceof MoAction) {
                actionCandidates.remove(action.getId());
                return;
            }
        }
    }

    public List<Pai> getFangruShoupaiList() {
        List<Pai> shoupaiList = new ArrayList<>();
        fangruShoupai.values().forEach(shoupai -> {
            shoupaiList.add(shoupai);
        });
        return shoupaiList;
    }

    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForZimoHu() {
        List<Pai> shoupaiList = getFangruShoupaiList();
        shoupaiList.add(gangmoShoupai);
        return ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
    }

    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForDianpaoHu(Pai dachupai) {
        List<Pai> shoupaiList = getFangruShoupaiList();
        shoupaiList.add(dachupai);
        return ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
    }

    public List<ShoupaiPaiXing> calculateAllHuPaiShoupaiPaiXingForQianggangHu(Pai gangpai) {
        List<Pai> shoupaiList = getFangruShoupaiList();
        shoupaiList.add(gangpai);
        return ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
    }

    public Pai findGangchuPai(int gangPaiId) {
        for (GangchuPaiZu gangchuPaiZu : gangchupaiZuList) {
            if (gangchuPaiZu.getGangzi().getPai1().getId() == gangPaiId) {
                return gangchuPaiZu.getGangzi().getPai1();
            }
            if (gangchuPaiZu.getGangzi().getPai2().getId() == gangPaiId) {
                return gangchuPaiZu.getGangzi().getPai2();
            }
            if (gangchuPaiZu.getGangzi().getPai3().getId() == gangPaiId) {
                return gangchuPaiZu.getGangzi().getPai3();
            }
            if (gangchuPaiZu.getGangzi().getPai4().getId() == gangPaiId) {
                return gangchuPaiZu.getGangzi().getPai4();
            }
        }
        return null;
    }
}
