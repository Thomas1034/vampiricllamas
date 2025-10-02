package com.startraveler.vampiricllamas.client.renderer;

import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;
import org.jetbrains.annotations.NotNull;

public class VampireLlamaRenderer extends LlamaRenderer {

    public static final ResourceLocation VAMPIRE_LLAMA_LOCATION = VampiricLlamas.location(
            "textures/entity/llama/vampire_llama.png");

    public VampireLlamaRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
        super(context, layer);
        this.addLayer(new VampireEyesLayer<>(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Llama entity) {
        return VAMPIRE_LLAMA_LOCATION;
    }

}
