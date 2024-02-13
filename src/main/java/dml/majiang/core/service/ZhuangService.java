package dml.majiang.core.service;

import dml.majiang.core.entity.MenFeng;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.service.repositoryset.ZhuangServiceRepositorySet;

public class ZhuangService {
    public static void setZhuangToDongMenFengPlayer(long panId, ZhuangServiceRepositorySet zhuangServiceRepositorySet) {
        PanRepository panRepository = zhuangServiceRepositorySet.getPanRepository();
        Pan pan = panRepository.take(panId);
        pan.setMenFengPlayerAsZhuang(MenFeng.dong);
    }
}
