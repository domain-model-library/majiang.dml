package dml.majiang.specialrules.service.repositoryset;

import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.repository.PanSpecialRulesStateRepository;

public interface GuipaiServiceRepositorySet {
    public PanRepository getPanRepository();

    public PanSpecialRulesStateRepository getPanSpecialRulesStateRepository();
}
