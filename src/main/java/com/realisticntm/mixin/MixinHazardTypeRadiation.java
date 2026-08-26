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

        double bqPerItem = level * RadiationConfigRTM.bqPerRadPerSecond;

        list.add("§a[" + I18nUtil.resolveKey("trait.radioactive") + "]");
        list.add(" §e" + BqFormat.format(bqPerItem));

        if (stack.getCount() > 1) {
            list.add(" §e" + I18nUtil.resolveKey("desc.stack") + " " + BqFormat.format(bqPerItem * stack.getCount()));
        }
    }
}
