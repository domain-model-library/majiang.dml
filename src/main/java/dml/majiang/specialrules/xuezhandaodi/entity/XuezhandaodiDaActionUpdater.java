package dml.majiang.specialrules.xuezhandaodi.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.da.DaActionUpdater;

import java.util.List;

public class XuezhandaodiDaActionUpdater implements DaActionUpdater {
    private long panId;

    private DaActionUpdater daActionUpdater;

    public XuezhandaodiDaActionUpdater(DaActionUpdater daActionUpdater) {
        this.panId = daActionUpdater.getPanId();
        this.daActionUpdater = daActionUpdater;
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
    public void updateActions(DaAction daAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        List<PanPlayer> huPlayers = pan.getHuPlayers();
        //暂时删除已经胡了的玩家
        for (PanPlayer huPlayer : huPlayers) {
            pan.removePlayer(huPlayer);
        }
        daActionUpdater.updateActions(daAction, pan, panFrames, panSpecialRulesState);
        //加回已经胡了的玩家
        for (PanPlayer huPlayer : huPlayers) {
            pan.putPlayer(huPlayer);
        }
    }
}
