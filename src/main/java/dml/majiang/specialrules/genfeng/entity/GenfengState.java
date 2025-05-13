package dml.majiang.specialrules.genfeng.entity;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.SpecialRuleState;

import java.util.HashSet;
import java.util.Set;

public class GenfengState implements SpecialRuleState {
    private Set<MajiangPai> genfengPaiSet = new HashSet<>();
    private Set<MajiangPai> zhengzaiBeiGenPaiSet = new HashSet<>();

    public Set<MajiangPai> getGenfengPaiSet() {
        return genfengPaiSet;
    }

    public void setGenfengPaiSet(Set<MajiangPai> genfengPaiSet) {
        this.genfengPaiSet = genfengPaiSet;
    }

    public Set<MajiangPai> getZhengzaiBeiGenPaiSet() {
        return zhengzaiBeiGenPaiSet;
    }

    public void setZhengzaiBeiGenPaiSet(Set<MajiangPai> zhengzaiBeiGenPaiSet) {
        this.zhengzaiBeiGenPaiSet = zhengzaiBeiGenPaiSet;
    }

    public boolean zhengzaiBeiGen(MajiangPai paiType) {
        return zhengzaiBeiGenPaiSet.contains(paiType);
    }

    public boolean isGenfengPai(MajiangPai paiType) {
        return genfengPaiSet.contains(paiType);
    }

    public void updateGenfengState(MajiangPai daPai) {
        if (genfengPaiSet.contains(daPai)) {
            zhengzaiBeiGenPaiSet.add(daPai);
        }
    }
}
