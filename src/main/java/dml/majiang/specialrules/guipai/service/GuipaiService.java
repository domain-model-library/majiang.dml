package dml.majiang.specialrules.guipai.service;

import dml.majiang.core.entity.*;
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
    public static void determineGuipai(long panId, GuipaiServiceRepositorySet guipaiServiceRepositorySet) {
        PanRepository panRepository = guipaiServiceRepositorySet.getPanRepository();
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = guipaiServiceRepositorySet.getPanSpecialRulesStateRepository();

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
     * 鬼牌放最左边
     *
     * @param panId
     * @param guipaiServiceRepositorySet
     */
    public static List<Pai> sortPlayerShoupai(long panId, String playerId, GuipaiServiceRepositorySet guipaiServiceRepositorySet) {
        PanRepository panRepository = guipaiServiceRepositorySet.getPanRepository();
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = guipaiServiceRepositorySet.getPanSpecialRulesStateRepository();

        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.find(panId);
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        MajiangPai guipaiType = guipaiState.getGuipaiType();
        Pan pan = panRepository.take(panId);
        PanPlayer player = pan.findPlayerById(playerId);
        List<Pai> fangruShoupaiList = player.getFangruShoupaiList();
        fangruShoupaiList.sort((pai1, pai2) -> {
            MajiangPai pai1Type = pai1.getPaiType();
            MajiangPai pai2Type = pai2.getPaiType();
            if (pai1Type.equals(guipaiType) && !pai2Type.equals(guipaiType)) {
                return -1;
            } else if (!pai1Type.equals(guipaiType) && pai2Type.equals(guipaiType)) {
                return 1;
            } else if (pai1Type.equals(guipaiType) && pai2Type.equals(guipaiType)) {
                return 0;
            } else {
                return pai1Type.compareTo(pai2Type);
            }
        });
        return fangruShoupaiList;
    }

    public static void setActGuipaiBenpaiPai(long panId, MajiangPai actGuipaiBenpaiPai,
                                             GuipaiServiceRepositorySet guipaiServiceRepositorySet) {
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = guipaiServiceRepositorySet.getPanSpecialRulesStateRepository();

        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.take(panId);
        ActGuipaiBenpaiState actGuipaiBenpaiState = new ActGuipaiBenpaiState();
        actGuipaiBenpaiState.setActGuipaiBenpaiPaiType(actGuipaiBenpaiPai);
        panSpecialRulesState.addSpecialRuleState(actGuipaiBenpaiState);
    }

    /**
     * 鬼牌放最左边，代替鬼牌本牌的牌要在鬼牌本牌的位置
     *
     * @param panId
     * @param guipaiServiceRepositorySet
     */
    public static void sortPlayerShoupaiWithActGuipaiBenpaiPai(long panId, String playerId, GuipaiServiceRepositorySet guipaiServiceRepositorySet) {
        PanRepository panRepository = guipaiServiceRepositorySet.getPanRepository();
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = guipaiServiceRepositorySet.getPanSpecialRulesStateRepository();

        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.find(panId);
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        ActGuipaiBenpaiState actGuipaiBenpaiState = panSpecialRulesState.findSpecialRuleState(ActGuipaiBenpaiState.class);
        MajiangPai guipaiType = guipaiState.getGuipaiType();
        MajiangPai actGuipaiBenpaiPai = actGuipaiBenpaiState.getActGuipaiBenpaiPaiType();
        Pan pan = panRepository.take(panId);
        PanPlayer player = pan.findPlayerById(playerId);
        List<Pai> fangruShoupaiList = player.getFangruShoupaiList();
        fangruShoupaiList.sort((pai1, pai2) -> {
            MajiangPai pai1Type = pai1.getPaiType();
            MajiangPai pai2Type = pai2.getPaiType();
            if (pai1Type.equals(guipaiType) && !pai2Type.equals(guipaiType)) {
                return -1;
            } else if (!pai1Type.equals(guipaiType) && pai2Type.equals(guipaiType)) {
                return 1;
            } else if (pai1Type.equals(guipaiType) && pai2Type.equals(guipaiType)) {
                return 0;
            } else {
                if (pai1Type.equals(actGuipaiBenpaiPai)) {
                    pai1Type = guipaiType;
                }
                if (pai2Type.equals(actGuipaiBenpaiPai)) {
                    pai2Type = guipaiType;
                }
                return pai1Type.compareTo(pai2Type);
            }
        });

    }

}
