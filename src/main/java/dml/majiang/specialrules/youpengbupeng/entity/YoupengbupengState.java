package dml.majiang.specialrules.youpengbupeng.entity;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.SpecialRuleState;

import java.util.ArrayList;
import java.util.List;

public class YoupengbupengState implements SpecialRuleState {
    private List<PlayerYoupengbupeng> playerYoupengbupengList = new ArrayList<>();


    public void recordPlayerYoupengbupeng(String playerId, MajiangPai paiType) {
        PlayerYoupengbupeng playerYoupengbupeng = new PlayerYoupengbupeng();
        playerYoupengbupeng.setPlayerId(playerId);
        playerYoupengbupeng.setPengpaiType(paiType);
        playerYoupengbupengList.add(playerYoupengbupeng);
    }

    public List<PlayerYoupengbupeng> getPlayerYoupengbupengList() {
        return playerYoupengbupengList;
    }

    public void setPlayerYoupengbupengList(List<PlayerYoupengbupeng> playerYoupengbupengList) {
        this.playerYoupengbupengList = playerYoupengbupengList;
    }

    public boolean isYoupengbupeng(String playerId, MajiangPai paiType) {
        for (PlayerYoupengbupeng playerYoupengbupeng : playerYoupengbupengList) {
            if (playerYoupengbupeng.getPlayerId().equals(playerId) && playerYoupengbupeng.getPengpaiType().equals(paiType)) {
                return true;
            }
        }
        return false;
    }

    public void clearYoupengbupengState(String playerId) {
        List<PlayerYoupengbupeng> toRemove = new ArrayList<>();
        for (PlayerYoupengbupeng playerYoupengbupeng : playerYoupengbupengList) {
            if (playerYoupengbupeng.getPlayerId().equals(playerId)) {
                toRemove.add(playerYoupengbupeng);
            }
        }
        playerYoupengbupengList.removeAll(toRemove);
    }

}
