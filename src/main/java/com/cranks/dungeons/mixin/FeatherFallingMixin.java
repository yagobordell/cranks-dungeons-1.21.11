package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class FeatherFallingMixin {

    @ModifyVariable(
            method = "damage",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private float applyFeatherFalling(float amount, ServerWorld world, DamageSource source) {
        if (source.isOf(net.minecraft.entity.damage.DamageTypes.FALL)) {
            LivingEntity entity = (LivingEntity) (Object) this;
            double featherFalling = entity.getAttributeValue(ModAttributes.FEATHER_FALLING);

            if (featherFalling > 0) {
                return amount * (float) (1.0 - featherFalling);
            }
        }

        return amount;
    }
}