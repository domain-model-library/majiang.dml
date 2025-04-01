package dml.majiang.core.entity.action.mo;


/**
 * 杠后补牌
 *
 * @author Neo
 */
public class GanghouBupai implements MopaiReason {

    private int gangActionId;


    public GanghouBupai() {
    }

    public GanghouBupai(int gangActionId) {
        this.gangActionId = gangActionId;
    }

    public int getGangActionId() {
        return gangActionId;
    }

    public void setGangActionId(int gangActionId) {
        this.gangActionId = gangActionId;
    }
}
