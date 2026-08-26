package com.realisticntm.radiation;

public final class BqFormat {

    private static final String[] UNITS = { "Bq", "kBq", "MBq", "GBq", "TBq", "PBq", "EBq", "ZBq", "YBq" };

    private BqFormat() {}

    public static String format(double bq) {
        if (bq <= 0) {
            return "0 Bq";
        }
        int unit = 0;
        while (bq >= 1000.0D && unit < UNITS.length - 1) {
            bq /= 1000.0D;
            unit++;
        }
        return com.hbm.lib.Library.roundFloat((float) bq, 3) + " " + UNITS[unit];
    }
}
