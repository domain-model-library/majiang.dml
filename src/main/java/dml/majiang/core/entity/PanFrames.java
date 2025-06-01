package dml.majiang.core.entity;

import dml.majiang.core.entity.action.PanPlayerAction;

import java.util.ArrayList;
import java.util.List;

public class PanFrames {
    private long panId;
    private Pan initialPan;
    private List<PanFrame> panFrameList = new ArrayList<>();

    public void addFrame(PanPlayerAction action, Pan panAfterAction) {
        PanFrame panFrame = new PanFrame();
        panFrame.setNumber(panFrameList.size());
        panFrame.setAction(action);
        panFrame.setPanAfterAction(panAfterAction);
        panFrameList.add(panFrame);
    }

    public PanFrame getLastFrame() {
        if (panFrameList.isEmpty()) {
            return null;
        }
        return panFrameList.get(panFrameList.size() - 1);
    }

    public PanFrame getFrame(int number) {
        if (number < 0 || number >= panFrameList.size()) {
            return null;
        }
        return panFrameList.get(number);
    }

    public long getPanId() {
        return panId;
    }

    public void setPanId(long panId) {
        this.panId = panId;
    }

    public Pan getInitialPan() {
        return initialPan;
    }

    public void setInitialPan(Pan initialPan) {
        this.initialPan = initialPan;
    }

    public List<PanFrame> getPanFrameList() {
        return panFrameList;
    }

    public void setPanFrameList(List<PanFrame> panFrameList) {
        this.panFrameList = panFrameList;
    }

    public boolean isEmpty() {
        return panFrameList.isEmpty();
    }

    public boolean hasFrameForPlayerAction(String playerId) {
        for (PanFrame panFrame : panFrameList) {
            if (panFrame.getAction().getActionPlayerId().equals(playerId)) {
                return true;
            }
        }
        return false;
    }
}
