package com.cranks.dungeons.equipment;

import com.cranks.dungeons.CranksDungeons;
import com.cranks.dungeons.stat.CustomStat;
import com.cranks.dungeons.stat.StatRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.*;

public class EquipmentStatApplier {

    private static final Map<UUID, Set<Identifier>> appliedModifiers = new HashMap<>();

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    /**
     * Updates player stats from equipped armor.
     * This method only handles ARMOR because armor stats are applied to the player entity.
     * Item tooltip updates are now handled by updateItemAttributes() when stats are added.
     */
    public static void updatePlayerStats(PlayerEntity player) {
        removeAllModifiers(player);

        // Get all equipped armor pieces
        List<ItemStack> armorItems = new ArrayList<>();
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = player.getEquippedStack(slot);
            if (!stack.isEmpty()) {
                armorItems.add(stack);
            }
        }

        // Calculate total custom stats from armor
        Map<String, Double> totalArmorStats = new HashMap<>();

        for (ItemStack stack : armorItems) {
            List<ItemStatManager.ItemStat> stats = ItemStatManager.getStats(stack);
            for (ItemStatManager.ItemStat itemStat : stats) {
                totalArmorStats.merge(itemStat.statId, itemStat.value, Double::sum);
            }
        }

        // Apply armor custom stats to player
        for (Map.Entry<String, Double> entry : totalArmorStats.entrySet()) {
            String statId = entry.getKey();
            double totalValue = entry.getValue();

            CustomStat stat = StatRegistry.getStat(statId);
            if (stat != null && totalValue != 0) {
                applyStatModifier(player, stat, totalValue);
            }
        }
    }

    /**
     * Updates an item's attribute modifiers based on its custom stats.
     * This is used for WEAPONS, TOOLS, and ARMOR - their stats must be added as item attributes.
     * Call this whenever stats are added/removed from an item.
     */
    public static void updateItemAttributes(ItemStack stack) {
        Optional<EquipmentType> equipType = EquipmentType.getTypeForItem(stack);
        if (equipType.isEmpty()) {
            return;
        }

        EquipmentType type = equipType.get();
        List<ItemStatManager.ItemStat> stats = ItemStatManager.getStats(stack);

        // Get the item's ORIGINAL default attributes
        ItemStack defaultStack = new ItemStack(stack.getItem());
        AttributeModifiersComponent originalDefaults = defaultStack.getOrDefault(
                DataComponentTypes.ATTRIBUTE_MODIFIERS,
                AttributeModifiersComponent.DEFAULT
        );

        for (var entry : originalDefaults.modifiers()) {
            System.out.println("  - " + entry.attribute().getIdAsString() + ": " + entry.modifier().value() + " (slot: " + entry.slot() + ")");
        }

        if (stats.isEmpty()) {
            stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, originalDefaults);
            return;
        }

        AttributeModifierSlot slot = getSlotForEquipmentType(type);

        AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();

        Set<RegistryEntry<EntityAttribute>> customStatAttributes = new HashSet<>();

        for (ItemStatManager.ItemStat itemStat : stats) {
            CustomStat stat = StatRegistry.getStat(itemStat.statId);
            if (stat != null) {
                customStatAttributes.add(stat.getAttribute());
            }
        }

        Map<RegistryEntry<EntityAttribute>, Double> defaultValues = new HashMap<>();
        Map<RegistryEntry<EntityAttribute>, AttributeModifierSlot> defaultSlots = new HashMap<>();

        for (var entry : originalDefaults.modifiers()) {
            if (customStatAttributes.contains(entry.attribute())) {
                defaultValues.put(entry.attribute(), entry.modifier().value());
                defaultSlots.put(entry.attribute(), entry.slot());
            } else {
                builder.add(entry.attribute(), entry.modifier(), entry.slot());
            }
        }

        for (ItemStatManager.ItemStat itemStat : stats) {
            CustomStat stat = StatRegistry.getStat(itemStat.statId);
            if (stat != null) {
                Identifier modifierId = Identifier.of(
                        CranksDungeons.MOD_ID,
                        "item_" + stat.getId()
                );

                double defaultValue = defaultValues.getOrDefault(stat.getAttribute(), 0.0);
                AttributeModifierSlot modifierSlot = defaultSlots.getOrDefault(stat.getAttribute(), slot);
                double combinedValue = defaultValue + itemStat.value;

                EntityAttributeModifier modifier = new EntityAttributeModifier(
                        modifierId,
                        combinedValue,
                        EntityAttributeModifier.Operation.ADD_VALUE
                );

                builder.add(
                        stat.getAttribute(),
                        modifier,
                        modifierSlot
                );
            }
        }

        AttributeModifiersComponent component = builder.build();
        stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, component);

        for (var entry : component.modifiers()) {
        }
    }

    /**
     * Determines the equipment slot for an equipment type.
     */
    private static AttributeModifierSlot getSlotForEquipmentType(EquipmentType type) {
        return switch (type) {
            case HELMET -> AttributeModifierSlot.HEAD;
            case CHESTPLATE -> AttributeModifierSlot.CHEST;
            case LEGGINGS -> AttributeModifierSlot.LEGS;
            case BOOTS -> AttributeModifierSlot.FEET;
            case SHIELD -> AttributeModifierSlot.OFFHAND;
            default -> AttributeModifierSlot.MAINHAND; // Weapons, tools, etc.
        };
    }

    /**
     * Checks if an equipment type is armor.
     */
    private static boolean isArmorType(EquipmentType type) {
        return type == EquipmentType.HELMET ||
                type == EquipmentType.CHESTPLATE ||
                type == EquipmentType.LEGGINGS ||
                type == EquipmentType.BOOTS;
    }

    /**
     * Gets the total stat value from a specific item.
     * Used for stats that can't be applied as item attributes (like durability bonus, fortune, etc.)
     */
    /**
     * Gets the total stat value from a specific item.
     * Used for stats that can't be applied as item attributes (like durability bonus, fortune, etc.)
     * Also works with equipment-type-prefixed stat IDs.
     */
    public static double getItemStatValue(ItemStack stack, String baseStatId) {
        if (stack.isEmpty()) {
            return 0.0;
        }

        List<ItemStatManager.ItemStat> stats = ItemStatManager.getStats(stack);

        // Try direct match first (for backwards compatibility)
        for (ItemStatManager.ItemStat itemStat : stats) {
            if (itemStat.statId.equals(baseStatId)) {
                return itemStat.value;
            }
        }

        // Try with equipment type prefixes (sword_, pickaxe_, etc.)
        // This checks if the stat ID ends with "_fire_damage", "_cold_damage", etc.
        for (ItemStatManager.ItemStat itemStat : stats) {
            if (itemStat.statId.endsWith("_" + baseStatId)) {
                return itemStat.value;
            }
        }

        return 0.0;
    }

    private static void applyStatModifier(PlayerEntity player, CustomStat stat, double value) {
        Identifier modifierId = Identifier.of(CranksDungeons.MOD_ID, "equipment_" + stat.getId());

        EntityAttributeModifier modifier = new EntityAttributeModifier(
                modifierId,
                value,
                EntityAttributeModifier.Operation.ADD_VALUE
        );

        var attributeInstance = player.getAttributeInstance(stat.getAttribute());
        if (attributeInstance != null) {
            attributeInstance.removeModifier(modifierId);
            attributeInstance.addTemporaryModifier(modifier);

            appliedModifiers.computeIfAbsent(player.getUuid(), k -> new HashSet<>()).add(modifierId);
        }
    }

    private static void removeAllModifiers(PlayerEntity player) {
        Set<Identifier> modifiers = appliedModifiers.get(player.getUuid());
        if (modifiers == null || modifiers.isEmpty()) {
            return;
        }

        for (CustomStat stat : StatRegistry.getAllStats()) {
            Identifier modifierId = Identifier.of(CranksDungeons.MOD_ID, "equipment_" + stat.getId());

            var attributeInstance = player.getAttributeInstance(stat.getAttribute());
            if (attributeInstance != null) {
                attributeInstance.removeModifier(modifierId);
            }
        }

        modifiers.clear();
    }

    public static void onPlayerDisconnect(PlayerEntity player) {
        appliedModifiers.remove(player.getUuid());
    }
}