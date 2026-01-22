package com.cranks.dungeons.datagen;

import com.cranks.dungeons.CranksDungeons;
import com.cranks.dungeons.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class RunicTomeRecipeProvider extends FabricRecipeProvider {

    public RunicTomeRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                var itemLookup = wrapperLookup.getOrThrow(RegistryKeys.ITEM);

                createTomeUpgradeRecipe(itemLookup, ModItems.RUNIC_TOME_T1, ModItems.RUNIC_TOME_T2, "t1_to_t2");
                createTomeUpgradeRecipe(itemLookup, ModItems.RUNIC_TOME_T2, ModItems.RUNIC_TOME_T3, "t2_to_t3");
                createTomeUpgradeRecipe(itemLookup, ModItems.RUNIC_TOME_T3, ModItems.RUNIC_TOME_T4, "t3_to_t4");
                createTomeUpgradeRecipe(itemLookup, ModItems.RUNIC_TOME_T4, ModItems.RUNIC_TOME_T5, "t4_to_t5");
            }

            private void createTomeUpgradeRecipe(RegistryWrapper.Impl<Item> itemLookup, Item input, Item output, String name) {
                ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.MISC, output, 1)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .input('#', input)
                        .criterion(hasItem(input), conditionsFromItem(input))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "Runic Tome Recipes";
    }
}