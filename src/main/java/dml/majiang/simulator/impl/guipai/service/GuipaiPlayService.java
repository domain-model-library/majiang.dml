package dml.majiang.simulator.impl.guipai.service;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.entity.action.chi.ChiActionProcessor;
import dml.majiang.core.entity.action.chi.ChiActionUpdater;
import dml.majiang.core.entity.action.chi.PengganghuFirstChiActionProcessor;
import dml.majiang.core.entity.action.da.DaActionProcessor;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.da.DachushoupaiDaActionProcessor;
import dml.majiang.core.entity.action.gang.GangActionProcessor;
import dml.majiang.core.entity.action.gang.GangActionUpdater;
import dml.majiang.core.entity.action.gang.HuFirstGangActionProcessor;
import dml.majiang.core.entity.action.guo.DoNothingGuoActionProcessor;
import dml.majiang.core.entity.action.guo.GuoActionProcessor;
import dml.majiang.core.entity.action.guo.GuoActionUpdater;
import dml.majiang.core.entity.action.hu.ClearAllActionHuActionUpdater;
import dml.majiang.core.entity.action.hu.HuActionProcessor;
import dml.majiang.core.entity.action.hu.HuActionUpdater;
import dml.majiang.core.entity.action.hu.PlayerSetHuHuActionProcessor;
import dml.majiang.core.entity.action.mo.MoActionProcessor;
import dml.majiang.core.entity.action.mo.MoActionUpdater;
import dml.majiang.core.entity.action.mo.PlayerMoPaiMoActionProcessor;
import dml.majiang.core.entity.action.peng.HuFirstPengActionProcessor;
import dml.majiang.core.entity.action.peng.PengActionProcessor;
import dml.majiang.core.entity.action.peng.PengActionUpdater;
import dml.majiang.core.entity.action.start.PanStartActionUpdater;
import dml.majiang.core.entity.action.start.ZhuangMoStartActionUpdater;
import dml.majiang.core.service.AvaliablePaiService;
import dml.majiang.core.service.FaPaiService;
import dml.majiang.core.service.MenFengService;
import dml.majiang.core.service.ZhuangService;
import dml.majiang.simulator.base.service.PlayService;
import dml.majiang.simulator.impl.guipai.entity.ActGuipaiBenpaiDaActionUpdaterImpl;
import dml.majiang.simulator.impl.guipai.entity.ActGuipaiBenpaiMoActionUpdaterImpl;
import dml.majiang.simulator.impl.guipai.entity.ActGuipaiBenpaiQiangganghuGangActionUpdaterImpl;
import dml.majiang.specialrules.guipai.entity.*;
import dml.majiang.specialrules.guipai.service.GuipaiService;
import dml.majiang.specialrules.guipai.service.repositoryset.GuipaiServiceRepositorySet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dml.majiang.core.entity.MajiangPai.baiban;

public class GuipaiPlayService extends PlayService implements GuipaiServiceRepositorySet {
    @Override
    protected MoActionProcessor createMoActionProcessor() {
        return new PlayerMoPaiMoActionProcessor();
    }

    @Override
    protected MoActionUpdater createMoActionUpdater() {
        return new ActGuipaiBenpaiMoActionUpdaterImpl();
    }

    @Override
    protected DaActionProcessor createDaActionProcessor() {
        return new DachushoupaiDaActionProcessor();
    }

    @Override
    protected DaActionUpdater createDaActionUpdater() {
        return new ActGuipaiBenpaiDaActionUpdaterImpl();
    }

    @Override
    protected ChiActionProcessor createChiActionProcessor() {
        return new PengganghuFirstChiActionProcessor();
    }

    @Override
    protected ChiActionUpdater createChiActionUpdater() {
        return new GuipaiChiActionUpdater();
    }

    @Override
    protected PengActionProcessor createPengActionProcessor() {
        return new HuFirstPengActionProcessor();
    }

    @Override
    protected PengActionUpdater createPengActionUpdater() {
        return new GuipaiPengActionUpdater();
    }

    @Override
    protected GangActionProcessor createGangActionProcessor() {
        return new HuFirstGangActionProcessor();
    }

    @Override
    protected GangActionUpdater createGangActionUpdater() {
        return new ActGuipaiBenpaiQiangganghuGangActionUpdaterImpl();
    }

    @Override
    protected HuActionProcessor createHuActionProcessor() {
        return new PlayerSetHuHuActionProcessor();
    }

    @Override
    protected HuActionUpdater createHuActionUpdater() {
        return new ClearAllActionHuActionUpdater();
    }

    @Override
    protected GuoActionProcessor createGuoActionProcessor() {
        return new DoNothingGuoActionProcessor();
    }

    @Override
    protected GuoActionUpdater createGuoActionUpdater() {
        return new GuipaiGuoActionUpdater();
    }

    @Override
    protected PanStartActionUpdater createStartActionUpdater() {
        return new ZhuangMoStartActionUpdater();
    }

    @Override
    protected void faPai(long panId) {
        FaPaiService.faPai16Shoupai(panId, this);
    }

    @Override
    protected void setZhuangPlayer(long panId) {
        //设置门风为东的玩家为庄家
        ZhuangService.setZhuangToDongMenFengPlayer(panId, this);
    }

    @Override
    protected void setPlayerMenFeng(long panId) {
        //随机设置玩家门风
        MenFengService.setPlayerMenFengRandomly(panId, this);
    }

    @Override
    protected void fillAvaliablePai(long panId) {
        //决定哪些可用牌，注入麻将牌
        AvaliablePaiService.fillAvaliablePaiWithNoHuapai(panId, this);
    }

    @Override
    protected void setPanSpecialRules(long panId) {
        //决定财神
        GuipaiService.determineGuipai(panId, this);
        GuipaiService.setActGuipaiBenpaiPai(panId, baiban, this);
        MajiangPai[] xushupaiArray = MajiangPai.xushupaiArray();
        MajiangPai[] fengpaiArray = MajiangPai.fengpaiArray();
        MajiangPai[] paiTypesForGuipaiAct;

        paiTypesForGuipaiAct = new MajiangPai[xushupaiArray.length + fengpaiArray.length + 2];
        System.arraycopy(xushupaiArray, 0, paiTypesForGuipaiAct, 0, xushupaiArray.length);
        System.arraycopy(fengpaiArray, 0, paiTypesForGuipaiAct, xushupaiArray.length, fengpaiArray.length);
        paiTypesForGuipaiAct[31] = MajiangPai.hongzhong;
        paiTypesForGuipaiAct[32] = MajiangPai.facai;
        GuipaiService.setGuipaiActPaiTypes(panId, new ArrayList<>(Arrays.asList(paiTypesForGuipaiAct)), this);
    }

    @Override
    public List<String[]> getPanSpecialRulesStateView() {
        List<String[]> panSpecialRulesStateView = new ArrayList<>();
        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.find(1L);
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        ActGuipaiBenpaiState actGuipaiBenpaiState = panSpecialRulesState.findSpecialRuleState(ActGuipaiBenpaiState.class);
        panSpecialRulesStateView.add(new String[]{"guipai", guipaiState.getGuipaiType().name()});
        panSpecialRulesStateView.add(new String[]{"actGuipaiBenpai", actGuipaiBenpaiState.getActGuipaiBenpaiPaiType().name()});
        return panSpecialRulesStateView;
    }
}
