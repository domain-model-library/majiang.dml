import dml.majiang.core.entity.MajiangPai;
import org.junit.Test;

public class HuTest {
    @Test
    public void test() {
//        GouXingCalculatorHelper.gouXingCalculator = new GouXingCalculator(17, 3);
//
//        //验证最普通的胡
//        PanPlayer player = new PanPlayer();
//        player.addShoupai(yiwan);
//        player.addShoupai(yiwan);
//        player.addShoupai(yiwan);
//
//        player.addShoupai(erwan);
//        player.addShoupai(erwan);
//        player.addShoupai(erwan);
//
//        player.addShoupai(sanwan);
//        player.addShoupai(sanwan);
//        player.addShoupai(sanwan);
//
//        player.addShoupai(siwan);
//        player.addShoupai(siwan);
//        player.addShoupai(siwan);
//
//        player.addShoupai(wuwan);
//        player.addShoupai(wuwan);
//        player.addShoupai(wuwan);
//
//        player.addShoupai(liuwan);
//        player.setGangmoShoupai(liuwan);
//
//        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList1 =
//                player.getShoupaiCalculator().calculateAllHuPaiShoupaiPaiXingForZimoHu(player,
//                        new NoDanpaiOneDuiziGouXingPanHu());
//        assertFalse(huPaiShoupaiPaiXingList1.isEmpty());
//
//        //验证有三个财神，白板当财神本牌的胡
//        //财神是西风
//        Set<MajiangPai> guipaiTypes = Set.of(xifeng);
//        player = new PanPlayer();
//        player.addShoupai(yiwan);
//        player.addShoupai(yiwan);
//        player.addShoupai(yiwan);
//
//        player.addShoupai(erwan);
//        player.addShoupai(erwan);
//        player.addShoupai(erwan);
//
//        player.addShoupai(xifeng);
//        player.addShoupai(sanwan);
//        player.addShoupai(sanwan);
//
//        player.addShoupai(xifeng);
//        player.addShoupai(siwan);
//        player.addShoupai(siwan);
//
//        player.addShoupai(xifeng);
//        player.addShoupai(wuwan);
//        player.addShoupai(wuwan);
//
//        player.addShoupai(baiban);
//        player.setGangmoShoupai(baiban);
//
//        MajiangPai[] paiTypesForGuipaiAct = calculatePaiTypesForGuipaiAct();
//        List<MajiangPai> guipaiList = List.of(xifeng, xifeng, xifeng);
//        MajiangPai actGuipaiBenpaiPai = baiban;
//        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList2 =
//                player.getShoupaiCalculator().calculateAllHuPaiShoupaiPaiXingForZimoHu(guipaiTypes, paiTypesForGuipaiAct,
//                        guipaiList, actGuipaiBenpaiPai, player, new NoDanpaiOneDuiziGouXingPanHu());
//        assertFalse(huPaiShoupaiPaiXingList2.isEmpty());
//
//        //验证有财神，白板当财神本牌的胡，而财神又正好是白板
//        guipaiTypes = Set.of(baiban);
//        player = new PanPlayer();
//        player.addShoupai(yiwan);
//        player.addShoupai(yiwan);
//        player.addShoupai(yiwan);
//
//        player.addShoupai(erwan);
//        player.addShoupai(erwan);
//        player.addShoupai(erwan);
//
//        player.addShoupai(baiban);
//        player.addShoupai(sanwan);
//        player.addShoupai(sanwan);
//
//        player.addShoupai(baiban);
//        player.addShoupai(siwan);
//        player.addShoupai(siwan);
//
//        player.addShoupai(wuwan);
//        player.addShoupai(wuwan);
//        player.addShoupai(wuwan);
//
//        player.addShoupai(xifeng);
//        player.setGangmoShoupai(xifeng);
//
//        guipaiList = List.of(baiban, baiban);
//        actGuipaiBenpaiPai = baiban;
//        List<ShoupaiPaiXing> huPaiShoupaiPaiXingList3 =
//                player.getShoupaiCalculator().calculateAllHuPaiShoupaiPaiXingForZimoHu(guipaiTypes, paiTypesForGuipaiAct,
//                        guipaiList, actGuipaiBenpaiPai, player, new NoDanpaiOneDuiziGouXingPanHu());
//        assertFalse(huPaiShoupaiPaiXingList3.isEmpty());

    }

    private MajiangPai[] calculatePaiTypesForGuipaiAct() {
        MajiangPai[] xushupaiArray = MajiangPai.xushupaiArray();
        MajiangPai[] fengpaiArray = MajiangPai.fengpaiArray();
        MajiangPai[] paiTypesForGuipaiAct;

        paiTypesForGuipaiAct = new MajiangPai[xushupaiArray.length + fengpaiArray.length + 2];
        System.arraycopy(xushupaiArray, 0, paiTypesForGuipaiAct, 0, xushupaiArray.length);
        System.arraycopy(fengpaiArray, 0, paiTypesForGuipaiAct, xushupaiArray.length, fengpaiArray.length);
        paiTypesForGuipaiAct[31] = MajiangPai.hongzhong;
        paiTypesForGuipaiAct[32] = MajiangPai.facai;
        return paiTypesForGuipaiAct;
    }

}
