package dml.majiang.specialrules.liuju.service.repositoryset;

import dml.majiang.core.repository.DaActionUpdaterRepository;
import dml.majiang.core.repository.GangActionUpdaterRepository;
import dml.majiang.core.repository.GuoActionUpdaterRepository;
import dml.majiang.core.repository.PanSpecialRulesStateRepository;

public interface ZhiShaoShengPaiLiuJuServiceRepositorySet {
    public PanSpecialRulesStateRepository getPanSpecialRulesStateRepository();

    public DaActionUpdaterRepository getDaActionUpdaterRepository();

    public GangActionUpdaterRepository getGangActionUpdaterRepository();

    public GuoActionUpdaterRepository getGuoActionUpdaterRepository();
}
