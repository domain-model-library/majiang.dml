package test.dml.majiang.simulator.base.entity;

public class PlayStateContainer {
    private PlayStateEnum playState = PlayStateEnum.initial;

    public PlayStateEnum getPlayState() {
        return playState;
    }

    public void setPlayState(PlayStateEnum playState) {
        this.playState = playState;
    }
}
