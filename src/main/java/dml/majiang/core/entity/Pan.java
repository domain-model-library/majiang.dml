package dml.majiang.core.entity;

import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.cursor.PaiCursor;
import dml.majiang.core.entity.cursor.PlayerLatestDachupaiCursor;

import java.util.*;

/**
 * 一盘麻将
 */
public class Pan {
    private long id;

    private Map<String, PanPlayer> playerIdPanPlayerMap = new HashMap<>();

    private Map<MenFeng, String> menFengPanPlayerIdMap = new HashMap<>();

    private String zhuangPlayerId;

    private List<Pai> avaliablePaiList;

    /**
     * 当前活跃的那张牌的定位
     */
    private PaiCursor activePaiCursor;//TODO 这东西也应该分到新的service里

    /**
     * 只玩哪些牌
     */
    private List<MajiangPai> playPaiTypeList;

    public Pan copy() {
        Pan pan = new Pan();
        pan.setId(id);
        pan.setAvaliablePaiList(new ArrayList<>(avaliablePaiList));
        Map<String, PanPlayer> newPlayerIdPanPlayerMap = new HashMap<>();
        for (Map.Entry<String, PanPlayer> entry : playerIdPanPlayerMap.entrySet()) {
            String playerId = entry.getKey();
            PanPlayer panPlayer = entry.getValue();
            PanPlayer newPanPlayer = panPlayer.copy();
            newPlayerIdPanPlayerMap.put(playerId, newPanPlayer);
        }
        pan.setPlayerIdPanPlayerMap(newPlayerIdPanPlayerMap);
        pan.setMenFengPanPlayerIdMap(new HashMap<>(menFengPanPlayerIdMap));
        pan.setZhuangPlayerId(zhuangPlayerId);
        pan.setPlayPaiTypeList(new ArrayList<>(playPaiTypeList));
        if (activePaiCursor != null) {
            pan.setActivePaiCursor(activePaiCursor.copy());
        }
        return pan;
    }

    public void override(Pan pan) {
        this.id = pan.getId();
        this.avaliablePaiList = new ArrayList<>(pan.getAvaliablePaiList());
        this.playerIdPanPlayerMap = new HashMap<>();
        for (Map.Entry<String, PanPlayer> entry : pan.getPlayerIdPanPlayerMap().entrySet()) {
            String playerId = entry.getKey();
            PanPlayer panPlayer = entry.getValue();
            PanPlayer newPanPlayer = panPlayer.copy();
            this.playerIdPanPlayerMap.put(playerId, newPanPlayer);
        }
        this.menFengPanPlayerIdMap = new HashMap<>(pan.getMenFengPanPlayerIdMap());
        this.zhuangPlayerId = pan.getZhuangPlayerId();
        this.playPaiTypeList = new ArrayList<>(pan.getPlayPaiTypeList());
        this.activePaiCursor = pan.getActivePaiCursor().copy();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addPlayer(String playerId) {
        PanPlayer panPlayer = new PanPlayer();
        panPlayer.setId(playerId);
        playerIdPanPlayerMap.put(playerId, panPlayer);
    }

    public List<String> allPlayerIds() {
        return new ArrayList<>(playerIdPanPlayerMap.keySet());
    }

    public void setPlayerMenFeng(String playerId, MenFeng menFeng) {
        PanPlayer panPlayer = playerIdPanPlayerMap.get(playerId);
        panPlayer.setMenFeng(menFeng);
        menFengPanPlayerIdMap.put(menFeng, playerId);
    }

    public void setMenFengPlayerAsZhuang(MenFeng menFeng) {
        for (PanPlayer panPlayer : playerIdPanPlayerMap.values()) {
            if (panPlayer.getMenFeng().equals(menFeng)) {
                zhuangPlayerId = panPlayer.getId();
                break;
            }
        }
    }

    public List<Pai> getAvaliablePaiList() {
        return avaliablePaiList;
    }

    public void setAvaliablePaiList(List<Pai> avaliablePaiList) {
        this.avaliablePaiList = avaliablePaiList;
    }

    public MenFeng findMenFengForZhuang() {
        PanPlayer zhuangPlayer = playerIdPanPlayerMap.get(zhuangPlayerId);
        return zhuangPlayer.getMenFeng();
    }

    public PanPlayer findPlayerByMenFeng(MenFeng menFeng) {
        String playerId = menFengPanPlayerIdMap.get(menFeng);
        return playerIdPanPlayerMap.get(playerId);
    }

    public List<PanPlayer> allPlayers() {
        return new ArrayList<>(playerIdPanPlayerMap.values());
    }

    public void addPlayerActionCandidate(PanPlayerAction action) {
        PanPlayer player = playerIdPanPlayerMap.get(action.getActionPlayerId());
        if (player != null) {
            player.addActionCandidate(action);
        }
    }

    public String getZhuangPlayerId() {
        return zhuangPlayerId;
    }

    public void setZhuangPlayerId(String zhuangPlayerId) {
        this.zhuangPlayerId = zhuangPlayerId;
    }

    public Map<String, PanPlayer> getPlayerIdPanPlayerMap() {
        return playerIdPanPlayerMap;
    }

    public PanPlayerAction getPlayerAction(String playerId, int actionId) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        if (player != null) {
            return player.findActionCandidate(actionId);
        } else {
            return null;
        }
    }

