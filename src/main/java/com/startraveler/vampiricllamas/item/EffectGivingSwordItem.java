package com.startraveler.vampiricllamas.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import net.neoforged.neoforge.common.util.TriPredicate;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class EffectGivingSwordItem extends SwordItem {
    protected final List<SwordEffect> effects;

    public EffectGivingSwordItem(Tier tier, Properties properties, List<SwordEffect> effects) {
        super(tier, properties);
        this.effects = effects;
    }

    @SuppressWarnings("unused")
    public EffectGivingSwordItem(Tier tier, Properties properties, Tool toolComponentData, List<SwordEffect> effects) {
        super(tier, properties, toolComponentData);
        this.effects = effects;
    }

    public static Supplier<MobEffectInstance> defaultEffect(@NotNull Holder<MobEffect> effect) {
        return EffectGivingSwordItem.defaultEffect(effect, 0);
    }

    public static Supplier<MobEffectInstance> defaultEffect(@NotNull Holder<MobEffect> effect, int amplifier) {
        return () -> new MobEffectInstance(effect, 40, amplifier, true, true);
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
