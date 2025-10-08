package com.startraveler.vampiricllamas.item;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class VampireCloakItem extends EffectGivingArmorItem {


    public VampireCloakItem(Holder<ArmorMaterial> material, Type type, Properties properties, List<ArmorEffect> effects) {
        super(material, type, properties, effects);
    }

    @SuppressWarnings("unused")
    public VampireCloakItem(Holder<ArmorMaterial> material, Type type, Properties properties, Supplier<MobEffectInstance> effect, BiPredicate<ItemStack, Entity> predicate) {
        super(material, type, properties, effect, predicate);
    }

    @Override
    public boolean canElytraFly(@NotNull ItemStack stack, net.minecraft.world.entity.@NotNull LivingEntity entity) {
        return ElytraItem.isFlyEnabled(stack);
    }

    @Override
    public boolean elytraFlightTick(@NotNull ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
        if (!entity.level().isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                if (nextFlightTick % 20 == 0) {
                    stack.hurtAndBreak(1, entity, net.minecraft.world.entity.EquipmentSlot.CHEST);
                }
                entity.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ELYTRA_GLIDE);
            }
        }
        return true;
    }


    @Override
    public @NotNull Holder<SoundEvent> getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_ELYTRA;
    }
}
