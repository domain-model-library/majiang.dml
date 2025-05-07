package dml.majiang.specialrules.xuezhandaodi.entity;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.hu.HuActionUpdater;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;

public class XuezhandaodiHuActionUpdater implements HuActionUpdater {

    private long panId;

    @Override
    public void setPanId(long panId) {
        this.panId = panId;
    }

    @Override
    public long getPanId() {
        return panId;
    }

    @Override
    public void updateActions(HuAction huAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        pan.clearAllPlayersActionCandidates();
        //还剩1个玩家没胡那就结束了
        if (pan.countPlayersNotHu() == 1) {
            return;
        }

        //找出没有胡的下一家
        PanPlayer huPlayer = pan.findPlayerById(huAction.getActionPlayerId());
        PanPlayer xiajiaPlayer = pan.findNextMenFengPlayer(huPlayer);
        while (xiajiaPlayer.getHu() != null) {
            xiajiaPlayer = pan.findNextMenFengPlayer(xiajiaPlayer);
        }
        //下家摸牌
        xiajiaPlayer.addActionCandidate(new MoAction(xiajiaPlayer.getId(), new LundaoMopai()));
    }
}
