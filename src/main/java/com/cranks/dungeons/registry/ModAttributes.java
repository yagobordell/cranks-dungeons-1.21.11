package com.cranks.dungeons.registry;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModAttributes {

    public static final String MOD_ID = "cranks-dungeons";

    // Register attributes immediately when class loads (before mixins run)
    public static final RegistryEntry<EntityAttribute> FIRE_RESISTANCE = register("fire_resistance", 0.0, 0.0, 0.75);
    public static final RegistryEntry<EntityAttribute> COLD_RESISTANCE = register("cold_resistance", 0.0, 0.0, 0.75);
    public static final RegistryEntry<EntityAttribute> LIGHTNING_RESISTANCE = register("lightning_resistance", 0.0, 0.0, 0.75);
    public static final RegistryEntry<EntityAttribute> VOID_RESISTANCE = register("void_resistance", 0.0, 0.0, 0.75);
    public static final RegistryEntry<EntityAttribute> CRIT_CHANCE = register("crit_chance", 0.05, 0.0, 1.0);

    // This method now just ensures the class is loaded
    public static void registerAttributes() {
        // Attributes are already registered via static initializers above
        System.out.println("Custom attributes loaded: fire_resistance, cold_resistance, lightning_resistance, void_resistance, crit_chance");
    }

    private static RegistryEntry<EntityAttribute> register(String name, double base, double min, double max) {
        Identifier id = Identifier.of(MOD_ID, name);

        EntityAttribute attribute = new ClampedEntityAttribute(
                "attribute.name." + MOD_ID + "." + name,
                base, min, max
        ).setTracked(true);

        Registry.register(Registries.ATTRIBUTE, id, attribute);

        return Registries.ATTRIBUTE.getEntry(id).orElseThrow(
                () -> new IllegalStateException("Failed to register attribute: " + name)
        );
    }
}