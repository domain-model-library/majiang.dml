package dml.majiang.specialrules.xuezhandaodi.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.action.gang.GangActionUpdater;

import java.util.List;

public class XuezhandaodiGangActionUpdater implements GangActionUpdater {
    private long panId;

    private GangActionUpdater gangActionUpdater;

    public XuezhandaodiGangActionUpdater(GangActionUpdater gangActionUpdater) {
        this.panId = gangActionUpdater.getPanId();
        this.gangActionUpdater = gangActionUpdater;
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
    public void updateActions(GangAction gangAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        List<PanPlayer> huPlayers = pan.getHuPlayers();
        //暂时删除已经胡了的玩家
        for (PanPlayer huPlayer : huPlayers) {
            pan.removePlayer(huPlayer);
        }
        gangActionUpdater.updateActions(gangAction, pan, panFrames, panSpecialRulesState);
        //加回已经胡了的玩家
        for (PanPlayer huPlayer : huPlayers) {
            pan.putPlayer(huPlayer);
        }
    }
}
