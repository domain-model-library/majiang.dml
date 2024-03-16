package dml.majiang.core.entity;

import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.cursor.PaiCursor;
import dml.majiang.core.entity.cursor.PlayerLatestDachupaiCursor;
import dml.majiang.core.entity.fenzu.Shunzi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一盘麻将
 */
public class Pan {
    private long id;

    private Map<String, PanPlayer> playerIdPanPlayerMap = new HashMap<>();

    private Map<MenFeng, String> menFengPanPlayerIdMap = new HashMap<>();

    private String zhuangPlayerId;

    private List<MajiangPai> avaliablePaiList;

    /**
     * 当前活跃的那张牌的定位
     */
    private PaiCursor activePaiCursor;

    /**
     * 只玩哪些牌
     */
    private List<MajiangPai> playPaiTypeList;

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

    public List<MajiangPai> getAvaliablePaiList() {
        return avaliablePaiList;
    }

    public void setAvaliablePaiList(List<MajiangPai> avaliablePaiList) {
        this.avaliablePaiList = avaliablePaiList;
    }

    public MenFeng findMenFengForZhuang() {
        PanPlayer zhuangPlayer = playerIdPanPlayerMap.get(zhuangPlayerId);
        return zhuangPlayer.getMenFeng();
    }

    public PanPlayer findPlayerByMenFeng(MenFeng menFeng) {
        for (PanPlayer panPlayer : playerIdPanPlayerMap.values()) {
            if (panPlayer.getMenFeng().equals(menFeng)) {
                return panPlayer;
            }
        }
        return null;
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
        MajiangPai pai = avaliablePaiList.remove(0);
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

    public PanPlayer findNextMenFengPlayer(String currentMenFengPlayerId) {
        PanPlayer currentMenFengPlayer = playerIdPanPlayerMap.get(currentMenFengPlayerId);
        MenFeng nextMenFeng = currentMenFengPlayer.getMenFeng().next();
        String nextPlayerId = menFengPanPlayerIdMap.get(nextMenFeng);
        while (nextPlayerId == null) {
            nextMenFeng = nextMenFeng.next();
            nextPlayerId = menFengPanPlayerIdMap.get(nextMenFeng);
        }
        return playerIdPanPlayerMap.get(nextPlayerId);
    }

    public void playerDaChuPai(String playerId, MajiangPai pai) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        player.daChuPai(pai);
        activePaiCursor = new PlayerLatestDachupaiCursor(playerId);
    }

    public PaiCursor getActivePaiCursor() {
        return activePaiCursor;
    }

    public void setActivePaiCursor(PaiCursor activePaiCursor) {
        this.activePaiCursor = activePaiCursor;
    }

    public PanPlayer findXiajia(PanPlayer player) {
        MenFeng xiajiaMenFeng = player.getMenFeng().next();
        String xiajiaPlayerId = menFengPanPlayerIdMap.get(xiajiaMenFeng);
        while (xiajiaPlayerId == null) {
            xiajiaMenFeng = xiajiaMenFeng.next();
            xiajiaPlayerId = menFengPanPlayerIdMap.get(xiajiaMenFeng);
        }
        return playerIdPanPlayerMap.get(xiajiaPlayerId);
    }

    public void playerChiPai(String chijinpaiPlayerId, String dachupaiPlayerId, MajiangPai chijinpai,
                             Shunzi chifaShunzi) {

        PanPlayer chijinpaiPlayer = playerIdPanPlayerMap.get(chijinpaiPlayerId);
        PanPlayer dachupaiPlayer = playerIdPanPlayerMap.get(dachupaiPlayerId);
        chijinpaiPlayer.chiPai(dachupaiPlayer, chijinpai, chifaShunzi);

    }

    public void playerPengPai(String pengjinpaiPlayerId, String dachupaiPlayerId, MajiangPai pai) {
        PanPlayer pengjinpaiPlayer = playerIdPanPlayerMap.get(pengjinpaiPlayerId);
        PanPlayer dachupaiPlayer = playerIdPanPlayerMap.get(dachupaiPlayerId);
        pengjinpaiPlayer.pengPai(dachupaiPlayer, pai);
    }

    public void playerGangDachupai(String gangjinpaiPlayerId, String dachupaiPlayerId, MajiangPai pai) {
        PanPlayer gangjinpaiPlayer = playerIdPanPlayerMap.get(gangjinpaiPlayerId);
        PanPlayer dachupaiPlayer = playerIdPanPlayerMap.get(dachupaiPlayerId);
        gangjinpaiPlayer.gangDachupai(dachupaiPlayer, pai);
    }

    public void playerShoupaiGangMo(String playerId, MajiangPai pai) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        player.gangMopai(pai);
    }

    public void playerGangSigeshoupai(String playerId, MajiangPai pai) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        player.gangSigeshoupai(pai);
    }

    public void playerKeziGangMo(String playerId, MajiangPai pai) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        player.keziGangMopai(pai);
    }

    public void playerKeziGangShoupai(String playerId, MajiangPai pai) {
        PanPlayer player = playerIdPanPlayerMap.get(playerId);
        player.keziGangShoupai(pai);
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
        avaliablePaiList.remove(pai);
    }

}
