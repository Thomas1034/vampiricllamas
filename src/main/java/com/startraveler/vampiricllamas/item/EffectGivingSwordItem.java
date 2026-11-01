package com.startraveler.vampiricllamas.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.TriPredicate;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class EffectGivingSwordItem extends Item {
    protected final List<SwordEffect> effects;

    public EffectGivingSwordItem(Properties properties, List<SwordEffect> effects) {
        super(properties);
        this.effects = effects;
    }

    public static Supplier<MobEffectInstance> defaultEffect(@NotNull Holder<MobEffect> effect) {
        return EffectGivingSwordItem.defaultEffect(effect, 0);
    }


    public static Supplier<MobEffectInstance> tickCountEffect(@NotNull Holder<MobEffect> effect, int ticks, int amplifier) {
        return () -> new MobEffectInstance(effect, ticks, amplifier, true, true);
    }

    public static Supplier<MobEffectInstance> defaultEffect(@NotNull Holder<MobEffect> effect, int amplifier) {
        return tickCountEffect(effect, 40, amplifier);
    }

    @Override
    public void postHurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        if (!attacker.level().isClientSide()) {
            for (SwordEffect effect : this.effects) {
                if (effect.predicate().test(stack, target, attacker)) {
                    attacker.addEffect(effect.effect().get());
                }
            }
        }

    }

    public record SwordEffect(Supplier<MobEffectInstance> effect,
            TriPredicate<ItemStack, LivingEntity, LivingEntity> predicate) {
    }
}
