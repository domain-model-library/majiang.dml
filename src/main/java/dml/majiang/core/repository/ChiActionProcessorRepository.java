package dml.majiang.core.repository;

import dml.common.repository.CommonRepository;
import dml.majiang.core.entity.action.chi.ChiActionProcessor;

public interface ChiActionProcessorRepository<E extends ChiActionProcessor> extends CommonRepository<E, Long> {
}