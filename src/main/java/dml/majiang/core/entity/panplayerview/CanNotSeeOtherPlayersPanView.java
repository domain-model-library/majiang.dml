package dml.majiang.core.entity.panplayerview;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;

/**
 * 最常见的盘视图，只能看到自己的牌和公开的牌
 */
public class CanNotSeeOtherPlayersPanView {

    private long id;

    private CanNotSeeOtherPlayersPlayerView selfPlayerView;
    private CanNotSeeOtherPlayersPlayerView shangjiaPlayerView;
    private CanNotSeeOtherPlayersPlayerView xiajiaPlayerView;
    private CanNotSeeOtherPlayersPlayerView duijiaPlayerView;
    private int avaliablePaiLeft;


    private String zhuangPlayerId;

    public CanNotSeeOtherPlayersPanView() {
    }

    public CanNotSeeOtherPlayersPanView(String playerId, Pan pan) {
        this.id = pan.getId();
        PanPlayer currPlayer = pan.findPlayerById(playerId);
        selfPlayerView = new CanNotSeeOtherPlayersPlayerView(currPlayer, playerId);
        currPlayer = pan.findNextMenFengPlayer(currPlayer);
        xiajiaPlayerView = new CanNotSeeOtherPlayersPlayerView(currPlayer, playerId);
        currPlayer = pan.findNextMenFengPlayer(currPlayer);
        duijiaPlayerView = new CanNotSeeOtherPlayersPlayerView(currPlayer, playerId);
        currPlayer = pan.findNextMenFengPlayer(currPlayer);
        shangjiaPlayerView = new CanNotSeeOtherPlayersPlayerView(currPlayer, playerId);
        this.zhuangPlayerId = pan.getZhuangPlayerId();

        this.avaliablePaiLeft = pan.countAvaliablePai();
    }

    public long getId() {
        return id;
    }

    public String getZhuangPlayerId() {
        return zhuangPlayerId;
    }

    public CanNotSeeOtherPlayersPlayerView getSelfPlayerView() {
        return selfPlayerView;
    }

    public CanNotSeeOtherPlayersPlayerView getShangjiaPlayerView() {
        return shangjiaPlayerView;
    }

    public CanNotSeeOtherPlayersPlayerView getXiajiaPlayerView() {
        return xiajiaPlayerView;
    }

    public CanNotSeeOtherPlayersPlayerView getDuijiaPlayerView() {
        return duijiaPlayerView;
    }

    public int getAvaliablePaiLeft() {
        return avaliablePaiLeft;
    }
}
