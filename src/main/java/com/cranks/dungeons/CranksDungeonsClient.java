package com.cranks.dungeons;

import com.cranks.dungeons.screen.ModScreenHandlers;
import com.cranks.dungeons.screen.RunicEnhancementAltarScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class CranksDungeonsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.RUNIC_ENHANCEMENT_ALTAR, RunicEnhancementAltarScreen::new);
    }
}
