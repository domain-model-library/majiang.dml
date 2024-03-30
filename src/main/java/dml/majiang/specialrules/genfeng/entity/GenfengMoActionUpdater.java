package dml.majiang.specialrules.genfeng.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.action.mo.MoActionUpdater;

/**
 * 跟风，出自温州麻将
 * <br/>
 * 风牌（不成对和不成刻）必须先出，（风牌必须跟着已经出掉的风牌出牌，即东风家出了西风牌以后，南家如有西风牌必须先出，没有西风牌可以随意打其他风牌，如没有风牌方可以出其他牌，另如东风家出了西风牌以后，南家没有西风牌出了东风牌，西家可以在西风牌和东风牌之间随意选一张出牌）。（这里的风牌包含“中发白”）。
 */
public class GenfengMoActionUpdater implements MoActionUpdater {

    private long panId;

    private MoActionUpdater moActionUpdater;

    public GenfengMoActionUpdater(MoActionUpdater moActionUpdater) {
        this.moActionUpdater = moActionUpdater;
    }

    @Override
    public void setPanId(long panId) {
        this.panId = panId;
    }

    @Override
    public long getPanId() {
        return panId;
    }

    @Override
    public void updateActions(MoAction moAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
        moActionUpdater.updateActions(moAction, pan, panSpecialRulesState);
        //TODO 实现跟风规则
    }
}
