package com.cranks.dungeons.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class RunicTomeItem extends Item {

    private final int tier;

    public RunicTomeItem(int tier, Settings settings) {
        super(settings);
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, net.minecraft.component.type.TooltipDisplayComponent displayComponent, java.util.function.Consumer<Text> textConsumer, net.minecraft.item.tooltip.TooltipType type) {
        textConsumer.accept(Text.literal("Tier " + tier).formatted(Formatting.GRAY));
        textConsumer.accept(Text.literal("Contains dormant power...").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
}