package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.ToolStatHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class DurabilityBonusMixin {

    @Inject(method = "getMaxDamage", at = @At("RETURN"), cancellable = true)
    private void applyDurabilityBonus(CallbackInfoReturnable<Integer> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        double durabilityBonus = ToolStatHelper.getDurabilityBonus(stack);

        if (durabilityBonus > 0) {
            int baseDurability = cir.getReturnValue();
            int newDurability = (int) (baseDurability * (1.0 + durabilityBonus));
            cir.setReturnValue(newDurability);
        }
    }
}