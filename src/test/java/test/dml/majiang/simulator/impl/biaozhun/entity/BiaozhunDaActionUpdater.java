package test.dml.majiang.simulator.impl.biaozhun.entity;

import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.da.ChiPengGangHuDaActionUpdater;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

import java.util.List;

public class BiaozhunDaActionUpdater extends ChiPengGangHuDaActionUpdater {

    private long panId;

    @Override
    protected Hu makeHu(DaAction daAction, Pai daPai, Pan pan, PanFrames panFrames, String huPlayerId, List<ShoupaiPaiXing> hupaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState) {
        if (hupaiShoupaiPaiXingList != null && !hupaiShoupaiPaiXingList.isEmpty()) {
            ShoupaiPaiXing shoupaiPaiXing = hupaiShoupaiPaiXingList.get(0);
            Hu hu = new BiaozhunHu();
            hu.setShoupaiPaiXing(shoupaiPaiXing);
            return hu;
        }
        return null;
    }

    @Override
    public void setPanId(long panId) {
        this.panId = panId;
    }

    @Override
    public long getPanId() {
        return panId;
    }
}
