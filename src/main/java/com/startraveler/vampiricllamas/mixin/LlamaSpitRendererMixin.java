package com.startraveler.vampiricllamas.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.vampiricllamas.client.renderer.LlamaBloodSpitRenderer;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.LlamaSpit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LlamaSpitRenderer.class)
public class LlamaSpitRendererMixin {

    @ModifyArg(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/LlamaSpitModel;renderType(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"), index = 0 // The texture is the 1nd parameter (0-based index)
    )
    public ResourceLocation modifyTextureLocation(ResourceLocation original, @Local(argsOnly = true) LlamaSpit entity) {
        LlamaSpitRenderer llamaSpit = (LlamaSpitRenderer) (Object) (this);

        if (llamaSpit instanceof LlamaBloodSpitRenderer bloodSpitRenderer) {
            return bloodSpitRenderer.getTextureLocation(entity);
        }
        return original;
    }
}
