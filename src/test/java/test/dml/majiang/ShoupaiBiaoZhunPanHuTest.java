package test.dml.majiang;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.shoupai.ShoupaiBiaoZhunPanHu;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;
import org.junit.Test;

import java.util.List;

public class ShoupaiBiaoZhunPanHuTest {
    @Test
    public void test() {
        List<Pai> shoupaiList = List.of(
                new Pai(1, MajiangPai.yiwan), new Pai(2, MajiangPai.yiwan), new Pai(3, MajiangPai.yiwan),
                new Pai(4, MajiangPai.sanwan), new Pai(5, MajiangPai.sanwan), new Pai(6, MajiangPai.sanwan),
                new Pai(7, MajiangPai.wuwan), new Pai(8, MajiangPai.wuwan), new Pai(9, MajiangPai.wuwan),
                new Pai(10, MajiangPai.qiwan), new Pai(11, MajiangPai.qiwan), new Pai(12, MajiangPai.qiwan),
                new Pai(13, MajiangPai.jiuwan), new Pai(14, MajiangPai.jiuwan), new Pai(15, MajiangPai.jiuwan),
                new Pai(16, MajiangPai.dongfeng), new Pai(17, MajiangPai.dongfeng)
        );
        List<ShoupaiPaiXing> hupaiShoupaiXingList = ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
        assert hupaiShoupaiXingList.size() == 1;

        shoupaiList = List.of(
                new Pai(1, MajiangPai.yiwan), new Pai(2, MajiangPai.yiwan), new Pai(3, MajiangPai.yiwan),
                new Pai(4, MajiangPai.erwan), new Pai(5, MajiangPai.erwan), new Pai(6, MajiangPai.erwan),
                new Pai(7, MajiangPai.sanwan), new Pai(8, MajiangPai.sanwan), new Pai(9, MajiangPai.sanwan),
                new Pai(10, MajiangPai.siwan), new Pai(11, MajiangPai.siwan), new Pai(12, MajiangPai.siwan),
                new Pai(13, MajiangPai.wuwan), new Pai(14, MajiangPai.wuwan), new Pai(15, MajiangPai.wuwan),
                new Pai(16, MajiangPai.liuwan), new Pai(17, MajiangPai.liuwan)
        );
        hupaiShoupaiXingList = ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
        assert hupaiShoupaiXingList.size() == 5;

        shoupaiList = List.of(
                new Pai(1, MajiangPai.yiwan), new Pai(2, MajiangPai.yiwan), new Pai(3, MajiangPai.yiwan),
                new Pai(4, MajiangPai.erwan),
                new Pai(5, MajiangPai.sanwan),
                new Pai(6, MajiangPai.siwan), new Pai(7, MajiangPai.siwan), new Pai(8, MajiangPai.siwan),
                new Pai(9, MajiangPai.liuwan), new Pai(10, MajiangPai.liuwan),
                new Pai(11, MajiangPai.qiwan), new Pai(12, MajiangPai.qiwan), new Pai(13, MajiangPai.qiwan),
                new Pai(14, MajiangPai.qiwan), new Pai(15, MajiangPai.qiwan), new Pai(16, MajiangPai.qiwan),
                new Pai(17, MajiangPai.qiwan)
        );
        hupaiShoupaiXingList = ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
        assert hupaiShoupaiXingList == null;

        shoupaiList = List.of(
                new Pai(1, MajiangPai.erwan), new Pai(2, MajiangPai.erwan), new Pai(3, MajiangPai.erwan),
                new Pai(4, MajiangPai.sanwan),
                new Pai(5, MajiangPai.siwan),
                new Pai(6, MajiangPai.wuwan), new Pai(7, MajiangPai.wuwan), new Pai(8, MajiangPai.wuwan),
                new Pai(9, MajiangPai.qiwan), new Pai(10, MajiangPai.qiwan), new Pai(17, MajiangPai.qiwan),
                new Pai(11, MajiangPai.bawan), new Pai(12, MajiangPai.bawan), new Pai(13, MajiangPai.bawan),
                new Pai(14, MajiangPai.bawan), new Pai(15, MajiangPai.bawan), new Pai(16, MajiangPai.bawan)
        );
        hupaiShoupaiXingList = ShoupaiBiaoZhunPanHu.getAllHuPaiShoupaiPaiXing(shoupaiList);
        assert hupaiShoupaiXingList.size() == 2;
    }
}
