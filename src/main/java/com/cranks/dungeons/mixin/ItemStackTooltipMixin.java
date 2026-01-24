package com.cranks.dungeons.mixin;

import com.cranks.dungeons.equipment.EquipmentStatApplier;
import com.cranks.dungeons.equipment.EquipmentType;
import com.cranks.dungeons.equipment.ItemStatManager;
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
 * - Item Name
 * - Base stats (Attack Damage, Attack Speed, etc.) - correctly calculated
 * - Runic Stats (custom stats with tier info)
 */
@Mixin(ItemStack.class)
public abstract class ItemStackTooltipMixin {

    // Player's base attribute values
    private static final double PLAYER_BASE_ATTACK_DAMAGE = 1.0;
    private static final double PLAYER_BASE_ATTACK_SPEED = 4.0;

    @Inject(method = "getTooltip", at = @At("RETURN"))
    private void customTooltipFormat(Item.TooltipContext context, net.minecraft.entity.player.PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        List<Text> tooltip = cir.getReturnValue();

        // Only modify if the item has custom stats
        List<ItemStatManager.ItemStat> customStats = ItemStatManager.getStats(stack);
        if (customStats.isEmpty()) {
            return;
        }

        // Check if this is a weapon/tool/armor with custom stats
        Optional<EquipmentType> equipType = EquipmentType.getTypeForItem(stack);
        if (equipType.isEmpty()) {
            return;
        }

        // Find the item name (always first line)
        if (tooltip.isEmpty()) {
            return;
        }

        Text itemName = tooltip.get(0);

        // Find where vanilla attribute section starts and ends
        int attributeSectionStart = -1;
        int attributeSectionEnd = -1;

        for (int i = 1; i < tooltip.size(); i++) {
            String line = tooltip.get(i).getString();

            // Find start of attribute section
            if ((line.contains("When in Main Hand:") ||
                    line.contains("When in Offhand:") ||
                    line.contains("When on Body:") ||
                    line.contains("When on Head:") ||
                    line.contains("When on Legs:") ||
                    line.contains("When on Feet:"))) {
                attributeSectionStart = i;
            }

            // Find end of attribute section (empty line or lore/enchantments)
            if (attributeSectionStart != -1 && attributeSectionEnd == -1) {
                if (line.trim().isEmpty() ||
                        line.contains("Unbreakable") ||
                        line.contains("Durability:") ||
                        line.contains("Enchantments:")) {
                    attributeSectionEnd = i;
                    break;
                }
            }
        }

        // If no end found, attribute section goes to the end before debug info
        if (attributeSectionStart != -1 && attributeSectionEnd == -1) {
            // Look for debug info (F3+H)
            for (int i = tooltip.size() - 1; i > attributeSectionStart; i--) {
                String line = tooltip.get(i).getString();
                if (line.contains("minecraft:") || line.contains("Durability:")) {
                    attributeSectionEnd = i;
                    break;
                }
            }
            if (attributeSectionEnd == -1) {
                attributeSectionEnd = tooltip.size();
            }
        }

        // Build new tooltip
        List<Text> newTooltip = new ArrayList<>();

        // Add item name
        newTooltip.add(itemName);

        // Add non-attribute lines before attribute section
        for (int i = 1; i < Math.min(attributeSectionStart != -1 ? attributeSectionStart : tooltip.size(), tooltip.size()); i++) {
            newTooltip.add(tooltip.get(i));
        }

        // Add custom stats section
        addCustomStatsSection(stack, newTooltip, equipType.get());

        // Add everything after attribute section (durability, enchants, debug info, etc.)
        if (attributeSectionEnd != -1 && attributeSectionEnd < tooltip.size()) {
            for (int i = attributeSectionEnd; i < tooltip.size(); i++) {
                newTooltip.add(tooltip.get(i));
            }
        }

        // Replace tooltip
        tooltip.clear();
        tooltip.addAll(newTooltip);
    }

