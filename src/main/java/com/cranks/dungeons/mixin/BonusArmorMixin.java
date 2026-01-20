package com.cranks.dungeons.mixin;

import com.cranks.dungeons.CranksDungeons;
import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class BonusArmorMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyBonusArmor(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Only run on server side
        if (!(player instanceof ServerPlayerEntity)) return;

        // Get bonus armor from custom attribute
        double bonusArmor = player.getAttributeValue(ModAttributes.BONUS_ARMOR);

        var armorAttr = player.getAttributeInstance(EntityAttributes.ARMOR);
        if (armorAttr != null) {
            Identifier modifierId = Identifier.of(CranksDungeons.MOD_ID, "bonus_armor");

            // Remove old modifier
            armorAttr.removeModifier(modifierId);

            // Add new modifier if bonus exists
            if (bonusArmor > 0) {
                EntityAttributeModifier modifier = new EntityAttributeModifier(
                        modifierId,
                        bonusArmor,
                        EntityAttributeModifier.Operation.ADD_VALUE
                );
                armorAttr.addTemporaryModifier(modifier);
            }
        }
    }
}