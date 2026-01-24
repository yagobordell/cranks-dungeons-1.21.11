package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.EquipmentStatApplier;
import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
        // Only process if damage was successful
        if (!cir.getReturnValue()) {
            return;
        }

        // Skip if this damage is FROM elemental sources (to prevent infinite recursion)
        if (cranks$processingElementalDamage.get()) {
            return;
        }

        // Only apply elemental damage from player attacks
        if (!(source.getAttacker() instanceof PlayerEntity attacker)) {
            return;
        }

        LivingEntity target = (LivingEntity) (Object) this;
        ItemStack weapon = attacker.getMainHandStack();
        ItemStack chestplate = attacker.getEquippedStack(EquipmentSlot.CHEST);

        System.out.println("=== ELEMENTAL DAMAGE DEBUG ===");
        System.out.println("Attacker: " + attacker.getName().getString());
        System.out.println("Target: " + target.getName().getString());
        System.out.println("Weapon: " + weapon.getItem());
        System.out.println("Chestplate: " + chestplate.getItem());
        System.out.println("Base damage: " + amount);

        try {
            cranks$processingElementalDamage.set(true);

            // Get elemental damage from WEAPON
            System.out.println("About to check weapon for fire_damage...");
            double fireDamageFromWeapon = EquipmentStatApplier.getItemStatValue(weapon, "fire_damage");
            System.out.println("Fire damage from weapon: " + fireDamageFromWeapon);

            System.out.println("About to check weapon for cold_damage...");
            double coldDamageFromWeapon = EquipmentStatApplier.getItemStatValue(weapon, "cold_damage");
            System.out.println("Cold damage from weapon: " + coldDamageFromWeapon);

            System.out.println("About to check weapon for lightning_damage...");
            double lightningDamageFromWeapon = EquipmentStatApplier.getItemStatValue(weapon, "lightning_damage");
            System.out.println("Lightning damage from weapon: " + lightningDamageFromWeapon);

            System.out.println("About to check weapon for void_damage...");
            double voidDamageFromWeapon = EquipmentStatApplier.getItemStatValue(weapon, "void_damage");
            System.out.println("Void damage from weapon: " + voidDamageFromWeapon);

            System.out.println("From weapon:");
            System.out.println("  Fire: " + fireDamageFromWeapon);
            System.out.println("  Cold: " + coldDamageFromWeapon);
            System.out.println("  Lightning: " + lightningDamageFromWeapon);
            System.out.println("  Void: " + voidDamageFromWeapon);

            // Get elemental damage from CHESTPLATE
            System.out.println("About to check chestplate for fire_damage...");
            double fireDamageFromChestplate = EquipmentStatApplier.getItemStatValue(chestplate, "fire_damage");
            System.out.println("About to check chestplate for cold_damage...");
            double coldDamageFromChestplate = EquipmentStatApplier.getItemStatValue(chestplate, "cold_damage");
            System.out.println("About to check chestplate for lightning_damage...");
            double lightningDamageFromChestplate = EquipmentStatApplier.getItemStatValue(chestplate, "lightning_damage");
            System.out.println("About to check chestplate for void_damage...");
            double voidDamageFromChestplate = EquipmentStatApplier.getItemStatValue(chestplate, "void_damage");

            System.out.println("From chestplate:");
            System.out.println("  Fire: " + fireDamageFromChestplate);
            System.out.println("  Cold: " + coldDamageFromChestplate);
            System.out.println("  Lightning: " + lightningDamageFromChestplate);
            System.out.println("  Void: " + voidDamageFromChestplate);

            double fireDamage = fireDamageFromWeapon + fireDamageFromChestplate;
            double coldDamage = coldDamageFromWeapon + coldDamageFromChestplate;
            double lightningDamage = lightningDamageFromWeapon + lightningDamageFromChestplate;
            double voidDamage = voidDamageFromWeapon + voidDamageFromChestplate;

            System.out.println("Total elemental:");
            System.out.println("  Fire: " + fireDamage);
            System.out.println("  Cold: " + coldDamage);
            System.out.println("  Lightning: " + lightningDamage);
            System.out.println("  Void: " + voidDamage);

            double totalElementalDamage = 0;

            if (fireDamage > 0) {
                double resistance = target.getAttributeValue(ModAttributes.FIRE_RESISTANCE);
                double finalDamage = fireDamage * (1.0 - resistance);
                System.out.println("Fire: " + fireDamage + " * (1 - " + resistance + ") = " + finalDamage);
                if (finalDamage > 0) {
                    target.setHealth(target.getHealth() - (float) finalDamage);
                    totalElementalDamage += finalDamage;
                    System.out.println("  Applied fire damage directly to health");
                }
            }

            if (coldDamage > 0) {
                double resistance = target.getAttributeValue(ModAttributes.COLD_RESISTANCE);
                double finalDamage = coldDamage * (1.0 - resistance);
                System.out.println("Cold: " + coldDamage + " * (1 - " + resistance + ") = " + finalDamage);
                if (finalDamage > 0) {
                    target.setHealth(target.getHealth() - (float) finalDamage);
                    totalElementalDamage += finalDamage;
                    System.out.println("  Applied cold damage directly to health");
                }
            }

            if (lightningDamage > 0) {
                double resistance = target.getAttributeValue(ModAttributes.LIGHTNING_RESISTANCE);
                double finalDamage = lightningDamage * (1.0 - resistance);
                System.out.println("Lightning: " + lightningDamage + " * (1 - " + resistance + ") = " + finalDamage);
                if (finalDamage > 0) {
                    target.setHealth(target.getHealth() - (float) finalDamage);
                    totalElementalDamage += finalDamage;
                    System.out.println("  Applied lightning damage directly to health");
                }
            }

            if (voidDamage > 0) {
                double resistance = target.getAttributeValue(ModAttributes.VOID_RESISTANCE);
                double finalDamage = voidDamage * (1.0 - resistance);
                System.out.println("Void: " + voidDamage + " * (1 - " + resistance + ") = " + finalDamage);
                if (finalDamage > 0) {
                    target.setHealth(target.getHealth() - (float) finalDamage);
                    totalElementalDamage += finalDamage;
                    System.out.println("  Applied void damage directly to health");
                }
            }

            System.out.println("Total elemental damage dealt: " + totalElementalDamage);

            double lifeStealFromWeapon = EquipmentStatApplier.getItemStatValue(weapon, "life_steal");
            double lifeStealFromChestplate = EquipmentStatApplier.getItemStatValue(chestplate, "life_steal");
            double lifeSteal = lifeStealFromWeapon + lifeStealFromChestplate;

            System.out.println("Life Steal: " + lifeSteal + " (weapon: " + lifeStealFromWeapon + ", chest: " + lifeStealFromChestplate + ")");

            if (lifeSteal > 0) {
                float totalDamage = amount + (float) totalElementalDamage;
                float healAmount = (float) (totalDamage * lifeSteal);
                System.out.println("Healing: " + totalDamage + " * " + lifeSteal + " = " + healAmount);
                attacker.heal(healAmount);
            }

            // Chance to burn from weapon + chestplate
            double chanceToBurnFromWeapon = EquipmentStatApplier.getItemStatValue(weapon, "chance_to_burn");
            double chanceToBurnFromChestplate = EquipmentStatApplier.getItemStatValue(chestplate, "chance_to_burn");
            double chanceToBurn = chanceToBurnFromWeapon + chanceToBurnFromChestplate;

            System.out.println("Chance to Burn: " + chanceToBurn + " (weapon: " + chanceToBurnFromWeapon + ", chest: " + chanceToBurnFromChestplate + ")");

            if (chanceToBurn > 0) {
                double roll = Math.random();
                System.out.println("Burn roll: " + roll + " < " + chanceToBurn + " = " + (roll < chanceToBurn));
                if (roll < chanceToBurn) {
                    ((net.minecraft.entity.Entity) target).setOnFireFor(4.0f);
                    System.out.println("Target set on fire!");
                }
            }

            System.out.println("=== END DEBUG ===\n");

        } finally {
            cranks$processingElementalDamage.set(false);
        }
    }
}