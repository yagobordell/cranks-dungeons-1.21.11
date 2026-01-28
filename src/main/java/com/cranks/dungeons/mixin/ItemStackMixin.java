package com.cranks.dungeons.mixin;

import com.cranks.dungeons.item.CustomRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "getName", at = @At("RETURN"), cancellable = true)
    private void applyCustomRarityColor(CallbackInfoReturnable<Text> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        CustomRarity rarity = CustomRarity.getRarity(stack);
        Formatting color = rarity.getColor();

        Text originalName = cir.getReturnValue();

        MutableText newName = Text.empty()
                .append(originalName.copyContentOnly())
                .setStyle(Style.EMPTY.withColor(color));

        cir.setReturnValue(newName);
    }
}