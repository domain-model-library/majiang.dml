package dml.majiang.simulator.base.entity;

import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.PanPlayer;

public class PlayerFangrushoupaiExchanger extends PaiExchanger {
    private PanPlayer panPlayer;
    private int paiId;

    public PlayerFangrushoupaiExchanger(PanPlayer panPlayer, int paiId) {
        this.panPlayer = panPlayer;
        this.paiId = paiId;
    }

    @Override
    public void exchange(PaiExchanger other) {
        if (other instanceof PlayerFangrushoupaiExchanger) {
            if (((PlayerFangrushoupaiExchanger) other).panPlayer.getId().equals(panPlayer.getId())) {
                return;
            }
        }
        super.exchange(other);
    }

    @Override
    protected void putPai(Pai otherPai) {
        panPlayer.addShoupai(otherPai);
    }

    @Override
    protected Pai exchangePai(Pai otherPai) {
        panPlayer.addShoupai(otherPai);
        return panPlayer.removeFangruShoupai(paiId);
    }

    @Override
    protected Pai takePai() {
        return panPlayer.removeFangruShoupai(paiId);
    }
}
