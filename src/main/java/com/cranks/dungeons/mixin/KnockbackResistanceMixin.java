package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class KnockbackResistanceMixin {

    @ModifyVariable(
            method = "takeKnockback",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private double applyKnockbackResistance(double strength) {
        LivingEntity entity = (LivingEntity) (Object) this;

        // Get knockback resistance from custom attribute
        double resistance = entity.getAttributeValue(ModAttributes.KNOCKBACK_RESISTANCE);

        if (resistance > 0) {
            // Reduce knockback strength based on resistance (0.0 = no reduction, 1.0 = full immunity)
            return strength * (1.0 - resistance);
        }

        return strength;
    }
}