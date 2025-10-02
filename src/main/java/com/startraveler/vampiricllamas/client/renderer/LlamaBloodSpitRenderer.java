package com.startraveler.vampiricllamas.client.renderer;

import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.LlamaSpit;
import org.jetbrains.annotations.NotNull;

public class LlamaBloodSpitRenderer extends LlamaSpitRenderer {
    public static final ResourceLocation LLAMA_BLOOD_SPIT_LOCATION = VampiricLlamas.location(
            "textures/entity/llama/blood_spit.png");

    public LlamaBloodSpitRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LlamaSpit entity) {
        return LLAMA_BLOOD_SPIT_LOCATION;
    }
}
