package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class DamageReductionMixin {

    @Unique
    private static final ThreadLocal<Boolean> cranks$processingResistance = ThreadLocal.withInitial(() -> false);

    @ModifyVariable(
            method = "damage",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private float cranks$applyElementalResistances(float amount, ServerWorld world, DamageSource source) {
        if (cranks$processingResistance.get()) {
            return amount;
        }

        try {
            cranks$processingResistance.set(true);
            LivingEntity entity = (LivingEntity) (Object) this;

            if (source.isOf(DamageTypes.IN_FIRE) ||
                    source.isOf(DamageTypes.ON_FIRE) ||
                    source.isOf(DamageTypes.LAVA) ||
                    source.isOf(DamageTypes.HOT_FLOOR)) {
                double res = entity.getAttributeValue(ModAttributes.FIRE_RESISTANCE);
                amount *= (float) (1.0 - res);
            }

            if (source.isOf(DamageTypes.LIGHTNING_BOLT)) {
                double res = entity.getAttributeValue(ModAttributes.LIGHTNING_RESISTANCE);
                amount *= (float) (1.0 - res);
            }

            if (source.isOf(DamageTypes.OUT_OF_WORLD)) {
                double res = entity.getAttributeValue(ModAttributes.VOID_RESISTANCE);
                amount *= (float) (1.0 - res);
            }

            if (source.isOf(DamageTypes.FREEZE)) {
                double res = entity.getAttributeValue(ModAttributes.COLD_RESISTANCE);
                amount *= (float) (1.0 - res);
            }

            return amount;
        } finally {
            cranks$processingResistance.set(false);
        }
    }
}