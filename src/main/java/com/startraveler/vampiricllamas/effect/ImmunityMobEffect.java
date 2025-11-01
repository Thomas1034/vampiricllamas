package com.startraveler.vampiricllamas.effect;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImmunityMobEffect extends MobEffect {

    private final List<Pair<Integer, Holder<MobEffect>>> immuneTo;

    public ImmunityMobEffect(MobEffectCategory category, int color, List<Pair<Integer, Holder<MobEffect>>> immuneTo) {
        super(category, color);
        this.immuneTo = immuneTo;
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
        // Remove listed effects
        for (Pair<Integer, Holder<MobEffect>> requiredAmplifierAndEffect : this.immuneTo) {
            if (requiredAmplifierAndEffect.left() <= amplifier) {
                entity.removeEffect(
                        requiredAmplifierAndEffect.right());
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
