package com.startraveler.vampiricllamas.effect;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImmunityMobEffect extends MobEffect {

    private final Map<Holder<MobEffect>, Integer> levelsRequiredForImmunity;

    public ImmunityMobEffect(MobEffectCategory category, int color, List<Pair<Integer, Holder<MobEffect>>> immuneTo) {
        super(category, color);
        this.levelsRequiredForImmunity = immuneTo.stream()
                .collect(Collectors.toUnmodifiableMap(Pair::right, Pair::left));
    }

    @Override
    public boolean applyEffectTick(@NonNull ServerLevel level, @NotNull LivingEntity entity, int amplifier) {
        super.applyEffectTick(level, entity, amplifier);
        // Remove listed effects if they're weaker than this one minus their handicap.
        this.cureEffects(entity, amplifier);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    public void cureEffects(LivingEntity entity, int amplifier) {
        for (MobEffectInstance instance : entity.getActiveEffects()) {
            if (this.canCureEffectInstance(instance, amplifier)) {
                entity.removeEffect(instance.getEffect());
            }
        }
    }

    public boolean canCureEffectInstance(MobEffectInstance instance, int thisAmplifier) {
        Holder<MobEffect> effectToCure = instance.getEffect();
        boolean canBeCured = this.levelsRequiredForImmunity.containsKey(effectToCure);
        if (canBeCured) {
            int levelRequiredForImmunity = this.levelsRequiredForImmunity.get(effectToCure);
            int adjustedEffectToCureAmplifier = instance.getAmplifier() + levelRequiredForImmunity;
            return thisAmplifier >= adjustedEffectToCureAmplifier;
        }
        return false;
    }
}
