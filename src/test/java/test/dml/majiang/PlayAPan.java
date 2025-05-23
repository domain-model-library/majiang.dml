package test.dml.majiang;

import dml.common.repository.TestCommonRepository;
import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanFrames;
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
import dml.majiang.core.entity.action.hu.*;
import dml.majiang.core.entity.action.mo.*;
import dml.majiang.core.entity.action.peng.HuFirstPengActionProcessor;
import dml.majiang.core.entity.action.peng.KezigangshoupaiPengActionUpdater;
import dml.majiang.core.entity.action.peng.PengActionProcessor;
import dml.majiang.core.entity.action.peng.PengActionUpdater;
import dml.majiang.core.entity.action.start.ZhuangMoStartActionUpdater;
import dml.majiang.core.entity.panplayerview.CanNotSeeOtherPlayersPanView;
import dml.majiang.core.entity.shoupai.ShoupaiPaiXing;
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
        // 创建一盘麻将
        Pan pan = PanPlayService.createPan(
                panIDGenerator++,
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
                new PlayerSetHuHuActionProcessor(),
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
    PanFramesRepository panFramesRepository = TestCommonRepository.instance(PanFramesRepository.class);
    long panIDGenerator = 1L;
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
            panFramesRepository,
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

    private PanFramesRepository panFramesRepository;

    private PanSpecialRulesStateRepository panSpecialRulesStateRepository;

    private MoActionProcessorRepository moActionProcessorRepository;

    private MoActionUpdaterRepository moActionUpdaterRepository;

    private DaActionProcessorRepository daActionProcessorRepository;

    private DaActionUpdaterRepository daActionUpdaterRepository;

    private ChiActionProcessorRepository chiActionProcessorRepository;

    private ChiActionUpdaterRepository chiActionUpdaterRepository;
    private PengActionProcessorRepository pengActionProcessorRepository;

    private PengActionUpdaterRepository pengActionUpdaterRepository;

    private GangActionProcessorRepository gangActionProcessorRepository;

    private GangActionUpdaterRepository gangActionUpdaterRepository;
    private HuActionProcessorRepository huActionProcessorRepository;

    private HuActionUpdaterRepository huActionUpdaterRepository;

    private GuoActionProcessorRepository guoActionProcessorRepository;

    private GuoActionUpdaterRepository guoActionUpdaterRepository;

    public PlayAPanServiceRepositorySet(PanRepository panRepository,
                                        PanFramesRepository panFramesRepository,
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
        this.panFramesRepository = panFramesRepository;
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
    public PanFramesRepository getPanFramesRepository() {
        return panFramesRepository;
    }

    @Override
    public PanSpecialRulesStateRepository getPanSpecialRulesStateRepository() {
        return panSpecialRulesStateRepository;
    }

    @Override
    public MoActionProcessorRepository getMoActionProcessorRepository() {
        return moActionProcessorRepository;
    }

    @Override
    public MoActionUpdaterRepository getMoActionUpdaterRepository() {
        return moActionUpdaterRepository;
    }

    @Override
    public PengActionProcessorRepository getPengActionProcessorRepository() {
        return pengActionProcessorRepository;
    }

    @Override
    public PengActionUpdaterRepository getPengActionUpdaterRepository() {
        return pengActionUpdaterRepository;
    }

    @Override
    public HuActionProcessorRepository getHuActionProcessorRepository() {
        return huActionProcessorRepository;
    }

    @Override
    public HuActionUpdaterRepository getHuActionUpdaterRepository() {
        return huActionUpdaterRepository;
    }

    @Override
    public GangActionProcessorRepository getGangActionProcessorRepository() {
        return gangActionProcessorRepository;
    }

    @Override
    public GangActionUpdaterRepository getGangActionUpdaterRepository() {
        return gangActionUpdaterRepository;
    }

    @Override
    public ChiActionProcessorRepository getChiActionProcessorRepository() {
        return chiActionProcessorRepository;
    }

    @Override
    public ChiActionUpdaterRepository getChiActionUpdaterRepository() {
        return chiActionUpdaterRepository;
    }

    @Override
    public DaActionProcessorRepository getDaActionProcessorRepository() {
        return daActionProcessorRepository;
    }

    @Override
    public DaActionUpdaterRepository getDaActionUpdaterRepository() {
        return daActionUpdaterRepository;
    }

    @Override
    public GuoActionProcessorRepository getGuoActionProcessorRepository() {
        return guoActionProcessorRepository;
    }

    @Override
    public GuoActionUpdaterRepository getGuoActionUpdaterRepository() {
        return guoActionUpdaterRepository;
    }


}

class TestGangHuDaMoActionUpdater extends GangHuDaMoActionUpdater {
    private long panId;


    @Override
    public void setPanId(long panId) {
        this.panId = panId;
    }

    @Override
    public long getPanId() {
        return panId;
    }

    @Override
    protected Hu makeHu(MoAction moAction, Pai moPai, Pan pan, PanFrames panFrames, List<ShoupaiPaiXing> hupaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState) {
        return null;
    }
}

class TestChiPengGangHuDaActionUpdater extends ChiPengGangHuDaActionUpdater {
    private long panId;

    @Override
    public void setPanId(long panId) {
        this.panId = panId;
    }

    @Override
    public long getPanId() {
        return panId;
    }

    @Override
    protected Hu makeHu(DaAction daAction, Pai daPai, Pan pan, PanFrames panFrames, String huPlayerId, List<ShoupaiPaiXing> hupaiShoupaiPaiXingList, PanSpecialRulesState panSpecialRulesState) {
        return null;
    }
}
