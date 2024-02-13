package dml.majiang.core.entity.action.hu;

import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

public class QianggangHu extends Hu {
    private String gangPlayerId;

    public QianggangHu() {
    }

    public QianggangHu(ShoupaiPaiXing shoupaiPaiXing, String gangPlayerId) {
        super(shoupaiPaiXing);
        this.gangPlayerId = gangPlayerId;
    }

    public String getGangPlayerId() {
        return gangPlayerId;
    }

    public void setGangPlayerId(String gangPlayerId) {
        this.gangPlayerId = gangPlayerId;
    }
}
