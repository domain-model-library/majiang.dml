package dml.majiang.specialrules.genfeng.service;

import dml.majiang.core.entity.MajiangPai;
import dml.majiang.core.entity.PanSpecialRulesState;
import dml.majiang.core.repository.PanSpecialRulesStateRepository;
import dml.majiang.specialrules.genfeng.entity.GenfengState;
import dml.majiang.specialrules.genfeng.service.repositoryset.GenfengServiceRepositorySet;

import java.util.Set;

public class GenfengService {
    public static void setGenfengPaiSet(long panId, Set<MajiangPai> genfengPaiSet, GenfengServiceRepositorySet repositorySet) {
        PanSpecialRulesStateRepository panSpecialRulesStateRepository = repositorySet.getPanSpecialRulesStateRepository();
        PanSpecialRulesState panSpecialRulesState = panSpecialRulesStateRepository.take(panId);
        GenfengState genfengState = new GenfengState();
        genfengState.setGenfengPaiSet(genfengPaiSet);
        panSpecialRulesState.addSpecialRuleState(genfengState);
    }
}
