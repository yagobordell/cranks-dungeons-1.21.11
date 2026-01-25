package com.cranks.dungeons.block;

import com.cranks.dungeons.CranksDungeons;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Block RUNIC_ENHANCEMENT_ALTAR;

    public static void register() {

        Identifier id = Identifier.of(CranksDungeons.MOD_ID, "runic_enhancement_altar");

        // ─── Key change: create RegistryKey ───
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);

        AbstractBlock.Settings blockSettings = AbstractBlock.Settings.create()
                .strength(3.5f)
                .requiresTool()
                .sounds(BlockSoundGroup.STONE)
                .nonOpaque() // <--- Add this line
                .registryKey(blockKey);

        RUNIC_ENHANCEMENT_ALTAR = Registry.register(
                Registries.BLOCK,
                id,
                new RunicEnhancementAltarBlock(blockSettings)
        );

        // Same for the item
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);

        Item.Settings itemSettings = new Item.Settings()
                .registryKey(itemKey);  // ← required for items too!

        Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(RUNIC_ENHANCEMENT_ALTAR, itemSettings)
        );

    }
}