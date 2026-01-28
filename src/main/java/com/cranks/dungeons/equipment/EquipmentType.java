package com.cranks.dungeons.equipment;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
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

        // 1. Check for specific Classes/Items FIRST (Most Accurate)
        if (stack.isOf(Items.ELYTRA)) return Optional.of(ELYTRA);
        if (item instanceof ShieldItem) return Optional.of(SHIELD);
        if (item instanceof BowItem) return Optional.of(BOW);
        if (item instanceof CrossbowItem) return Optional.of(CROSSBOW);
        if (item instanceof TridentItem) return Optional.of(TRIDENT);
        if (item instanceof MaceItem) return Optional.of(MACE);

        // 2. Check for generic Armor slots using Data Components
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

        // 3. Check for weapons/tools using Tags and Strict Suffixes
        // This prevents "waxed_copper" matching "axe"

        // Swords
        if (stack.isIn(ItemTags.SWORDS) || itemName.endsWith("_sword") || itemName.equals("sword")) {
            return Optional.of(SWORD);
        }

        // Spears (Custom items usually follow the "_spear" naming convention)
        if (itemName.endsWith("_spear") || itemName.equals("spear")) {
            return Optional.of(SPEAR);
        }

        // Axes (Strict check: must end with _axe or be in the Axe tag)
        if (stack.isIn(ItemTags.AXES) || itemName.endsWith("_axe") || itemName.equals("axe")) {
            // Extra safety to ensure pickaxes aren't caught as axes (though endsWith solves this)
            if (!itemName.endsWith("_pickaxe")) {
                return Optional.of(AXE);
            }
        }

        // Other Tools
        if (stack.isIn(ItemTags.PICKAXES) || itemName.endsWith("_pickaxe") || itemName.equals("pickaxe")) {
            return Optional.of(PICKAXE);
        }
        if (stack.isIn(ItemTags.SHOVELS) || itemName.endsWith("_shovel") || itemName.equals("shovel")) {
            return Optional.of(SHOVEL);
        }
        if (stack.isIn(ItemTags.HOES) || itemName.endsWith("_hoe") || itemName.equals("hoe")) {
            return Optional.of(HOE);
        }

        // If it's a block or non-equipment item, it will reach here and return empty.
        return Optional.empty();
    }

    public static boolean canEnhance(ItemStack stack) {
        return getTypeForItem(stack).isPresent();
    }
}