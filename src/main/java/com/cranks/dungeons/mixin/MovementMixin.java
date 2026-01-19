package com.cranks.dungeons.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MovementMixin {

    @Inject(method = "jump", at = @At("TAIL"))
    private void preserveJumpMomentum(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof PlayerEntity player) {
            if (player.isSprinting()) {
                float yaw = player.getYaw() * ((float) Math.PI / 180F);
                Vec3d currentVel = player.getVelocity();

                player.setVelocity(currentVel.add(
                        (double) (-MathHelper.sin(yaw) * 0.2F),
                        0.0D,
                        (double) (MathHelper.cos(yaw) * 0.2F)
                ));

                player.velocityDirty = true;
            }
        }
    }
}