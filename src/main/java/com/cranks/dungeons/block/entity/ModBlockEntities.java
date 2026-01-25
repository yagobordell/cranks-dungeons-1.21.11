package com.cranks.dungeons.block.entity;

import com.cranks.dungeons.CranksDungeons;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<RunicEnhancementAltarBlockEntity> RUNIC_ALTAR_BE;

    public static void register() {
        RUNIC_ALTAR_BE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(CranksDungeons.MOD_ID, "runic_altar_be"),
                FabricBlockEntityTypeBuilder.create(
                        RunicEnhancementAltarBlockEntity::new,
                        // Use a direct registry lookup to avoid class-loading loops
                        Registries.BLOCK.get(Identifier.of(CranksDungeons.MOD_ID, "runic_enhancement_altar"))
                ).build()
        );
    }
}