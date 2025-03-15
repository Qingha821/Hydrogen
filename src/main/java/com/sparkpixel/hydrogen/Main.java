package com.sparkpixel.hydrogen;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("hydrogen")
public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public Main() {
        LOGGER.info("Hydrogen Mod Initialized");

        // 注册配置（如果需要）
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {
        LOGGER.debug("Common setup complete");
    }
}