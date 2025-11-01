package com.startraveler.vampiricllamas.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.entity.VampireLlama;
import com.startraveler.vampiricllamas.entity.VampiresThatAttack;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VampireEyesLayer<T extends Llama, M extends LlamaModel<T>> extends EyesLayer<T, M> {
    private static final ResourceLocation DEFAULT_VAMPIRE_EYES = VampiricLlamas.location(
            "textures/entity/llama/vampire_llama_eyes.png");
    private static final ResourceLocation INVISIBLE_VAMPIRE_EYES = VampiricLlamas.location(
            "textures/entity/llama/vampire_llama_eyes_hidden.png");
    protected final RenderType visibleType;
    protected final RenderType invisibleType;

    public VampireEyesLayer(RenderLayerParent<T, M> parent) {
        this(parent, DEFAULT_VAMPIRE_EYES);
    }

    public VampireEyesLayer(RenderLayerParent<T, M> parent, ResourceLocation location) {
        super(parent);
        this.visibleType = RenderType.eyes(location);
        this.invisibleType = RenderType.eyes(INVISIBLE_VAMPIRE_EYES);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, MultiBufferSource buffer, int packedLight, @NotNull T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexconsumer = buffer.getBuffer(this.renderType(livingEntity));
        this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public @NotNull RenderType renderType() {
        return this.renderType(null);
    }

    protected RenderType renderType(@Nullable T entity) {
        if ((entity instanceof VampiresThatAttack vampireLlama && vampireLlama.isAttacking()) || !VampiricLlamas.isDay(
                entity != null ? entity.level() : null)) {
            return this.visibleType;
        } else {

            return this.invisibleType;
        }
    }
}
