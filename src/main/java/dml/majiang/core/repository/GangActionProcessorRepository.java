package dml.majiang.core.repository;

import dml.common.repository.CommonRepository;
import dml.majiang.core.entity.action.gang.GangActionProcessor;

public interface GangActionProcessorRepository<E extends GangActionProcessor> extends CommonRepository<E, Long> {
}
