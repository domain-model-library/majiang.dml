package dml.majiang.core.service.repositoryset;

import dml.majiang.core.repository.PanRepository;
import dml.majiang.core.repository.WaitingPlayerCursorRepository;

public interface WaitingPlayerCursorServiceRepositorySet {
    public WaitingPlayerCursorRepository getWaitingPlayerCursorRepository();

    public PanRepository getPanRepository();
}
