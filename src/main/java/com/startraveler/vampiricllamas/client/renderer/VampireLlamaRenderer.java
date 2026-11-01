package com.startraveler.vampiricllamas.client.renderer;

import com.google.common.collect.Maps;
import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.client.VampireLlamaRenderState;
import com.startraveler.vampiricllamas.entity.VampiresThatAttack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.animal.llama.BabyLlamaModel;
import net.minecraft.client.model.animal.llama.LlamaModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LlamaRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.equine.Llama;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class VampireLlamaRenderer<T extends Llama & VampiresThatAttack> extends AgeableMobRenderer<T, VampireLlamaRenderState, LlamaModel> {

    private static final Identifier YELLOW_VAMPIRE_EYES = VampiricLlamas.id("textures/entity/llama/llamia_eyes.png");
    private static final Map<Llama.Variant, Identifier> TEXTURES = Maps.newEnumMap(Map.of(
            Llama.Variant.CREAMY,
            Identifier.withDefaultNamespace("textures/entity/llama/llama_creamy.png"),
            Llama.Variant.WHITE,
            Identifier.withDefaultNamespace("textures/entity/llama/llama_white.png"),
            Llama.Variant.BROWN,
            Identifier.withDefaultNamespace("textures/entity/llama/llama_brown.png"),
            Llama.Variant.GRAY,
            Identifier.withDefaultNamespace("textures/entity/llama/llama_gray.png")
    ));
    private static final Map<Llama.Variant, Identifier> BABY_TEXTURES = Maps.newEnumMap(Map.of(
            Llama.Variant.CREAMY,
            Identifier.withDefaultNamespace("textures/entity/llama/llama_creamy_baby.png"),
            Llama.Variant.WHITE,
            Identifier.withDefaultNamespace("textures/entity/llama/llama_white_baby.png"),
            Llama.Variant.BROWN,
            Identifier.withDefaultNamespace("textures/entity/llama/llama_brown_baby.png"),
            Llama.Variant.GRAY,
            Identifier.withDefaultNamespace("textures/entity/llama/llama_gray_baby.png")
    ));

    public VampireLlamaRenderer(EntityRendererProvider.Context context, ModelLayerLocation model, ModelLayerLocation babyModel, Identifier eyesLoc) {
        super(
                context,
                new LlamaModel(context.bakeLayer(model)),
                new BabyLlamaModel(context.bakeLayer(babyModel)),
                0.7F
        );
        this.addLayer((RenderLayer<VampireLlamaRenderState, LlamaModel>) (Object) new LlamaDecorLayer(
                (RenderLayerParent<LlamaRenderState, LlamaModel>) (Object) this,
                context.getModelSet(),
                context.getEquipmentRenderer()
        ));
        this.addLayer((RenderLayer<VampireLlamaRenderState, LlamaModel>) (Object) new VampireEyesLayer<>(
                (AgeableMobRenderer<?, VampireLlamaRenderState, EntityModel<VampireLlamaRenderState>>) (Object) this,
                eyesLoc
        ));
    }

    public @NonNull Identifier getTextureLocation(VampireLlamaRenderState state) {
        if (state.isAttacking || state.isNight) {
            return state.trueAppearance;
        }
        Map<Llama.Variant, Identifier> textures = state.isBaby ? BABY_TEXTURES : TEXTURES;
        return textures.get(state.variant);
    }

    public void extractRenderState(T entity, VampireLlamaRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.variant = entity.getVariant();
        state.hasChest = !entity.isBaby() && entity.hasChest();
        state.bodyItem = entity.getBodyArmorItem();
        state.isTraderLlama = entity.isTraderLlama();
        state.isAttacking = entity.isAttacking();
        state.trueAppearance = entity.trueAppearance();
        state.eyes = entity.eyes();
    }

    public VampireLlamaRenderState createRenderState() {
        return new VampireLlamaRenderState();
    }

}
