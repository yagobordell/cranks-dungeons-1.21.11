package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class LuckMixin {

    @Inject(method = "getLuck", at = @At("RETURN"), cancellable = true)
    private void applyCustomLuck(CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Get luck bonus from custom attribute
        double luckBonus = player.getAttributeValue(ModAttributes.LUCK);

        if (luckBonus > 0) {
            float currentLuck = cir.getReturnValue();
            float newLuck = currentLuck + (float) luckBonus;
            cir.setReturnValue(newLuck);
        }
    }
}