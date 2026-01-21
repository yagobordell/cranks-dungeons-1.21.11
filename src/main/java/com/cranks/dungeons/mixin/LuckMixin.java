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
public class LuckMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyCustomLuck(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Only run on server side
        if (!(player instanceof ServerPlayerEntity)) return;

        // Get luck bonus from custom attribute
        double luckBonus = player.getAttributeValue(ModAttributes.LUCK);

        var luckAttr = player.getAttributeInstance(EntityAttributes.LUCK);
        if (luckAttr != null) {
            Identifier modifierId = Identifier.of(CranksDungeons.MOD_ID, "luck_bonus");

            // Remove old modifier
            luckAttr.removeModifier(modifierId);

            // Add new modifier if bonus exists
            if (luckBonus > 0) {
                EntityAttributeModifier modifier = new EntityAttributeModifier(
                        modifierId,
                        luckBonus,
                        EntityAttributeModifier.Operation.ADD_VALUE
                );
                luckAttr.addTemporaryModifier(modifier);
            }
        }
    }
}