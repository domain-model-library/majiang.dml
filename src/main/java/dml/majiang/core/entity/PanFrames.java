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
        panFrame.setAction(action);
        panFrame.setPanAfterAction(panAfterAction);
        panFrameList.add(panFrame);
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


}
