package com.startraveler.vampiricllamas;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class VampiricLlamasToolTiers {

    public static final Tier FANG = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            380, 6.0F, 1.0F, 8,
            () -> Ingredient.of(Tags.Items.BONES)
    );

}
