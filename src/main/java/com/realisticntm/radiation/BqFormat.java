package com.realisticntm.radiation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.realisticntm.config.RadiationConfigRTM;

public final class BqFormat {

    private static final String[] UNITS = { "Bq", "kBq", "MBq", "GBq", "TBq", "PBq", "EBq", "ZBq", "YBq" };

    /**
     * Matches radiation lines added by the Cursed Addon, e.g.
     * "§a -::§cAlpha 1.230 RAD" or "... 4.510e+03 RAD/s".
     * The numeric token is the last number before the trailing unit.
     */
    private static final Pattern CURSED_RAD_LINE = Pattern
            .compile("^(.* -::.*?)([0-9]+(?:\\.[0-9]+)?(?:[eE][+-]?[0-9]+)?)([^0-9]*) (RAD/s|RAD)$");

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

    /**
     * Converts a single Cursed Addon tooltip line from RAD to becquerels.
     * Returns {@code null} if the line is not a Cursed RAD line and must be left untouched.
     */
    public static String rewriteRadLine(String line) {
        Matcher matcher = CURSED_RAD_LINE.matcher(line);
        if (!matcher.matches()) {
            return null;
        }
        double rad;
        try {
            rad = Double.parseDouble(matcher.group(2));
        } catch (NumberFormatException ex) {
            return null;
        }
        String perSecond = matcher.group(4).endsWith("/s") ? "/s" : "";
        return matcher.group(1) + format(rad * RadiationConfigRTM.bqPerRadPerSecond) + perSecond;
    }
}
