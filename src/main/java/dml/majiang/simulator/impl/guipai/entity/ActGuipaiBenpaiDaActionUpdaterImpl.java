package dml.majiang.simulator.impl.guipai.entity;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;
import dml.majiang.specialrules.guipai.entity.ActGuipaiBenpaiDaActionUpdater;
import dml.majiang.specialrules.guipai.entity.HupaiShoupaiPaiXingListWithGuipaiAct;

import java.util.List;

public class ActGuipaiBenpaiDaActionUpdaterImpl extends ActGuipaiBenpaiDaActionUpdater {
    private long panId;

    @Override
    protected Hu makeHuWithGuipai(DaAction daAction, Pai daPai, Pan pan, PanFrames panFrames, String huPlayerId,
                                  List<HupaiShoupaiPaiXingListWithGuipaiAct> hupaiShoupaiPaiXingListWithGuipaiActList,
                                  PanSpecialRulesState panSpecialRulesState, MajiangPai guipaiType, MajiangPai actGuipaiBenpaiPaiType,
                                  List<MajiangPai> guipaiActPaiTypeList) {
        if (hupaiShoupaiPaiXingListWithGuipaiActList == null || hupaiShoupaiPaiXingListWithGuipaiActList.isEmpty()) {
            return null;
        }
        GuipaiHu guipaiHu = new GuipaiHu();
        guipaiHu.setShoupaiPaiXing(hupaiShoupaiPaiXingListWithGuipaiActList.get(0).getHupaiShoupaiPaiXingList().get(0));
        return guipaiHu;
    }

    @Override
    protected Hu makeHuWithoutGuipai(DaAction daAction, Pai daPai, Pan pan, PanFrames panFrames, String huPlayerId, List<ShoupaiPaiXing> hupaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState, MajiangPai guipaiType, MajiangPai actGuipaiBenpaiPaiType) {
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
