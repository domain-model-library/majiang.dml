package dml.majiang.simulator.impl.biaozhun.service;

import dml.majiang.core.entity.action.chi.ChiActionProcessor;
import dml.majiang.core.entity.action.chi.ChiActionUpdater;
import dml.majiang.core.entity.action.chi.ChiPlayerDaPaiChiActionUpdater;
import dml.majiang.core.entity.action.chi.PengganghuFirstChiActionProcessor;
import dml.majiang.core.entity.action.da.DaActionProcessor;
import dml.majiang.core.entity.action.da.DaActionUpdater;
import dml.majiang.core.entity.action.da.DachushoupaiDaActionProcessor;
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
import dml.majiang.core.entity.action.hu.PlayerSetHuHuActionProcessor;
import dml.majiang.core.entity.action.mo.MoActionProcessor;
import dml.majiang.core.entity.action.mo.MoActionUpdater;
import dml.majiang.core.entity.action.mo.PlayerMoPaiMoActionProcessor;
import dml.majiang.core.entity.action.peng.HuFirstPengActionProcessor;
import dml.majiang.core.entity.action.peng.KezigangshoupaiPengActionUpdater;
import dml.majiang.core.entity.action.peng.PengActionProcessor;
import dml.majiang.core.entity.action.peng.PengActionUpdater;
import dml.majiang.core.entity.action.start.PanStartActionUpdater;
import dml.majiang.core.entity.action.start.ZhuangMoStartActionUpdater;
import dml.majiang.core.service.*;
import dml.majiang.simulator.base.service.PlayService;
import dml.majiang.simulator.impl.biaozhun.entity.BiaozhunDaActionUpdater;
import dml.majiang.simulator.impl.biaozhun.entity.BiaozhunGangHuDaMoActionUpdater;

import java.util.ArrayList;
import java.util.List;

public class BiaozhunPlayService extends PlayService {

    @Override
    protected void addPlayers(long panId) {
        List<String> playerIdList = new ArrayList<>();
        playerIdList.add("1");
        playerIdList.add("2");
        playerIdList.add("3");
        playerIdList.add("4");
        PanPlayService.addPlayers(panId, playerIdList, this);
    }

    @Override
    protected MoActionProcessor createMoActionProcessor() {
        return new PlayerMoPaiMoActionProcessor();
    }

    @Override
    protected MoActionUpdater createMoActionUpdater() {
        return new BiaozhunGangHuDaMoActionUpdater();
    }

    @Override
    protected DaActionProcessor createDaActionProcessor() {
        return new DachushoupaiDaActionProcessor();
    }

    @Override
    protected DaActionUpdater createDaActionUpdater() {
        return new BiaozhunDaActionUpdater();
    }

    @Override
    protected ChiActionProcessor createChiActionProcessor() {
        return new PengganghuFirstChiActionProcessor();
    }

    @Override
    protected ChiActionUpdater createChiActionUpdater() {
        return new ChiPlayerDaPaiChiActionUpdater();
    }

    @Override
    protected PengActionProcessor createPengActionProcessor() {
        return new HuFirstPengActionProcessor();
    }

    @Override
    protected PengActionUpdater createPengActionUpdater() {
        return new KezigangshoupaiPengActionUpdater();
    }

    @Override
    protected GangActionProcessor createGangActionProcessor() {
        return new HuFirstGangActionProcessor();
    }

    @Override
    protected GangActionUpdater createGangActionUpdater() {
        return new GangPlayerMoPaiGangActionUpdater();
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
        return new PlayerDaPaiOrXiajiaMoPaiGuoActionUpdater();
    }

    @Override
    protected PanStartActionUpdater createStartActionUpdater() {
        return new ZhuangMoStartActionUpdater();
    }

    @Override
    protected void faPai(long panId) {
        FaPaiService.faPai13Shoupai(panId, this);
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
    }

    @Override
    public List<String[]> getPanSpecialRulesStateView() {
        return null;
    }

}
