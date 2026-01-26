package com.cranks.dungeons.screen;

import com.cranks.dungeons.CranksDungeons;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<RunicEnhancementAltarScreenHandler> RUNIC_ENHANCEMENT_ALTAR;

    public static void register() {
        System.out.println(">>> LOADING SCREEN HANDLERS");
        RUNIC_ENHANCEMENT_ALTAR = Registry.register(
                Registries.SCREEN_HANDLER,
                Identifier.of(CranksDungeons.MOD_ID, "runic_enhancement_altar_gui"),
                // Use the standard ScreenHandlerType or Extended if you need extra data
                new ScreenHandlerType<>(RunicEnhancementAltarScreenHandler::new, net.minecraft.resource.featuretoggle.FeatureFlags.VANILLA_FEATURES)
        );
    }
}