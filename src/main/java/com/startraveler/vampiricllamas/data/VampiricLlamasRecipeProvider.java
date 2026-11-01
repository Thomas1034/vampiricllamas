package com.startraveler.vampiricllamas.data;

import com.startraveler.vampiricllamas.VampiricLlamasItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class VampiricLlamasRecipeProvider extends RecipeProvider {

    public VampiricLlamasRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
        super(lookupProvider, output);
    }

    @Override
    protected void buildRecipes() {
        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.TOTEM_OF_PERSISTING)
                .pattern("BLB")
                .pattern("LFL")
                .pattern("BIB")
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .define('F', VampiricLlamasItems.LLAMA_FANG)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Tags.Items.BONES)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        this.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(this.output, "totem_of_persisting");

        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.TOTEM_OF_ENVENOMATION)
                .pattern("BLB")
                .pattern("LFL")
                .pattern("BIB")
                .define('L', VampiricLlamasItems.VAMPIRE_SCALE)
                .define('F', VampiricLlamasItems.LLAMA_FANG)
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('B', Items.SPIDER_EYE)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_SCALE),
                        this.has(VampiricLlamasItems.VAMPIRE_SCALE)
                )
                .save(this.output, "totem_of_envenomation");

        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.FANG_KNIFE)
                .pattern(" F")
                .pattern("B ")
                .define('F', VampiricLlamasItems.LLAMA_FANG)
                .define('B', Tags.Items.RODS_WOODEN)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.LLAMA_FANG),
                        this.has(VampiricLlamasItems.LLAMA_FANG)
                )
                .save(this.output, "fang_knife");

        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_LEATHER_HELMET)
                .pattern("LLL")
                .pattern("L L")
                .pattern("   ")
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        this.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(this.output, "vampire_leather_helmet");
        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE)
                .pattern("L L")
                .pattern("LLL")
                .pattern("LLL")
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        this.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(this.output, "vampire_leather_chestplate");
        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_CLOAK)
                .pattern("PCP")
                .pattern("PLP")
                .pattern("L L")
                .define('C', Items.LEATHER)
                .define('P', Items.PHANTOM_MEMBRANE)
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        this.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(this.output, "vampire_cloak");
        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS)
                .pattern("LLL")
                .pattern("L L")
                .pattern("L L")
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        this.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(this.output, "vampire_leather_leggings");
        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS)
                .pattern("   ")
                .pattern("L L")
                .pattern("L L")
                .define('L', VampiricLlamasItems.VAMPIRE_LEATHER)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_LEATHER),
                        this.has(VampiricLlamasItems.VAMPIRE_LEATHER)
                )
                .save(this.output, "vampire_leather_boots");

        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_SCALE_HELMET)
                .pattern("LLL")
                .pattern("L L")
                .pattern("   ")
                .define('L', VampiricLlamasItems.VAMPIRE_SCALE)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_SCALE),
                        this.has(VampiricLlamasItems.VAMPIRE_SCALE)
                )
                .save(this.output, "vampire_scale_helmet");
        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_SCALE_CHESTPLATE)
                .pattern("L L")
                .pattern("LLL")
                .pattern("LLL")
                .define('L', VampiricLlamasItems.VAMPIRE_SCALE)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_SCALE),
                        this.has(VampiricLlamasItems.VAMPIRE_SCALE)
                )
                .save(this.output, "vampire_scale_chestplate");
        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_SCALE_LEGGINGS)
                .pattern("LLL")
                .pattern("L L")
                .pattern("L L")
                .define('L', VampiricLlamasItems.VAMPIRE_SCALE)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_SCALE),
                        this.has(VampiricLlamasItems.VAMPIRE_SCALE)
                )
                .save(this.output, "vampire_scale_leggings");
        this.shaped(RecipeCategory.COMBAT, VampiricLlamasItems.VAMPIRE_SCALE_BOOTS)
                .pattern("   ")
                .pattern("L L")
                .pattern("L L")
                .define('L', VampiricLlamasItems.VAMPIRE_SCALE)
                .unlockedBy(
                        VampiricLlamasRecipeProvider.getHasName(VampiricLlamasItems.VAMPIRE_SCALE),
                        this.has(VampiricLlamasItems.VAMPIRE_SCALE)
                )
                .save(this.output, "vampire_scale_boots");
        this.dyedItem(VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get(), "dyed_armor");
        this.dyedItem(VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get(), "dyed_armor");
        this.dyedItem(VampiricLlamasItems.VAMPIRE_CLOAK.get(), "dyed_armor");
        this.dyedItem(VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get(), "dyed_armor");
        this.dyedItem(VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get(), "dyed_armor");
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registries, @NonNull RecipeOutput output) {
            return new VampiricLlamasRecipeProvider(registries, output);
        }

        @Override
        public @NonNull String getName() {
            return "Vampiric Llamas Recipes";
        }
    }
}