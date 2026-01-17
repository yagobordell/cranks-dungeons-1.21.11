package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class ElementalDamageMixin {

    @Shadow
    public abstract boolean damage(ServerWorld world, DamageSource source, float amount);

    @Shadow
    public abstract void heal(float amount);

    @Shadow
    public abstract double getAttributeValue(net.minecraft.registry.entry.RegistryEntry<net.minecraft.entity.attribute.EntityAttribute> attribute);

    @Unique
    private static final ThreadLocal<Boolean> cranks$processingElementalDamage = ThreadLocal.withInitial(() -> false);

    @Inject(method = "damage", at = @At("RETURN"))
    private void applyElementalDamageOnHit(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() || cranks$processingElementalDamage.get()) {
            return;
        }

        if (source.getAttacker() instanceof LivingEntity attacker) {
            LivingEntity target = (LivingEntity) (Object) this;

            try {
                cranks$processingElementalDamage.set(true);

                double fireDamage = attacker.getAttributeValue(ModAttributes.FIRE_DAMAGE);
                double coldDamage = attacker.getAttributeValue(ModAttributes.COLD_DAMAGE);
                double lightningDamage = attacker.getAttributeValue(ModAttributes.LIGHTNING_DAMAGE);
                double voidDamage = attacker.getAttributeValue(ModAttributes.VOID_DAMAGE);

                double totalElementalDamage = 0;

                if (fireDamage > 0) {
                    double resistance = target.getAttributeValue(ModAttributes.FIRE_RESISTANCE);
                    double finalDamage = fireDamage * (1.0 - resistance);
                    if (finalDamage > 0) {
                        DamageSource fireSource = new DamageSource(
                                world.getRegistryManager().getOrThrow(net.minecraft.registry.RegistryKeys.DAMAGE_TYPE)
                                        .getOrThrow(net.minecraft.entity.damage.DamageTypes.IN_FIRE)
                        );
                        target.damage(world, fireSource, (float) finalDamage);
                        totalElementalDamage += finalDamage;
                    }
                }

                if (coldDamage > 0) {
                    double resistance = target.getAttributeValue(ModAttributes.COLD_RESISTANCE);
                    double finalDamage = coldDamage * (1.0 - resistance);
                    if (finalDamage > 0) {
                        DamageSource coldSource = new DamageSource(
                                world.getRegistryManager().getOrThrow(net.minecraft.registry.RegistryKeys.DAMAGE_TYPE)
                                        .getOrThrow(net.minecraft.entity.damage.DamageTypes.FREEZE)
                        );
                        target.damage(world, coldSource, (float) finalDamage);
                        totalElementalDamage += finalDamage;
                    }
                }

                if (lightningDamage > 0) {
                    double resistance = target.getAttributeValue(ModAttributes.LIGHTNING_RESISTANCE);
                    double finalDamage = lightningDamage * (1.0 - resistance);
                    if (finalDamage > 0) {
                        DamageSource lightningSource = new DamageSource(
                                world.getRegistryManager().getOrThrow(net.minecraft.registry.RegistryKeys.DAMAGE_TYPE)
                                        .getOrThrow(net.minecraft.entity.damage.DamageTypes.LIGHTNING_BOLT)
                        );
                        target.damage(world, lightningSource, (float) finalDamage);
                        totalElementalDamage += finalDamage;
                    }
                }

                if (voidDamage > 0) {
                    double resistance = target.getAttributeValue(ModAttributes.VOID_RESISTANCE);
                    double finalDamage = voidDamage * (1.0 - resistance);
                    if (finalDamage > 0) {
                        DamageSource voidSource = new DamageSource(
                                world.getRegistryManager().getOrThrow(net.minecraft.registry.RegistryKeys.DAMAGE_TYPE)
                                        .getOrThrow(net.minecraft.entity.damage.DamageTypes.OUT_OF_WORLD)
                        );
                        target.damage(world, voidSource, (float) finalDamage);
                        totalElementalDamage += finalDamage;
                    }
                }

                double lifeSteal = attacker.getAttributeValue(ModAttributes.LIFE_STEAL);
                if (lifeSteal > 0) {
                    float totalDamage = amount + (float) totalElementalDamage;
                    float healAmount = (float) (totalDamage * lifeSteal);
                    attacker.heal(healAmount);
                }

                double chanceToBurn = attacker.getAttributeValue(ModAttributes.CHANCE_TO_BURN);
                if (chanceToBurn > 0 && Math.random() < chanceToBurn) {
                    ((net.minecraft.entity.Entity) target).setOnFireFor(4.0f);
                }

            } finally {
                cranks$processingElementalDamage.set(false);
            }
        }
    }
}