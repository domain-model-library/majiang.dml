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

    public MenFeng xiajia() {
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

    public MenFeng shangjia() {
        if (this.equals(dong)) {
            return bei;
        } else if (this.equals(nan)) {
            return dong;
        } else if (this.equals(xi)) {
            return nan;
        } else {
            return xi;
        }
    }

    public MenFeng duijia() {
        if (this.equals(dong)) {
            return xi;
        } else if (this.equals(nan)) {
            return bei;
        } else if (this.equals(xi)) {
            return dong;
        } else {
            return nan;
        }
    }
}