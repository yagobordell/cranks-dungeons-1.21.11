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
            boolean isTargetingEntity = crosshairTarget != null &&
                    crosshairTarget.getType() == HitResult.Type.ENTITY;

            if (isTargetingEntity) {
                float progress = player.getAttackCooldownProgress(0.0F);

                if (progress < 1.0F) {
                    cranks$wasNotReady = true;
                    cranks$isReadyToSnap = false;
                } else if (progress >= 1.0F && cranks$wasNotReady) {
                    if (cranks$isReadyToSnap) {
                        this.doAttack();
                        cranks$isReadyToSnap = false;
                        cranks$wasNotReady = false;
                    } else {
                        cranks$isReadyToSnap = true;
                    }
                }
            } else {
                cranks$isReadyToSnap = false;
                cranks$wasNotReady = false;
            }
        } else {
            cranks$isReadyToSnap = false;
            cranks$wasNotReady = false;
        }
    }
}