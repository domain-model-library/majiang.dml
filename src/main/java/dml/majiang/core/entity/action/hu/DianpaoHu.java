package dml.majiang.core.entity.action.hu;

import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

public class DianpaoHu extends Hu {
    private String dianpaoPlayerId;

    public DianpaoHu() {
    }

    public DianpaoHu(String playerId, ShoupaiPaiXing shoupaiPaiXing, String dianpaoPlayerId) {
        super(playerId, shoupaiPaiXing);
        this.dianpaoPlayerId = dianpaoPlayerId;
    }

    public String getDianpaoPlayerId() {
        return dianpaoPlayerId;
    }

    public void setDianpaoPlayerId(String dianpaoPlayerId) {
        this.dianpaoPlayerId = dianpaoPlayerId;
    }
}
