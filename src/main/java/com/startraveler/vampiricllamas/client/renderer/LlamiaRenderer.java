package com.startraveler.vampiricllamas.client.renderer;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.entity.Llamia;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LlamiaRenderer extends LlamaRenderer {

    public static final ResourceLocation LLAMIA_LOCATION = VampiricLlamas.location(
            "textures/entity/llama/llamia.png");
    private static final ResourceLocation YELLOW_VAMPIRE_EYES = VampiricLlamas.location(
            "textures/entity/llama/llamia_eyes.png");

    public LlamiaRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
        super(context, layer);
        this.addLayer(new VampireEyesLayer<>(this, YELLOW_VAMPIRE_EYES));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Llama entity) {
        Level level = entity.level();
        if ((entity instanceof Llamia llamia && llamia.isAttacking()) || !VampiricLlamas.isDay(
                level
        )) {
            return LLAMIA_LOCATION;
        } else {

            return super.getTextureLocation(entity);
        }
    }
}
