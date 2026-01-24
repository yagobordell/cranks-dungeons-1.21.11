package com.cranks.dungeons.equipment;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import java.util.*;

public enum EquipmentType {
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,

    SWORD,
    SPEAR,
    MACE,
    AXE,
    PICKAXE,
    SHOVEL,
    HOE,

    BOW,
    CROSSBOW,
    TRIDENT,

    SHIELD,
    ELYTRA;

    public static Optional<EquipmentType> getTypeForItem(ItemStack stack) {
        Item item = stack.getItem();

        if (item instanceof ShieldItem) {
            return Optional.of(SHIELD);
        }
        // Check for armor pieces first
        var equippable = stack.get(DataComponentTypes.EQUIPPABLE);
        if (equippable != null) {
            return switch (equippable.slot()) {
                case HEAD -> Optional.of(HELMET);
                case CHEST -> Optional.of(CHESTPLATE);
                case LEGS -> Optional.of(LEGGINGS);
                case FEET -> Optional.of(BOOTS);
                default -> Optional.empty();
            };
        }

        // Check by item class/type first (more reliable)
        if (item instanceof ShieldItem) {
            return Optional.of(SHIELD);
        }
        if (item instanceof BowItem) {
            return Optional.of(BOW);
        }
        if (item instanceof CrossbowItem) {
            return Optional.of(CROSSBOW);
        }
        if (item instanceof TridentItem) {
            return Optional.of(TRIDENT);
        }
        if (item instanceof MaceItem) {
            return Optional.of(MACE);
        }

        String itemName = item.toString().toLowerCase();

        // Check for weapons
        if (itemName.contains("sword")) {
            return Optional.of(SWORD);
        }
        if (itemName.contains("spear")) {
            return Optional.of(SPEAR);
        }

        // Check for tools (order matters - axe before pickaxe)
        if (itemName.contains("axe") && !itemName.contains("pickaxe")) {
            return Optional.of(AXE);
        }
        if (itemName.contains("pickaxe")) {
            return Optional.of(PICKAXE);
        }
        if (itemName.contains("shovel")) {
            return Optional.of(SHOVEL);
        }
        if (itemName.contains("hoe")) {
            return Optional.of(HOE);
        }

        // Check for elytra by name
        if (itemName.contains("elytra")) {
            return Optional.of(ELYTRA);
        }

        return Optional.empty();
    }

    public static boolean canEnhance(ItemStack stack) {
        return getTypeForItem(stack).isPresent();
    }
}