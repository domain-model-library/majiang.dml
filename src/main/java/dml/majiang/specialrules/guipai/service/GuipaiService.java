package dml.majiang.specialrules.guipai.service;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.repository.PanSpecialRulesStateRepository;
import dml.majiang.specialrules.guipai.entity.ActGuipaiBenpaiState;
import dml.majiang.specialrules.guipai.entity.GuipaiState;
import dml.majiang.specialrules.guipai.service.repositoryset.GuipaiServiceRepositorySet;

import java.util.List;
import java.util.Random;

public class GuipaiService {
    /**
     * 随机选择鬼牌
     *
     * @param panId
     */
    public static void determineGuipai(long panId, GuipaiServiceRepositorySet repositorySet) {
        PanRepository panRepository = repositorySet.getPanRepository();
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = repositorySet.getPanSpecialRulesStateRepository();

        Pan pan = panRepository.take(panId);
        List<MajiangPai> paiTypeList = pan.getPlayPaiTypeList();
        Random r = new Random(panId);
        r.nextInt(paiTypeList.size());
        MajiangPai guipaiType = paiTypeList.get(r.nextInt(paiTypeList.size()));
        GuipaiState guipaiState = new GuipaiState();
        guipaiState.setGuipaiType(guipaiType);
        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.take(panId);
        panSpecialRulesState.addSpecialRuleState(guipaiState);
        pan.removeAvaliablePai(guipaiType);
    }

    /**
     * 指定鬼牌
     *
     * @param panId
     * @param guipaiServiceRepositorySet
     */
    public static void determineFixedPaiGuipai(long panId, MajiangPai guipai, GuipaiServiceRepositorySet guipaiServiceRepositorySet) {
        PanRepository panRepository = guipaiServiceRepositorySet.getPanRepository();
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = guipaiServiceRepositorySet.getPanSpecialRulesStateRepository();

        GuipaiState guipaiState = new GuipaiState();
        guipaiState.setGuipaiType(guipai);
        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.take(panId);
        panSpecialRulesState.addSpecialRuleState(guipaiState);
        Pan pan = panRepository.take(panId);
        pan.removeAvaliablePai(guipai);
    }

    /**
     * 查询鬼牌
     */
    public static MajiangPai getGuipai(long panId, GuipaiServiceRepositorySet guipaiServiceRepositorySet) {
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = guipaiServiceRepositorySet.getPanSpecialRulesStateRepository();
        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.find(panId);
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        return guipaiState.getGuipaiType();
    }

    /**
     * 设置鬼牌可以替代的牌
     */
    public static void setGuipaiActPaiTypes(long panId, List<MajiangPai> guipaiActPaiTypes,
                                            GuipaiServiceRepositorySet guipaiServiceRepositorySet) {
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = guipaiServiceRepositorySet.getPanSpecialRulesStateRepository();
        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.take(panId);
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        guipaiState.setGuipaiActPaiTypes(guipaiActPaiTypes);
    }

    /**
     * 设置代替鬼牌本牌的牌
     */
    public static void setActGuipaiBenpaiPai(long panId, MajiangPai actGuipaiBenpaiPai,
                                             GuipaiServiceRepositorySet guipaiServiceRepositorySet) {
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = guipaiServiceRepositorySet.getPanSpecialRulesStateRepository();

        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.take(panId);
        ActGuipaiBenpaiState actGuipaiBenpaiState = new ActGuipaiBenpaiState();
        actGuipaiBenpaiState.setActGuipaiBenpaiPaiType(actGuipaiBenpaiPai);
        panSpecialRulesState.addSpecialRuleState(actGuipaiBenpaiState);
    }


}
