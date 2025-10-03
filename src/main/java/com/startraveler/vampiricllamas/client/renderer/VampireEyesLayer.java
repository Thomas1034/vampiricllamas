package com.startraveler.vampiricllamas.client.renderer;

import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;
import org.jetbrains.annotations.NotNull;

public class VampireEyesLayer<T extends Llama, M extends LlamaModel<T>> extends EyesLayer<T, M> {
    private static final ResourceLocation DEFAULT_VAMPIRE_EYES = VampiricLlamas.location(
            "textures/entity/llama/vampire_llama_eyes.png");
    protected final RenderType type;

    public VampireEyesLayer(RenderLayerParent<T, M> parent) {
        this(parent, DEFAULT_VAMPIRE_EYES);
    }

    public VampireEyesLayer(RenderLayerParent<T, M> parent, ResourceLocation location) {
        super(parent);
        this.type = RenderType.eyes(location);
    }

    @Override
    public @NotNull RenderType renderType() {
        // TODO override somehow so their eyes don't glow in the daytime?
        // How do I get entity context?
        return this.type;
    }
}
