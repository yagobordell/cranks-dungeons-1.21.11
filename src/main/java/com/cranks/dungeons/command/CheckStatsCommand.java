package com.cranks.dungeons.command;

import com.cranks.dungeons.registry.ModAttributes;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CheckStatsCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("checkstats")
                    .executes(context -> {
                        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

                        player.sendMessage(Text.literal("=== Your Current Stats ===").formatted(Formatting.GOLD, Formatting.BOLD), false);

                        player.sendMessage(Text.literal("Offensive:").formatted(Formatting.RED, Formatting.BOLD), false);
                        player.sendMessage(Text.literal("  Attack Damage: " + String.format("%.1f", player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE))), false);
                        player.sendMessage(Text.literal("  Attack Speed: " + String.format("%.1f%%", player.getAttributeValue(EntityAttributes.ATTACK_SPEED) * 100)), false);
                        player.sendMessage(Text.literal("  Crit Chance: " + String.format("%.1f%%", player.getAttributeValue(ModAttributes.CRIT_CHANCE) * 100)), false);
                        player.sendMessage(Text.literal("  Fire Damage: " + String.format("%.1f", player.getAttributeValue(ModAttributes.FIRE_DAMAGE))), false);
                        player.sendMessage(Text.literal("  Cold Damage: " + String.format("%.1f", player.getAttributeValue(ModAttributes.COLD_DAMAGE))), false);
                        player.sendMessage(Text.literal("  Lightning Damage: " + String.format("%.1f", player.getAttributeValue(ModAttributes.LIGHTNING_DAMAGE))), false);
                        player.sendMessage(Text.literal("  Void Damage: " + String.format("%.1f", player.getAttributeValue(ModAttributes.VOID_DAMAGE))), false);
                        player.sendMessage(Text.literal("  Life Steal: " + String.format("%.1f%%", player.getAttributeValue(ModAttributes.LIFE_STEAL) * 100)), false);
                        player.sendMessage(Text.literal("  Chance to Burn: " + String.format("%.1f%%", player.getAttributeValue(ModAttributes.CHANCE_TO_BURN) * 100)), false);
                        player.sendMessage(Text.literal("  Attack Range: +" + String.format("%.1f", player.getAttributeValue(ModAttributes.ATTACK_RANGE))), false);
                        player.sendMessage(Text.literal("  Knockback: +" + String.format("%.1f", player.getAttributeValue(ModAttributes.KNOCKBACK))), false);

                        player.sendMessage(Text.literal("Defensive:").formatted(Formatting.BLUE, Formatting.BOLD), false);
                        player.sendMessage(Text.literal("  Max Health: " + String.format("%.1f", player.getAttributeValue(EntityAttributes.MAX_HEALTH))), false);
                        player.sendMessage(Text.literal("  Life Regen: " + String.format("%.2f HP/s", player.getAttributeValue(ModAttributes.LIFE_REGENERATION))), false);
                        player.sendMessage(Text.literal("  Armor: " + String.format("%.1f", player.getAttributeValue(EntityAttributes.ARMOR))), false);
                        player.sendMessage(Text.literal("  Armor Toughness: " + String.format("%.1f", player.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS))), false);
                        player.sendMessage(Text.literal("  Fire Resistance: " + String.format("%.1f%%", player.getAttributeValue(ModAttributes.FIRE_RESISTANCE) * 100)), false);
                        player.sendMessage(Text.literal("  Cold Resistance: " + String.format("%.1f%%", player.getAttributeValue(ModAttributes.COLD_RESISTANCE) * 100)), false);
                        player.sendMessage(Text.literal("  Lightning Resistance: " + String.format("%.1f%%", player.getAttributeValue(ModAttributes.LIGHTNING_RESISTANCE) * 100)), false);
                        player.sendMessage(Text.literal("  Void Resistance: " + String.format("%.1f%%", player.getAttributeValue(ModAttributes.VOID_RESISTANCE) * 100)), false);
                        player.sendMessage(Text.literal("  Knockback Resistance: " + String.format("%.1f%%", player.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE) * 100)), false);

                        player.sendMessage(Text.literal("Utility:").formatted(Formatting.GREEN, Formatting.BOLD), false);
                        player.sendMessage(Text.literal("  Movement Speed: " + String.format("%.1f%%", player.getAttributeValue(EntityAttributes.MOVEMENT_SPEED) * 100)), false);
                        player.sendMessage(Text.literal("  Luck: " + String.format("%.1f", player.getAttributeValue(EntityAttributes.LUCK))), false);
                        player.sendMessage(Text.literal("  Feather Falling: " + String.format("%.1f%%", player.getAttributeValue(ModAttributes.FEATHER_FALLING) * 100)), false);
                        player.sendMessage(Text.literal("  Experience Bonus: " + String.format("%.1f%%", player.getAttributeValue(ModAttributes.EXPERIENCE_BONUS) * 100)), false);

                        return 1;
                    }));
        });
    }
}