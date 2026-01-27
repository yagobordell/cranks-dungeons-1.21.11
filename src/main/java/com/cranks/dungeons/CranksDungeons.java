package com.cranks.dungeons;

import com.cranks.dungeons.block.ModBlocks;
import com.cranks.dungeons.block.entity.ModBlockEntities;
import com.cranks.dungeons.item.ModItems;
import com.cranks.dungeons.loot.RunicTomeLootHandler;
import com.cranks.dungeons.registry.ModAttributes;
import com.cranks.dungeons.screen.ModScreenHandlers;
import com.cranks.dungeons.stat.StatRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CranksDungeons implements ModInitializer {

	public static final String MOD_ID = "cranks-dungeons";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModBlockEntities.register();
		ModItems.registerModItems();
		ModScreenHandlers.register();
		ModAttributes.registerAttributes();
		StatRegistry.registerStats();
		com.cranks.dungeons.command.ApplyStatCommand.register();
		com.cranks.dungeons.command.CheckStatsCommand.register();
		com.cranks.dungeons.event.FortuneEventHandler.register();
		RunicTomeLootHandler.register();
		FabricDefaultAttributeRegistry.register(EntityType.PLAYER, PlayerEntity.createPlayerAttributes().add(ModAttributes.CROP_FORTUNE, 0.0));

		registerCreativeTabItems();

		LOGGER.info("Cranks Dungeons initialized!");
	}

	private void registerCreativeTabItems() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
			content.add(ModBlocks.RUNIC_ENHANCEMENT_ALTAR);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
			content.add(ModItems.RUNIC_TOME_T1);
			content.add(ModItems.RUNIC_TOME_T2);
			content.add(ModItems.RUNIC_TOME_T3);
			content.add(ModItems.RUNIC_TOME_T4);
			content.add(ModItems.RUNIC_TOME_T5);
		});
	}
}