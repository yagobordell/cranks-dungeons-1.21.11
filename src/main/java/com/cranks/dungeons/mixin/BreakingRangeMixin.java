package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.ToolStatHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

        // Get breaking range from the tool in main hand
        ItemStack tool = player.getMainHandStack();
        double rangeBonus = ToolStatHelper.getBreakingRange(tool);

        cir.setReturnValue(baseRange + rangeBonus);
    }
}