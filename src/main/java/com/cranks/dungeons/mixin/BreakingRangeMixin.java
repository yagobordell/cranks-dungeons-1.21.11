package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class BreakingRangeMixin {

    @Inject(method = "getBlockInteractionRange", at = @At("RETURN"), cancellable = true)
    private void modifyBreakingRange(CallbackInfoReturnable<Double> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        double baseRange = cir.getReturnValue();
        double rangeBonus = player.getAttributeValue(ModAttributes.BREAKING_RANGE);
        cir.setReturnValue(baseRange + rangeBonus);
    }
}