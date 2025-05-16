package dml.majiang.specialrules.youpengbupeng.entity;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.action.peng.PengAction;

public class YoupengbupengDaActionUpdater implements DaActionUpdater {
    private long panId;
    private DaActionUpdater daActionUpdater;

    public YoupengbupengDaActionUpdater() {
    }

    public YoupengbupengDaActionUpdater(DaActionUpdater daActionUpdater) {
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
        daActionUpdater.updateActions(daAction, pan, panFrames, panSpecialRulesState);
        YoupengbupengState youpengbupengState = panSpecialRulesState.findSpecialRuleState(YoupengbupengState.class);
        PanPlayer daPlayer = pan.findPlayerById(daAction.getActionPlayerId());
        PanPlayer xiajiaPlayer = pan.findNextMenFengPlayer(daPlayer);
        while (xiajiaPlayer != null && !xiajiaPlayer.getId().equals(daPlayer.getId())) {
            PengAction pengAction = xiajiaPlayer.getPengCandidateAction();
            if (pengAction != null) {
                PanPlayer dachuPlayer = pan.findPlayerById(pengAction.getDachupaiPlayerId());
                Pai dachuPai = dachuPlayer.findDachupai(pengAction.getPengPaiId());
                if (youpengbupengState.isYoupengbupeng(xiajiaPlayer.getId(), dachuPai.getPaiType())) {
                    xiajiaPlayer.removeActionCandidate(pengAction.getId());
                    //如果也没有吃杠胡，那么也不需要过，如果又正好是下家那么还要让他摸
                    if (!(xiajiaPlayer.hasHuActionCandidate() || xiajiaPlayer.hasChiActionCandidate() || xiajiaPlayer.hasGangActionCandidate())) {
                        xiajiaPlayer.removeGuoActionCandidate();
                        PanPlayer xiaJiaOfDaPlayer = pan.findNextMenFengPlayer(daPlayer);
                        if (xiajiaPlayer.getId().equals(xiaJiaOfDaPlayer.getId())) {
                            xiajiaPlayer.addActionCandidate(new MoAction(xiajiaPlayer.getId(), new LundaoMopai()));
                        }
                    }
                }
            }
            xiajiaPlayer = pan.findNextMenFengPlayer(xiajiaPlayer);
        }
    }

    public DaActionUpdater getDaActionUpdater() {
        return daActionUpdater;
    }

    public void setDaActionUpdater(DaActionUpdater daActionUpdater) {
        this.daActionUpdater = daActionUpdater;
    }
}
