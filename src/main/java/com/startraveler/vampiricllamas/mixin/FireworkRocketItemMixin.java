package com.startraveler.vampiricllamas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin {

    @WrapOperation(method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isFallFlying()Z"))
    public boolean disallowFireworkLaunchingWithEffects(Player instance, Operation<Boolean> original) {

        return original.call(instance) && instance.getActiveEffects().stream().noneMatch((MobEffectInstance mobEffectInstance) -> mobEffectInstance.getEffect().is(
                VampiricLlamas.FLIGHT_DISABLING_EFFECTS));
    }

}
