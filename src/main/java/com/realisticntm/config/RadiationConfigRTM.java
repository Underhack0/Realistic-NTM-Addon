<<<<<<< HEAD
package com.realisticntm.config;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;

import com.realisticntm.RealisticNTM;

public class RadiationConfigRTM {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_ACTIVITY = "activity_values_bq";

    /**
     * 1 RAD/s corresponds to this many becquerels.
     * Default: 3.7e10 Bq (1 Curie) ~ 1 RAD/s for a gamma source in contact.
     */
    public static double bqPerRadPerSecond = 3.7E10D;

    /** Show item radiation in becquerels instead of RAD (also converts NTM: Cursed Addon tooltips). */
    public static boolean becquerelTooltips = true;

    private static Configuration config;

    private static final Map<String, Double> defaults = new LinkedHashMap<>();
    private static final Map<String, Double> values = new LinkedHashMap<>();

    static {
        double k = 3.7E10D;
        defaults.put("hbm:ingot_uranium", 0.35D * k);
        defaults.put("hbm:nugget_uranium", 0.035D * k);
        defaults.put("hbm:block_uranium", 3.5D * k);
        defaults.put("hbm:ingot_u235", 1.0D * k);
        defaults.put("hbm:ingot_u238", 0.25D * k);
        defaults.put("hbm:ingot_plutonium", 7.5D * k);
        defaults.put("hbm:ingot_th232", 0.1D * k);
        defaults.put("hbm:ingot_ra226", 7.5D * k);
        defaults.put("hbm:ingot_am241", 8.5D * k);
        defaults.put("hbm:powder_yellowcake", 1.05D * k);
        defaults.put("hbm:nuclear_waste", 15.0D * k);
        defaults.put("hbm:billet_nuclear_waste", 7.5D * k);
        defaults.put("hbm:trinitite", 0.1D * k);
        defaults.put("hbm:scrap_nuclear", 1.0D * k);
        defaults.put("hbm:ancient_scrap", 150.0D * k);
        defaults.put("hbm:demon_core_closed", 100000.0D * k);
        defaults.put("hbm:cell_balefire", 50.0D * k);
        defaults.put("hbm:ingot_uranium_fuel", 0.5D * k);
        defaults.put("hbm:billet_uranium_fuel", 0.25D * k);
        defaults.put("hbm:ingot_plutonium_fuel", 4.25D * k);
        defaults.put("hbm:billet_mox_fuel", 1.25D * k);
    }

    public static void init(File suggestedConfigFile) {
        config = new Configuration(suggestedConfigFile, "1.0.0", true);

        bqPerRadPerSecond = config.get(CATEGORY_GENERAL, "bqPerRadPerSecond", 3.7E10D,
                "How many becquerels correspond to 1 RAD/s of the internal radiation system.")
                .setMinValue(1.0D).getDouble();

        becquerelTooltips = config.get(CATEGORY_GENERAL, "becquerelTooltips", true,
                "Display item radiation in becquerels (Bq) instead of RAD.\n" +
                        "Also converts the per-type (alpha/beta/gamma/x-ray/neutrons/radon) radiation\n" +
                        "lines added by NTM: Cursed Addon.")
                .getBoolean();

        String comment = "Activity of radioactive items in becquerels.\n" + "Key formats:\n" +
                "  modid:item_name      - all metadata values\n" +
                "  modid:item_name#meta - a specific metadata value\n" +
                "  oredict:name         - an OreDictionary entry";
        config.getCategory(CATEGORY_ACTIVITY).setComment(comment);

        for (Map.Entry<String, Double> entry : defaults.entrySet()) {
            config.get(CATEGORY_ACTIVITY, entry.getKey(), entry.getValue());
        }

        if (config.hasChanged()) {
            config.save();
        }

        reloadValues();
    }

    private static void reloadValues() {
        values.clear();
        Map<String, net.minecraftforge.common.config.Property> props = config.getCategory(CATEGORY_ACTIVITY)
                .getValues();
        for (Map.Entry<String, net.minecraftforge.common.config.Property> e : props.entrySet()) {
            try {
                double bq = e.getValue().getDouble();
                if (bq > 0) {
                    values.put(e.getKey(), bq);
                }
            } catch (Exception ex) {
                RealisticNTM.LOGGER.warn("Invalid Bq value for key '{}': {}", e.getKey(), e.getValue().getString());
            }
        }
    }

