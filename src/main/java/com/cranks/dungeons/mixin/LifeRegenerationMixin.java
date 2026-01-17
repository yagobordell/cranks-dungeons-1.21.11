package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LifeRegenerationMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyLifeRegeneration(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!entity.isAlive() || entity.getHealth() >= entity.getMaxHealth()) {
            return;
        }

        double lifeRegen = entity.getAttributeValue(ModAttributes.LIFE_REGENERATION);

        if (lifeRegen > 0) {
            float healAmount = (float) (lifeRegen / 20.0);
            entity.heal(healAmount);
        }
    }
}