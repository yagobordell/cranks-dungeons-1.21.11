package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.EquipmentStatApplier;
import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = LivingEntity.class, priority = 10000)
public abstract class CriticalStrikeMixin {

    @ModifyVariable(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/damage/DamageSource;F)V",
                    shift = At.Shift.BEFORE
            ),
            ordinal = 0,
            argsOnly = true
    )
    private float applyTotalCalculatedDamage(float amount, ServerWorld world, DamageSource source) {
        if (source.getAttacker() instanceof PlayerEntity attacker) {
            LivingEntity target = (LivingEntity) (Object) this;
            ItemStack weapon = attacker.getMainHandStack();
            ItemStack chest = attacker.getEquippedStack(EquipmentSlot.CHEST);

            float fire = getModifiedElement(weapon, chest, "fire_damage", target, ModAttributes.FIRE_RESISTANCE);
            float cold = getModifiedElement(weapon, chest, "cold_damage", target, ModAttributes.COLD_RESISTANCE);
            float light = getModifiedElement(weapon, chest, "lightning_damage", target, ModAttributes.LIGHTNING_RESISTANCE);
            float voidDmg = getModifiedElement(weapon, chest, "void_damage", target, ModAttributes.VOID_RESISTANCE);

            float totalElemental = fire + cold + light + voidDmg;

            float combinedBase = amount + totalElemental;

            double critChance = attacker.getAttributeValue(ModAttributes.CRIT_CHANCE);
            critChance += EquipmentStatApplier.getItemStatValue(weapon, "crit_chance");

            float finalAmount = combinedBase;
            boolean isCrit = false;

            if (critChance > 0 && attacker.getRandom().nextFloat() < critChance) {
                finalAmount = combinedBase * 1.5f;
                isCrit = true;
                spawnCriticalEffects(world, target);
            }

            double lifeStealStat = EquipmentStatApplier.getItemStatValue(weapon, "life_steal")
                    + EquipmentStatApplier.getItemStatValue(chest, "life_steal");

            if (lifeStealStat > 0) {
                float healAmount = (float) (finalAmount * lifeStealStat);
                attacker.heal(healAmount);
            }

            if (isCrit || totalElemental > 0) {
                System.out.println(String.format("[Dungeons] Base: %.1f | Element: +%.1f | Crit: %b | Total: %.1f",
                        amount, totalElemental, isCrit, finalAmount));
            }

            return finalAmount;
        }
        return amount;
    }

    @Unique
    private float getModifiedElement(ItemStack w, ItemStack c, String key, LivingEntity target, RegistryEntry<EntityAttribute> resAttr) {
        double dmg = EquipmentStatApplier.getItemStatValue(w, key) + EquipmentStatApplier.getItemStatValue(c, key);
        if (dmg <= 0) return 0;
        double res = target.getAttributeValue(resAttr);
        return (float) (dmg * (1.0 - res));
    }

    @Unique
    private void spawnCriticalEffects(ServerWorld world, LivingEntity target) {
        world.spawnParticles(
                net.minecraft.particle.ParticleTypes.ENCHANTED_HIT,
                target.getX(), target.getY() + target.getHeight() * 0.75, target.getZ(),
                15, 0.2, 0.2, 0.2, 0.15
        );

        for (int i = 0; i < 12; i++) {
            double angle = i * (Math.PI * 2 / 12);
            double vx = Math.cos(angle) * 0.2;
            double vz = Math.sin(angle) * 0.2;
            world.spawnParticles(
                    net.minecraft.particle.ParticleTypes.CRIT,
                    target.getX(), target.getY() + target.getHeight() / 2, target.getZ(),
                    0, vx, 0.1, vz, 0.5
            );
        }

        world.playSound(null, target.getBlockPos(),
                net.minecraft.sound.SoundEvents.ENTITY_PLAYER_ATTACK_CRIT,
                net.minecraft.sound.SoundCategory.PLAYERS, 1.0f, 1.2f);
    }
}