    public static Map<String, Double> getActivityValues() {
        return values;
    }
}
=======
package com.realisticntm.config;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;

import com.realisticntm.RealisticNTM;

public class RadiationConfigRTM {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_ACTIVITY = "activity_values_bq";

    /**
     * 1 RAD/s corresponds to this many becquerels.
     * Default: 3.7e10 Bq (1 Curie) ~ 1 RAD/s for a gamma source in contact.
     */
    public static double bqPerRadPerSecond = 3.7E10D;

    private static Configuration config;

    private static final Map<String, Double> defaults = new LinkedHashMap<>();
    private static final Map<String, Double> values = new LinkedHashMap<>();

    static {
        double k = 3.7E10D;
        defaults.put("hbm:ingot_uranium", 0.35D * k);
        defaults.put("hbm:nugget_uranium", 0.035D * k);
        defaults.put("hbm:block_uranium", 3.5D * k);
        defaults.put("hbm:ingot_u235", 1.0D * k);
        defaults.put("hbm:ingot_u238", 0.25D * k);
        defaults.put("hbm:ingot_plutonium", 7.5D * k);
        defaults.put("hbm:ingot_th232", 0.1D * k);
        defaults.put("hbm:ingot_ra226", 7.5D * k);
        defaults.put("hbm:ingot_am241", 8.5D * k);
        defaults.put("hbm:powder_yellowcake", 1.05D * k);
        defaults.put("hbm:nuclear_waste", 15.0D * k);
        defaults.put("hbm:billet_nuclear_waste", 7.5D * k);
        defaults.put("hbm:trinitite", 0.1D * k);
        defaults.put("hbm:scrap_nuclear", 1.0D * k);
        defaults.put("hbm:ancient_scrap", 150.0D * k);
        defaults.put("hbm:demon_core_closed", 100000.0D * k);
        defaults.put("hbm:cell_balefire", 50.0D * k);
        defaults.put("hbm:ingot_uranium_fuel", 0.5D * k);
        defaults.put("hbm:billet_uranium_fuel", 0.25D * k);
        defaults.put("hbm:ingot_plutonium_fuel", 4.25D * k);
        defaults.put("hbm:billet_mox_fuel", 1.25D * k);
    }

    public static void init(File suggestedConfigFile) {
        config = new Configuration(suggestedConfigFile, "1.0.0", true);

        bqPerRadPerSecond = config.get(CATEGORY_GENERAL, "bqPerRadPerSecond", 3.7E10D,
                "How many becquerels correspond to 1 RAD/s of the internal radiation system.")
                .setMinValue(1.0D).getDouble();

        String comment = "Activity of radioactive items in becquerels.\n" + "Key formats:\n" +
                "  modid:item_name      - all metadata values\n" +
                "  modid:item_name#meta - a specific metadata value\n" +
                "  oredict:name         - an OreDictionary entry";
        config.getCategory(CATEGORY_ACTIVITY).setComment(comment);

        for (Map.Entry<String, Double> entry : defaults.entrySet()) {
            config.get(CATEGORY_ACTIVITY, entry.getKey(), entry.getValue());
        }

        if (config.hasChanged()) {
            config.save();
        }

        reloadValues();
    }

    private static void reloadValues() {
        values.clear();
        Map<String, net.minecraftforge.common.config.Property> props = config.getCategory(CATEGORY_ACTIVITY)
                .getValues();
        for (Map.Entry<String, net.minecraftforge.common.config.Property> e : props.entrySet()) {
            try {
                double bq = e.getValue().getDouble();
                if (bq > 0) {
                    values.put(e.getKey(), bq);
                }
            } catch (Exception ex) {
                RealisticNTM.LOGGER.warn("Invalid Bq value for key '{}': {}", e.getKey(), e.getValue().getString());
            }
        }
    }

    public static Map<String, Double> getActivityValues() {
        return values;
    }
}
>>>>>>> 678bf43fba2c86bf348971819cc46252882eb0c2
