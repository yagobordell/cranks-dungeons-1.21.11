package com.cranks.dungeons.event;

import com.cranks.dungeons.registry.ModAttributes;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrecisionMiningHandler {

    private static final Map<UUID, Integer> storedDamage = new HashMap<>();

    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            ItemStack stack = player.getMainHandStack();

            if (stack.isDamageable()) {
                double precisionChance = player.getAttributeValue(ModAttributes.PRECISION_MINING);

                if (precisionChance > 0 && Math.random() < precisionChance) {
                    // Store current damage value
                    storedDamage.put(player.getUuid(), stack.getDamage());
                }
            }

            return true;
        });

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            UUID playerId = player.getUuid();

            if (storedDamage.containsKey(playerId)) {
                ItemStack stack = player.getMainHandStack();
                int previousDamage = storedDamage.get(playerId);

                stack.setDamage(previousDamage);

                storedDamage.remove(playerId);
            }
        });
    }
}