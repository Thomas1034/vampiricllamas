package com.startraveler.vampiricllamas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @WrapOperation(method = "getArmorCoverPercentage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"))
    private boolean adjustArmorCoverPercentage(ItemStack instance, Operation<Boolean> original) {

        LivingEntity entity = (LivingEntity) (Object) (this);
        Level level = entity.level();
        BlockPos blockPos = entity.blockPosition();

        return original.call(instance) || ((instance.is(VampiricLlamas.INVISIBLE_ARMOR_IN_DARK) && VampiricLlamas.isDarkEnoughForVampireEffects(
                level,
                blockPos
        )));
    }
}
