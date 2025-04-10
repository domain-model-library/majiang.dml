package dml.majiang.core.entity.action.hu;


import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

public abstract class Hu {
    private ShoupaiPaiXing shoupaiPaiXing;

    public Hu() {
    }

    public Hu(ShoupaiPaiXing shoupaiPaiXing) {
        this.shoupaiPaiXing = shoupaiPaiXing;
    }


    public ShoupaiPaiXing getShoupaiPaiXing() {
        return shoupaiPaiXing;
    }

    public void setShoupaiPaiXing(ShoupaiPaiXing shoupaiPaiXing) {
        this.shoupaiPaiXing = shoupaiPaiXing;
    }

}
