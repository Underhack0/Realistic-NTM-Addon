<<<<<<< HEAD
package com.realisticntm.radiation;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import com.hbm.hazard.HazardData;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.realisticntm.RealisticNTM;
import com.realisticntm.config.RadiationConfigRTM;

public final class RadiationOverrideApplier {

    private RadiationOverrideApplier() {}

    public static void apply() {
        double k = RadiationConfigRTM.bqPerRadPerSecond;
        int applied = 0;

        for (Map.Entry<String, Double> entry : RadiationConfigRTM.getActivityValues().entrySet()) {
            String key = entry.getKey();
            double bq = entry.getValue();
            try {
                if (applyKey(key, bq, k)) {
                    applied++;
                }
            } catch (Exception ex) {
                RealisticNTM.LOGGER.warn("Failed to apply activity for key '{}'", key, ex);
            }
        }

        HazardSystem.clearCaches();
        RealisticNTM.LOGGER.info("Applied becquerel activity values to {} items (1 RAD/s = {} Bq).", applied, k);
    }

    private static boolean applyKey(String key, double bq, double k) {
        if (key.startsWith("oredict:")) {
            String oreName = key.substring("oredict:".length());
            HazardSystem.register(oreName, buildOverridingData(HazardSystem.oreMap.get(oreName), bq, k));
            return true;
        }

        String itemName = key;
        int meta = -1;
        int hash = key.indexOf('#');
        if (hash >= 0) {
            itemName = key.substring(0, hash);
            meta = Integer.parseInt(key.substring(hash + 1));
        }

        ResourceLocation location = new ResourceLocation(itemName);
        if (!ForgeRegistries.ITEMS.containsKey(location)) {
            RealisticNTM.LOGGER.warn("Unknown item in radiation config: {}", key);
            return false;
        }
        Item item = ForgeRegistries.ITEMS.getValue(location);

        if (meta >= 0) {
            HazardData existing = merge(HazardSystem.itemMap.get(item), HazardSystem.stackMap
                    .get(new com.hbm.inventory.RecipesCommon.ComparableStack(item, 1, meta).makeSingular()));
            HazardSystem.register(new ItemStack(item, 1, meta), buildOverridingData(existing, bq, k));
        } else {
            HazardData existing = HazardSystem.itemMap.get(item);
            HazardSystem.register(item, buildOverridingData(existing, bq, k));
        }
        return true;
    }

    private static HazardData merge(HazardData a, HazardData b) {
        if (a == null) return b;
        if (b == null) return a;
        HazardData merged = new HazardData();
        merged.entries.addAll(a.entries);
        merged.entries.addAll(b.entries);
        return merged;
    }

    private static HazardData buildOverridingData(HazardData existing, double bq, double k) {
        HazardData data = new HazardData();
        if (existing != null) {
            for (HazardEntry hazardEntry : existing.entries) {
                if (hazardEntry.type != HazardRegistry.RADIATION) {
                    data.addEntry(hazardEntry);
                }
            }
        }
        data.addEntry(new HazardEntry(HazardRegistry.RADIATION, bq / k));
        data.setOverride(true);
        return data;
    }
}
=======
package com.realisticntm.radiation;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import com.hbm.hazard.HazardData;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.realisticntm.RealisticNTM;
import com.realisticntm.config.RadiationConfigRTM;

public final class RadiationOverrideApplier {

    private RadiationOverrideApplier() {}

    public static void apply() {
        double k = RadiationConfigRTM.bqPerRadPerSecond;
        int applied = 0;

        for (Map.Entry<String, Double> entry : RadiationConfigRTM.getActivityValues().entrySet()) {
            String key = entry.getKey();
            double bq = entry.getValue();
            try {
                if (applyKey(key, bq, k)) {
                    applied++;
                }
            } catch (Exception ex) {
                RealisticNTM.LOGGER.warn("Failed to apply activity for key '{}'", key, ex);
            }
        }

        HazardSystem.clearCaches();
        RealisticNTM.LOGGER.info("Applied becquerel activity values to {} items (1 RAD/s = {} Bq).", applied, k);
    }

    private static boolean applyKey(String key, double bq, double k) {
        if (key.startsWith("oredict:")) {
            String oreName = key.substring("oredict:".length());
            HazardSystem.register(oreName, buildOverridingData(HazardSystem.oreMap.get(oreName), bq, k));
            return true;
        }

        String itemName = key;
        int meta = -1;
        int hash = key.indexOf('#');
        if (hash >= 0) {
            itemName = key.substring(0, hash);
            meta = Integer.parseInt(key.substring(hash + 1));
        }

        ResourceLocation location = new ResourceLocation(itemName);
        if (!ForgeRegistries.ITEMS.containsKey(location)) {
            RealisticNTM.LOGGER.warn("Unknown item in radiation config: {}", key);
            return false;
        }
        Item item = ForgeRegistries.ITEMS.getValue(location);

        if (meta >= 0) {
            HazardData existing = merge(HazardSystem.itemMap.get(item), HazardSystem.stackMap
                    .get(new com.hbm.inventory.RecipesCommon.ComparableStack(item, 1, meta).makeSingular()));
            HazardSystem.register(new ItemStack(item, 1, meta), buildOverridingData(existing, bq, k));
        } else {
            HazardData existing = HazardSystem.itemMap.get(item);
            HazardSystem.register(item, buildOverridingData(existing, bq, k));
        }
        return true;
    }

    private static HazardData merge(HazardData a, HazardData b) {
        if (a == null) return b;
        if (b == null) return a;
        HazardData merged = new HazardData();
        merged.entries.addAll(a.entries);
        merged.entries.addAll(b.entries);
        return merged;
    }

    private static HazardData buildOverridingData(HazardData existing, double bq, double k) {
        HazardData data = new HazardData();
        if (existing != null) {
            for (HazardEntry hazardEntry : existing.entries) {
                if (hazardEntry.type != HazardRegistry.RADIATION) {
                    data.addEntry(hazardEntry);
                }
            }
        }
        data.addEntry(new HazardEntry(HazardRegistry.RADIATION, bq / k));
        data.setOverride(true);
        return data;
    }
}
>>>>>>> 678bf43fba2c86bf348971819cc46252882eb0c2
