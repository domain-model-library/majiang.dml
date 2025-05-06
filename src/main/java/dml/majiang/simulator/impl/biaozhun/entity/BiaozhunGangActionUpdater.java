package dml.majiang.simulator.impl.biaozhun.entity;

import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.action.gang.QiangganghuGangActionUpdater;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

import java.util.List;

public class BiaozhunGangActionUpdater extends QiangganghuGangActionUpdater {

    private long panId;

    @Override
    protected Hu makeHu(GangAction gangAction, Pai gangpai, Pan pan, PanFrames panFrames,
                        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState) {
        if (huPaiShoupaiPaiXingList != null && !huPaiShoupaiPaiXingList.isEmpty()) {
            ShoupaiPaiXing shoupaiPaiXing = huPaiShoupaiPaiXingList.get(0);
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
