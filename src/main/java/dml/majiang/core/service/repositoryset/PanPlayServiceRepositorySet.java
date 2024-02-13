package dml.majiang.core.service.repositoryset;

import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.chi.ChiActionProcessor;
import dml.majiang.core.entity.action.chi.ChiActionUpdater;
import dml.majiang.core.entity.action.da.DaActionProcessor;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.gang.GangActionProcessor;
import dml.majiang.core.entity.action.gang.GangActionUpdater;
import dml.majiang.core.entity.action.guo.GuoActionProcessor;
import dml.majiang.core.entity.action.guo.GuoActionUpdater;
import dml.majiang.core.entity.action.hu.HuActionProcessor;
import dml.majiang.core.entity.action.hu.HuActionUpdater;
import dml.majiang.core.entity.action.mo.MoActionProcessor;
import dml.majiang.core.entity.action.mo.MoActionUpdater;
import dml.majiang.core.entity.action.peng.PengActionProcessor;
import dml.majiang.core.entity.action.peng.PengActionUpdater;
import dml.majiang.core.repository.*;

public interface PanPlayServiceRepositorySet {
    public PanRepository getPanRepository();

    public PanIDGeneratorRepository getPanIDGeneratorRepository();

    public PanSpecialRulesStateRepository<PanSpecialRulesState> getPanSpecialRulesStateRepository();

    public MoActionProcessorRepository<MoActionProcessor> getMoActionProcessorRepository();

    public MoActionUpdaterRepository<MoActionUpdater> getMoActionUpdaterRepository();

    public PengActionProcessorRepository<PengActionProcessor> getPengActionProcessorRepository();

    public PengActionUpdaterRepository<PengActionUpdater> getPengActionUpdaterRepository();

    public HuActionProcessorRepository<HuActionProcessor> getHuActionProcessorRepository();

    public HuActionUpdaterRepository<HuActionUpdater> getHuActionUpdaterRepository();

    public GangActionProcessorRepository<GangActionProcessor> getGangActionProcessorRepository();

    public GangActionUpdaterRepository<GangActionUpdater> getGangActionUpdaterRepository();

    public ChiActionProcessorRepository<ChiActionProcessor> getChiActionProcessorRepository();

    public ChiActionUpdaterRepository<ChiActionUpdater> getChiActionUpdaterRepository();

    public DaActionProcessorRepository<DaActionProcessor> getDaActionProcessorRepository();

    public DaActionUpdaterRepository<DaActionUpdater> getDaActionUpdaterRepository();

    public GuoActionProcessorRepository<GuoActionProcessor> getGuoActionProcessorRepository();

    public GuoActionUpdaterRepository<GuoActionUpdater> getGuoActionUpdaterRepository();
}
