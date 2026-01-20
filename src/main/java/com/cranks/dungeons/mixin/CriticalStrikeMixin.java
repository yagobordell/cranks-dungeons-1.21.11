package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.EquipmentStatApplier;
import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class CriticalStrikeMixin {

    @Unique
    private static final ThreadLocal<Boolean> isCriticalHit = ThreadLocal.withInitial(() -> false);

    @ModifyVariable(
            method = "damage",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private float applyCriticalStrike(float amount, ServerWorld world, DamageSource source) {
        isCriticalHit.set(false);

        if (source.getAttacker() instanceof PlayerEntity attacker) {
            // Get crit chance from ARMOR (player attributes)
            double critChance = attacker.getAttributeValue(ModAttributes.CRIT_CHANCE);

            // Add crit chance from the WEAPON being used
            ItemStack weapon = attacker.getMainHandStack();
            critChance += EquipmentStatApplier.getItemStatValue(weapon, "crit_chance");

            if (critChance > 0 && Math.random() < critChance) {
                isCriticalHit.set(true);
                return amount * 2.0f;
            }
        }

        return amount;
    }

    @ModifyVariable(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;F)V"
            ),
            ordinal = 0,
            argsOnly = true
    )
    private float showCritParticles(float amount, ServerWorld world, DamageSource source) {
        if (isCriticalHit.get()) {
            LivingEntity target = (LivingEntity) (Object) this;

            world.getServer().execute(() -> {
                world.spawnParticles(
                        net.minecraft.particle.ParticleTypes.ENCHANTED_HIT,
                        target.getX(), target.getY() + target.getHeight() * 0.75, target.getZ(),
                        15,
                        0.2, 0.2, 0.2,
                        0.15
                );

                for (int i = 0; i < 12; i++) {
                    double angle = i * (Math.PI * 2 / 12);
                    double vx = Math.cos(angle) * 0.2;
                    double vz = Math.sin(angle) * 0.2;

                    world.spawnParticles(
                            net.minecraft.particle.ParticleTypes.CRIT,
                            target.getX(),
                            target.getY() + target.getHeight() / 2,
                            target.getZ(),
                            0,
                            vx, 0.1, vz,
                            0.5
                    );
                }

                world.playSound(null, target.getBlockPos(),
                        net.minecraft.sound.SoundEvents.ENTITY_PLAYER_ATTACK_CRIT,
                        net.minecraft.sound.SoundCategory.PLAYERS, 1.0f, 1.2f);
            });
        }

        return amount;
    }
}