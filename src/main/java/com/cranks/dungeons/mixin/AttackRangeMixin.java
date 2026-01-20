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
public class AttackRangeMixin {

    @Inject(method = "getEntityInteractionRange", at = @At("RETURN"), cancellable = true)
    private void modifyAttackRange(CallbackInfoReturnable<Double> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        double baseRange = cir.getReturnValue();

        // Get attack range from ARMOR (player attributes)
        double attackRangeBonus = player.getAttributeValue(ModAttributes.ATTACK_RANGE);

        // Add attack range from WEAPON
        ItemStack weapon = player.getMainHandStack();
        attackRangeBonus += EquipmentStatApplier.getItemStatValue(weapon, "attack_range");

        cir.setReturnValue(baseRange + attackRangeBonus);
    }
}