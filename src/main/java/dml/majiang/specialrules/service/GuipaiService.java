package dml.majiang.specialrules.service;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pan;
import dml.majiang.core.entity.PanPlayer;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.repository.PanSpecialRulesStateRepository;
import dml.majiang.specialrules.entity.GuipaiState;
import dml.majiang.specialrules.service.repositoryset.GuipaiServiceRepositorySet;

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
     * 鬼牌放最左边
     *
     * @param panId
     * @param guipaiServiceRepositorySet
     */
    public static void sortPlayerShoupai(long panId, GuipaiServiceRepositorySet guipaiServiceRepositorySet) {
        PanRepository panRepository = guipaiServiceRepositorySet.getPanRepository();
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = guipaiServiceRepositorySet.getPanSpecialRulesStateRepository();

        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.find(panId);
        GuipaiState guipaiState = panSpecialRulesState.findSpecialRuleState(GuipaiState.class);
        MajiangPai guipaiType = guipaiState.getGuipaiType();
        Pan pan = panRepository.take(panId);
        for (PanPlayer player : pan.allPlayers()) {
            List<MajiangPai> fangruShoupaiList = player.getFangruShoupaiList();
            fangruShoupaiList.sort((pai1, pai2) -> {
                if (pai1.equals(guipaiType) && !pai2.equals(guipaiType)) {
                    return -1;
                } else if (!pai1.equals(guipaiType) && pai2.equals(guipaiType)) {
                    return 1;
                } else if (pai1.equals(guipaiType) && pai2.equals(guipaiType)) {
                    return 0;
                } else {
                    return pai1.compareTo(pai2);
                }
            });
        }

    }

}
