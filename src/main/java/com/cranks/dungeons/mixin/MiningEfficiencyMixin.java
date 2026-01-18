package com.cranks.dungeons.mixin;

import com.cranks.dungeons.registry.ModAttributes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MiningEfficiencyMixin {

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void applyMiningEfficiency(BlockState block, CallbackInfoReturnable<Float> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        double efficiencyBonus = player.getAttributeValue(ModAttributes.MINING_EFFICIENCY);

        if (efficiencyBonus > 0) {
            float currentSpeed = cir.getReturnValue();
            float newSpeed = currentSpeed * (float) (1.0 + efficiencyBonus);
            cir.setReturnValue(newSpeed);
        }
    }
}