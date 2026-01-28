package com.cranks.dungeons.mixin;

import com.cranks.dungeons.item.CustomRarity;
import net.minecraft.component.type.ItemEnchantmentsComponent; // Updated symbol
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilSlotLimitMixin {

    @Shadow @Final private Property levelCost;

    @Inject(method = "updateResult", at = @At("RETURN"))
    private void enforceEnchantmentSlotLimit(CallbackInfo ci) {
        AnvilScreenHandler handler = (AnvilScreenHandler) (Object) this;
        ItemStack resultStack = handler.getSlot(2).getStack();

        if (resultStack.isEmpty()) {
            return;
        }

        CustomRarity rarity = CustomRarity.getRarity(resultStack);
        int maxSlots = switch (rarity) {
            case COMMON -> 1;
            case RARE -> 2;
            case UNIQUE -> 3;
        };

        ItemEnchantmentsComponent enchantments = resultStack.getEnchantments();
        int currentEnchantCount = enchantments.getEnchantmentEntries().size();

        if (currentEnchantCount > maxSlots) {
            handler.getSlot(2).setStack(ItemStack.EMPTY);
            this.levelCost.set(0);
        }
    }
}