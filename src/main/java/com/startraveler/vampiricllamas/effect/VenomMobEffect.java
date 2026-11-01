package com.startraveler.vampiricllamas.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jspecify.annotations.NonNull;

public class VenomMobEffect extends MobEffect {
    public VenomMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(@NonNull ServerLevel level, LivingEntity entity, int amplifier) {
        float health = entity.getHealth();
        if (health > 2.0F) {
            Registry<DamageType> damageTypes = entity.damageSources().damageTypes;
            Holder<DamageType> damageTypeReference = damageTypes.get(NeoForgeMod.POISON_DAMAGE)
                    .orElse(damageTypes.getOrThrow(DamageTypes.MAGIC));
            if (entity.getHealth() > 3.0F) {
                entity.hurtServer(level, new DamageSource(damageTypeReference), 2.0F);
            } else {
                entity.hurtServer(level, new DamageSource(damageTypeReference), 1.0F);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int tickEvery = 50 >> amplifier;
        return tickEvery == 0 || duration % tickEvery == 0;
    }
}
