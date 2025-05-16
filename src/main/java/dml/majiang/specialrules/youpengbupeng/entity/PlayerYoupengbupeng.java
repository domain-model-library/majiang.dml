package dml.majiang.specialrules.youpengbupeng.entity;

import dml.majiang.core.entity.MajiangPai;

public class PlayerYoupengbupeng {
    private String playerId;
    private MajiangPai pengpaiType;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public MajiangPai getPengpaiType() {
        return pengpaiType;
    }

    public void setPengpaiType(MajiangPai pengpaiType) {
        this.pengpaiType = pengpaiType;
    }

    public String toString() {
        return "PlayerYoupengbupeng{" +
                "playerId='" + playerId + '\'' +
                ", pengpaiType=" + pengpaiType +
                '}';
    }
}
