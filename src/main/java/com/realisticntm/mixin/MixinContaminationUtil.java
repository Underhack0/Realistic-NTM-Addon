<<<<<<< HEAD
package com.realisticntm.mixin;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.hbm.hazard.type.HazardTypeRadiation;
import com.hbm.lib.Library;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;
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
            list.add("§a[" + I18nUtil.resolveKey("trait.radioactive") + "]");

            if (!RadiationConfigRTM.becquerelTooltips) {
                float stackRad = activationRads / stack.getCount();
                list.add(" §e" + Library.roundFloat(HazardTypeRadiation.getNewValue(stackRad), 3) +
                        HazardTypeRadiation.getSuffix(stackRad) + " RAD/s");

                if (stack.getCount() > 1) {
                    list.add(" §eStack: " + Library.roundFloat(HazardTypeRadiation.getNewValue(activationRads), 3) +
                            HazardTypeRadiation.getSuffix(activationRads) + " RAD/s");
                }
                return;
            }

            float stackRad = activationRads / stack.getCount();
            list.add(" §e" + BqFormat.format(stackRad * RadiationConfigRTM.bqPerRadPerSecond));

            if (stack.getCount() > 1) {
                list.add(" §e" + I18nUtil.resolveKey("desc.stack") + " " +
                        BqFormat.format(activationRads * RadiationConfigRTM.bqPerRadPerSecond));
            }
        }
    }
}
=======
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
>>>>>>> 678bf43fba2c86bf348971819cc46252882eb0c2
