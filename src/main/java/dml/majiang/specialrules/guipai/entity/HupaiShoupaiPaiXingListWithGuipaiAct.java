package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HupaiShoupaiPaiXingListWithGuipaiAct {
    private Map<Integer, MajiangPai> guipaiActMap = new HashMap<>();
    private List<ShoupaiPaiXing> hupaiShoupaiPaiXingList;

    public void addGuipaiAct(int guipaiId, MajiangPai actPaiType) {
        guipaiActMap.put(guipaiId, actPaiType);
    }

    public Map<Integer, MajiangPai> getGuipaiActMap() {
        return guipaiActMap;
    }

    public List<ShoupaiPaiXing> getHupaiShoupaiPaiXingList() {
        return hupaiShoupaiPaiXingList;
    }

    public void setHupaiShoupaiPaiXingList(List<ShoupaiPaiXing> hupaiShoupaiPaiXingList) {
        this.hupaiShoupaiPaiXingList = hupaiShoupaiPaiXingList;
    }


}
