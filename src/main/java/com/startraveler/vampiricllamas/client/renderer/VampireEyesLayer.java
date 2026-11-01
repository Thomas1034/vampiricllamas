package com.startraveler.vampiricllamas.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.client.VampireLlamaRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class VampireEyesLayer<S extends VampireLlamaRenderState, M extends EntityModel<S>> extends EyesLayer<S, M> {
    private static final Identifier INVISIBLE_VAMPIRE_EYES = VampiricLlamas.id(
            "textures/entity/llama/vampire_llama_eyes_hidden.png");
    protected final RenderType visibleType;
    protected final RenderType invisibleType;

    public VampireEyesLayer(RenderLayerParent<S, M> parent, Identifier location) {
        super(parent);
        this.visibleType = RenderTypes.eyes(location);
        this.invisibleType = RenderTypes.eyes(INVISIBLE_VAMPIRE_EYES);
    }


    @Override
    public void submit(@NonNull PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float yRot, float xRot) {
        submitNodeCollector.order(1)
                .submitModel(
                        this.getParentModel(),
                        state,
                        poseStack,
                        this.renderType(state),
                        lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        state.outlineColor,
                        null
                );
    }


    @Override
    public @NotNull RenderType renderType() {
        return this.renderType(null);
    }

    protected RenderType renderType(@Nullable S state) {
        if (state == null) {
            return this.invisibleType;
        }
        if (state.isAttacking || state.isNight) {
            return this.visibleType;
        } else {

            return this.invisibleType;
        }
    }
}
