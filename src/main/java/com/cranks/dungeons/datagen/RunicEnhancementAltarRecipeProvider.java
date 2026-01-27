package com.cranks.dungeons.datagen;

import com.cranks.dungeons.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class RunicEnhancementAltarRecipeProvider extends FabricRecipeProvider {

    public RunicEnhancementAltarRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                var itemLookup = wrapperLookup.getOrThrow(RegistryKeys.ITEM);

                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.DECORATIONS, ModBlocks.RUNIC_ENHANCEMENT_ALTAR, 1)
                        .pattern(" D ")
                        .pattern("SSS")
                        .pattern("SSS")
                        .input('D', Items.DIAMOND)
                        .input('S', Blocks.STONE)
                        .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "Runic Altar Recipes";
    }
}