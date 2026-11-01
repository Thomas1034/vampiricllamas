package com.startraveler.vampiricllamas.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nullable;

@Mixin(WingsLayer.class)
public class ElytraLayerMixin {

    @SuppressWarnings("unused")
    @ModifyExpressionValue(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;FF)V", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isEmpty()Z"))
    private boolean makeVampireArmorsNotRenderInTheDark(boolean original, @Local ItemStack itemStack, @Local(argsOnly = true) int lightCoords) {
        @Nullable ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel == null) {
            return original;
        }
        @NotNull RegistryAccess access = clientLevel.registryAccess();
        boolean isInvisibleArmor = VampiricLlamas.isInvisibleArmor(itemStack, access);
        int maxLight = Math.max(LightCoordsUtil.smoothBlock(lightCoords), LightCoordsUtil.smoothSky(lightCoords));
        boolean armorIsInvisible = isInvisibleArmor && VampiricLlamas.isDarkEnoughForVampireEffects(maxLight);
        return original || armorIsInvisible;
    }

}
