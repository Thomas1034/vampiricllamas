package com.startraveler.vampiricllamas.item;

import com.startraveler.vampiricllamas.VampiricLlamas;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class EffectGivingArmorItem extends ArmorItem {
    protected final List<ArmorEffect> effects;

    public EffectGivingArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties, Supplier<MobEffectInstance> effect, BiPredicate<ItemStack, Entity> predicate) {
        this(material, type, properties, List.of(new ArmorEffect(effect, predicate)));
    }

    public EffectGivingArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties, List<ArmorEffect> effects) {
        super(material, type, properties);
        this.effects = effects;
    }

    public static Supplier<MobEffectInstance> defaultEffect(@NotNull Holder<MobEffect> effect) {
        return EffectGivingArmorItem.defaultEffect(effect, 0);
    }

    public static Supplier<MobEffectInstance> defaultEffect(@NotNull Holder<MobEffect> effect, int amplifier) {
        return () -> new MobEffectInstance(effect, 210, amplifier, true, true);
    }

    public static BiPredicate<ItemStack, Entity> alwaysTrue() {
        return (@NotNull ItemStack i, @NotNull Entity e) -> true;
    }

    public static BiPredicate<ItemStack, Entity> inDarkness() {
        return (@NotNull ItemStack i, @NotNull Entity e) -> VampiricLlamas.isDarkEnoughForVampireEffects(
                e.level(),
                e.blockPosition()
        );
    }

    public static BiPredicate<ItemStack, Entity> fullSuit(TagKey<Item> armors) {
        return (@NotNull ItemStack i, @NotNull Entity e) -> {

            if (i.getItem() instanceof ArmorItem armorItem && e instanceof LivingEntity livingEntity) {
                int totalArmors = 0;
                int matchingArmors = 0;
                ResourceKey<ArmorMaterial> material = armorItem.getMaterial().getKey();
                if (material != null) {
                    for (ItemStack worn : livingEntity.getArmorSlots()) {
                        totalArmors++;
                        if (worn.is(armors)) {
                            matchingArmors++;
                        }
                    }
                }
                return totalArmors == matchingArmors && totalArmors > 0;
            }
            return false;
        };
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (entity instanceof LivingEntity livingEntity && !level.isClientSide()) {
            for (ItemStack wornArmor : livingEntity.getArmorSlots()) {
                if (wornArmor.is(this)) {
                    for (ArmorEffect effect : this.effects) {
                        if (effect.predicate().test(stack, entity)) {
                            livingEntity.addEffect(effect.effect.get());
                        }
                    }
                }
            }
        }
    }

    public record ArmorEffect(Supplier<MobEffectInstance> effect, BiPredicate<ItemStack, Entity> predicate) {
    }
}
