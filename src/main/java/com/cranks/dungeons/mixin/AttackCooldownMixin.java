package com.cranks.dungeons.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class AttackCooldownMixin {

    @Unique
    private int cranks$lastSlot = -1;
    @Unique
    private boolean cranks$wasFullyCharged = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void checkChargeState(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getAttackCooldownProgress(0.5f) >= 1.0f) {
            cranks$wasFullyCharged = true;
        }

        int currentSlot = player.getInventory().getSelectedSlot();
        if (cranks$lastSlot != currentSlot) {
            cranks$lastSlot = currentSlot;
        }

        if (player.handSwingTicks > 0) {
            cranks$wasFullyCharged = false;
        }
    }

    @Inject(method = "getAttackCooldownProgress", at = @At("HEAD"), cancellable = true)
    private void overrideProgress(float baseTime, CallbackInfoReturnable<Float> cir) {
        if (cranks$wasFullyCharged) {
            cir.setReturnValue(1.0f);
        }
    }

    // THIS IS THE KEY FIX: Override the hand equipping animation progress too
    @Inject(method = "getHandEquippingProgress", at = @At("HEAD"), cancellable = true)
    private void overrideEquipProgress(float baseTime, CallbackInfoReturnable<Float> cir) {
        if (cranks$wasFullyCharged) {
            cir.setReturnValue(1.0f);
        }
    }
}