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
public abstract class MaxHealthMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyMaxHealthBonus(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (!(player instanceof ServerPlayerEntity)) return;

        double healthBonus = player.getAttributeValue(ModAttributes.MAX_HEALTH_BONUS);

        var maxHealthAttr = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
        if (maxHealthAttr != null) {
            Identifier modifierId = Identifier.of(CranksDungeons.MOD_ID, "max_health_bonus");

            maxHealthAttr.removeModifier(modifierId);

            if (healthBonus > 0) {
                EntityAttributeModifier modifier = new EntityAttributeModifier(
                        modifierId,
                        healthBonus,
                        EntityAttributeModifier.Operation.ADD_VALUE
                );
                maxHealthAttr.addTemporaryModifier(modifier);
            }
        }
    }
}