package dml.majiang.core.entity;

public class WaitingPlayerCursor {
    private long panId;
    private String waitingPlayerId;

    public WaitingPlayerCursor() {
    }

    public WaitingPlayerCursor(long panId) {
        this.panId = panId;
    }

    public long getPanId() {
        return panId;
    }

    public void setPanId(long panId) {
        this.panId = panId;
    }

    public String getWaitingPlayerId() {
        return waitingPlayerId;
    }

    public void setWaitingPlayerId(String waitingPlayerId) {
        this.waitingPlayerId = waitingPlayerId;
    }
}
