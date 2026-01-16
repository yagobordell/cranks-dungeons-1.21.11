package com.cranks.dungeons;

import com.cranks.dungeons.registry.ModAttributes;
import net.fabricmc.api.ModInitializer;

public class CranksDungeons implements ModInitializer {

	public static final String MOD_ID = "cranks-dungeons";

	@Override
	public void onInitialize() {
		ModAttributes.registerAttributes();
		com.cranks.dungeons.command.TestAttributesCommand.register();
		System.out.println("Cranks Dungeons: Custom attributes registered!");
	}
}