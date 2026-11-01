package com.startraveler.vampiricllamas.data;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.VampiricLlamasItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class VampiricLlamasItemTagProvider extends ItemTagsProvider {
    public VampiricLlamasItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, VampiricLlamas.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(Tags.Items.LEATHERS).add(VampiricLlamasItems.VAMPIRE_LEATHER.get());

        this.tag(VampiricLlamas.VAMPIRE_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get());
        this.tag(VampiricLlamas.VAMPIRE_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get());
        this.tag(VampiricLlamas.VAMPIRE_ARMOR).add(VampiricLlamasItems.VAMPIRE_CLOAK.get());
        this.tag(VampiricLlamas.VAMPIRE_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get());
        this.tag(VampiricLlamas.VAMPIRE_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get());
        this.tag(VampiricLlamas.INVISIBLE_ARMOR_IN_DARK).addTag(VampiricLlamas.VAMPIRE_ARMOR);
        this.tag(ItemTags.CAULDRON_CAN_REMOVE_DYE).add(VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get());
        this.tag(ItemTags.CAULDRON_CAN_REMOVE_DYE).add(VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get());
        this.tag(ItemTags.CAULDRON_CAN_REMOVE_DYE).add(VampiricLlamasItems.VAMPIRE_CLOAK.get());
        this.tag(ItemTags.CAULDRON_CAN_REMOVE_DYE).add(VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get());
        this.tag(ItemTags.CAULDRON_CAN_REMOVE_DYE).add(VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get());
        this.tag(ItemTags.ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get());
        this.tag(ItemTags.ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get());
        this.tag(ItemTags.ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get());
        this.tag(ItemTags.ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get());
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_CLOAK.get());
        this.tag(ItemTags.EQUIPPABLE_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_CLOAK.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR).add(VampiricLlamasItems.VAMPIRE_CLOAK.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get());
        this.tag(ItemTags.HEAD_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get());
        this.tag(ItemTags.CHEST_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get());
        this.tag(ItemTags.LEG_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get());
        this.tag(ItemTags.FOOT_ARMOR).add(VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get());
        this.tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get());
        this.tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get());
        this.tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get());
        this.tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get());
        this.tag(Tags.Items.ARMORS).add(VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get());
        this.tag(Tags.Items.ARMORS).add(VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get());
        this.tag(Tags.Items.ARMORS).add(VampiricLlamasItems.VAMPIRE_CLOAK.get());
        this.tag(Tags.Items.ARMORS).add(VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get());
        this.tag(Tags.Items.ARMORS).add(VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get());


        this.tag(VampiricLlamas.VAMPIRE_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_HELMET.get());
        this.tag(VampiricLlamas.VAMPIRE_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_CHESTPLATE.get());
        this.tag(VampiricLlamas.VAMPIRE_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_LEGGINGS.get());
        this.tag(VampiricLlamas.VAMPIRE_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_BOOTS.get());
        this.tag(ItemTags.ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_SCALE_HELMET.get());
        this.tag(ItemTags.ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_SCALE_CHESTPLATE.get());
        this.tag(ItemTags.ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_SCALE_LEGGINGS.get());
        this.tag(ItemTags.ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_SCALE_BOOTS.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_HELMET.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_CHESTPLATE.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_LEGGINGS.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_BOOTS.get());
        this.tag(ItemTags.HEAD_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_HELMET.get());
        this.tag(ItemTags.CHEST_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_CHESTPLATE.get());
        this.tag(ItemTags.LEG_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_LEGGINGS.get());
        this.tag(ItemTags.FOOT_ARMOR).add(VampiricLlamasItems.VAMPIRE_SCALE_BOOTS.get());
        this.tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_SCALE_HELMET.get());
        this.tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_SCALE_CHESTPLATE.get());
        this.tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_SCALE_LEGGINGS.get());
        this.tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(VampiricLlamasItems.VAMPIRE_SCALE_BOOTS.get());
        this.tag(Tags.Items.ARMORS).add(VampiricLlamasItems.VAMPIRE_SCALE_HELMET.get());
        this.tag(Tags.Items.ARMORS).add(VampiricLlamasItems.VAMPIRE_SCALE_CHESTPLATE.get());
        this.tag(Tags.Items.ARMORS).add(VampiricLlamasItems.VAMPIRE_SCALE_LEGGINGS.get());
        this.tag(Tags.Items.ARMORS).add(VampiricLlamasItems.VAMPIRE_SCALE_BOOTS.get());

        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(VampiricLlamasItems.TOTEM_OF_ENVENOMATION.get());

    }
}
