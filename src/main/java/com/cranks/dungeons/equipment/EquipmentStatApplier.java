package com.cranks.dungeons.equipment;

import com.cranks.dungeons.CranksDungeons;
import com.cranks.dungeons.stat.CustomStat;
import com.cranks.dungeons.stat.StatRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

    public static void updatePlayerStats(PlayerEntity player) {
        removeAllModifiers(player);

        // Only apply stats from ARMOR to player attributes
        List<ItemStack> armorItems = new ArrayList<>();
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = player.getEquippedStack(slot);
            if (!stack.isEmpty()) {
                armorItems.add(stack);
            }
        }

        Map<String, Double> totalArmorStats = new HashMap<>();

        for (ItemStack stack : armorItems) {
            List<ItemStatManager.ItemStat> stats = ItemStatManager.getStats(stack);
            for (ItemStatManager.ItemStat itemStat : stats) {
                totalArmorStats.merge(itemStat.statId, itemStat.value, Double::sum);
            }
        }

        // Apply armor stats to player
        for (Map.Entry<String, Double> entry : totalArmorStats.entrySet()) {
            String statId = entry.getKey();
            double totalValue = entry.getValue();

            CustomStat stat = StatRegistry.getStat(statId);
            if (stat != null && totalValue != 0) {
                applyStatModifier(player, stat, totalValue);
            }
        }
    }

    private static boolean isArmor(ItemStack stack) {
        var equippable = stack.get(DataComponentTypes.EQUIPPABLE);
        if (equippable == null) {
            return false;
        }

        EquipmentSlot slot = equippable.slot();
        return slot == EquipmentSlot.HEAD ||
                slot == EquipmentSlot.CHEST ||
                slot == EquipmentSlot.LEGS ||
                slot == EquipmentSlot.FEET;
    }

    /**
     * Gets the total stat value from a specific item.
     * Used for stats that can't be applied as item attributes (like durability bonus, fortune, etc.)
     */
    public static double getItemStatValue(ItemStack stack, String statId) {
        if (stack.isEmpty()) {
            return 0.0;
        }

        List<ItemStatManager.ItemStat> stats = ItemStatManager.getStats(stack);
        for (ItemStatManager.ItemStat itemStat : stats) {
            if (itemStat.statId.equals(statId)) {
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