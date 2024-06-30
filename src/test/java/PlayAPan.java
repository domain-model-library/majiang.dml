import dml.common.repository.TestCommonRepository;
import dml.common.repository.TestCommonSingletonRepository;
import dml.id.entity.LongIdGenerator;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.chi.ChiActionProcessor;
import dml.majiang.core.entity.action.chi.ChiActionUpdater;
import dml.majiang.core.entity.action.chi.ChiPlayerDaPaiChiActionUpdater;
import dml.majiang.core.entity.action.chi.PengganghuFirstChiActionProcessor;
import dml.majiang.core.entity.action.da.*;
import dml.majiang.core.entity.action.gang.GangActionProcessor;
import dml.majiang.core.entity.action.gang.GangActionUpdater;
import dml.majiang.core.entity.action.gang.GangPlayerMoPaiGangActionUpdater;
import dml.majiang.core.entity.action.gang.HuFirstGangActionProcessor;
import dml.majiang.core.entity.action.guo.DoNothingGuoActionProcessor;
import dml.majiang.core.entity.action.guo.GuoActionProcessor;
import dml.majiang.core.entity.action.guo.GuoActionUpdater;
import dml.majiang.core.entity.action.guo.PlayerDaPaiOrXiajiaMoPaiGuoActionUpdater;
import dml.majiang.core.entity.action.hu.ClearAllActionHuActionUpdater;
import dml.majiang.core.entity.action.hu.HuActionProcessor;
import dml.majiang.core.entity.action.hu.HuActionUpdater;
import dml.majiang.core.entity.action.hu.PanSetHuHuActionProcessor;
import dml.majiang.core.entity.action.mo.*;
import dml.majiang.core.entity.action.peng.HuFirstPengActionProcessor;
import dml.majiang.core.entity.action.peng.KezigangshoupaiPengActionUpdater;
import dml.majiang.core.entity.action.peng.PengActionProcessor;
import dml.majiang.core.entity.action.peng.PengActionUpdater;
import dml.majiang.core.entity.action.start.ZhuangMoStartActionUpdater;
import dml.majiang.core.entity.panplayerview.CanNotSeeOtherPlayersPanView;
import dml.majiang.core.entity.shoupai.gouxing.GouXingCalculator;
import dml.majiang.core.entity.shoupai.gouxing.GouXingCalculatorHelper;
import dml.majiang.core.repository.*;
import dml.majiang.core.service.*;
import dml.majiang.core.service.repositoryset.*;
import org.junit.Test;

import java.util.List;

/**
 * 玩一盘
 */
public class PlayAPan {

    /**
     * 测试玩一盘一般麻将的全流程
     */
    @Test
    public void play() {
        GouXingCalculatorHelper.gouXingCalculator = new GouXingCalculator(13, 0);
        // 创建一盘麻将
        Pan pan = PanPlayService.createPan(
                new PlayerMoPaiMoActionProcessor(),
                new TestGangHuDaMoActionUpdater(),
                new DachushoupaiDaActionProcessor(),
                new TestChiPengGangHuDaActionUpdater(),
                new PengganghuFirstChiActionProcessor(),
                new ChiPlayerDaPaiChiActionUpdater(),
                new HuFirstPengActionProcessor(),
                new KezigangshoupaiPengActionUpdater(),
                new HuFirstGangActionProcessor(),
                new GangPlayerMoPaiGangActionUpdater(),
                new PanSetHuHuActionProcessor(),
                new ClearAllActionHuActionUpdater(),
                new DoNothingGuoActionProcessor(),
                new PlayerDaPaiOrXiajiaMoPaiGuoActionUpdater(),
                playAPanServiceRepositorySet);

        //决定哪些可用牌，注入麻将牌
        AvaliablePaiService.fillAvaliablePaiWithNoHuapai(pan.getId(), playAPanServiceRepositorySet);

        // 加入玩家
        PanPlayService.addPlayers(pan.getId(), List.of("1", "2", "3", "4"), playAPanServiceRepositorySet);

        //随机设置玩家门风
        MenFengService.setPlayerMenFengRandomly(pan.getId(), playAPanServiceRepositorySet);

        //设置门风为东的玩家为庄家
        ZhuangService.setZhuangToDongMenFengPlayer(pan.getId(), playAPanServiceRepositorySet);

        //发牌
        FaPaiService.faPai13Shoupai(pan.getId(), playAPanServiceRepositorySet);

        //排序手牌
        PanPlayService.sortPlayerShoupai(pan.getId(), playAPanServiceRepositorySet);

        //开始一盘
        PanPlayService.start(pan.getId(), new ZhuangMoStartActionUpdater(), playAPanServiceRepositorySet);

        //查询庄家看到的一盘，从中获取庄家唯一的action id
        String zhuangPlayerId = pan.getZhuangPlayerId();
        CanNotSeeOtherPlayersPanView panViewForZhuang = PanPlayerViewService.buildPanViewForPlayer(pan.getId(), zhuangPlayerId, playAPanServiceRepositorySet);
        int actionId = panViewForZhuang.getSelfPlayerView().getActionCandidates().values().iterator().next().getId();

        //庄家摸牌
        PanPlayService.action(pan.getId(), zhuangPlayerId, actionId, playAPanServiceRepositorySet);

    }

