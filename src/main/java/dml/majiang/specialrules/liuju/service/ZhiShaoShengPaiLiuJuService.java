package dml.majiang.specialrules.liuju.service;

import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.gang.GangActionUpdater;
import dml.majiang.core.entity.action.guo.GuoActionUpdater;
import dml.majiang.core.repository.DaActionUpdaterRepository;
import dml.majiang.core.repository.GangActionUpdaterRepository;
import dml.majiang.core.repository.GuoActionUpdaterRepository;
import dml.majiang.core.repository.PanSpecialRulesStateRepository;
import dml.majiang.specialrules.liuju.entity.ZhiShaoShengPaiDaActionUpdater;
import dml.majiang.specialrules.liuju.entity.ZhiShaoShengPaiGangActionUpdater;
import dml.majiang.specialrules.liuju.entity.ZhiShaoShengPaiGuoActionUpdater;
import dml.majiang.specialrules.liuju.entity.ZhiShaoShengPaiRuleState;
import dml.majiang.specialrules.liuju.service.repositoryset.ZhiShaoShengPaiLiuJuServiceRepositorySet;

/**
 * 至少剩牌流局相关服务
 */
public class ZhiShaoShengPaiLiuJuService {
    /**
     * 添加至少剩牌流局能力
     */
    public static void enableZhiShaoShengPaiLiuJu(long panId, int zhiShaoShengPai,
                                                  ZhiShaoShengPaiLiuJuServiceRepositorySet repositorySet) {
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = repositorySet.getPanSpecialRulesStateRepository();
        DaActionUpdaterRepository<DaActionUpdater> daActionUpdaterRepository = repositorySet.getDaActionUpdaterRepository();
        GangActionUpdaterRepository<GangActionUpdater> gangActionUpdaterRepository = repositorySet.getGangActionUpdaterRepository();
        GuoActionUpdaterRepository<GuoActionUpdater> guoActionUpdaterRepository = repositorySet.getGuoActionUpdaterRepository();

        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.take(panId);
        ZhiShaoShengPaiRuleState zhiShaoShengPaiRuleState = new ZhiShaoShengPaiRuleState(zhiShaoShengPai);
        panSpecialRulesState.addSpecialRuleState(zhiShaoShengPaiRuleState);

        DaActionUpdater daActionUpdater = daActionUpdaterRepository.remove(panId);
        ZhiShaoShengPaiDaActionUpdater zhiShaoShengPaiDaActionUpdater = new ZhiShaoShengPaiDaActionUpdater(daActionUpdater);
        daActionUpdaterRepository.put(zhiShaoShengPaiDaActionUpdater);

        GangActionUpdater gangActionUpdater = gangActionUpdaterRepository.remove(panId);
        ZhiShaoShengPaiGangActionUpdater zhiShaoShengPaiGangActionUpdater = new ZhiShaoShengPaiGangActionUpdater(gangActionUpdater);
        gangActionUpdaterRepository.put(zhiShaoShengPaiGangActionUpdater);

        GuoActionUpdater guoActionUpdater = guoActionUpdaterRepository.remove(panId);
        ZhiShaoShengPaiGuoActionUpdater zhiShaoShengPaiGuoActionUpdater = new ZhiShaoShengPaiGuoActionUpdater(guoActionUpdater);
        guoActionUpdaterRepository.put(zhiShaoShengPaiGuoActionUpdater);
    }
}
