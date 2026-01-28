package com.cranks.dungeons.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class SingleEnchantMixin {

    // Using HEAD to prevent the extra rolls from even happening
    @Inject(method = "generateEnchantments", at = @At("RETURN"), cancellable = true)
    private static void forceSingleEnchantment(Random random, ItemStack stack, int level, Stream<RegistryEntry<Enchantment>> possibleEnchantments, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        List<EnchantmentLevelEntry> list = cir.getReturnValue();

        if (list != null && list.size() > 1) {
            // We only care about the first one rolled
            List<EnchantmentLevelEntry> singleList = new ArrayList<>();
            singleList.add(list.get(0));
            cir.setReturnValue(singleList);
        }
    }
}