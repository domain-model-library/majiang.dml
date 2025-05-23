package dml.majiang.core.entity.cursor;

/**
 * 牌游标，用于保存定位牌需要的信息。由于牌没有id,所以定位牌需要通过一些信息的计算来实现。
 *
 * @author Neo
 */
public interface PaiCursor {
    PaiCursor copy();
}
