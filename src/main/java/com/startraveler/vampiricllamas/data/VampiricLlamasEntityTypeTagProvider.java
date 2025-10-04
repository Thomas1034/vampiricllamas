package com.startraveler.vampiricllamas.data;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.VampiricLlamasEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class VampiricLlamasEntityTypeTagProvider extends EntityTypeTagsProvider {
    public VampiricLlamasEntityTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        super(output, lookupProvider, VampiricLlamas.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(VampiricLlamasEntities.LLAMA_BLOOD_SPIT.get());
        this.tag(EntityTypeTags.UNDEAD).add(VampiricLlamasEntities.VAMPIRE_LLAMA.get());
        this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(VampiricLlamasEntities.VAMPIRE_LLAMA.get());

    }
}
