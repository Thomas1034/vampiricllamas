package com.startraveler.vampiricllamas.data;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.VampiricLlamasArmorMaterials;
import com.startraveler.vampiricllamas.VampiricLlamasItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

public class VampiricLlamasModelProvider extends ModelProvider {

    public VampiricLlamasModelProvider(PackOutput output) {
        super(output, VampiricLlamas.MODID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {

        itemModels.generateFlatItem(VampiricLlamasItems.VAMPIRE_LLAMA_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(VampiricLlamasItems.LLAMIA_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(VampiricLlamasItems.VAMPIRE_LEATHER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(VampiricLlamasItems.VAMPIRE_SCALE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(VampiricLlamasItems.LLAMA_FANG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(VampiricLlamasItems.TOTEM_OF_PERSISTING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(VampiricLlamasItems.TOTEM_OF_ENVENOMATION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(VampiricLlamasItems.FANG_KNIFE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateTrimmableItem(
                VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get(),
                VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_KEY, ItemModelGenerators.TRIM_PREFIX_HELMET, true
        );
        itemModels.generateTrimmableItem(
                VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get(),
                VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_KEY,
                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE,
                true
        );
        itemModels.generateTrimmableItem(
                VampiricLlamasItems.VAMPIRE_CLOAK.get(),
                VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_KEY,
                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE,
                true
        );
        itemModels.generateTrimmableItem(
                VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get(),
                VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_KEY,
                ItemModelGenerators.TRIM_PREFIX_LEGGINGS,
                true
        );
        itemModels.generateTrimmableItem(
                VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get(),
                VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_KEY,
                ItemModelGenerators.TRIM_PREFIX_BOOTS,
                true
        );
        itemModels.generateTrimmableItem(
                VampiricLlamasItems.VAMPIRE_SCALE_HELMET.get(),
                VampiricLlamasArmorMaterials.VAMPIRE_SCALE_KEY,
                ItemModelGenerators.TRIM_PREFIX_HELMET, false
        );
        itemModels.generateTrimmableItem(
                VampiricLlamasItems.VAMPIRE_SCALE_CHESTPLATE.get(),
                VampiricLlamasArmorMaterials.VAMPIRE_SCALE_KEY,
                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false
        );
        itemModels.generateTrimmableItem(
                VampiricLlamasItems.VAMPIRE_SCALE_LEGGINGS.get(),
                VampiricLlamasArmorMaterials.VAMPIRE_SCALE_KEY,
                ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false
        );
        itemModels.generateTrimmableItem(
                VampiricLlamasItems.VAMPIRE_SCALE_BOOTS.get(),
                VampiricLlamasArmorMaterials.VAMPIRE_SCALE_KEY,
                ItemModelGenerators.TRIM_PREFIX_BOOTS, false
        );
    }
}
