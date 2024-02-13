package dml.majiang.core.repository;

import dml.common.repository.CommonRepository;
import dml.majiang.core.entity.action.da.DaActionProcessor;

public interface DaActionProcessorRepository<E extends DaActionProcessor> extends CommonRepository<E, Long> {
}
