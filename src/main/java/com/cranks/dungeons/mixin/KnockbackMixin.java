package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class KnockbackMixin {

    @ModifyVariable(
            method = "takeKnockback",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private double modifyKnockbackStrength(double strength) {
        LivingEntity entity = (LivingEntity) (Object) this;
        double knockbackBonus = entity.getAttributeValue(ModAttributes.KNOCKBACK);
        return strength + knockbackBonus;
    }
}