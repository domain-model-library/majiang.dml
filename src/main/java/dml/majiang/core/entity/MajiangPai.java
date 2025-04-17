package dml.majiang.core.entity;

public enum MajiangPai {
    yiwan, erwan, sanwan, siwan, wuwan, liuwan, qiwan, bawan, jiuwan, yitong, ertong, santong, sitong, wutong, liutong, qitong, batong, jiutong, yitiao, ertiao, santiao, sitiao, wutiao, liutiao, qitiao, batiao, jiutiao, dongfeng, nanfeng, xifeng, beifeng, hongzhong, facai, baiban, chun, xia, qiu, dong, mei, lan, zhu, ju;
    private static MajiangPai[] array = MajiangPai.values();
    public static final int count = MajiangPai.values().length;

    public static MajiangPai valueOf(int ordinal) {
        return array[ordinal];
    }

    public static boolean isXushupai(MajiangPai pai) {
        int ordinal = pai.ordinal();
        return (ordinal >= 0 && ordinal <= 26);
    }

    public static boolean isZipai(MajiangPai pai) {
        int ordinal = pai.ordinal();
        return (ordinal >= 27 && ordinal <= 33);
    }

    public static boolean isFengpai(MajiangPai pai) {
        int ordinal = pai.ordinal();
        return (ordinal >= 27 && ordinal <= 30);
    }

    public static MajiangPai[] xushupaiAndZipaiArray() {
        MajiangPai[] xushupaiAndZipaiArray = new MajiangPai[34];
        System.arraycopy(array, 0, xushupaiAndZipaiArray, 0, 34);
        return xushupaiAndZipaiArray;
    }

    public static MajiangPai[] xushupaiArray() {
        MajiangPai[] xushupaiArray = new MajiangPai[27];
        System.arraycopy(array, 0, xushupaiArray, 0, 27);
        return xushupaiArray;
    }

    public static MajiangPai[] fengpaiArray() {
        MajiangPai[] fengpaiArray = new MajiangPai[4];
        System.arraycopy(array, 27, fengpaiArray, 0, 4);
        return fengpaiArray;
    }

    public static MajiangPai next(MajiangPai pai) {
        int ordinal = pai.ordinal();
        if (ordinal == 33) {
            return null;
        }
        return array[ordinal + 1];
    }

    public static MajiangPai previous(MajiangPai pai) {
        int ordinal = pai.ordinal();
        if (ordinal == 0) {
            return null;
        }
        return array[ordinal - 1];
    }

    public static MajiangPai nextXushupai(MajiangPai pai) {
        int ordinal = pai.ordinal();
        if (ordinal == 26) {
            return null;
        }
        return array[ordinal + 1];
    }

    public static MajiangPai previousXushupai(MajiangPai pai) {
        int ordinal = pai.ordinal();
        if (ordinal == 0) {
            return null;
        }
        return array[ordinal - 1];
    }

    public static MajiangPai nextTongzuXushupai(MajiangPai pai) {
        if (!isXushupai(pai)) {
            return null;
        }
        int ordinal = pai.ordinal();
        int zuMinOrdinal = (ordinal / 9) * 9;
        int zuMaxOrdinal = zuMinOrdinal + 8;
        if (ordinal == zuMaxOrdinal) {
            return null;
        }
        int nextOrdinal = ordinal + 1;
        return array[nextOrdinal];
    }

    public static MajiangPai previousTongzuXushupai(MajiangPai pai) {
        if (!isXushupai(pai)) {
            return null;
        }
        int ordinal = pai.ordinal();
        int zuMinOrdinal = (ordinal / 9) * 9;
        if (ordinal == zuMinOrdinal) {
            return null;
        }
        int previousOrdinal = ordinal - 1;
        return array[previousOrdinal];
    }
}
