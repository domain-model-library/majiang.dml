package dml.majiang.core.entity;

public enum MenFeng {
    dong, nan, xi, bei;

    public MenFeng next() {
        if (this.equals(dong)) {
            return nan;
        } else if (this.equals(nan)) {
            return xi;
        } else if (this.equals(xi)) {
            return bei;
        } else {
            return dong;
        }
    }
}