package dml.majiang.core.entity.shoupai;


import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.fenzu.Duizi;
import dml.majiang.core.entity.fenzu.Gangzi;
import dml.majiang.core.entity.fenzu.Kezi;
import dml.majiang.core.entity.fenzu.Shunzi;

import java.util.ArrayList;
import java.util.List;

public class ShoupaiPaiXing {

    private List<Pai> danpaiList = new ArrayList<>();
    private List<Duizi> duiziList = new ArrayList<>();
    private List<Kezi> keziList = new ArrayList<>();
    private List<Gangzi> gangziList = new ArrayList<>();
    private List<Shunzi> shunziList = new ArrayList<>();

    public void addShunzi(Shunzi shunzi) {
        shunziList.add(shunzi);
    }

    public void addDuizi(Duizi duizi) {
        duiziList.add(duizi);
    }

    public void addKezi(Kezi kezi) {
        keziList.add(kezi);
    }

    public void addGangzi(Gangzi gangzi) {
        gangziList.add(gangzi);
    }

    public void addDanpai(Pai danpai) {
        danpaiList.add(danpai);
    }

    public List<Pai> getDanpaiList() {
        return danpaiList;
    }

    public void setDanpaiList(List<Pai> danpaiList) {
        this.danpaiList = danpaiList;
    }

    public List<Duizi> getDuiziList() {
        return duiziList;
    }

    public void setDuiziList(List<Duizi> duiziList) {
        this.duiziList = duiziList;
    }

    public List<Kezi> getKeziList() {
        return keziList;
    }

    public void setKeziList(List<Kezi> keziList) {
        this.keziList = keziList;
    }

    public List<Gangzi> getGangziList() {
        return gangziList;
    }

    public void setGangziList(List<Gangzi> gangziList) {
        this.gangziList = gangziList;
    }

    public List<Shunzi> getShunziList() {
        return shunziList;
    }

    public void setShunziList(List<Shunzi> shunziList) {
        this.shunziList = shunziList;
    }

}
