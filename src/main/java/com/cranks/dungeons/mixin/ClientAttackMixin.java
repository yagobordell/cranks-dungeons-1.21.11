package com.cranks.dungeons.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class ClientAttackMixin {

    @Shadow public ClientPlayerEntity player;
    @Shadow @Final public GameOptions options;
    @Shadow public HitResult crosshairTarget;
    @Shadow protected abstract boolean doAttack();
    @Shadow private int attackCooldown;

    @Unique private boolean cranks$isReadyToSnap = false;
    @Unique private boolean cranks$wasNotReady = false;

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void onDoAttack(CallbackInfoReturnable<Boolean> cir) {
        if (player != null && player.getAttackCooldownProgress(0.0F) < 1.0F) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void handleAutoAttack(CallbackInfo ci) {
        if (player == null || MinecraftClient.getInstance().currentScreen != null) return;

        this.attackCooldown = 0;

        if (options.attackKey.isPressed()) {
            // Check if we're targeting an entity every tick (not just on initial press)
            boolean isTargetingEntity = crosshairTarget != null &&
                    crosshairTarget.getType() == HitResult.Type.ENTITY;

            float progress = player.getAttackCooldownProgress(0.0F);

            // Track when cooldown drops below 100%
            if (progress < 1.0F) {
                cranks$wasNotReady = true;
                cranks$isReadyToSnap = false;
            }
            // When cooldown reaches 100% and we previously saw it wasn't ready
            else if (progress >= 1.0F && cranks$wasNotReady) {
                if (cranks$isReadyToSnap) {
                    // Only attack if we're currently targeting an entity
                    if (isTargetingEntity) {
                        this.doAttack();
                        cranks$isReadyToSnap = false;
                        cranks$wasNotReady = false;
                    }
                    // If not targeting, keep the ready state so we attack when we do target
                } else {
                    // Become ready to attack on next tick (if targeting entity)
                    cranks$isReadyToSnap = true;
                }
            }
        } else {
            // Reset when attack key is released
            cranks$isReadyToSnap = false;
            cranks$wasNotReady = false;
        }
    }
}