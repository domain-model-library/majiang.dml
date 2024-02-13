package dml.majiang.core.entity.panplayerview;

import dml.majiang.core.entity.Pan;

import java.util.HashMap;
import java.util.Map;

/**
 * 最常见的盘视图，只能看到自己的牌和公开的牌
 */
public class CanNotSeeOtherPlayersPanView {

    private long id;

    private Map<String, CanNotSeeOtherPlayersPlayerView> playerIdPanPlayerMap = new HashMap<>();

    private String zhuangPlayerId;

    public CanNotSeeOtherPlayersPanView() {
    }

    public CanNotSeeOtherPlayersPanView(String playerId, Pan pan) {
        this.id = pan.getId();
        pan.getPlayerIdPanPlayerMap().forEach((pid, panPlayer) -> playerIdPanPlayerMap.put(pid, new CanNotSeeOtherPlayersPlayerView(panPlayer, playerId)));
        this.zhuangPlayerId = pan.getZhuangPlayerId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, CanNotSeeOtherPlayersPlayerView> getPlayerIdPanPlayerMap() {
        return playerIdPanPlayerMap;
    }

    public void setPlayerIdPanPlayerMap(Map<String, CanNotSeeOtherPlayersPlayerView> playerIdPanPlayerMap) {
        this.playerIdPanPlayerMap = playerIdPanPlayerMap;
    }

    public String getZhuangPlayerId() {
        return zhuangPlayerId;
    }

    public void setZhuangPlayerId(String zhuangPlayerId) {
        this.zhuangPlayerId = zhuangPlayerId;
    }

}
