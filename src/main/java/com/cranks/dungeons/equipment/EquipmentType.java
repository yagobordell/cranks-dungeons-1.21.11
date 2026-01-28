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
        if (stack.isEmpty()) return Optional.empty();
        Item item = stack.getItem();
        String itemName = item.toString().toLowerCase();

        // 1. Check for specific Unique Items FIRST
        // This prevents Elytra from being caught by the generic CHEST slot check
        if (stack.isOf(Items.ELYTRA) || itemName.contains("elytra")) {
            return Optional.of(ELYTRA);
        }
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

        // 2. Check for generic Armor slots
        // Items like Chestplates will be caught here, but Elytra is already handled above
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

        // 3. Check for weapons/tools by name
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

        return Optional.empty();
    }

    public static boolean canEnhance(ItemStack stack) {
        return getTypeForItem(stack).isPresent();
    }
}