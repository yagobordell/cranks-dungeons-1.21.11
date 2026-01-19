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
        // Cast 'this' to LivingEntity
        LivingEntity entity = (LivingEntity) (Object) this;

        // Only apply to players
        if (entity instanceof PlayerEntity player) {
            // If the player is sprinting, they normally lose a bit of 'oomph'
            // We add a small velocity boost to keep the forward speed consistent
            if (player.isSprinting()) {
                float yaw = player.getYaw() * ((float) Math.PI / 180F);
                Vec3d currentVel = player.getVelocity();

                // Add back 0.2 units of horizontal velocity in the direction the player is looking
                player.setVelocity(currentVel.add(
                        (double) (-MathHelper.sin(yaw) * 0.2F),
                        0.0D,
                        (double) (MathHelper.cos(yaw) * 0.2F)
                ));

                // Mark velocity as dirty so the server syncs the change to the client
                player.velocityDirty = true;
            }
        }
    }
}