    private void addCustomStatsSection(ItemStack stack, List<Text> tooltip, EquipmentType type) {
        // Get the attribute modifiers to calculate totals
        AttributeModifiersComponent attributes = stack.getOrDefault(
                DataComponentTypes.ATTRIBUTE_MODIFIERS,
                AttributeModifiersComponent.DEFAULT
        );

        // Calculate total values for each attribute
        Map<RegistryEntry<EntityAttribute>, Double> attributeTotals = new HashMap<>();
        for (var entry : attributes.modifiers()) {
            attributeTotals.merge(entry.attribute(), entry.modifier().value(), Double::sum);
        }

        // Show base stats based on equipment type
        DecimalFormat df = new DecimalFormat("#.##");

        boolean isArmor = type == EquipmentType.HELMET || type == EquipmentType.CHESTPLATE ||
                type == EquipmentType.LEGGINGS || type == EquipmentType.BOOTS;

        if (isArmor) {
            // Show armor stats
            if (attributeTotals.containsKey(EntityAttributes.ARMOR)) {
                double armor = attributeTotals.get(EntityAttributes.ARMOR);
                tooltip.add(Text.literal("Armor: " + df.format(armor))
                        .formatted(Formatting.DARK_GREEN));
            }

            if (attributeTotals.containsKey(EntityAttributes.ARMOR_TOUGHNESS)) {
                double toughness = attributeTotals.get(EntityAttributes.ARMOR_TOUGHNESS);
                tooltip.add(Text.literal("Armor Toughness: " + df.format(toughness))
                        .formatted(Formatting.DARK_GREEN));
            }
        } else {
            // Show weapon/tool stats
            if (attributeTotals.containsKey(EntityAttributes.ATTACK_DAMAGE)) {
                double damageModifier = attributeTotals.get(EntityAttributes.ATTACK_DAMAGE);
                double totalDamage = PLAYER_BASE_ATTACK_DAMAGE + damageModifier;
                tooltip.add(Text.literal("Attack Damage: " + df.format(totalDamage))
                        .formatted(Formatting.DARK_GREEN));
            }

            if (attributeTotals.containsKey(EntityAttributes.ATTACK_SPEED)) {
                double speedModifier = attributeTotals.get(EntityAttributes.ATTACK_SPEED);
                double totalSpeed = PLAYER_BASE_ATTACK_SPEED + speedModifier;
                tooltip.add(Text.literal("Attack Speed: " + df.format(totalSpeed))
                        .formatted(Formatting.DARK_GREEN));
            }
        }

        // Add RUNIC STATS section
        List<ItemStatManager.ItemStat> stats = ItemStatManager.getStats(stack);
        if (!stats.isEmpty()) {
            tooltip.add(Text.empty());
            tooltip.add(Text.literal("RUNIC STATS:").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD));

            for (ItemStatManager.ItemStat itemStat : stats) {
                CustomStat stat = StatRegistry.getStat(itemStat.statId);
                if (stat != null) {
                    Text statLine = createRunicStatLine(stat, itemStat.value, itemStat.tier);
                    tooltip.add(statLine);
                }
            }
        }
    }

    /**
     * Calculate attribute totals by summing all ADD_VALUE modifiers.
     * This gives us the modifier value that will be applied to the player's base.
     */
    private Map<RegistryEntry<EntityAttribute>, Double> calculateAttributeTotals(AttributeModifiersComponent attributes) {
        Map<RegistryEntry<EntityAttribute>, Double> totals = new HashMap<>();

        for (var entry : attributes.modifiers()) {
            EntityAttributeModifier modifier = entry.modifier();

            // Only handle ADD_VALUE operations (operation 0)
            if (modifier.operation() == EntityAttributeModifier.Operation.ADD_VALUE) {
                totals.merge(entry.attribute(), modifier.value(), Double::sum);
            }
        }

        return totals;
    }

    private Text createRunicStatLine(CustomStat stat, double value, int tier) {
        DecimalFormat df = new DecimalFormat("#.##");
        String valueStr;

        // Format the value
        if (stat.formatValue(value).contains("%")) {
            // It's a percentage stat
            valueStr = df.format(value * 100) + "%";
        } else {
            valueStr = df.format(value);
        }

        // Get tier color
        Formatting tierColor = getTierColor(tier);

        // Format: "Cold Damage: 5.2 [T5]"
        return Text.literal(stat.getDisplayName() + ": ")
                .formatted(stat.getColor())
                .append(Text.literal(valueStr)
                        .formatted(Formatting.WHITE))
                .append(Text.literal(" [T" + tier + "]")
                        .formatted(tierColor));
    }

    private Formatting getTierColor(int tier) {
        return switch (tier) {
            case 1 -> Formatting.GRAY;
            case 2 -> Formatting.GRAY;
            case 3 -> Formatting.GREEN;
            case 4 -> Formatting.GREEN;
            case 5 -> Formatting.GOLD;
            default -> Formatting.WHITE;
        };
    }
}