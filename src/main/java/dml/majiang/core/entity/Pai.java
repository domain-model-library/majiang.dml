package dml.majiang.core.entity;

/**
 * 一张麻将牌。每张牌有一个唯一id。
 */
public class Pai {
    private int id;
    private MajiangPai paiType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MajiangPai getPaiType() {
        return paiType;
    }

    public void setPaiType(MajiangPai paiType) {
        this.paiType = paiType;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Pai) {
            Pai other = (Pai) obj;
            return this.id == other.id;
        }
        return false;
    }

    public int hashCode() {
        return id;
    }

}
