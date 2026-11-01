package com.startraveler.vampiricllamas.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nullable;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorModelMixin {

    @ModifyExpressionValue(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;shouldRender(Lnet/minecraft/world/item/equipment/Equippable;Lnet/minecraft/world/entity/EquipmentSlot;)Z"))
    private <S extends HumanoidRenderState> boolean makeVampireArmorsNotRenderInTheDark(boolean original, @Local(argsOnly = true) ItemStack itemStack, @Local(argsOnly = true) S state) {
        @Nullable ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel == null) {
            return original;
        }
        @NotNull RegistryAccess access = clientLevel.registryAccess();
        boolean isInvisibleArmor = VampiricLlamas.isInvisibleArmor(itemStack, access);
        int maxLight = Math.max(
                LightCoordsUtil.smoothBlock(state.lightCoords),
                LightCoordsUtil.smoothSky(state.lightCoords)
        );
        boolean armorIsInvisible = isInvisibleArmor && VampiricLlamas.isDarkEnoughForVampireEffects(maxLight);
        return original && !armorIsInvisible;
    }

}
