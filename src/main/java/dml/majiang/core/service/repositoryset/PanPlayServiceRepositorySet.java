package dml.majiang.core.service.repositoryset;

import dml.majiang.core.repository.*;

public interface PanPlayServiceRepositorySet {
    public PanRepository getPanRepository();

    public PanSpecialRulesStateRepository getPanSpecialRulesStateRepository();

    public MoActionProcessorRepository getMoActionProcessorRepository();

    public MoActionUpdaterRepository getMoActionUpdaterRepository();

    public PengActionProcessorRepository getPengActionProcessorRepository();

    public PengActionUpdaterRepository getPengActionUpdaterRepository();

    public HuActionProcessorRepository getHuActionProcessorRepository();

    public HuActionUpdaterRepository getHuActionUpdaterRepository();

    public GangActionProcessorRepository getGangActionProcessorRepository();

    public GangActionUpdaterRepository getGangActionUpdaterRepository();

    public ChiActionProcessorRepository getChiActionProcessorRepository();

    public ChiActionUpdaterRepository getChiActionUpdaterRepository();

    public DaActionProcessorRepository getDaActionProcessorRepository();

    public DaActionUpdaterRepository getDaActionUpdaterRepository();

    public GuoActionProcessorRepository getGuoActionProcessorRepository();

    public GuoActionUpdaterRepository getGuoActionUpdaterRepository();
}
