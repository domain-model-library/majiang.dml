package dml.majiang.core.entity.shoupai;


import dml.majiang.core.entity.MajiangPai;
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

    public void replacePaiType(MajiangPai paiType, MajiangPai toReplaceType) {
        for (Pai danpai : danpaiList) {
            if (danpai.getPaiType().equals(paiType)) {
                danpai.setPaiType(toReplaceType);
            }
        }
        for (Duizi duizi : duiziList) {
            if (duizi.getPai1().getPaiType().equals(paiType)) {
                duizi.getPai1().setPaiType(toReplaceType);
            }
            if (duizi.getPai2().getPaiType().equals(paiType)) {
                duizi.getPai2().setPaiType(toReplaceType);
            }
        }
        for (Kezi kezi : keziList) {
            if (kezi.getPai1().getPaiType().equals(paiType)) {
                kezi.getPai1().setPaiType(toReplaceType);
            }
            if (kezi.getPai2().getPaiType().equals(paiType)) {
                kezi.getPai2().setPaiType(toReplaceType);
            }
            if (kezi.getPai3().getPaiType().equals(paiType)) {
                kezi.getPai3().setPaiType(toReplaceType);
            }
        }
        for (Gangzi gangzi : gangziList) {
            if (gangzi.getPai1().getPaiType().equals(paiType)) {
                gangzi.getPai1().setPaiType(toReplaceType);
            }
            if (gangzi.getPai2().getPaiType().equals(paiType)) {
                gangzi.getPai2().setPaiType(toReplaceType);
            }
            if (gangzi.getPai3().getPaiType().equals(paiType)) {
                gangzi.getPai3().setPaiType(toReplaceType);
            }
            if (gangzi.getPai4().getPaiType().equals(paiType)) {
                gangzi.getPai4().setPaiType(toReplaceType);
            }
        }
        for (Shunzi shunzi : shunziList) {
            if (shunzi.getPai1().getPaiType().equals(paiType)) {
                shunzi.getPai1().setPaiType(toReplaceType);
            }
            if (shunzi.getPai2().getPaiType().equals(paiType)) {
                shunzi.getPai2().setPaiType(toReplaceType);
            }
            if (shunzi.getPai3().getPaiType().equals(paiType)) {
                shunzi.getPai3().setPaiType(toReplaceType);
            }
        }
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
