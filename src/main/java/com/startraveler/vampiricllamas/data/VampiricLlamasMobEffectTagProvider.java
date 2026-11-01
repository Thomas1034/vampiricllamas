package com.startraveler.vampiricllamas.data;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.VampiricLlamasEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class VampiricLlamasMobEffectTagProvider extends IntrinsicHolderTagsProvider<MobEffect> {
    public VampiricLlamasMobEffectTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(
                output,
                Registries.MOB_EFFECT,
                lookupProvider,
                mobEffect -> ResourceKey.create(
                        Registries.MOB_EFFECT,
                        Objects.requireNonNull(BuiltInRegistries.MOB_EFFECT.getKey(mobEffect))
                ),
                VampiricLlamas.MODID
        );
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(VampiricLlamas.FLIGHT_DISABLING_EFFECTS).add(VampiricLlamasEffects.BAT_CURSE.get());
    }
}
