package com.startraveler.vampiricllamas.data;

import com.startraveler.vampiricllamas.VampiricLlamasItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class VampiricLlamasRecipeProvider extends RecipeProvider {
    // Get the parameters from GatherDataEvent.
    public VampiricLlamasRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.TOTEM_OF_PERSISTING)
                .pattern("BLB")
                .pattern("LFL")
                .pattern("BIB")
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .define('F', VampiricLlamasItems.LLAMA_FANG)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Tags.Items.BONES)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        VampiricLlamasRecipeProvider.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(output, "totem_of_persisting");

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.FANG_KNIFE)
                .pattern(" F")
                .pattern("B ")
                .define('F', VampiricLlamasItems.LLAMA_FANG)
                .define('B', Tags.Items.RODS_WOODEN)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.LLAMA_FANG),
                        VampiricLlamasRecipeProvider.has(VampiricLlamasItems.LLAMA_FANG)
                )
                .save(output, "fang_knife");

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_LEATHER_HELMET)
                .pattern("LLL")
                .pattern("L L")
                .pattern("   ")
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        VampiricLlamasRecipeProvider.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(output, "vampire_leather_helmet");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE)
                .pattern("L L")
                .pattern("LLL")
                .pattern("LLL")
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        VampiricLlamasRecipeProvider.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(output, "vampire_leather_chestplate");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_CLOAK)
                .pattern("PCP")
                .pattern("PLP")
                .pattern("L L")
                .define('C', Items.LEATHER)
                .define('P', Items.PHANTOM_MEMBRANE)
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        VampiricLlamasRecipeProvider.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(output, "vampire_cloak");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS)
                .pattern("LLL")
                .pattern("L L")
                .pattern("L L")
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        VampiricLlamasRecipeProvider.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(output, "vampire_leather_leggings");
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS)
                .pattern("   ")
                .pattern("L L")
                .pattern("L L")
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        VampiricLlamasRecipeProvider.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(output, "vampire_leather_boots");

    }
}