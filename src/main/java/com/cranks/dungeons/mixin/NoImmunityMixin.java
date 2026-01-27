package com.cranks.dungeons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class NoImmunityMixin {

    @Inject(method = "damage", at = @At("HEAD"))
    private void forceAcceptDamage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ((Entity)(Object)this).timeUntilRegen = 0;

        if (source.getAttacker() instanceof net.minecraft.entity.player.PlayerEntity) {
            LivingEntity victim = (LivingEntity)(Object)this;
            victim.hurtTime = 0;
            victim.maxHurtTime = 0;
        }
    }
}