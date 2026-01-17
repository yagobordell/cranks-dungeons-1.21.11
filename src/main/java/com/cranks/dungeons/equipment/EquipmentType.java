package com.cranks.dungeons.equipment;

import com.cranks.dungeons.stat.StatCategory;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.*;
import net.minecraft.component.DataComponentTypes;

import java.util.*;

public enum EquipmentType {
    HELMET(StatCategory.DEFENSIVE),
    CHESTPLATE(StatCategory.DEFENSIVE, StatCategory.OFFENSIVE),
    LEGGINGS(StatCategory.DEFENSIVE),
    BOOTS(StatCategory.DEFENSIVE, StatCategory.UTILITY),

    SWORD(StatCategory.OFFENSIVE),
    AXE(StatCategory.TOOL),
    PICKAXE(StatCategory.TOOL),
    SHOVEL(StatCategory.TOOL),
    HOE(StatCategory.TOOL),

    BOW(StatCategory.OFFENSIVE),
    CROSSBOW(StatCategory.OFFENSIVE),
    TRIDENT(StatCategory.OFFENSIVE),

    SHIELD(StatCategory.DEFENSIVE),
    ELYTRA(StatCategory.UTILITY);

    private final List<StatCategory> allowedCategories;

    EquipmentType(StatCategory... categories) {
        this.allowedCategories = Arrays.asList(categories);
    }

    public List<StatCategory> getAllowedCategories() {
        return allowedCategories;
    }

    public static Optional<EquipmentType> getTypeForItem(ItemStack stack) {
        Item item = stack.getItem();

        EquippableComponent equippable = stack.get(DataComponentTypes.EQUIPPABLE);
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

        if (itemName.contains("sword")) return Optional.of(SWORD);

        if (itemName.contains("axe") && !itemName.contains("pickaxe")) return Optional.of(AXE);

        if (itemName.contains("pickaxe")) return Optional.of(PICKAXE);
        if (itemName.contains("shovel")) return Optional.of(SHOVEL);
        if (itemName.contains("hoe")) return Optional.of(HOE);

        if (itemName.contains("bow") && !itemName.contains("crossbow")) return Optional.of(BOW);
        if (itemName.contains("crossbow")) return Optional.of(CROSSBOW);
        if (itemName.contains("trident")) return Optional.of(TRIDENT);

        if (itemName.contains("shield")) return Optional.of(SHIELD);
        if (itemName.contains("elytra")) return Optional.of(ELYTRA);

        return Optional.empty();
    }

    public static boolean canEnhance(ItemStack stack) {
        return getTypeForItem(stack).isPresent();
    }
}