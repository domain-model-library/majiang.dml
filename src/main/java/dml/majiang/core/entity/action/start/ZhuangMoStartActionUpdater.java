package dml.majiang.core.entity.action.start;

import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.action.mo.LundaoMopai;
import dml.majiang.core.entity.action.mo.MoAction;

/**
 * 最常见的一盘开始后的动作更新器,就是庄家摸牌
 */
public class ZhuangMoStartActionUpdater implements PanStartActionUpdater {

    @Override
    public void process(Pan pan) {
        pan.addPlayerActionCandidate(new MoAction(pan.getZhuangPlayerId(), new LundaoMopai()));
    }
}
