package dml.majiang.core.entity.chupaizu;

import dml.majiang.core.entity.fenzu.Shunzi;

public class ChichuPaiZu {
    private int chijinpaiId;
    private Shunzi shunzi;
    private String dachuPlayerId;
    private String chiPlayerId;

    public ChichuPaiZu() {
    }

    public ChichuPaiZu(int chijinpaiId, Shunzi shunzi, String dachuPlayerId, String chiPlayerId) {
        this.chijinpaiId = chijinpaiId;
        this.shunzi = shunzi;
        this.dachuPlayerId = dachuPlayerId;
        this.chiPlayerId = chiPlayerId;
    }

    public int getChijinpaiId() {
        return chijinpaiId;
    }

    public void setChijinpaiId(int chijinpaiId) {
        this.chijinpaiId = chijinpaiId;
    }

    public Shunzi getShunzi() {
        return shunzi;
    }

    public void setShunzi(Shunzi shunzi) {
        this.shunzi = shunzi;
    }

    public String getDachuPlayerId() {
        return dachuPlayerId;
    }

    public void setDachuPlayerId(String dachuPlayerId) {
        this.dachuPlayerId = dachuPlayerId;
    }

    public String getChiPlayerId() {
        return chiPlayerId;
    }

    public void setChiPlayerId(String chiPlayerId) {
        this.chiPlayerId = chiPlayerId;
    }
}
