package com.startraveler.vampiricllamas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorModelMixin {

    @WrapOperation(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/Model;ILnet/minecraft/resources/ResourceLocation;)V"))
    private <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> void makeVampireArmorsNotRenderInTheDark(HumanoidArmorLayer<T, M, A> instance, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Model model, int tintColor, ResourceLocation location, Operation<Void> originalOperation, @Local ItemStack itemStack, @Local(argsOnly = true) T entity) {
        final Level level = entity.level();
        final BlockPos blockPos = entity.blockPosition();
        boolean isInvisibleArmor = VampiricLlamas.isInvisibleArmor(itemStack, level.registryAccess());
        boolean armorIsInvisible = isInvisibleArmor && VampiricLlamas.isDarkEnoughForVampireEffects(level, blockPos);
        if (!armorIsInvisible) {
            originalOperation.call(instance, poseStack, bufferSource, packedLight, model, tintColor, location);
        }
    }


    @WrapOperation(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderGlint(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/Model;)V"))
    private <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> void makeVampireArmorsNotGlintInTheDark(HumanoidArmorLayer<T, M, A> instance, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Model model, Operation<Void> originalOperation, @Local ItemStack itemStack, @Local(argsOnly = true) T entity) {
        final Level level = entity.level();
        boolean isInvisibleArmor = VampiricLlamas.isInvisibleArmor(itemStack, level.registryAccess());
        boolean armorIsInvisible = isInvisibleArmor && VampiricLlamas.isDarkEnoughForVampireEffects(
                level,
                packedLight
        ) && entity.isInvisible();
        if (!armorIsInvisible) {
            originalOperation.call(instance, poseStack, bufferSource, packedLight, model);
        }
    }

}
