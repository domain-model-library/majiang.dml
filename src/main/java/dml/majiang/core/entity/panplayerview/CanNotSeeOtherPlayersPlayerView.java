package dml.majiang.core.entity.panplayerview;

import dml.majiang.core.entity.MenFeng;
import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.action.hu.Hu;
import dml.majiang.core.entity.chupaizu.ChichuPaiZu;
import dml.majiang.core.entity.chupaizu.GangchuPaiZu;
import dml.majiang.core.entity.chupaizu.PengchuPaiZu;
import dml.majiang.core.entity.fenzu.GangType;

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
    private Pai gangmoShoupai;

    /**
     * 已放入的手牌列表
     */
    private List<Pai> fangruShoupaiList = new ArrayList<>();

    private Map<Integer, PanPlayerAction> actionCandidates = new HashMap<>();

    private int fangruShoupaiCount;

    private List<Pai> dachupaiList;

    private List<ChichuPaiZu> chichupaiZuList;
    private List<PengchuPaiZu> pengchupaiZuList;

    /**
     * 如果不是自己，那只有明杠可见，暗杠只记录数量
     */
    private List<GangchuPaiZu> gangchupaiZuList = new ArrayList<>();
    private int angangCount;
    private Hu hu;


    public CanNotSeeOtherPlayersPlayerView() {
    }

    public CanNotSeeOtherPlayersPlayerView(PanPlayer player, String toWiewPlayerId) {
        this.id = player.getId();
        this.menFeng = player.getMenFeng();
        this.dachupaiList = player.getDachupaiList();
        this.chichupaiZuList = player.getChichupaiZuList();
        this.pengchupaiZuList = player.getPengchupaiZuList();
        if (player.getId().equals(toWiewPlayerId)) {
            fangruShoupaiList.addAll(player.getFangruShoupai().values());
            gangmoShoupai = player.getGangmoShoupai();
            actionCandidates.putAll(player.getActionCandidates());
            gangchupaiZuList = player.getGangchupaiZuList();
        } else {
            fangruShoupaiCount = player.getFangruShoupai().size();
            //只有明杠可见
            for (GangchuPaiZu gangchuPaiZu : player.getGangchupaiZuList()) {
                if (gangchuPaiZu.getGangType().equals(GangType.shoupaigangmo)
                        || gangchuPaiZu.getGangType().equals(GangType.gangsigeshoupai)) {
                    angangCount++;
                } else {
                    gangchupaiZuList.add(gangchuPaiZu);
                }
            }
        }
        this.hu = player.getHu();
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

    public List<Pai> getFangruShoupaiList() {
        return fangruShoupaiList;
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

    public Pai getGangmoShoupai() {
        return gangmoShoupai;
    }

    public List<Pai> getDachupaiList() {
        return dachupaiList;
    }

    public List<ChichuPaiZu> getChichupaiZuList() {
        return chichupaiZuList;
    }

    public List<PengchuPaiZu> getPengchupaiZuList() {
        return pengchupaiZuList;
    }

    public List<GangchuPaiZu> getGangchupaiZuList() {
        return gangchupaiZuList;
    }

    public int getAngangCount() {
        return angangCount;
    }

    public Hu getHu() {
        return hu;
    }
}
