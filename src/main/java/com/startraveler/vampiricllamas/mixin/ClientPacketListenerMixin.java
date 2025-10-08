package com.startraveler.vampiricllamas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.VampiricLlamasDataComponents;
import com.startraveler.vampiricllamas.client.particle.TunableSpeedTrackingEmitter;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {


    @WrapOperation(method = "findTotem(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private static boolean allowOtherItemsToCountAsTotems(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(VampiricLlamas.ACTS_AS_TOTEM_OF_UNDYING);
    }


    @WrapOperation(method = "handleEntityEvent(Lnet/minecraft/network/protocol/game/ClientboundEntityEventPacket;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleEngine;createTrackingEmitter(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/particles/ParticleOptions;I)V"))
    private void addOtherItemParticles(ParticleEngine instance, Entity entity, ParticleOptions data, int lifetime, Operation<Void> original) {
        ClientPacketListener thisCPL = (ClientPacketListener) (Object) this;
        if (entity instanceof Player player) {
            ItemStack totem = ClientPacketListener.findTotem(player);
            VampiricLlamasDataComponents.ParticleOptionsData particleOptionsData = totem.get(
                    VampiricLlamasDataComponents.PARTICLE_OPTIONS);
            if (particleOptionsData != null) {
                if (entity == thisCPL.minecraft.player) {
                    thisCPL.minecraft.particleEngine.trackingEmitters.add(new TunableSpeedTrackingEmitter(
                            thisCPL.minecraft.particleEngine.level,
                            entity,
                            particleOptionsData.localOptions(),
                            particleOptionsData.speed(),
                            lifetime
                    ));
                } else {
                    thisCPL.minecraft.particleEngine.trackingEmitters.add(new TunableSpeedTrackingEmitter(
                            thisCPL.minecraft.particleEngine.level,
                            entity,
                            particleOptionsData.options(),
                            particleOptionsData.speed(),
                            lifetime
                    ));
                }

                return;
            }
        }
        original.call(instance, entity, data, lifetime);
    }

}
