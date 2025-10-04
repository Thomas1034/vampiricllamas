package com.startraveler.vampiricllamas.client.renderer;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.entity.VampireLlama;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VampireLlamaRenderer extends LlamaRenderer {

    public static final ResourceLocation VAMPIRE_LLAMA_LOCATION = VampiricLlamas.location(
            "textures/entity/llama/vampire_llama.png");

    public VampireLlamaRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
        super(context, layer);
        this.addLayer(new VampireEyesLayer<>(this));
    }

    public static int getSkyDarken(@NotNull Level level) {
        double d0 = 1.0 - (double) (level.getRainLevel(1.0F) * 5.0F) / 16.0;
        double d1 = 1.0 - (double) (level.getThunderLevel(1.0F) * 5.0F) / 16.0;
        double d2 = 0.5 + 2.0 * Mth.clamp(Mth.cos(level.getTimeOfDay(1.0F) * (float) (Math.PI * 2)), -0.25, 0.25);
        return (int) ((1.0 - d2 * d0 * d1) * 11.0);
    }

    public static boolean isDay(@Nullable Level level) {
        if (level == null) {
            return true;
        }
        return getSkyDarken(level) < 4;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Llama entity) {
        if ((entity instanceof VampireLlama vampireLlama && vampireLlama.isAttacking()) || !isDay(entity.level())) {
            return VAMPIRE_LLAMA_LOCATION;
        } else {

            return super.getTextureLocation(entity);
        }
    }
}
