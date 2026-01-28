package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.EquipmentStatApplier;
import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = LivingEntity.class, priority = 1000)
public abstract class KnockbackProcessorMixin {

    @ModifyVariable(
            method = "takeKnockback",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private double calculateFinalKnockback(double strength) {
        LivingEntity target = (LivingEntity) (Object) this;
        LivingEntity attacker = target.getAttacker();

        double finalStrength = strength;

        // 1. Apply Attacker's Bonus first (Multiplicative is recommended)
        if (attacker instanceof PlayerEntity player) {
            double knockbackBonus = player.getAttributeValue(ModAttributes.KNOCKBACK);
            ItemStack weapon = player.getMainHandStack();
            knockbackBonus += EquipmentStatApplier.getItemStatValue(weapon, "knockback");

            // Using multiplier to keep it from being "rocket-powered"
            finalStrength *= (1.0 + knockbackBonus);
        }

        // 2. Apply Target's Resistance to the NEW total
        double resistance = target.getAttributeValue(ModAttributes.KNOCKBACK_RESISTANCE);

        // Clamp resistance between 0 and 1 so it doesn't pull the player inward
        double resistanceMultiplier = Math.max(0.0, 1.0 - resistance);

        finalStrength *= resistanceMultiplier;

        // Logging for your balancing
        // System.out.println("Knockback Logic: Base " + strength + " -> Final " + finalStrength);

        return finalStrength;
    }
}