    PanRepository panRepository = TestCommonRepository.instance(PanRepository.class);
    PanIDGeneratorRepository panIDGeneratorRepository = TestCommonSingletonRepository.instance(PanIDGeneratorRepository.class,
            new LongIdGenerator(1L) {
            });
    PanSpecialRulesStateRepository panSpecialRulesStateRepository =
            TestCommonRepository.instance(PanSpecialRulesStateRepository.class);
    MoActionProcessorRepository<MoActionProcessor> moActionProcessorRepository =
            TestCommonRepository.instance(MoActionProcessorRepository.class);
    MoActionUpdaterRepository<MoActionUpdater> moActionUpdaterRepository =
            TestCommonRepository.instance(MoActionUpdaterRepository.class);

    DaActionProcessorRepository<DaActionProcessor> daActionProcessorRepository =
            TestCommonRepository.instance(DaActionProcessorRepository.class);
    DaActionUpdaterRepository<DaActionUpdater> daActionUpdaterRepository =
            TestCommonRepository.instance(DaActionUpdaterRepository.class);
    ChiActionProcessorRepository<ChiActionProcessor> chiActionProcessorRepository =
            TestCommonRepository.instance(ChiActionProcessorRepository.class);
    ChiActionUpdaterRepository<ChiActionUpdater> chiActionUpdaterRepository =
            TestCommonRepository.instance(ChiActionUpdaterRepository.class);
    PengActionProcessorRepository<PengActionProcessor> pengActionProcessorRepository =
            TestCommonRepository.instance(PengActionProcessorRepository.class);
    PengActionUpdaterRepository<PengActionUpdater> pengActionUpdaterRepository =
            TestCommonRepository.instance(PengActionUpdaterRepository.class);
    GangActionProcessorRepository<GangActionProcessor> gangActionProcessorRepository =
            TestCommonRepository.instance(GangActionProcessorRepository.class);
    GangActionUpdaterRepository<GangActionUpdater> gangActionUpdaterRepository =
            TestCommonRepository.instance(GangActionUpdaterRepository.class);
    HuActionProcessorRepository<HuActionProcessor> huActionProcessorRepository =
            TestCommonRepository.instance(HuActionProcessorRepository.class);
    HuActionUpdaterRepository<HuActionUpdater> huActionUpdaterRepository =
            TestCommonRepository.instance(HuActionUpdaterRepository.class);
    GuoActionProcessorRepository<GuoActionProcessor> guoActionProcessorRepository =
            TestCommonRepository.instance(GuoActionProcessorRepository.class);
    GuoActionUpdaterRepository<GuoActionUpdater> guoActionUpdaterRepository =
            TestCommonRepository.instance(GuoActionUpdaterRepository.class);
    PlayAPanServiceRepositorySet playAPanServiceRepositorySet = new PlayAPanServiceRepositorySet(panRepository,
            panIDGeneratorRepository,
            panSpecialRulesStateRepository,
            moActionProcessorRepository,
            moActionUpdaterRepository,
            daActionProcessorRepository,
            daActionUpdaterRepository,
            chiActionProcessorRepository,
            chiActionUpdaterRepository,
            pengActionProcessorRepository,
            pengActionUpdaterRepository,
            gangActionProcessorRepository,
            gangActionUpdaterRepository,
            huActionProcessorRepository,
            huActionUpdaterRepository,
            guoActionProcessorRepository,
            guoActionUpdaterRepository);


}

