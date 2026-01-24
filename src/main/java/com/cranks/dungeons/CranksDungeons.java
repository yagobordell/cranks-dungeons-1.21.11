package com.cranks.dungeons;

import com.cranks.dungeons.item.ModItems;
import com.cranks.dungeons.loot.RunicTomeLootHandler;
import com.cranks.dungeons.registry.ModAttributes;
import com.cranks.dungeons.stat.StatRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CranksDungeons implements ModInitializer {

	public static final String MOD_ID = "cranks-dungeons";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModAttributes.registerAttributes();
		StatRegistry.registerStats();
		com.cranks.dungeons.command.ApplyStatCommand.register();
		com.cranks.dungeons.command.CheckStatsCommand.register();
		com.cranks.dungeons.event.FortuneEventHandler.register();
		RunicTomeLootHandler.register();
		FabricDefaultAttributeRegistry.register(EntityType.PLAYER, PlayerEntity.createPlayerAttributes().add(ModAttributes.CROP_FORTUNE, 0.0));
		LOGGER.info("Cranks Dungeons initialized!");
	}
}