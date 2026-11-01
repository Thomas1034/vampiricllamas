package com.startraveler.vampiricllamas.mixin;

import com.startraveler.vampiricllamas.client.renderer.LlamaBloodSpitRenderer;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LlamaSpitRenderer.class)
public class LlamaSpitRendererMixin {

    @ModifyArg(method = "submit(Lnet/minecraft/client/renderer/entity/state/LlamaSpitRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/resources/Identifier;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"), index = 3 // The texture is the 3rd parameter (0-based index)
    )
    public Identifier modifyTextureLocation(Identifier original) {
        LlamaSpitRenderer llamaSpit = (LlamaSpitRenderer) (Object) (this);

        if (llamaSpit instanceof LlamaBloodSpitRenderer) {
            return LlamaBloodSpitRenderer.LLAMA_BLOOD_SPIT_LOCATION;
        }
        return original;
    }
}