    public void playerMoPai(String playerId) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        Pai pai = avaliablePaiList.remove(0);
        player.setGangmoShoupai(pai);
    }

    public void setPlayerIdPanPlayerMap(Map<String, PanPlayer> playerIdPanPlayerMap) {
        this.playerIdPanPlayerMap = playerIdPanPlayerMap;
    }

    public int countAvaliablePai() {
        return avaliablePaiList.size();
    }

    public void clearAllPlayersActionCandidates() {
        for (PanPlayer player : playerIdPanPlayerMap.values()) {
            player.clearActionCandidates();
        }
    }

    public PanPlayer findPlayerById(String playerId) {
        return playerIdPanPlayerMap.get(playerId);
    }

    public void playerDaChuPai(String playerId, int paiId) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        player.daChuPai(paiId);
        activePaiCursor = new PlayerLatestDachupaiCursor(playerId);
    }

    public PaiCursor getActivePaiCursor() {
        return activePaiCursor;
    }

    public void setActivePaiCursor(PaiCursor activePaiCursor) {
        this.activePaiCursor = activePaiCursor;
    }

    public PanPlayer findNextMenFengPlayer(PanPlayer player) {
        MenFeng xiajiaMenFeng = player.getMenFeng().next();
        String xiajiaPlayerId = menFengPanPlayerIdMap.get(xiajiaMenFeng);
        while (xiajiaPlayerId == null) {
            xiajiaMenFeng = xiajiaMenFeng.next();
            xiajiaPlayerId = menFengPanPlayerIdMap.get(xiajiaMenFeng);
        }
        return playerIdPanPlayerMap.get(xiajiaPlayerId);
    }

    public void playerChiPai(String chijinpaiPlayerId, String dachupaiPlayerId, int chijinpaiId, int[] shunziPaiIds) {

        PanPlayer chijinpaiPlayer = playerIdPanPlayerMap.get(chijinpaiPlayerId);
        PanPlayer dachupaiPlayer = playerIdPanPlayerMap.get(dachupaiPlayerId);
        chijinpaiPlayer.chiPai(dachupaiPlayer, chijinpaiId, shunziPaiIds);

    }

    public void playerPengPai(String pengjinpaiPlayerId, String dachupaiPlayerId, int paiId) {
        PanPlayer pengjinpaiPlayer = playerIdPanPlayerMap.get(pengjinpaiPlayerId);
        PanPlayer dachupaiPlayer = playerIdPanPlayerMap.get(dachupaiPlayerId);
        pengjinpaiPlayer.pengPai(dachupaiPlayer, paiId);
    }

    public void playerGangDachupai(String gangjinpaiPlayerId, String dachupaiPlayerId, int paiId) {
        PanPlayer gangjinpaiPlayer = playerIdPanPlayerMap.get(gangjinpaiPlayerId);
        PanPlayer dachupaiPlayer = playerIdPanPlayerMap.get(dachupaiPlayerId);
        gangjinpaiPlayer.gangDachupai(dachupaiPlayer, paiId);
    }

    public void playerShoupaiGangMo(String playerId) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        player.gangMopai();
    }

    public void playerGangSigeshoupai(String playerId, int[] paiIds) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        player.gangSigeshoupai(paiIds);
    }

    public void playerKeziGangMo(String playerId) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        player.keziGangMopai();
    }

    public void playerKeziGangShoupai(String playerId, int paiId) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        player.keziGangShoupai(paiId);
    }

    public void playerClearActionCandidates(String playerId) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        if (player != null) {
            player.clearActionCandidates();
        }
    }

    public boolean allPlayerHasNoActionCandidates() {
        for (PanPlayer player : playerIdPanPlayerMap.values()) {
            if (!player.getActionCandidates().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public PanPlayer findXiajia(String playerId) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        MenFeng playerMenFeng = player.getMenFeng();
        MenFeng xiajiaMenFeng = playerMenFeng.xiajia();
        String xiajiaPlayerId = menFengPanPlayerIdMap.get(xiajiaMenFeng);
        if (xiajiaPlayerId == null) {
            return null;
        }
        return playerIdPanPlayerMap.get(xiajiaPlayerId);
    }

    public PanPlayer findShangjia(String playerId) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        MenFeng playerMenFeng = player.getMenFeng();
        MenFeng shangjiaMenFeng = playerMenFeng.shangjia();
        String shangjiaPlayerId = menFengPanPlayerIdMap.get(shangjiaMenFeng);
        if (shangjiaPlayerId == null) {
            return null;
        }
        return playerIdPanPlayerMap.get(shangjiaPlayerId);
    }

    public PanPlayer findDuijia(String playerId) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        MenFeng playerMenFeng = player.getMenFeng();
        MenFeng duijiaMenFeng = playerMenFeng.duijia();
        String duijiaPlayerId = menFengPanPlayerIdMap.get(duijiaMenFeng);
        if (duijiaPlayerId == null) {
            return null;
        }
        return playerIdPanPlayerMap.get(duijiaPlayerId);
    }

    public Hu findHuIfExists() {
        for (PanPlayer player : playerIdPanPlayerMap.values()) {
            Hu hu = player.getHu();
            if (hu != null) {
                return hu;
            }
        }
        return null;
    }

    public List<MajiangPai> getPlayPaiTypeList() {
        return playPaiTypeList;
    }

    public void setPlayPaiTypeList(List<MajiangPai> playPaiTypeList) {
        this.playPaiTypeList = playPaiTypeList;
    }

    public Map<MenFeng, String> getMenFengPanPlayerIdMap() {
        return menFengPanPlayerIdMap;
    }

    public void setMenFengPanPlayerIdMap(Map<MenFeng, String> menFengPanPlayerIdMap) {
        this.menFengPanPlayerIdMap = menFengPanPlayerIdMap;
    }

    public void removeAvaliablePai(MajiangPai pai) {
        Iterator<Pai> i = avaliablePaiList.iterator();
        while (i.hasNext()) {
            Pai avaliablePai = i.next();
            if (avaliablePai.getPaiType().equals(pai)) {
                i.remove();
                break;
            }
        }
    }

    public void releasePlayerActionBlockedByHigherPriorityAction() {
        for (PanPlayer player : playerIdPanPlayerMap.values()) {
            player.releaseActionBlockedByHigherPriorityAction();
        }
    }

    public void removeMoActionCandidate() {
        for (PanPlayer player : playerIdPanPlayerMap.values()) {
            player.removeMoActionCandidate();
        }
    }

    public MoAction findMoCandidateAction() {
        for (PanPlayer player : playerIdPanPlayerMap.values()) {
            MoAction moAction = player.findMoCandidateAction();
            if (moAction != null) {
                return moAction;
            }
        }
        return null;
    }

    public void setPlayerHu(String playerId, Hu hu) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        if (player != null) {
            player.setHu(hu);
        }
    }

    public Pai getFirstAvaliablePai() {
        if (avaliablePaiList != null && !avaliablePaiList.isEmpty()) {
            return avaliablePaiList.get(0);
        }
        return null;
    }

    public Pai getAvaliablePai(int paiId) {
        for (Pai pai : avaliablePaiList) {
            if (pai.getId() == paiId) {
                return pai;
            }
        }
        return null;
    }

    public DaAction findDaCandidateAction(int paiId) {
        for (PanPlayer player : playerIdPanPlayerMap.values()) {
            DaAction daAction = player.findDaCandidateAction(paiId);
            if (daAction != null) {
                return daAction;
            }
        }
        return null;
    }


    public int countPlayersNotHu() {
        int count = 0;
        for (PanPlayer player : playerIdPanPlayerMap.values()) {
            if (player.getHu() == null) {
                count++;
            }
        }
        return count;
    }

    public List<PanPlayer> getHuPlayers() {
        List<PanPlayer> huPlayers = new ArrayList<>();
        for (PanPlayer player : playerIdPanPlayerMap.values()) {
            if (player.getHu() != null) {
                huPlayers.add(player);
            }
        }
        return huPlayers;
    }

    public void removePlayer(PanPlayer player) {
        playerIdPanPlayerMap.remove(player.getId());
        menFengPanPlayerIdMap.remove(player.getMenFeng());
    }

    public void putPlayer(PanPlayer player) {
        playerIdPanPlayerMap.put(player.getId(), player);
        menFengPanPlayerIdMap.put(player.getMenFeng(), player.getId());
    }

    public boolean playerHasHuActionCandidates() {
        for (PanPlayer player : playerIdPanPlayerMap.values()) {
            if (player.hasHuActionCandidate()) {
                return true;
            }
        }
        return false;
    }
}
