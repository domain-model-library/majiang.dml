package dml.majiang.specialrules.xuezhandaodi.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.guo.GuoAction;
import dml.majiang.core.entity.action.guo.GuoActionUpdater;

import java.util.List;

public class XuezhandaodiGuoActionUpdater implements GuoActionUpdater {
    private long panId;

    private GuoActionUpdater guoActionUpdater;

    public XuezhandaodiGuoActionUpdater() {
    }

    public XuezhandaodiGuoActionUpdater(GuoActionUpdater guoActionUpdater) {
        this.panId = guoActionUpdater.getPanId();
        this.guoActionUpdater = guoActionUpdater;
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
    public void updateActions(GuoAction guoAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        List<PanPlayer> huPlayers = pan.getHuPlayers();
        //暂时删除已经胡了的玩家
        for (PanPlayer huPlayer : huPlayers) {
            pan.removePlayer(huPlayer);
        }
        guoActionUpdater.updateActions(guoAction, pan, panFrames, panSpecialRulesState);
        //加回已经胡了的玩家
        for (PanPlayer huPlayer : huPlayers) {
            pan.putPlayer(huPlayer);
        }
    }

    public GuoActionUpdater getGuoActionUpdater() {
        return guoActionUpdater;
    }

    public void setGuoActionUpdater(GuoActionUpdater guoActionUpdater) {
        this.guoActionUpdater = guoActionUpdater;
    }
}
