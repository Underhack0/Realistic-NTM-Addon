package com.realisticntm.mixin;

import java.util.List;
import java.util.ListIterator;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.hbm.hazard.HazardSystem;
import com.realisticntm.config.RadiationConfigRTM;
import com.realisticntm.radiation.BqFormat;

/**
 * Compatibility with NTM: Cursed Addon.
 * Cursed suppresses the vanilla radiation tooltip and appends its own aggregated
 * per-type (alpha/beta/x-ray/gamma/neutrons/activation/radon) block in RAD,
 * with every line marked by " -::". After that block is built, we rewrite the
 * RAD values in-place to becquerels.
 */
@Mixin(value = HazardSystem.class, remap = false)
public class MixinHazardSystem {

    @Inject(method = "addHazardInfo", at = @At("TAIL"), remap = false)
    private static void realisticntm$convertCursedRadLines(ItemStack stack, EntityPlayer player, List<String> list,
                                                           ITooltipFlag flagIn, CallbackInfo ci) {
        if (!RadiationConfigRTM.becquerelTooltips) return;

        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.indexOf(" -::") < 0 || !line.endsWith(" RAD") && !line.endsWith(" RAD/s")) continue;
            String converted = BqFormat.rewriteRadLine(line);
            if (converted != null) {
                iterator.set(converted);
            }
        }
    }
}
