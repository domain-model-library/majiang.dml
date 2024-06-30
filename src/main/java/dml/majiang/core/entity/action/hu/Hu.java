package dml.majiang.core.entity.action.hu;


import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

public abstract class Hu {
    private String playerId;
    private ShoupaiPaiXing shoupaiPaiXing;

    public Hu() {
    }

    public Hu(String playerId, ShoupaiPaiXing shoupaiPaiXing) {
        this.playerId = playerId;
        this.shoupaiPaiXing = shoupaiPaiXing;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public ShoupaiPaiXing getShoupaiPaiXing() {
        return shoupaiPaiXing;
    }

    public void setShoupaiPaiXing(ShoupaiPaiXing shoupaiPaiXing) {
        this.shoupaiPaiXing = shoupaiPaiXing;
    }


}
