package com.realisticntm.mixin;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.hbm.util.ContaminationUtil;
import com.realisticntm.config.RadiationConfigRTM;
import com.realisticntm.radiation.BqFormat;

@Mixin(value = ContaminationUtil.class, remap = false)
public class MixinContaminationUtil {

    /**
     * @author Realistic NTM
     * @reason Display neutron activation activity in becquerels instead of RAD/s.
     */
    @Overwrite(remap = false)
    public static void addNeutronRadInfo(ItemStack stack, EntityPlayer player, List<String> list, ITooltipFlag flagIn) {
        if (ContaminationUtil.isRadItem(stack)) return;

        float activationRads = ContaminationUtil.getNeutronRads(stack);
        if (activationRads > 0) {
            double bqTotal = activationRads * RadiationConfigRTM.bqPerRadPerSecond;
            list.add("§a[" + com.hbm.util.I18nUtil.resolveKey("trait.radioactive") + "]");

            float stackRad = activationRads / stack.getCount();
            list.add(" §e" + BqFormat.format(stackRad * RadiationConfigRTM.bqPerRadPerSecond));

            if (stack.getCount() > 1) {
                list.add(" §e" + com.hbm.util.I18nUtil.resolveKey("desc.stack") + " " + BqFormat.format(bqTotal));
            }
        }
    }
}
