package dml.majiang.specialrules.genfeng.entity;

import dml.majiang.core.entity.*;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.action.mo.MoActionUpdater;

import java.util.*;

/**
 * 跟风，出自温州麻将
 * <br/>
 * 风牌（不成对和不成刻）必须先出，（风牌必须跟着已经出掉的风牌出牌，即东风家出了西风牌以后，南家如有西风牌必须先出，没有西风牌可以随意打其他风牌，如没有风牌方可以出其他牌，另如东风家出了西风牌以后，南家没有西风牌出了东风牌，西家可以在西风牌和东风牌之间随意选一张出牌）。（这里的风牌包含“中发白”）。
 */
public class GenfengMoActionUpdater implements MoActionUpdater {

    private long panId;

    private MoActionUpdater moActionUpdater;

    public GenfengMoActionUpdater() {
    }

    public GenfengMoActionUpdater(MoActionUpdater moActionUpdater) {
        this.panId = moActionUpdater.getPanId();
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
    public void updateActions(MoAction moAction, Pan pan, PanFrames panFrames, PanSpecialRulesState panSpecialRulesState) {
        moActionUpdater.updateActions(moAction, pan, panFrames, panSpecialRulesState);
        PanPlayer moPlayer = pan.findPlayerById(moAction.getActionPlayerId());
        //限制玩家只能打的牌
        Set<MajiangPai> daPaiCandidates = new HashSet<>();
        //是否有正在被跟的牌
        GenfengState genfengState = panSpecialRulesState.findSpecialRuleState(GenfengState.class);
        List<Pai> shoupaiList = moPlayer.getShoupaiList();
        for (Pai pai : shoupaiList) {
            if (genfengState.zhengzaiBeiGen(pai.getPaiType())) {
                daPaiCandidates.add(pai.getPaiType());
            }
        }
        if (daPaiCandidates.isEmpty()) {
            //是否有风牌要先打出
            Map<MajiangPai, Integer> genfengPaiCountMap = new HashMap<>();
            for (Pai pai : shoupaiList) {
                if (genfengState.isGenfengPai(pai.getPaiType())) {
                    genfengPaiCountMap.put(pai.getPaiType(), genfengPaiCountMap.getOrDefault(pai.getPaiType(), 0) + 1);
                }
            }
            for (Map.Entry<MajiangPai, Integer> entry : genfengPaiCountMap.entrySet()) {
                MajiangPai paiType = entry.getKey();
                int count = entry.getValue();
                if (count == 1) {
                    //如果只有一张风牌，必须打出
                    daPaiCandidates.add(paiType);
                }
            }
        }
        //只有允许打的牌才可以打
        if (!daPaiCandidates.isEmpty()) {
            moPlayer.removeAllDaActions();
            for (Pai pai : shoupaiList) {
                if (daPaiCandidates.contains(pai.getPaiType())) {
                    moPlayer.addActionCandidate(new DaAction(moPlayer.getId(), pai.getId()));
                }
            }
        }
    }

    public MoActionUpdater getMoActionUpdater() {
        return moActionUpdater;
    }

    public void setMoActionUpdater(MoActionUpdater moActionUpdater) {
        this.moActionUpdater = moActionUpdater;
    }
}
