package com.realisticntm.mixin;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.hbm.hazard.modifier.IHazardModifier;
import com.hbm.hazard.type.HazardTypeRadiation;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;
import com.realisticntm.config.RadiationConfigRTM;
import com.realisticntm.radiation.BqFormat;

@Mixin(value = HazardTypeRadiation.class, remap = false)
public class MixinHazardTypeRadiation {

    /**
     * @author Realistic NTM
     * @reason Display item activity in becquerels instead of RAD/s.
     */
    @Overwrite(remap = false)
    @SideOnly(Side.CLIENT)
    public void addHazardInformation(EntityPlayer player, List<String> list, double level, ItemStack stack,
                                     List<IHazardModifier> modifiers) {
        level = IHazardModifier.evalAllModifiers(stack, player, level, modifiers);
        if (level == 0) return;

        list.add("§a[" + I18nUtil.resolveKey("trait.radioactive") + "]");

        if (!RadiationConfigRTM.becquerelTooltips) {
            list.add(" §e" + Library.roundFloat(HazardTypeRadiation.getNewValue(level), 3) +
                    HazardTypeRadiation.getSuffix(level) + " " + I18nUtil.resolveKey("desc.rads"));

            if (stack.getCount() > 1) {
                double stackRad = level * stack.getCount();
                list.add(" §e" + I18nUtil.resolveKey("desc.stack") + " " +
                        Library.roundFloat(HazardTypeRadiation.getNewValue(stackRad), 3) +
                        HazardTypeRadiation.getSuffix(stackRad) + " " + I18nUtil.resolveKey("desc.rads"));
            }
            return;
        }

        double bqPerItem = level * RadiationConfigRTM.bqPerRadPerSecond;

        list.add(" §e" + BqFormat.format(bqPerItem));

        if (stack.getCount() > 1) {
            list.add(" §e" + I18nUtil.resolveKey("desc.stack") + " " + BqFormat.format(bqPerItem * stack.getCount()));
        }
    }
}
