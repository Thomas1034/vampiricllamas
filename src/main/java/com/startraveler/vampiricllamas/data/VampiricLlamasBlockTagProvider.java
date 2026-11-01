package com.startraveler.vampiricllamas.data;

import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class VampiricLlamasBlockTagProvider extends BlockTagsProvider {
    public VampiricLlamasBlockTagProvider(PackOutput output,
                                          CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, VampiricLlamas.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

    }
}
