<<<<<<< HEAD
package com.realisticntm;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.LogManager;

import com.realisticntm.config.RadiationConfigRTM;
import com.realisticntm.radiation.RadiationOverrideApplier;

@Mod(modid = Tags.MODID,
     name = Tags.MODNAME,
     version = Tags.VERSION,
     dependencies = "required-after:mixinbooter;after:hbm",
     acceptedMinecraftVersions = "[1.12,1.13)")
public class RealisticNTM {

    public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Tags.MODNAME);

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        RadiationConfigRTM.init(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        RadiationOverrideApplier.apply();
    }
}
=======
package com.realisticntm;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.LogManager;

import com.realisticntm.config.RadiationConfigRTM;
import com.realisticntm.radiation.RadiationOverrideApplier;

@Mod(modid = Tags.MODID,
     name = Tags.MODNAME,
     version = Tags.VERSION,
     dependencies = "required-after:mixinbooter;after:hbm",
     acceptedMinecraftVersions = "[1.12,1.13)")
public class RealisticNTM {

    public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Tags.MODNAME);

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        RadiationConfigRTM.init(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        RadiationOverrideApplier.apply();
    }
}
>>>>>>> 678bf43fba2c86bf348971819cc46252882eb0c2
