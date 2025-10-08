package com.startraveler.vampiricllamas.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin {

    @SuppressWarnings("unused")
    @ModifyReturnValue(method = "shouldRender(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At(value = "RETURN"))
    private <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> boolean makeVampireArmorsNotRenderInTheDark(boolean original, @Local(argsOnly = true) ItemStack itemStack, @Local(argsOnly = true) T entity) {
        final Level level = entity.level();
        final BlockPos blockPos = entity.blockPosition();
        boolean isInvisibleArmor = VampiricLlamas.isInvisibleArmor(itemStack, level.registryAccess());
        boolean armorIsInvisible = isInvisibleArmor && VampiricLlamas.isDarkEnoughForVampireEffects(level, blockPos);
        return original && !armorIsInvisible;
    }

}
