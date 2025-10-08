package com.startraveler.vampiricllamas.data;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.VampiricLlamasEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class VampiricLlamasMobEffectTagProvider extends TagsProvider<MobEffect> {
    public VampiricLlamasMobEffectTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.MOB_EFFECT, lookupProvider, VampiricLlamas.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(VampiricLlamas.FLIGHT_DISABLING_EFFECTS).add(VampiricLlamasEffects.BAT_CURSE.getKey());

    }
}
