package dml.majiang.core.repository;

import dml.common.repository.CommonRepository;
import dml.majiang.core.entity.action.mo.MoActionProcessor;

public interface MoActionProcessorRepository<E extends MoActionProcessor> extends CommonRepository<E, Long> {
}
