package dml.majiang.core.entity.chupaizu;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.fenzu.Shunzi;

public class ChichuPaiZu {
    private MajiangPai chijinpai;
    private Shunzi shunzi;
    private String dachuPlayerId;
    private String chiPlayerId;

    public ChichuPaiZu() {
    }

    public ChichuPaiZu(int chijinpaiId, Shunzi shunzi, String dachuPlayerId, String chiPlayerId) {
        this.chijinpai = chijinpai;
        this.shunzi = shunzi;
        this.dachuPlayerId = dachuPlayerId;
        this.chiPlayerId = chiPlayerId;
    }

    public MajiangPai getChijinpai() {
        return chijinpai;
    }

    public void setChijinpai(MajiangPai chijinpai) {
        this.chijinpai = chijinpai;
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
