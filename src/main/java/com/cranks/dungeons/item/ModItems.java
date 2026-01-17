package com.cranks.dungeons.item;

import com.cranks.dungeons.CranksDungeons;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item RUNIC_TOME_T1 = registerItem("runic_tome_t1", 1);
    public static final Item RUNIC_TOME_T2 = registerItem("runic_tome_t2", 2);
    public static final Item RUNIC_TOME_T3 = registerItem("runic_tome_t3", 3);
    public static final Item RUNIC_TOME_T4 = registerItem("runic_tome_t4", 4);
    public static final Item RUNIC_TOME_T5 = registerItem("runic_tome_t5", 5);

    private static Item registerItem(String name, int tier) {
        Identifier id = Identifier.of(CranksDungeons.MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

        return Registry.register(Registries.ITEM, key,
                new RunicTomeItem(tier, new Item.Settings().registryKey(key)));
    }

    public static void registerModItems() {
        System.out.println("Registering items for " + CranksDungeons.MOD_ID);
    }
}