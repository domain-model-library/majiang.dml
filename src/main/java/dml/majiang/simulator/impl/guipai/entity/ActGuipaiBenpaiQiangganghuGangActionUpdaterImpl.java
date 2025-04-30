package dml.majiang.simulator.impl.guipai.entity;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;
import dml.majiang.specialrules.guipai.entity.ActGuipaiBenpaiQiangganghuGangActionUpdater;
import dml.majiang.specialrules.guipai.entity.HupaiShoupaiPaiXingListWithGuipaiAct;

import java.util.List;

public class ActGuipaiBenpaiQiangganghuGangActionUpdaterImpl extends ActGuipaiBenpaiQiangganghuGangActionUpdater {
    private long panId;

    @Override
    protected Hu makeHuWithGuipai(GangAction gangAction, Pai gangpai, Pan pan, PanFrames panFrames, String huPlayerId, List<HupaiShoupaiPaiXingListWithGuipaiAct> hupaiShoupaiPaiXingListWithGuipaiActList, PanSpecialRulesState panSpecialRulesState, MajiangPai guipaiType, MajiangPai actGuipaiBenpaiPaiType) {
        if (hupaiShoupaiPaiXingListWithGuipaiActList == null || hupaiShoupaiPaiXingListWithGuipaiActList.isEmpty()) {
            return null;
        }
        GuipaiHu guipaiHu = new GuipaiHu();
        guipaiHu.setShoupaiPaiXing(hupaiShoupaiPaiXingListWithGuipaiActList.get(0).getHupaiShoupaiPaiXingList().get(0));
        return guipaiHu;
    }

    @Override
    protected Hu makeHuWithoutGuipai(GangAction gangAction, Pai gangpai, Pan pan, PanFrames panFrames, String huPlayerId, List<ShoupaiPaiXing> hupaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState, MajiangPai guipaiType, MajiangPai actGuipaiBenpaiPaiType) {
        if (hupaiShoupaiPaiXingList == null || hupaiShoupaiPaiXingList.isEmpty()) {
            return null;
        }
        GuipaiHu guipaiHu = new GuipaiHu();
        guipaiHu.setShoupaiPaiXing(hupaiShoupaiPaiXingList.get(0));
        return guipaiHu;
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
