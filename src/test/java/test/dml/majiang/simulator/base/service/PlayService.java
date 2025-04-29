package test.dml.majiang.simulator.base.service;

import dml.common.repository.TestCommonRepository;
import dml.common.repository.TestCommonSingletonRepository;
import dml.majiang.core.entity.Pai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.action.chi.ChiActionProcessor;
import dml.majiang.core.entity.action.chi.ChiActionUpdater;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.da.DaActionProcessor;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.gang.GangActionProcessor;
import dml.majiang.core.entity.action.gang.GangActionUpdater;
import dml.majiang.core.entity.action.guo.GuoActionProcessor;
import dml.majiang.core.entity.action.guo.GuoActionUpdater;
import dml.majiang.core.entity.action.hu.HuActionProcessor;
import dml.majiang.core.entity.action.hu.HuActionUpdater;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.action.mo.MoActionProcessor;
import dml.majiang.core.entity.action.mo.MoActionUpdater;
import dml.majiang.core.entity.action.peng.PengActionProcessor;
import dml.majiang.core.entity.action.peng.PengActionUpdater;
import dml.majiang.core.entity.action.start.PanStartActionUpdater;
import dml.majiang.core.repository.*;
import dml.majiang.core.service.PanPlayService;
import dml.majiang.core.service.WaitingPlayerCursorService;
import dml.majiang.core.service.repositoryset.*;
import test.dml.majiang.simulator.base.entity.PlayConfig;
import test.dml.majiang.simulator.base.entity.PlayStateContainer;
import test.dml.majiang.simulator.base.entity.PlayStateEnum;
import test.dml.majiang.simulator.base.repository.PlayConfigRepository;
import test.dml.majiang.simulator.base.repository.PlayStateContainerRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayService implements
        PanPlayServiceRepositorySet,
        AvaliablePaiServiceRepositorySet,
        MenFengServiceRepositorySet,
        ZhuangServiceRepositorySet,
        FaPaiServiceRepositorySet,
        WaitingPlayerCursorServiceRepositorySet {

    protected PanRepository panRepository;
    protected PanFramesRepository panFramesRepository;
    protected PanSpecialRulesStateRepository panSpecialRulesStateRepository;
    protected MoActionProcessorRepository<?> moActionProcessorRepository;
    protected MoActionUpdaterRepository<?> moActionUpdaterRepository;
    protected PengActionProcessorRepository<?> pengActionProcessorRepository;
    protected PengActionUpdaterRepository<?> pengActionUpdaterRepository;
    protected HuActionProcessorRepository<?> huActionProcessorRepository;
    protected HuActionUpdaterRepository<?> huActionUpdaterRepository;
    protected GangActionProcessorRepository<?> gangActionProcessorRepository;
    protected GangActionUpdaterRepository<?> gangActionUpdaterRepository;
    protected ChiActionProcessorRepository<?> chiActionProcessorRepository;
    protected ChiActionUpdaterRepository<?> chiActionUpdaterRepository;
    protected DaActionProcessorRepository<?> daActionProcessorRepository;
    protected DaActionUpdaterRepository<?> daActionUpdaterRepository;
    protected GuoActionProcessorRepository<?> guoActionProcessorRepository;
    protected GuoActionUpdaterRepository<?> guoActionUpdaterRepository;
    protected WaitingPlayerCursorRepository waitingPlayerCursorRepository;
    protected PlayConfigRepository playConfigRepository;
    protected PlayStateContainerRepository playStateContainerRepository;

    public PlayService() {
        panRepository = TestCommonRepository.instance(PanRepository.class);
        panFramesRepository = TestCommonRepository.instance(PanFramesRepository.class);
        panSpecialRulesStateRepository = TestCommonRepository.instance(PanSpecialRulesStateRepository.class);
        moActionProcessorRepository = TestCommonRepository.instance(MoActionProcessorRepository.class);
        moActionUpdaterRepository = TestCommonRepository.instance(MoActionUpdaterRepository.class);
        pengActionProcessorRepository = TestCommonRepository.instance(PengActionProcessorRepository.class);
        pengActionUpdaterRepository = TestCommonRepository.instance(PengActionUpdaterRepository.class);
        huActionProcessorRepository = TestCommonRepository.instance(HuActionProcessorRepository.class);
        huActionUpdaterRepository = TestCommonRepository.instance(HuActionUpdaterRepository.class);
        gangActionProcessorRepository = TestCommonRepository.instance(GangActionProcessorRepository.class);
        gangActionUpdaterRepository = TestCommonRepository.instance(GangActionUpdaterRepository.class);
        chiActionProcessorRepository = TestCommonRepository.instance(ChiActionProcessorRepository.class);
        chiActionUpdaterRepository = TestCommonRepository.instance(ChiActionUpdaterRepository.class);
        daActionProcessorRepository = TestCommonRepository.instance(DaActionProcessorRepository.class);
        daActionUpdaterRepository = TestCommonRepository.instance(DaActionUpdaterRepository.class);
        guoActionProcessorRepository = TestCommonRepository.instance(GuoActionProcessorRepository.class);
        guoActionUpdaterRepository = TestCommonRepository.instance(GuoActionUpdaterRepository.class);
        waitingPlayerCursorRepository = TestCommonRepository.instance(WaitingPlayerCursorRepository.class);
        playConfigRepository = TestCommonSingletonRepository.instance(PlayConfigRepository.class,
                new PlayConfig());
        playStateContainerRepository = TestCommonSingletonRepository.instance(PlayStateContainerRepository.class,
                new PlayStateContainer());
    }

    public Pan startPan() {
        long panId = 1L;
        List<String> playerIdList = new ArrayList<>();
        playerIdList.add("player1");
        playerIdList.add("player2");
        playerIdList.add("player3");
        playerIdList.add("player4");
        PanPlayService.removePan(panId, this);
        // 创建一盘麻将
        Pan pan = PanPlayService.createPan(
                panId,
                createMoActionProcessor(),
                createMoActionUpdater(),
                createDaActionProcessor(),
                createDaActionUpdater(),
                createChiActionProcessor(),
                createChiActionUpdater(),
                createPengActionProcessor(),
                createPengActionUpdater(),
                createGangActionProcessor(),
                createGangActionUpdater(),
                createHuActionProcessor(),
                createHuActionUpdater(),
                createGuoActionProcessor(),
                createGuoActionUpdater(),
                this);

        //决定哪些可用牌，注入麻将牌
        fillAvaliablePai(panId);

        // 加入玩家
        PanPlayService.addPlayers(panId, playerIdList, this);

        //设置玩家门风
        setPlayerMenFeng(panId);

        //设置玩家为庄家
        setZhuangPlayer(panId);

        //设置特殊规则
        setPanSpecialRules(panId);

        //发牌
        faPai(panId);

        //开始一盘
        PanPlayService.start(panId, createStartActionUpdater(), this);

        pan = panRepository.find(panId);
        PlayConfig playConfig = playConfigRepository.get();
        PlayStateContainer playStateContainer = playStateContainerRepository.take();
        if (playConfig.isCanEditShoupai()) {
            playStateContainer.setPlayState(PlayStateEnum.shoupaiEditing);
            return pan;
        }

        //如果有人摸牌，就直接摸牌了
        MoAction moAction = pan.findMoCandidateAction();
        if (moAction != null) {
            if (!playConfig.isCanSpecifyMoPai()) {
                action(pan.getId(), moAction.getActionPlayerId(), moAction.getId());
                playStateContainer.setPlayState(PlayStateEnum.waitingForOtherAction);
                return pan;
            } else {
                playStateContainer.setPlayState(PlayStateEnum.waitingForMoAction);
                return pan;
            }
        }

        return pan;
    }

    public void action(long panId, String playerId, int actionId) {
        PanPlayerAction action = PanPlayService.action(panId, playerId, actionId, this);
        WaitingPlayerCursorService.updateWaitingPlayerIdToDaPlayer(panId, this);

        PlayConfig playConfig = playConfigRepository.get();
        PlayStateContainer playStateContainer = playStateContainerRepository.take();
        Pan pan = panRepository.find(panId);
        MoAction moAction = pan.findMoCandidateAction();
        if (moAction != null) {
            if (!playConfig.isCanSpecifyMoPai()) {
                action(pan.getId(), moAction.getActionPlayerId(), moAction.getId());
                playStateContainer.setPlayState(PlayStateEnum.waitingForOtherAction);
            } else {
                playStateContainer.setPlayState(PlayStateEnum.waitingForMoAction);
            }
        } else {
            if (!pan.allPlayerHasNoActionCandidates()) {
                playStateContainer.setPlayState(PlayStateEnum.waitingForOtherAction);
            } else {
                playStateContainer.setPlayState(PlayStateEnum.finished);
            }
        }
    }

    public void modifyShoupaiDone() {
        Pan pan = panRepository.find(1L);
        PlayConfig playConfig = playConfigRepository.get();
        PlayStateContainer playStateContainer = playStateContainerRepository.take();
        //如果有人摸牌，就直接摸牌了
        MoAction moAction = pan.findMoCandidateAction();
        if (moAction != null) {
            if (!playConfig.isCanSpecifyMoPai()) {
                action(pan.getId(), moAction.getActionPlayerId(), moAction.getId());
                playStateContainer.setPlayState(PlayStateEnum.waitingForOtherAction);
            } else {
                playStateContainer.setPlayState(PlayStateEnum.waitingForMoAction);
            }
        }
    }

    public Pan getPan() {
        return panRepository.find(1L);
    }

    public void setCanModifyShoupai(boolean canModifyShoupai) {
        PlayConfig playConfig = playConfigRepository.take();
        playConfig.setCanEditShoupai(canModifyShoupai);
    }

    public void setCanSetMopai(boolean canSetMopai) {
        PlayConfig playConfig = playConfigRepository.take();
        playConfig.setCanSpecifyMoPai(canSetMopai);
    }

    public PlayStateEnum getPlayState() {
        PlayStateContainer playStateContainer = playStateContainerRepository.get();
        return playStateContainer.getPlayState();
    }

    public void specificMoPai(int paiId) {
        Pan pan = panRepository.take(1L);
        Pai moPai = null;
        List<Pai> avaliablePaiList = pan.getAvaliablePaiList();
        for (Pai pai : avaliablePaiList) {
            if (pai.getId() == paiId) {
                moPai = pai;
                break;
            }
        }
        avaliablePaiList.remove(moPai);
        avaliablePaiList.add(0, moPai);
        MoAction moAction = pan.findMoCandidateAction();
        PanPlayService.action(pan.getId(), moAction.getActionPlayerId(), moAction.getId(), this);
        WaitingPlayerCursorService.updateWaitingPlayerIdToDaPlayer(pan.getId(), this);
        PlayStateContainer playStateContainer = playStateContainerRepository.take();
        playStateContainer.setPlayState(PlayStateEnum.waitingForOtherAction);
    }

    public DaAction findDaCandidateAction(int paiId) {
        Pan pan = panRepository.find(1L);
        DaAction daAction = pan.findDaCandidateAction(paiId);
        return daAction;
    }

    public PlayConfig getPlayConfig() {
        PlayConfig playConfig = playConfigRepository.get();
        return playConfig;
    }

    @Override
    public WaitingPlayerCursorRepository getWaitingPlayerCursorRepository() {
        return waitingPlayerCursorRepository;
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

    protected abstract MoActionProcessor createMoActionProcessor();

    protected abstract MoActionUpdater createMoActionUpdater();

    protected abstract DaActionProcessor createDaActionProcessor();

    protected abstract DaActionUpdater createDaActionUpdater();

    protected abstract ChiActionProcessor createChiActionProcessor();

    protected abstract ChiActionUpdater createChiActionUpdater();

    protected abstract PengActionProcessor createPengActionProcessor();

    protected abstract PengActionUpdater createPengActionUpdater();

    protected abstract GangActionProcessor createGangActionProcessor();

    protected abstract GangActionUpdater createGangActionUpdater();

    protected abstract HuActionProcessor createHuActionProcessor();

    protected abstract HuActionUpdater createHuActionUpdater();

    protected abstract GuoActionProcessor createGuoActionProcessor();

    protected abstract GuoActionUpdater createGuoActionUpdater();

    protected abstract PanStartActionUpdater createStartActionUpdater();

    protected abstract void faPai(long panId);

    protected abstract void setZhuangPlayer(long panId);

    protected abstract void setPlayerMenFeng(long panId);

    protected abstract void fillAvaliablePai(long panId);

    protected abstract void setPanSpecialRules(long panId);

    public abstract List<String[]> getPanSpecialRulesStateView();
}