class PlayAPanServiceRepositorySet implements PanPlayServiceRepositorySet,
        MenFengServiceRepositorySet,
        ZhuangServiceRepositorySet,
        AvaliablePaiServiceRepositorySet,
        FaPaiServiceRepositorySet,
        PanPlayerViewServiceRepositorySet {
    private PanRepository panRepository;
    private PanIDGeneratorRepository panIDGeneratorRepository;

    private PanSpecialRulesStateRepository panSpecialRulesStateRepository;

    private MoActionProcessorRepository<MoActionProcessor> moActionProcessorRepository;

    private MoActionUpdaterRepository<MoActionUpdater> moActionUpdaterRepository;

    private DaActionProcessorRepository<DaActionProcessor> daActionProcessorRepository;

    private DaActionUpdaterRepository<DaActionUpdater> daActionUpdaterRepository;

    private ChiActionProcessorRepository<ChiActionProcessor> chiActionProcessorRepository;

    private ChiActionUpdaterRepository<ChiActionUpdater> chiActionUpdaterRepository;
    private PengActionProcessorRepository<PengActionProcessor> pengActionProcessorRepository;

    private PengActionUpdaterRepository<PengActionUpdater> pengActionUpdaterRepository;

    private GangActionProcessorRepository<GangActionProcessor> gangActionProcessorRepository;

    private GangActionUpdaterRepository<GangActionUpdater> gangActionUpdaterRepository;
    private HuActionProcessorRepository<HuActionProcessor> huActionProcessorRepository;

    private HuActionUpdaterRepository<HuActionUpdater> huActionUpdaterRepository;

    private GuoActionProcessorRepository<GuoActionProcessor> guoActionProcessorRepository;

    private GuoActionUpdaterRepository<GuoActionUpdater> guoActionUpdaterRepository;

    public PlayAPanServiceRepositorySet(PanRepository panRepository,
                                        PanIDGeneratorRepository panIDGeneratorRepository,
                                        PanSpecialRulesStateRepository panSpecialRulesStateRepository,
                                        MoActionProcessorRepository<MoActionProcessor> moActionProcessorRepository,
                                        MoActionUpdaterRepository<MoActionUpdater> moActionUpdaterRepository,
                                        DaActionProcessorRepository<DaActionProcessor> daActionProcessorRepository,
                                        DaActionUpdaterRepository<DaActionUpdater> daActionUpdaterRepository,
                                        ChiActionProcessorRepository<ChiActionProcessor> chiActionProcessorRepository,
                                        ChiActionUpdaterRepository<ChiActionUpdater> chiActionUpdaterRepository,
                                        PengActionProcessorRepository<PengActionProcessor> pengActionProcessorRepository,
                                        PengActionUpdaterRepository<PengActionUpdater> pengActionUpdaterRepository,
                                        GangActionProcessorRepository<GangActionProcessor> gangActionProcessorRepository,
                                        GangActionUpdaterRepository<GangActionUpdater> gangActionUpdaterRepository,
                                        HuActionProcessorRepository<HuActionProcessor> huActionProcessorRepository,
                                        HuActionUpdaterRepository<HuActionUpdater> huActionUpdaterRepository,
                                        GuoActionProcessorRepository<GuoActionProcessor> guoActionProcessorRepository,
                                        GuoActionUpdaterRepository<GuoActionUpdater> guoActionUpdaterRepository) {
        this.panRepository = panRepository;
        this.panIDGeneratorRepository = panIDGeneratorRepository;
        this.panSpecialRulesStateRepository = panSpecialRulesStateRepository;
        this.moActionProcessorRepository = moActionProcessorRepository;
        this.moActionUpdaterRepository = moActionUpdaterRepository;
        this.daActionProcessorRepository = daActionProcessorRepository;
        this.daActionUpdaterRepository = daActionUpdaterRepository;
        this.chiActionProcessorRepository = chiActionProcessorRepository;
        this.chiActionUpdaterRepository = chiActionUpdaterRepository;
        this.pengActionProcessorRepository = pengActionProcessorRepository;
        this.pengActionUpdaterRepository = pengActionUpdaterRepository;
        this.gangActionProcessorRepository = gangActionProcessorRepository;
        this.gangActionUpdaterRepository = gangActionUpdaterRepository;
        this.huActionProcessorRepository = huActionProcessorRepository;
        this.huActionUpdaterRepository = huActionUpdaterRepository;
        this.guoActionProcessorRepository = guoActionProcessorRepository;
        this.guoActionUpdaterRepository = guoActionUpdaterRepository;
    }

    @Override
    public PanRepository getPanRepository() {
        return panRepository;
    }

    @Override
    public PanIDGeneratorRepository getPanIDGeneratorRepository() {
        return panIDGeneratorRepository;
    }

    @Override
    public PanSpecialRulesStateRepository getPanSpecialRulesStateRepository() {
        return panSpecialRulesStateRepository;
    }

    @Override
    public MoActionProcessorRepository<MoActionProcessor> getMoActionProcessorRepository() {
        return moActionProcessorRepository;
    }

    @Override
    public MoActionUpdaterRepository<MoActionUpdater> getMoActionUpdaterRepository() {
        return moActionUpdaterRepository;
    }

    @Override
    public PengActionProcessorRepository<PengActionProcessor> getPengActionProcessorRepository() {
        return pengActionProcessorRepository;
    }

    @Override
    public PengActionUpdaterRepository<PengActionUpdater> getPengActionUpdaterRepository() {
        return pengActionUpdaterRepository;
    }

    @Override
    public HuActionProcessorRepository<HuActionProcessor> getHuActionProcessorRepository() {
        return huActionProcessorRepository;
    }

    @Override
    public HuActionUpdaterRepository<HuActionUpdater> getHuActionUpdaterRepository() {
        return huActionUpdaterRepository;
    }

    @Override
    public GangActionProcessorRepository<GangActionProcessor> getGangActionProcessorRepository() {
        return gangActionProcessorRepository;
    }

    @Override
    public GangActionUpdaterRepository<GangActionUpdater> getGangActionUpdaterRepository() {
        return gangActionUpdaterRepository;
    }

    @Override
    public ChiActionProcessorRepository<ChiActionProcessor> getChiActionProcessorRepository() {
        return chiActionProcessorRepository;
    }

    @Override
    public ChiActionUpdaterRepository<ChiActionUpdater> getChiActionUpdaterRepository() {
        return chiActionUpdaterRepository;
    }

    @Override
    public DaActionProcessorRepository<DaActionProcessor> getDaActionProcessorRepository() {
        return daActionProcessorRepository;
    }

    @Override
    public DaActionUpdaterRepository<DaActionUpdater> getDaActionUpdaterRepository() {
        return daActionUpdaterRepository;
    }

    @Override
    public GuoActionProcessorRepository<GuoActionProcessor> getGuoActionProcessorRepository() {
        return guoActionProcessorRepository;
    }

    @Override
    public GuoActionUpdaterRepository<GuoActionUpdater> getGuoActionUpdaterRepository() {
        return guoActionUpdaterRepository;
    }


}

class TestGangHuDaMoActionUpdater extends GangHuDaMoActionUpdater {
    private long panId;

    @Override
    protected void tryAndGenerateHuCandidateAction(MoAction moAction, Pan pan, PanSpecialRulesState panSpecialRulesState) {
    }

    @Override
    public void setPanId(long panId) {
        this.panId = panId;
    }

    @Override
    public long getPanId() {
        return panId;
    }
}

class TestChiPengGangHuDaActionUpdater extends ChiPengGangHuDaActionUpdater {
    private long panId;

    @Override
    protected void tryAndGenerateHuCandidateAction(DaAction daAction, Pan pan,
                                                   PanSpecialRulesState panSpecialRulesState, PanPlayer panPlayer) {
    }

    @Override
    public void setPanId(long panId) {
        this.panId = panId;
    }

    @Override
    public long getPanId() {
        return panId;
    }
}
