package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.EquipmentStatApplier;
import com.cranks.dungeons.equipment.EquipmentType;
import com.cranks.dungeons.equipment.ItemStatManager;
import com.cranks.dungeons.item.CustomRarity;
import com.cranks.dungeons.stat.CustomStat;
import com.cranks.dungeons.stat.StatRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Custom tooltip formatter that shows:
 * - Item Name [ Rarity ]
 * - Base stats (Attack Damage, Attack Speed, etc.)
 * - Runic Stats (custom stats with tier info)
 */
@Mixin(ItemStack.class)
public abstract class ItemStackTooltipMixin {

    private static final double PLAYER_BASE_ATTACK_DAMAGE = 1.0;
    private static final double PLAYER_BASE_ATTACK_SPEED = 4.0;

    @Inject(method = "getTooltip", at = @At("RETURN"))
    private void customTooltipFormat(Item.TooltipContext context, net.minecraft.entity.player.PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        List<Text> tooltip = cir.getReturnValue();

        Optional<EquipmentType> equipType = EquipmentType.getTypeForItem(stack);
        List<ItemStatManager.ItemStat> customStats = ItemStatManager.getStats(stack);

        if (customStats.isEmpty() && equipType.isEmpty()) {
            return;
        }

        if (tooltip.isEmpty()) {
            return;
        }

        boolean isShield = equipType.isPresent() && equipType.get() == EquipmentType.SHIELD;

        if (isShield) {
            handleShieldTooltip(tooltip, stack);
        } else {
            handleNormalTooltip(tooltip, stack, equipType.orElse(null));
        }
    }

    private void handleShieldTooltip(List<Text> tooltip, ItemStack stack) {
        // Apply rarity to the name line
        Text itemName = modifyItemNameWithRarity(stack, tooltip.get(0));

        List<Text> newTooltip = new ArrayList<>();
        newTooltip.add(itemName);

        List<ItemStatManager.ItemStat> stats = ItemStatManager.getStats(stack);
        if (!stats.isEmpty()) {
            newTooltip.add(Text.literal("RUNIC STATS:").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD));

            for (ItemStatManager.ItemStat itemStat : stats) {
                CustomStat stat = StatRegistry.getStat(itemStat.statId);
                if (stat != null) {
                    newTooltip.add(createRunicStatLine(stat, itemStat.value, itemStat.tier));
                }
            }
        }

        for (int i = 1; i < tooltip.size(); i++) {
            String line = tooltip.get(i).getString();
            if (line.contains("Durability:") || line.contains("minecraft:") || line.contains("Enchantments:") || line.contains("Unbreakable")) {
                newTooltip.add(tooltip.get(i));
            }
        }

