package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.ItemStatManager;
import com.cranks.dungeons.registry.ModAttributes;
import com.cranks.dungeons.stat.CustomStat;
import com.cranks.dungeons.stat.StatRegistry;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class DurabilityBonusMixin {

    @Inject(method = "getMaxDamage", at = @At("RETURN"), cancellable = true)
    private void applyDurabilityBonus(CallbackInfoReturnable<Integer> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        List<ItemStatManager.ItemStat> stats = ItemStatManager.getStats(stack);
        double durabilityBonus = 0.0;

        for (ItemStatManager.ItemStat itemStat : stats) {
            if (itemStat.statId.equals("durability_bonus")) {
                durabilityBonus = itemStat.value;
                break;
            }
        }

        if (durabilityBonus > 0) {
            int baseDurability = cir.getReturnValue();
            int newDurability = (int) (baseDurability * (1.0 + durabilityBonus));
            cir.setReturnValue(newDurability);
        }
    }
}