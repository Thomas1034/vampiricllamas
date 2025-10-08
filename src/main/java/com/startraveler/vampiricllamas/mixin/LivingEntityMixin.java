package com.startraveler.vampiricllamas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @WrapOperation(method = "checkTotemDeathProtection(Lnet/minecraft/world/damagesource/DamageSource;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private static boolean allowOtherItemsToCountAsTotems(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(VampiricLlamas.ACTS_AS_TOTEM_OF_UNDYING);
    }

    @WrapOperation(method = "checkTotemDeathProtection(Lnet/minecraft/world/damagesource/DamageSource;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/stats/StatType;get(Ljava/lang/Object;)Lnet/minecraft/stats/Stat;"))
    private static <T> Stat<T> awardScoreForOtherTotems(StatType<T> instance, T value, Operation<Stat<T>> original, @Local ItemStack itemStack) {
        return original.call(instance, itemStack.is(Items.TOTEM_OF_UNDYING) ? value : itemStack.getItem());
    }

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
