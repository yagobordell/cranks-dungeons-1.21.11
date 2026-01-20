package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.EquipmentStatApplier;
import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class BonusAttackDamageMixin {

    @ModifyVariable(
            method = "damage",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private float applyBonusAttackDamage(float amount, ServerWorld world, DamageSource source) {
        if (source.getAttacker() instanceof PlayerEntity attacker) {
            // Get bonus attack damage from ARMOR (player attributes)
            double bonusDamage = attacker.getAttributeValue(ModAttributes.BONUS_ATTACK_DAMAGE);

            // Add bonus attack damage from WEAPON
            ItemStack weapon = attacker.getMainHandStack();
            bonusDamage += EquipmentStatApplier.getItemStatValue(weapon, "bonus_attack_damage");

            if (bonusDamage > 0) {
                return amount + (float) bonusDamage;
            }
        }

        return amount;
    }
}