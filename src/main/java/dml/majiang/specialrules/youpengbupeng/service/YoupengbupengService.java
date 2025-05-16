package dml.majiang.specialrules.youpengbupeng.service;

import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.guo.GuoActionProcessor;
import dml.majiang.core.entity.action.mo.MoActionProcessor;
import dml.majiang.core.repository.DaActionUpdaterRepository;
import dml.majiang.core.repository.GuoActionProcessorRepository;
import dml.majiang.core.repository.MoActionProcessorRepository;
import dml.majiang.core.repository.PanSpecialRulesStateRepository;
import dml.majiang.specialrules.youpengbupeng.entity.YoupengbupengDaActionUpdater;
import dml.majiang.specialrules.youpengbupeng.entity.YoupengbupengGuoActionProcessor;
import dml.majiang.specialrules.youpengbupeng.entity.YoupengbupengMoActionProcessor;
import dml.majiang.specialrules.youpengbupeng.entity.YoupengbupengState;
import dml.majiang.specialrules.youpengbupeng.service.repositoryset.YoupengbupengServiceRepositorySet;

public class YoupengbupengService {
    /**
     * 添加有碰不碰能力
     */
    public static void enableYoupengbupeng(long panId, YoupengbupengServiceRepositorySet repositorySet) {
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = repositorySet.getPanSpecialRulesStateRepository();
        DaActionUpdaterRepository<DaActionUpdater> daActionProcessorRepository = repositorySet.getDaActionUpdaterRepository();
        MoActionProcessorRepository<MoActionProcessor> moActionUpdaterRepository = repositorySet.getMoActionProcessorRepository();
        GuoActionProcessorRepository<GuoActionProcessor> guoActionProcessorRepository = repositorySet.getGuoActionProcessorRepository();

        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.take(panId);
        YoupengbupengState youpengbupengState = new YoupengbupengState();
        panSpecialRulesState.addSpecialRuleState(youpengbupengState);

        DaActionUpdater daActionUpdater = daActionProcessorRepository.remove(panId);
        YoupengbupengDaActionUpdater youpengbupengDaActionUpdater = new YoupengbupengDaActionUpdater(daActionUpdater);
        daActionProcessorRepository.put(youpengbupengDaActionUpdater);

        MoActionProcessor moActionProcessor = moActionUpdaterRepository.remove(panId);
        YoupengbupengMoActionProcessor youpengbupengMoActionProcessor = new YoupengbupengMoActionProcessor(moActionProcessor);
        moActionUpdaterRepository.put(youpengbupengMoActionProcessor);

        GuoActionProcessor guoActionProcessor = guoActionProcessorRepository.remove(panId);
        YoupengbupengGuoActionProcessor youpengbupengGuoActionProcessor = new YoupengbupengGuoActionProcessor(guoActionProcessor);
        guoActionProcessorRepository.put(youpengbupengGuoActionProcessor);
    }
}
