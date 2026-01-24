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

        String itemName = item.toString().toLowerCase();

        System.out.println("Checking equipment type for: " + itemName);

        // Check for weapons
        if (itemName.contains("sword")) {
            System.out.println("Recognized as SWORD");
            return Optional.of(SWORD);
        }
        if (itemName.contains("mace")) {
            System.out.println("Recognized as MACE");
            return Optional.of(MACE);
        }
        if (itemName.contains("spear")) {
            System.out.println("Recognized as SPEAR");
            return Optional.of(SPEAR);
        }

        // Check for tools (order matters - axe before pickaxe)
        if (itemName.contains("axe") && !itemName.contains("pickaxe")) {
            System.out.println("Recognized as AXE");
            return Optional.of(AXE);
        }
        if (itemName.contains("pickaxe")) {
            System.out.println("Recognized as PICKAXE");
            return Optional.of(PICKAXE);
        }
        if (itemName.contains("shovel")) {
            System.out.println("Recognized as SHOVEL");
            return Optional.of(SHOVEL);
        }
        if (itemName.contains("hoe")) {
            System.out.println("Recognized as HOE");
            return Optional.of(HOE);
        }

        // Check for ranged weapons
        if (itemName.contains("bow") && !itemName.contains("crossbow")) {
            System.out.println("Recognized as BOW");
            return Optional.of(BOW);
        }
        if (itemName.contains("crossbow")) {
            System.out.println("Recognized as CROSSBOW");
            return Optional.of(CROSSBOW);
        }
        if (itemName.contains("trident")) {
            System.out.println("Recognized as TRIDENT");
            return Optional.of(TRIDENT);
        }

        // Check for misc equipment
        if (itemName.contains("shield")) {
            System.out.println("Recognized as SHIELD");
            return Optional.of(SHIELD);
        }
        if (itemName.contains("elytra")) {
            System.out.println("Recognized as ELYTRA");
            return Optional.of(ELYTRA);
        }

        System.out.println("NOT RECOGNIZED as any equipment type");
        return Optional.empty();
    }

    public static boolean canEnhance(ItemStack stack) {
        return getTypeForItem(stack).isPresent();
    }
}