package com.cranks.dungeons.equipment;

import com.cranks.dungeons.CranksDungeons;
import com.cranks.dungeons.stat.CustomStat;
import com.cranks.dungeons.stat.StatRegistry;
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

        List<ItemStack> equippedItems = new ArrayList<>();

        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = player.getEquippedStack(slot);
            if (!stack.isEmpty()) {
                equippedItems.add(stack);
            }
        }

        ItemStack mainHand = player.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!mainHand.isEmpty()) {
            equippedItems.add(mainHand);
        }

        ItemStack offhand = player.getEquippedStack(EquipmentSlot.OFFHAND);
        if (!offhand.isEmpty()) {
            equippedItems.add(offhand);
        }

        Map<String, Double> totalStats = new HashMap<>();

        for (ItemStack stack : equippedItems) {
            List<ItemStatManager.ItemStat> stats = ItemStatManager.getStats(stack);
            for (ItemStatManager.ItemStat itemStat : stats) {
                totalStats.merge(itemStat.statId, itemStat.value, Double::sum);
            }
        }

        for (Map.Entry<String, Double> entry : totalStats.entrySet()) {
            String statId = entry.getKey();
            double totalValue = entry.getValue();

            CustomStat stat = StatRegistry.getStat(statId);
            if (stat != null && totalValue != 0) {
                applyStatModifier(player, stat, totalValue);
            }
        }
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