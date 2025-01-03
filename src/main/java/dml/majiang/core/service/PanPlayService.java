package dml.majiang.core.service;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.PanPlayerAction;
import dml.majiang.core.entity.action.chi.ChiAction;
import dml.majiang.core.entity.action.chi.ChiActionProcessor;
import dml.majiang.core.entity.action.chi.ChiActionUpdater;
import dml.majiang.core.entity.action.da.DaAction;
import dml.majiang.core.entity.action.da.DaActionProcessor;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.gang.GangAction;
import dml.majiang.core.entity.action.gang.GangActionProcessor;
import dml.majiang.core.entity.action.gang.GangActionUpdater;
import dml.majiang.core.entity.action.guo.GuoAction;
import dml.majiang.core.entity.action.guo.GuoActionProcessor;
import dml.majiang.core.entity.action.guo.GuoActionUpdater;
import dml.majiang.core.entity.action.hu.HuAction;
import dml.majiang.core.entity.action.hu.HuActionProcessor;
import dml.majiang.core.entity.action.hu.HuActionUpdater;
import dml.majiang.core.entity.action.mo.MoAction;
import dml.majiang.core.entity.action.mo.MoActionProcessor;
import dml.majiang.core.entity.action.mo.MoActionUpdater;
import dml.majiang.core.entity.action.peng.PengAction;
import dml.majiang.core.entity.action.peng.PengActionProcessor;
import dml.majiang.core.entity.action.peng.PengActionUpdater;
import dml.majiang.core.entity.action.start.PanStartActionUpdater;
import dml.majiang.core.repository.*;
import dml.majiang.core.service.repositoryset.PanPlayServiceRepositorySet;

import java.util.List;

/**
 * 盘局服务
 */
public class PanPlayService {
    public static Pan createPan(long panId,
                                MoActionProcessor moActionProcessor,
                                MoActionUpdater moActionUpdater,
                                DaActionProcessor daActionProcessor,
                                DaActionUpdater daActionUpdater,
                                ChiActionProcessor chiActionProcessor,
                                ChiActionUpdater chiActionUpdater,
                                PengActionProcessor pengActionProcessor,
                                PengActionUpdater pengActionUpdater,
                                GangActionProcessor gangActionProcessor,
                                GangActionUpdater gangActionUpdater,
                                HuActionProcessor huActionProcessor,
                                HuActionUpdater huActionUpdater,
                                GuoActionProcessor guoActionProcessor,
                                GuoActionUpdater guoActionUpdater,
                                PanPlayServiceRepositorySet panPlayServiceRepositorySet) {
        PanRepository panRepository = panPlayServiceRepositorySet.getPanRepository();
        MoActionProcessorRepository<MoActionProcessor> moActionProcessorRepository = panPlayServiceRepositorySet.getMoActionProcessorRepository();
        MoActionUpdaterRepository<MoActionUpdater> moActionUpdaterRepository = panPlayServiceRepositorySet.getMoActionUpdaterRepository();
        DaActionProcessorRepository<DaActionProcessor> daActionProcessorRepository = panPlayServiceRepositorySet.getDaActionProcessorRepository();
        DaActionUpdaterRepository<DaActionUpdater> daActionUpdaterRepository = panPlayServiceRepositorySet.getDaActionUpdaterRepository();
        ChiActionProcessorRepository<ChiActionProcessor> chiActionProcessorRepository = panPlayServiceRepositorySet.getChiActionProcessorRepository();
        ChiActionUpdaterRepository<ChiActionUpdater> chiActionUpdaterRepository = panPlayServiceRepositorySet.getChiActionUpdaterRepository();
        PengActionProcessorRepository<PengActionProcessor> pengActionProcessorRepository = panPlayServiceRepositorySet.getPengActionProcessorRepository();
        PengActionUpdaterRepository<PengActionUpdater> pengActionUpdaterRepository = panPlayServiceRepositorySet.getPengActionUpdaterRepository();
        GangActionProcessorRepository<GangActionProcessor> gangActionProcessorRepository = panPlayServiceRepositorySet.getGangActionProcessorRepository();
        GangActionUpdaterRepository<GangActionUpdater> gangActionUpdaterRepository = panPlayServiceRepositorySet.getGangActionUpdaterRepository();
        HuActionProcessorRepository<HuActionProcessor> huActionProcessorRepository = panPlayServiceRepositorySet.getHuActionProcessorRepository();
        HuActionUpdaterRepository<HuActionUpdater> huActionUpdaterRepository = panPlayServiceRepositorySet.getHuActionUpdaterRepository();
        GuoActionProcessorRepository<GuoActionProcessor> guoActionProcessorRepository = panPlayServiceRepositorySet.getGuoActionProcessorRepository();
        GuoActionUpdaterRepository<GuoActionUpdater> guoActionUpdaterRepository = panPlayServiceRepositorySet.getGuoActionUpdaterRepository();
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = panPlayServiceRepositorySet.getPanSpecialRulesStateRepository();

        Pan newPan = new Pan();
        newPan.setId(panId);
        panRepository.put(newPan);
        moActionProcessor.setPanId(newPan.getId());
        moActionProcessorRepository.put(moActionProcessor);
        moActionUpdater.setPanId(newPan.getId());
        moActionUpdaterRepository.put(moActionUpdater);
        daActionProcessor.setPanId(newPan.getId());
        daActionProcessorRepository.put(daActionProcessor);
        daActionUpdater.setPanId(newPan.getId());
        daActionUpdaterRepository.put(daActionUpdater);
        chiActionProcessor.setPanId(newPan.getId());
        chiActionProcessorRepository.put(chiActionProcessor);
        chiActionUpdater.setPanId(newPan.getId());
        chiActionUpdaterRepository.put(chiActionUpdater);
        pengActionProcessor.setPanId(newPan.getId());
        pengActionProcessorRepository.put(pengActionProcessor);
        pengActionUpdater.setPanId(newPan.getId());
        pengActionUpdaterRepository.put(pengActionUpdater);
        gangActionProcessor.setPanId(newPan.getId());
        gangActionProcessorRepository.put(gangActionProcessor);
        gangActionUpdater.setPanId(newPan.getId());
        gangActionUpdaterRepository.put(gangActionUpdater);
        huActionProcessor.setPanId(newPan.getId());
        huActionProcessorRepository.put(huActionProcessor);
        huActionUpdater.setPanId(newPan.getId());
        huActionUpdaterRepository.put(huActionUpdater);
        guoActionProcessor.setPanId(newPan.getId());
        guoActionProcessorRepository.put(guoActionProcessor);
        guoActionUpdater.setPanId(newPan.getId());
        guoActionUpdaterRepository.put(guoActionUpdater);
        PanSpecialRulesState panSpecialRulesState = new PanSpecialRulesState();
        panSpecialRulesState.setPanId(newPan.getId());
        panSpecialRulesStateRepository.put(panSpecialRulesState);
        return newPan;
    }

