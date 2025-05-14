package dml.majiang.specialrules.genfeng.service;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.da.DaActionProcessor;
import dml.majiang.core.entity.action.mo.MoActionUpdater;
import dml.majiang.core.repository.DaActionProcessorRepository;
import dml.majiang.core.repository.MoActionUpdaterRepository;
import dml.majiang.core.repository.PanSpecialRulesStateRepository;
import dml.majiang.specialrules.genfeng.entity.GenfengDaActionProcessor;
import dml.majiang.specialrules.genfeng.entity.GenfengMoActionUpdater;
import dml.majiang.specialrules.genfeng.entity.GenfengState;
import dml.majiang.specialrules.genfeng.service.repositoryset.GenfengServiceRepositorySet;

import java.util.Set;

public class GenfengService {

    /**
     * 添加跟风能力
     */
    public static void enableGenfeng(long panId, Set<MajiangPai> genfengPaiSet, GenfengServiceRepositorySet repositorySet) {
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = repositorySet.getPanSpecialRulesStateRepository();
        DaActionProcessorRepository<DaActionProcessor> daActionProcessorRepository = repositorySet.getDaActionProcessorRepository();
        MoActionUpdaterRepository<MoActionUpdater> moActionUpdaterRepository = repositorySet.getMoActionUpdaterRepository();

        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.take(panId);
        GenfengState genfengState = new GenfengState();
        genfengState.setGenfengPaiSet(genfengPaiSet);
        panSpecialRulesState.addSpecialRuleState(genfengState);

        DaActionProcessor daActionProcessor = daActionProcessorRepository.remove(panId);
        GenfengDaActionProcessor genfengDaActionProcessor = new GenfengDaActionProcessor(daActionProcessor);
        daActionProcessorRepository.put(genfengDaActionProcessor);

        MoActionUpdater moActionUpdater = moActionUpdaterRepository.remove(panId);
        GenfengMoActionUpdater genfengMoActionUpdater = new GenfengMoActionUpdater(moActionUpdater);
        moActionUpdaterRepository.put(genfengMoActionUpdater);
    }

}
