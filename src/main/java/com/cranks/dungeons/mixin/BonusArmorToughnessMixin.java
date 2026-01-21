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
public abstract class BonusArmorToughnessMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyBonusArmorToughness(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (!(player instanceof ServerPlayerEntity)) return;

        double bonusToughness = player.getAttributeValue(ModAttributes.BONUS_ARMOR_TOUGHNESS);

        var toughnessAttr = player.getAttributeInstance(EntityAttributes.ARMOR_TOUGHNESS);
        if (toughnessAttr != null) {
            Identifier modifierId = Identifier.of(CranksDungeons.MOD_ID, "bonus_armor_toughness");

            toughnessAttr.removeModifier(modifierId);

            if (bonusToughness > 0) {
                EntityAttributeModifier modifier = new EntityAttributeModifier(
                        modifierId,
                        bonusToughness,
                        EntityAttributeModifier.Operation.ADD_VALUE
                );
                toughnessAttr.addTemporaryModifier(modifier);
            }
        }
    }
}