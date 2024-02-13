package dml.majiang.core.entity.fenzu;


import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.shoupai.ShoupaiDuiziZu;

public class Duizi implements MajiangPaiFenZu {

    private MajiangPai paiType;

    public Duizi() {
    }

    public Duizi(MajiangPai paiType) {
        this.paiType = paiType;
    }

    @Override
    public ShoupaiDuiziZu generateShoupaiMajiangPaiFenZuSkeleton() {
        ShoupaiDuiziZu shoupaiDuiziZu = new ShoupaiDuiziZu();
        shoupaiDuiziZu.setDuiziType(paiType);
        return shoupaiDuiziZu;
    }

    @Override
    public int countPai(MajiangPai paiType) {
        if (paiType.equals(this.paiType)) {
            return 2;
        } else {
            return 0;
        }
    }

    @Override
    public MajiangPai[] toPaiArray() {
        return new MajiangPai[]{paiType, paiType};
    }

    public MajiangPai getPaiType() {
        return paiType;
    }

    public void setPaiType(MajiangPai paiType) {
        this.paiType = paiType;
    }


}
