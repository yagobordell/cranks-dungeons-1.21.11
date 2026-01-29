package com.cranks.dungeons.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ItemStack.class, priority = 1500)
public class EnchantedBookDescriptionMixin {

    @Inject(method = "getTooltip", at = @At("RETURN"))
    private void addEnchantedBookDescriptions(Item.TooltipContext context,
                                              net.minecraft.entity.player.PlayerEntity player,
                                              TooltipType type,
                                              CallbackInfoReturnable<List<Text>> cir) {
        ItemStack stack = (ItemStack) (Object) this;

        if (!stack.isOf(Items.ENCHANTED_BOOK)) {
            return;
        }


        List<Text> tooltip = cir.getReturnValue();

        var enchantments = stack.getOrDefault(DataComponentTypes.STORED_ENCHANTMENTS,
                net.minecraft.component.type.ItemEnchantmentsComponent.DEFAULT);


        int descriptionsAdded = 0;

        for (var entry : enchantments.getEnchantmentEntries()) {
            String enchantId = entry.getKey().getKey().get().getValue().toString();
            String enchantName = enchantId.contains(":") ? enchantId.split(":")[1] : enchantId;

            if (isCustomEnchantment(enchantName)) {
                String descKey1 = "enchantment.cranks-dungeons." + enchantName + ".desc";
                String descKey2 = "enchantment.cranks-dungeons." + enchantName + ".desc2";
                String displayName = entry.getKey().value().description().getString();

                for (int i = 1; i < tooltip.size(); i++) {
                    String line = tooltip.get(i).getString();

                    if (line.equals(displayName)) {
                        // Add first line
                        Text description1 = Text.translatable(descKey1).formatted(Formatting.GRAY, Formatting.ITALIC);
                        tooltip.add(i + 1 + descriptionsAdded, description1);
                        descriptionsAdded++;

                        // Add second line if it exists
                        try {
                            Text description2 = Text.translatable(descKey2).formatted(Formatting.GRAY, Formatting.ITALIC);
                            // Only add if translation exists (won't be the key itself)
                            if (!description2.getString().equals(descKey2)) {
                                tooltip.add(i + 1 + descriptionsAdded, description2);
                                descriptionsAdded++;
                            }
                        } catch (Exception e) {
                            // No second line, that's fine
                        }

                        break;
                    }
                }
            }
        }
    }

    private boolean isCustomEnchantment(String name) {
        return name.equals("lunge") ||
                name.equals("riptide") ||
                name.equals("wind_burst");
    }
}