    public static void addPlayers(long panId, List<String> playerIds, PanPlayServiceRepositorySet panPlayServiceRepositorySet) {
        PanRepository panRepository = panPlayServiceRepositorySet.getPanRepository();
        Pan pan = panRepository.take(panId);
        for (String playerId : playerIds) {
            pan.addPlayer(playerId);
        }
    }

    public static void sortPlayerShoupai(long panId, PanPlayServiceRepositorySet panPlayServiceRepositorySet) {
        PanRepository panRepository = panPlayServiceRepositorySet.getPanRepository();

        Pan pan = panRepository.take(panId);
        for (PanPlayer player : pan.allPlayers()) {
            List<MajiangPai> fangruShoupaiList = player.getFangruShoupaiList();
            fangruShoupaiList.sort((pai1, pai2) -> pai1.compareTo(pai2));
        }
    }

    public static void start(long panId, PanStartActionUpdater panStartActionUpdater,
                             PanPlayServiceRepositorySet panPlayServiceRepositorySet) {
        PanRepository panRepository = panPlayServiceRepositorySet.getPanRepository();

        Pan pan = panRepository.take(panId);
        panStartActionUpdater.process(pan);
    }

    public static PanPlayerAction action(long panId, String playerId, int actionId,
                                         PanPlayServiceRepositorySet panPlayServiceRepositorySet) {
        PanRepository panRepository = panPlayServiceRepositorySet.getPanRepository();
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = panPlayServiceRepositorySet.getPanSpecialRulesStateRepository();
        MoActionProcessorRepository<MoActionProcessor> moActionProcessorRepository = panPlayServiceRepositorySet.getMoActionProcessorRepository();
        MoActionUpdaterRepository<MoActionUpdater> moActionUpdaterRepository = panPlayServiceRepositorySet.getMoActionUpdaterRepository();
        DaActionProcessorRepository<DaActionProcessor> daActionProcessorRepository = panPlayServiceRepositorySet.getDaActionProcessorRepository();
        DaActionUpdaterRepository<DaActionUpdater> daActionUpdaterRepository = panPlayServiceRepositorySet.getDaActionUpdaterRepository();
        ChiActionProcessorRepository<ChiActionProcessor> chiActionProcessorRepository = panPlayServiceRepositorySet.getChiActionProcessorRepository();
        ChiActionUpdaterRepository<ChiActionUpdater> chiActionUpdaterRepository = panPlayServiceRepositorySet.getChiActionUpdaterRepository();
        PengActionProcessorRepository<PengActionProcessor> pengActionProcessorRepository = panPlayServiceRepositorySet.getPengActionProcessorRepository();
        PengActionUpdaterRepository<PengActionUpdater> pengActionUpdaterRepository = panPlayServiceRepositorySet.getPengActionUpdaterRepository();
        GangActionProcessorRepository<GangActionProcessor> gangActionProcessorRepository = panPlayServiceRepositorySet.getGangActionProcessorRepository();
        GangActionUpdaterRepository<GangActionUpdater> gangActionUpdaterRepository = panPlayServiceRepositorySet.getGangActionUpdaterRepository();
        HuActionProcessorRepository<HuActionProcessor> huActionProcessorRepository = panPlayServiceRepositorySet.getHuActionProcessorRepository();
        HuActionUpdaterRepository<HuActionUpdater> huActionUpdaterRepository = panPlayServiceRepositorySet.getHuActionUpdaterRepository();
        GuoActionProcessorRepository<GuoActionProcessor> guoActionProcessorRepository = panPlayServiceRepositorySet.getGuoActionProcessorRepository();
        GuoActionUpdaterRepository<GuoActionUpdater> guoActionUpdaterRepository = panPlayServiceRepositorySet.getGuoActionUpdaterRepository();

        Pan pan = panRepository.take(panId);
        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.find(panId);
        PanPlayerAction action = pan.getPlayerAction(playerId, actionId);
        if (action instanceof MoAction) {
            MoAction moAction = (MoAction) action;
            MoActionProcessor moActionProcessor = moActionProcessorRepository.find(panId);
            moActionProcessor.process(moAction, pan, panSpecialRulesState);
            MoActionUpdater moActionUpdater = moActionUpdaterRepository.find(panId);
            moActionUpdater.updateActions(moAction, pan, panSpecialRulesState);
        } else if (action instanceof DaAction) {
            DaAction daAction = (DaAction) action;
            DaActionProcessor daActionProcessor = daActionProcessorRepository.find(panId);
            daActionProcessor.process(daAction, pan, panSpecialRulesState);
            DaActionUpdater daActionUpdater = daActionUpdaterRepository.find(panId);
            daActionUpdater.updateActions(daAction, pan, panSpecialRulesState);
        } else if (action instanceof ChiAction) {
            ChiAction chiAction = (ChiAction) action;
            ChiActionProcessor chiActionProcessor = chiActionProcessorRepository.find(panId);
            chiActionProcessor.process(chiAction, pan, panSpecialRulesState);
            ChiActionUpdater chiActionUpdater = chiActionUpdaterRepository.find(panId);
            chiActionUpdater.updateActions(chiAction, pan, panSpecialRulesState);
        } else if (action instanceof PengAction) {
            PengAction pengAction = (PengAction) action;
            PengActionProcessor pengActionProcessor = pengActionProcessorRepository.find(panId);
            pengActionProcessor.process(pengAction, pan, panSpecialRulesState);
            PengActionUpdater pengActionUpdater = pengActionUpdaterRepository.find(panId);
            pengActionUpdater.updateActions(pengAction, pan, panSpecialRulesState);
        } else if (action instanceof GangAction) {
            GangAction gangAction = (GangAction) action;
            GangActionProcessor gangActionProcessor = gangActionProcessorRepository.find(panId);
            gangActionProcessor.process(gangAction, pan, panSpecialRulesState);
            GangActionUpdater gangActionUpdater = gangActionUpdaterRepository.find(panId);
            gangActionUpdater.updateActions(gangAction, pan, panSpecialRulesState);
        } else if (action instanceof HuAction) {
            HuAction huAction = (HuAction) action;
            HuActionProcessor huActionProcessor = huActionProcessorRepository.find(panId);
            huActionProcessor.process(huAction, pan, panSpecialRulesState);
            HuActionUpdater huActionUpdater = huActionUpdaterRepository.find(panId);
            huActionUpdater.updateActions(huAction, pan, panSpecialRulesState);
        } else if (action instanceof GuoAction) {
            GuoAction guoAction = (GuoAction) action;
            GuoActionProcessor guoActionProcessor = guoActionProcessorRepository.find(panId);
            guoActionProcessor.process(guoAction, pan, panSpecialRulesState);
            GuoActionUpdater guoActionUpdater = guoActionUpdaterRepository.find(panId);
            guoActionUpdater.updateActions(guoAction, pan, panSpecialRulesState);
        }
        return action;
    }

}
