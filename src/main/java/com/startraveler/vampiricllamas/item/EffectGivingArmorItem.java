package com.startraveler.vampiricllamas.item;

import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class EffectGivingArmorItem extends Item {
    protected final List<ArmorEffect> effects;

    public EffectGivingArmorItem(Properties properties, Supplier<MobEffectInstance> effect, BiPredicate<ItemStack, Entity> predicate) {
        this(properties, List.of(new ArmorEffect(effect, predicate)));
    }

    public EffectGivingArmorItem(Properties properties, List<ArmorEffect> effects) {
        super(properties);
        this.effects = effects;
    }

    public static Supplier<MobEffectInstance> defaultEffect(@NotNull Holder<MobEffect> effect) {
        return EffectGivingArmorItem.defaultEffect(effect, 0);
    }

    public static Supplier<MobEffectInstance> defaultEffect(@NotNull Holder<MobEffect> effect, int amplifier) {
        return () -> new MobEffectInstance(effect, 210, amplifier, true, true);
    }

    public static BiPredicate<ItemStack, Entity> alwaysTrue() {
        return (@NotNull ItemStack _, @NotNull Entity _) -> true;
    }

    public static BiPredicate<ItemStack, Entity> inDarkness() {
        return (@NotNull ItemStack _, @NotNull Entity e) -> VampiricLlamas.isDarkEnoughForVampireEffects(
                e.level(),
                e.blockPosition()
        );
    }

    public static BiPredicate<ItemStack, Entity> fullSuit(TagKey<Item> armors) {
        return (@NotNull ItemStack _, @NotNull Entity e) -> {
            int totalArmors = 0;
            int matchingArmors = 0;
            if (e instanceof LivingEntity livingEntity) {

                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    if (livingEntity.canUseSlot(equipmentSlot)) {
                        totalArmors++;
                        ItemStack stackInSlot = livingEntity.getItemBySlot(equipmentSlot);
                        if (stackInSlot.is(armors)) {
                            matchingArmors++;
                        }
                    }
                }
            }
            return totalArmors == matchingArmors && totalArmors > 0;
        };
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level, @NotNull Entity entity, EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);
        if (entity instanceof LivingEntity livingEntity && !level.isClientSide()) {
            for (ArmorEffect effect : this.effects) {
                if (effect.predicate().test(stack, entity)) {
                    livingEntity.addEffect(effect.effect.get());
                }
            }
        }
    }

    public record ArmorEffect(Supplier<MobEffectInstance> effect, BiPredicate<ItemStack, Entity> predicate) {
    }
}
