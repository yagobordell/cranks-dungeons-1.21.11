package com.cranks.dungeons.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class CriticalHitMixin {

    @ModifyVariable(
            method = "attack",
            at = @At(value = "STORE"),
            ordinal = 2
    )
    private boolean disableCriticalHits(boolean isCritical) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getVelocity().y < 0) {
            return false;
        }

        return isCritical;
    }
}