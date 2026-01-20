package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.EquipmentStatApplier;
import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class KnockbackMixin {

    @Shadow
    public abstract LivingEntity getAttacker();

    @ModifyVariable(
            method = "takeKnockback",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private double modifyKnockbackStrength(double strength) {
        LivingEntity entity = (LivingEntity) (Object) this;

        LivingEntity attacker = entity.getAttacker();
        if (attacker instanceof PlayerEntity player) {
            // Get knockback from ARMOR (player attributes)
            double knockbackBonus = player.getAttributeValue(ModAttributes.KNOCKBACK);

            // Add knockback from WEAPON
            ItemStack weapon = player.getMainHandStack();
            knockbackBonus += EquipmentStatApplier.getItemStatValue(weapon, "knockback");

            return strength + knockbackBonus;
        }

        return strength;
    }
}