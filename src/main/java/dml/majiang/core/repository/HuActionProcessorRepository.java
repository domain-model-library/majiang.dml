package dml.majiang.core.repository;

import dml.common.repository.CommonRepository;
import dml.majiang.core.entity.action.hu.HuActionProcessor;

public interface HuActionProcessorRepository<E extends HuActionProcessor> extends CommonRepository<E, Long> {
}
