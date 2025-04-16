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
            danpai.replacePaiType(paiType, toReplaceType);
        }
        for (Duizi duizi : duiziList) {
            duizi.replacePaiType(paiType, toReplaceType);
        }
        for (Kezi kezi : keziList) {
            kezi.replacePaiType(paiType, toReplaceType);
        }
        for (Gangzi gangzi : gangziList) {
            gangzi.replacePaiType(paiType, toReplaceType);
        }
        for (Shunzi shunzi : shunziList) {
            shunzi.replacePaiType(paiType, toReplaceType);
        }
    }

    public void setPaiType(int paiId, MajiangPai paiType) {
        for (Pai danpai : danpaiList) {
            if (danpai.getId() == paiId) {
                danpai.setPaiType(paiType);
                return;
            }
        }
        for (Duizi duizi : duiziList) {
            if (duizi.getPai1().getId() == paiId) {
                duizi.getPai1().setPaiType(paiType);
                return;
            }
            if (duizi.getPai2().getId() == paiId) {
                duizi.getPai2().setPaiType(paiType);
                return;
            }
        }
        for (Kezi kezi : keziList) {
            if (kezi.getPai1().getId() == paiId) {
                kezi.getPai1().setPaiType(paiType);
                return;
            }
            if (kezi.getPai2().getId() == paiId) {
                kezi.getPai2().setPaiType(paiType);
                return;
            }
            if (kezi.getPai3().getId() == paiId) {
                kezi.getPai3().setPaiType(paiType);
                return;
            }
        }
        for (Gangzi gangzi : gangziList) {
            if (gangzi.getPai1().getId() == paiId) {
                gangzi.getPai1().setPaiType(paiType);
                return;
            }
            if (gangzi.getPai2().getId() == paiId) {
                gangzi.getPai2().setPaiType(paiType);
                return;
            }
            if (gangzi.getPai3().getId() == paiId) {
                gangzi.getPai3().setPaiType(paiType);
                return;
            }
            if (gangzi.getPai4().getId() == paiId) {
                gangzi.getPai4().setPaiType(paiType);
                return;
            }
        }
        for (Shunzi shunzi : shunziList) {
            if (shunzi.getPai1().getId() == paiId) {
                shunzi.getPai1().setPaiType(paiType);
                return;
            }
            if (shunzi.getPai2().getId() == paiId) {
                shunzi.getPai2().setPaiType(paiType);
                return;
            }
            if (shunzi.getPai3().getId() == paiId) {
                shunzi.getPai3().setPaiType(paiType);
                return;
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
