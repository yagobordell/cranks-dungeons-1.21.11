package com.cranks.dungeons.event;

import com.cranks.dungeons.equipment.ToolStatHelper;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class FortuneEventHandler {

    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {

            if (world instanceof ServerWorld serverWorld) {
                String blockId = state.getBlock().getTranslationKey().toLowerCase();
                boolean isOre = blockId.contains("ore") || blockId.contains("debris");

                if (isOre) {
                    // Get fortune from the tool being used
                    ItemStack tool = player.getMainHandStack();
                    double fortuneChance = ToolStatHelper.getFortune(tool);

                    if (fortuneChance > 0 && player.getRandom().nextDouble() < fortuneChance) {

                        Block.dropStacks(state, serverWorld, pos, blockEntity, player, tool);

                        serverWorld.playSound(null, pos,
                                net.minecraft.sound.SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                                net.minecraft.sound.SoundCategory.BLOCKS, 0.5f, 1.5f);
                    }
                }
            }
            return true;
        });
    }
}