        tooltip.clear();
        tooltip.addAll(newTooltip);
    }

    private void handleNormalTooltip(List<Text> tooltip, ItemStack stack, EquipmentType equipType) {
        // Apply rarity to the name line
        Text itemName = modifyItemNameWithRarity(stack, tooltip.get(0));

        int attributeSectionStart = -1;
        int attributeSectionEnd = -1;

        for (int i = 1; i < tooltip.size(); i++) {
            String line = tooltip.get(i).getString();
            if ((line.contains("When in Main Hand:") || line.contains("When in Offhand:") ||
                    line.contains("When on Body:") || line.contains("When on Head:") ||
                    line.contains("When on Legs:") || line.contains("When on Feet:"))) {
                attributeSectionStart = i;
            }

            if (attributeSectionStart != -1 && attributeSectionEnd == -1) {
                if (line.trim().isEmpty() || line.contains("Unbreakable") || line.contains("Durability:") || line.contains("Enchantments:")) {
                    attributeSectionEnd = i;
                    break;
                }
            }
        }

        if (attributeSectionStart != -1 && attributeSectionEnd == -1) {
            for (int i = tooltip.size() - 1; i > attributeSectionStart; i--) {
                String line = tooltip.get(i).getString();
                if (line.contains("minecraft:") || line.contains("Durability:")) {
                    attributeSectionEnd = i;
                    break;
                }
            }
            if (attributeSectionEnd == -1) attributeSectionEnd = tooltip.size();
        }

        List<Text> newTooltip = new ArrayList<>();
        newTooltip.add(itemName);

        if (equipType != null) {
            addCustomStatsSection(stack, newTooltip, equipType);
        }

        if (attributeSectionEnd != -1 && attributeSectionEnd < tooltip.size()) {
            for (int i = attributeSectionEnd; i < tooltip.size(); i++) {
                String line = tooltip.get(i).getString();

                if (line.contains("attribute.name.cranks-dungeons") ||
                        line.contains("Attack Damage") ||
                        line.contains("Attack Speed") ||
                        line.contains("Armor")) {
                    continue;
                }

                newTooltip.add(tooltip.get(i));
            }
        }

        tooltip.clear();
        tooltip.addAll(newTooltip);
    }

    private void addCustomStatsSection(ItemStack stack, List<Text> tooltip, EquipmentType type) {
        AttributeModifiersComponent attributes = stack.getOrDefault(
                DataComponentTypes.ATTRIBUTE_MODIFIERS,
                AttributeModifiersComponent.DEFAULT
        );

        Map<RegistryEntry<EntityAttribute>, Double> attributeTotals = new HashMap<>();
        for (var entry : attributes.modifiers()) {
            attributeTotals.merge(entry.attribute(), entry.modifier().value(), Double::sum);
        }

        DecimalFormat df = new DecimalFormat("#.##");
        boolean isArmor = type == EquipmentType.HELMET || type == EquipmentType.CHESTPLATE ||
                type == EquipmentType.LEGGINGS || type == EquipmentType.BOOTS || type == EquipmentType.ELYTRA;

        if (isArmor) {
            if (attributeTotals.containsKey(EntityAttributes.ARMOR)) {
                tooltip.add(Text.literal("Armor: " + df.format(attributeTotals.get(EntityAttributes.ARMOR))).formatted(Formatting.DARK_GREEN));
            }
            if (attributeTotals.containsKey(EntityAttributes.ARMOR_TOUGHNESS)) {
                tooltip.add(Text.literal("Armor Toughness: " + df.format(attributeTotals.get(EntityAttributes.ARMOR_TOUGHNESS))).formatted(Formatting.DARK_GREEN));
            }
        } else {
            if (attributeTotals.containsKey(EntityAttributes.ATTACK_DAMAGE)) {
                tooltip.add(Text.literal("Attack Damage: " + df.format(PLAYER_BASE_ATTACK_DAMAGE + attributeTotals.get(EntityAttributes.ATTACK_DAMAGE))).formatted(Formatting.DARK_GREEN));
            }
            if (attributeTotals.containsKey(EntityAttributes.ATTACK_SPEED)) {
                tooltip.add(Text.literal("Attack Speed: " + df.format(PLAYER_BASE_ATTACK_SPEED + attributeTotals.get(EntityAttributes.ATTACK_SPEED))).formatted(Formatting.DARK_GREEN));
            }
        }

        CustomRarity rarity = CustomRarity.getRarity(stack);
        int maxSlots = switch (rarity) {
            case COMMON -> 1;
            case RARE -> 2;
            case UNIQUE -> 3;
        };

        var enchantmentComponent = stack.getEnchantments();
        List<Text> activeEnchants = new ArrayList<>();

        for (var entry : enchantmentComponent.getEnchantmentEntries()) {
            Text enchantName = entry.getKey().value().description();
            activeEnchants.add(Text.literal("[ " + enchantName.getString() + " ]").formatted(Formatting.AQUA));
        }

        for (int i = 0; i < maxSlots; i++) {
            if (i < activeEnchants.size()) {
                tooltip.add(activeEnchants.get(i));
            } else {
                tooltip.add(Text.literal("[ Enchantment Slot ]").formatted(Formatting.GRAY));
            }
        }

        List<ItemStatManager.ItemStat> stats = ItemStatManager.getStats(stack);
        if (!stats.isEmpty()) {
            tooltip.add(Text.literal("Runic Stats:").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD));

            for (ItemStatManager.ItemStat itemStat : stats) {
                CustomStat stat = StatRegistry.getStat(itemStat.statId);
                if (stat != null) {
                    tooltip.add(createRunicStatLine(stat, itemStat.value, itemStat.tier));
                }
            }
        }
    }

    private Text createRunicStatLine(CustomStat stat, double value, int tier) {
        DecimalFormat df = new DecimalFormat("#.##");
        String valueStr = stat.formatValue(value).contains("%") ? df.format(value * 100) + "%" : df.format(value);
        return Text.literal(stat.getDisplayName() + ": " + valueStr + " ").formatted(Formatting.WHITE)
                .append(Text.literal("[T" + tier + "]").formatted(getTierColor(tier)));
    }

    private Formatting getTierColor(int tier) {
        return switch (tier) {
            case 1, 2 -> Formatting.GRAY;
            case 3, 4 -> Formatting.GREEN;
            case 5 -> Formatting.GOLD;
            default -> Formatting.WHITE;
        };
    }

    private Text modifyItemNameWithRarity(ItemStack stack, Text originalName) {
        CustomRarity rarity = CustomRarity.getRarity(stack);

        return Text.literal(originalName.getString())
                .formatted(Formatting.WHITE)
                .append(Text.literal(" "))
                .append(Text.literal("[ " + rarity.getName() + " ]")
                        .formatted(rarity.getColor(), Formatting.BOLD));
    }
}