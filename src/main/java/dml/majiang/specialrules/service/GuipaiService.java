package dml.majiang.specialrules.service;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.Pan;
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
}
