import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;
import dml.majiang.core.entity.shoupai.gouxing.GouXingCalculator;
import dml.majiang.core.entity.shoupai.gouxing.GouXingCalculatorHelper;
import dml.majiang.core.entity.shoupai.gouxing.NoDanpaiOneDuiziGouXingPanHu;
import org.junit.Test;

import java.util.List;

import static dml.majiang.core.entity.MajiangPai.*;
import static org.junit.Assert.assertFalse;

public class HuTest {
    @Test
    public void test() {
        GouXingCalculatorHelper.gouXingCalculator = new GouXingCalculator(17, 3);

        //验证最普通的胡
        PanPlayer player = new PanPlayer();
        player.addShoupai(yiwan);
        player.addShoupai(yiwan);
        player.addShoupai(yiwan);

        player.addShoupai(erwan);
        player.addShoupai(erwan);
        player.addShoupai(erwan);

        player.addShoupai(sanwan);
        player.addShoupai(sanwan);
        player.addShoupai(sanwan);

        player.addShoupai(siwan);
        player.addShoupai(siwan);
        player.addShoupai(siwan);

        player.addShoupai(wuwan);
        player.addShoupai(wuwan);
        player.addShoupai(wuwan);

        player.addShoupai(liuwan);
        player.setGangmoShoupai(liuwan);

        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList =
                player.getShoupaiCalculator().calculateAllHuPaiShoupaiPaiXingForZimoHu(player,
                        new NoDanpaiOneDuiziGouXingPanHu());
        assertFalse(huPaiShoupaiPaiXingList.isEmpty());

    }

}
