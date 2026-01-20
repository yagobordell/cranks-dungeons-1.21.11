package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.EquipmentStatApplier;
import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class AttackSpeedMixin {

    @Inject(method = "getAttackCooldownProgress", at = @At("RETURN"), cancellable = true)
    private void modifyAttackSpeed(float baseTime, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Get attack speed from ARMOR (player attributes)
        double attackSpeed = player.getAttributeValue(ModAttributes.ATTACK_SPEED);

        // Add attack speed from WEAPON
        ItemStack weapon = player.getMainHandStack();
        attackSpeed += EquipmentStatApplier.getItemStatValue(weapon, "attack_speed");

        if (attackSpeed > 0) {
            float currentProgress = cir.getReturnValue();
            float newProgress = currentProgress * (float) (1.0 + attackSpeed);
            cir.setReturnValue(Math.min(newProgress, 1.0f));
        }
    }
}