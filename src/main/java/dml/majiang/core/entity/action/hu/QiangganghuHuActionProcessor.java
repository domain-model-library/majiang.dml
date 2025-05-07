package dml.majiang.core.entity.action.hu;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.fenzu.GangType;

/**
 * 支持抢杠胡
 */
public class QiangganghuHuActionProcessor implements HuActionProcessor {

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
    public void process(HuAction huAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        Hu hu = huAction.getHu();
        pan.setPlayerHu(huAction.getActionPlayerId(), hu);

        // 如果是抢杠胡，要拆解杠
        PanFrame lastPanFrame = panFrames.getLastFrame();
        PanPlayerAction lastAction = lastPanFrame.getAction();
        if (lastAction instanceof GangAction) {
            GangAction gangAction = (GangAction) lastAction;
            GangType gangType = gangAction.getGangType();
            int gangPaiId = gangAction.getGangPaiId();
            if (gangType.equals(GangType.kezigangmo)) {
                PanFrame frameBeforeGang = panFrames.getFrame(lastPanFrame.getNumber() - 1);
                Pan panBeforeGang = frameBeforeGang.getPanAfterAction();
                pan.override(panBeforeGang);
                PanPlayer gangPlayer = pan.findPlayerById(gangAction.getActionPlayerId());
                gangPlayer.removeGangmoShoupai();
            } else if (gangType.equals(GangType.kezigangshoupai)) {
                PanFrame frameBeforeGang = panFrames.getFrame(lastPanFrame.getNumber() - 1);
                Pan panBeforeGang = frameBeforeGang.getPanAfterAction();
                pan.override(panBeforeGang);
                PanPlayer gangPlayer = pan.findPlayerById(gangAction.getActionPlayerId());
                gangPlayer.removeFangruShoupai(gangPaiId);
            } else if (gangType.equals(GangType.shoupaigangmo)) {
                PanFrame frameBeforeGang = panFrames.getFrame(lastPanFrame.getNumber() - 1);
                Pan panBeforeGang = frameBeforeGang.getPanAfterAction();
                pan.override(panBeforeGang);
                PanPlayer gangPlayer = pan.findPlayerById(gangAction.getActionPlayerId());
                gangPlayer.removeGangmoShoupai();
            }
        }
    }

}
