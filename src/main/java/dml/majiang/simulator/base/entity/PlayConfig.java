package dml.majiang.simulator.base.entity;

public class PlayConfig {
    /**
     * 可以修改手牌
     */
    private boolean canEditShoupai;

    /**
     * 可以指定摸牌
     */
    private boolean canSpecifyMoPai;

    public boolean isCanEditShoupai() {
        return canEditShoupai;
    }

    public void setCanEditShoupai(boolean canEditShoupai) {
        this.canEditShoupai = canEditShoupai;
    }

    public boolean isCanSpecifyMoPai() {
        return canSpecifyMoPai;
    }

    public void setCanSpecifyMoPai(boolean canSpecifyMoPai) {
        this.canSpecifyMoPai = canSpecifyMoPai;
    }
}
