package dml.majiang.specialrules.youpengbupeng.service.repositoryset;

import dml.majiang.core.repository.DaActionUpdaterRepository;
import dml.majiang.core.repository.GuoActionProcessorRepository;
import dml.majiang.core.repository.MoActionProcessorRepository;
import dml.majiang.core.repository.PanSpecialRulesStateRepository;

public interface YoupengbupengServiceRepositorySet {
    PanSpecialRulesStateRepository getPanSpecialRulesStateRepository();

    DaActionUpdaterRepository getDaActionUpdaterRepository();

    MoActionProcessorRepository getMoActionProcessorRepository();

    GuoActionProcessorRepository getGuoActionProcessorRepository();
}
