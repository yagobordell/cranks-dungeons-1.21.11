package com.cranks.dungeons;

import com.cranks.dungeons.item.ModItems;
import com.cranks.dungeons.registry.ModAttributes;
import com.cranks.dungeons.stat.StatRegistry;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CranksDungeons implements ModInitializer {

	public static final String MOD_ID = "cranks-dungeons";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModAttributes.registerAttributes();
		StatRegistry.registerStats();  // NEW LINE
		com.cranks.dungeons.command.TestAttributesCommand.register();
		LOGGER.info("Cranks Dungeons initialized!");
	}
}