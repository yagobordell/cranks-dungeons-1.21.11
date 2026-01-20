package com.cranks.dungeons.command;

import com.cranks.dungeons.equipment.EquipmentStatApplier;
import com.cranks.dungeons.registry.ModAttributes;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
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

                        player.sendMessage(Text.literal("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━").formatted(Formatting.DARK_GRAY), false);
                        player.sendMessage(Text.literal("      Your Current Stats").formatted(Formatting.GOLD, Formatting.BOLD), false);
                        player.sendMessage(Text.literal("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━").formatted(Formatting.DARK_GRAY), false);

                        // OFFENSIVE STATS
                        player.sendMessage(Text.empty(), false);
                        player.sendMessage(Text.literal("⚔ Offensive").formatted(Formatting.RED, Formatting.BOLD), false);

                        // Get weapon stats
                        ItemStack weapon = player.getMainHandStack();
                        double weaponCrit = EquipmentStatApplier.getItemStatValue(weapon, "crit_chance");
                        double weaponFireDmg = EquipmentStatApplier.getItemStatValue(weapon, "fire_damage");
                        double weaponColdDmg = EquipmentStatApplier.getItemStatValue(weapon, "cold_damage");
                        double weaponLightningDmg = EquipmentStatApplier.getItemStatValue(weapon, "lightning_damage");
                        double weaponVoidDmg = EquipmentStatApplier.getItemStatValue(weapon, "void_damage");
                        double weaponLifeSteal = EquipmentStatApplier.getItemStatValue(weapon, "life_steal");
                        double weaponBurn = EquipmentStatApplier.getItemStatValue(weapon, "chance_to_burn");
                        double weaponRange = EquipmentStatApplier.getItemStatValue(weapon, "attack_range");
                        double weaponKnockback = EquipmentStatApplier.getItemStatValue(weapon, "knockback");
                        double weaponSpeed = EquipmentStatApplier.getItemStatValue(weapon, "attack_speed");
                        double weaponBonusDmg = EquipmentStatApplier.getItemStatValue(weapon, "bonus_attack_damage");

                        double baseDamage = player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
                        double totalCrit = player.getAttributeValue(ModAttributes.CRIT_CHANCE) + weaponCrit;
                        double totalFireDmg = player.getAttributeValue(ModAttributes.FIRE_DAMAGE) + weaponFireDmg;
                        double totalColdDmg = player.getAttributeValue(ModAttributes.COLD_DAMAGE) + weaponColdDmg;
                        double totalLightningDmg = player.getAttributeValue(ModAttributes.LIGHTNING_DAMAGE) + weaponLightningDmg;
                        double totalVoidDmg = player.getAttributeValue(ModAttributes.VOID_DAMAGE) + weaponVoidDmg;
                        double totalLifeSteal = player.getAttributeValue(ModAttributes.LIFE_STEAL) + weaponLifeSteal;
                        double totalBurn = player.getAttributeValue(ModAttributes.CHANCE_TO_BURN) + weaponBurn;
                        double totalRange = player.getAttributeValue(ModAttributes.ATTACK_RANGE) + weaponRange;
                        double totalKnockback = player.getAttributeValue(ModAttributes.KNOCKBACK) + weaponKnockback;
                        double totalSpeed = player.getAttributeValue(ModAttributes.ATTACK_SPEED) + weaponSpeed;
                        double totalBonusDmg = player.getAttributeValue(ModAttributes.BONUS_ATTACK_DAMAGE) + weaponBonusDmg;

                        sendStat(player, "Attack Damage", baseDamage + totalBonusDmg, 0, "");
                        if (totalSpeed > 0) sendStat(player, "Attack Speed", totalSpeed * 100, 0, "%");
                        if (totalCrit > 0) sendStat(player, "Critical Chance", totalCrit * 100, 0, "%");
                        if (totalBonusDmg > 0) sendStat(player, "Bonus Damage", totalBonusDmg, 0, "");
                        if (totalFireDmg > 0) sendStat(player, "Fire Damage", totalFireDmg, 0, "");
                        if (totalColdDmg > 0) sendStat(player, "Cold Damage", totalColdDmg, 0, "");
                        if (totalLightningDmg > 0) sendStat(player, "Lightning Damage", totalLightningDmg, 0, "");
                        if (totalVoidDmg > 0) sendStat(player, "Void Damage", totalVoidDmg, 0, "");
                        if (totalLifeSteal > 0) sendStat(player, "Life Steal", totalLifeSteal * 100, 0, "%");
                        if (totalBurn > 0) sendStat(player, "Burn Chance", totalBurn * 100, 0, "%");
                        if (totalRange > 0) sendStat(player, "Attack Range", totalRange, 0, " blocks");
                        if (totalKnockback > 0) sendStat(player, "Knockback", totalKnockback, 0, "");

                        // DEFENSIVE STATS
                        player.sendMessage(Text.empty(), false);
                        player.sendMessage(Text.literal("🛡 Defensive").formatted(Formatting.BLUE, Formatting.BOLD), false);

                        double maxHealth = player.getAttributeValue(EntityAttributes.MAX_HEALTH);
                        double armor = player.getAttributeValue(EntityAttributes.ARMOR);
                        double toughness = player.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS);
                        double lifeRegen = player.getAttributeValue(ModAttributes.LIFE_REGENERATION);
                        double fireRes = player.getAttributeValue(ModAttributes.FIRE_RESISTANCE);
                        double coldRes = player.getAttributeValue(ModAttributes.COLD_RESISTANCE);
                        double lightningRes = player.getAttributeValue(ModAttributes.LIGHTNING_RESISTANCE);
                        double voidRes = player.getAttributeValue(ModAttributes.VOID_RESISTANCE);
                        double kbRes = player.getAttributeValue(ModAttributes.KNOCKBACK_RESISTANCE);
                        double featherFall = player.getAttributeValue(ModAttributes.FEATHER_FALLING);

                        sendStat(player, "Max Health", maxHealth, 20, " HP");
                        sendStat(player, "Armor", armor, 0, "");
                        if (toughness > 0) sendStat(player, "Armor Toughness", toughness, 0, "");
                        if (lifeRegen > 0) sendStat(player, "Life Regeneration", lifeRegen, 0, " HP/s");
                        if (fireRes > 0) sendStat(player, "Fire Resistance", fireRes * 100, 0, "%");
                        if (coldRes > 0) sendStat(player, "Cold Resistance", coldRes * 100, 0, "%");
                        if (lightningRes > 0) sendStat(player, "Lightning Resistance", lightningRes * 100, 0, "%");
                        if (voidRes > 0) sendStat(player, "Void Resistance", voidRes * 100, 0, "%");
                        if (kbRes > 0) sendStat(player, "Knockback Resistance", kbRes * 100, 0, "%");
                        if (featherFall > 0) sendStat(player, "Feather Falling", featherFall * 100, 0, "%");

                        // UTILITY STATS
                        player.sendMessage(Text.empty(), false);
                        player.sendMessage(Text.literal("✦ Utility").formatted(Formatting.GREEN, Formatting.BOLD), false);

                        double speed = player.getAttributeValue(ModAttributes.MOVEMENT_SPEED);
                        double luck = player.getAttributeValue(ModAttributes.LUCK);
                        double expBonus = player.getAttributeValue(ModAttributes.EXPERIENCE_BONUS);

                        ItemStack tool = player.getMainHandStack();
                        double miningEff = EquipmentStatApplier.getItemStatValue(tool, "mining_efficiency");
                        double fortune = EquipmentStatApplier.getItemStatValue(tool, "fortune");
                        double precision = EquipmentStatApplier.getItemStatValue(tool, "precision_mining");
                        double breakRange = EquipmentStatApplier.getItemStatValue(tool, "breaking_range");

                        if (speed > 0) sendStat(player, "Movement Speed", speed * 100, 0, "%");
                        if (luck > 0) sendStat(player, "Luck", luck, 0, "");
                        if (expBonus > 0) sendStat(player, "Experience Bonus", expBonus * 100, 0, "%");
                        if (miningEff > 0) sendStat(player, "Mining Efficiency", miningEff * 100, 0, "%");
                        if (fortune > 0) sendStat(player, "Fortune Chance", fortune * 100, 0, "%");
                        if (precision > 0) sendStat(player, "Precision Mining", precision * 100, 0, "%");
                        if (breakRange > 0) sendStat(player, "Breaking Range", breakRange, 0, " blocks");

                        player.sendMessage(Text.literal("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━").formatted(Formatting.DARK_GRAY), false);

                        return 1;
                    }));
        });
    }

    private static void sendStat(ServerPlayerEntity player, String name, double value, double baseValue, String suffix) {
        String display;
        Formatting color = Formatting.WHITE;

        if (value == baseValue) {
            display = String.format("  %s: %.1f%s", name, value, suffix);
        } else if (value > baseValue) {
            double bonus = value - baseValue;
            display = String.format("  %s: %.1f%s (+%.1f)", name, value, suffix, bonus);
            color = Formatting.GREEN;
        } else {
            double penalty = baseValue - value;
            display = String.format("  %s: %.1f%s (-%.1f)", name, value, suffix, penalty);
            color = Formatting.RED;
        }

        player.sendMessage(Text.literal(display).formatted(color), false);
    }
}