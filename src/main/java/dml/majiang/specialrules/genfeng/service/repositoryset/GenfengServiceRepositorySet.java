package dml.majiang.specialrules.genfeng.service.repositoryset;

import dml.majiang.core.repository.DaActionProcessorRepository;
import dml.majiang.core.repository.MoActionUpdaterRepository;
import dml.majiang.core.repository.PanSpecialRulesStateRepository;

public interface GenfengServiceRepositorySet {

    PanSpecialRulesStateRepository getPanSpecialRulesStateRepository();

    DaActionProcessorRepository getDaActionProcessorRepository();

    MoActionUpdaterRepository getMoActionUpdaterRepository();
}
