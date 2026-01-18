package com.cranks.dungeons.event;

import com.cranks.dungeons.registry.ModAttributes;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class FortuneEventHandler {

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (world instanceof ServerWorld serverWorld) {
                double fortuneChance = player.getAttributeValue(ModAttributes.FORTUNE);

                if (fortuneChance > 0 && Math.random() < fortuneChance) {
                    // Drop extra loot
                    BlockState blockState = state;
                    BlockEntity be = blockEntity;
                    net.minecraft.block.Block.dropStacks(blockState, serverWorld, pos, be, player, player.getMainHandStack());
                }
            }
        });
    }
}