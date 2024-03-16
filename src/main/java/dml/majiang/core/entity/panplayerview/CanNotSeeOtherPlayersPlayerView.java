package dml.majiang.core.entity.panplayerview;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.MenFeng;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.action.PanPlayerAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanNotSeeOtherPlayersPlayerView {

    private String id;
    private MenFeng menFeng;

    /**
     * 刚摸进待处理的手牌（未放入）
     */
    private MajiangPai gangmoShoupai;

    /**
     * 已放入的手牌列表
     */
    private List<MajiangPai> fangruShoupaiList = new ArrayList<>();

    private Map<Integer, PanPlayerAction> actionCandidates = new HashMap<>();

    private int fangruShoupaiCount;


    public CanNotSeeOtherPlayersPlayerView() {
    }

    public CanNotSeeOtherPlayersPlayerView(PanPlayer player, String toWiewPlayerId) {
        this.id = player.getId();
        this.menFeng = player.getMenFeng();
        if (player.getId().equals(toWiewPlayerId)) {
            fangruShoupaiList.addAll(player.getFangruShoupaiList());
            gangmoShoupai = player.getGangmoShoupai();
            actionCandidates.putAll(player.getActionCandidates());
        } else {
            fangruShoupaiCount = player.getFangruShoupaiList().size();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MenFeng getMenFeng() {
        return menFeng;
    }

    public void setMenFeng(MenFeng menFeng) {
        this.menFeng = menFeng;
    }

    public List<MajiangPai> getFangruShoupaiList() {
        return fangruShoupaiList;
    }

    public void setFangruShoupaiList(List<MajiangPai> fangruShoupaiList) {
        this.fangruShoupaiList = fangruShoupaiList;
    }

    public Map<Integer, PanPlayerAction> getActionCandidates() {
        return actionCandidates;
    }

    public void setActionCandidates(Map<Integer, PanPlayerAction> actionCandidates) {
        this.actionCandidates = actionCandidates;
    }

    public int getFangruShoupaiCount() {
        return fangruShoupaiCount;
    }

}
