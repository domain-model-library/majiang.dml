package dml.majiang.core.entity.cursor;

/**
 * 定位玩家最后打出的牌需要的信息
 *
 * @author Neo
 */
public class PlayerLatestDachupaiCursor implements PaiCursor {

    private String playerId;

    public PlayerLatestDachupaiCursor() {
    }

    public PlayerLatestDachupaiCursor(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

}
