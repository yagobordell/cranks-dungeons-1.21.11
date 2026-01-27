package com.cranks.dungeons.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class RunicTomeItem extends Item {

    private final int tier;
    private final CustomRarity customRarity;

    public RunicTomeItem(int tier, CustomRarity customRarity, Settings settings) {
        super(settings);
        this.tier = tier;
        this.customRarity = customRarity;
    }

    public int getTier() {
        return tier;
    }

    public CustomRarity getCustomRarity() {
        return customRarity;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.empty()
                .append(super.getName(stack))
                .append(Text.literal(" "))
                .append(Text.literal("[ " + customRarity.getName() + " ]").formatted(customRarity.getColor(), Formatting.BOLD));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, net.minecraft.component.type.TooltipDisplayComponent displayComponent, java.util.function.Consumer<Text> textConsumer, net.minecraft.item.tooltip.TooltipType type) {
        textConsumer.accept(Text.literal("Tier " + tier).formatted(Formatting.GRAY));
        textConsumer.accept(Text.literal("Power sealed within runes").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
}