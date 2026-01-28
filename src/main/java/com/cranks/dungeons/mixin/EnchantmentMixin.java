package com.cranks.dungeons.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    /**
     * @author TheCrankOf87
     * @reason Remove Roman numerals from all enchantment tooltips.
     */
    @Overwrite
    public static Text getName(RegistryEntry<Enchantment> enchantment, int level) {
        return enchantment.value().description();
    }
}