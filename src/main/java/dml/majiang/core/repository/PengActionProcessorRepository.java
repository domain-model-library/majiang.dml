package dml.majiang.core.repository;

import dml.common.repository.CommonRepository;
import dml.majiang.core.entity.action.peng.PengActionProcessor;

public interface PengActionProcessorRepository<E extends PengActionProcessor> extends CommonRepository<E, Long> {
}
