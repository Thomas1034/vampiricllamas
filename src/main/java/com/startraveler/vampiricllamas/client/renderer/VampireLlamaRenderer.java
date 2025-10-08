package com.startraveler.vampiricllamas.client.renderer;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.entity.VampireLlama;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.level.Level;
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
        Level level = entity.level();
        if ((entity instanceof VampireLlama vampireLlama && vampireLlama.isAttacking()) || !VampiricLlamas.isDay(
                level
        )) {
            return VAMPIRE_LLAMA_LOCATION;
        } else {

            return super.getTextureLocation(entity);
        }
    }
}
