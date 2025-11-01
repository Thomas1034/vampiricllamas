package com.startraveler.vampiricllamas.effect;

import com.startraveler.vampiricllamas.VampiricLlamasEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;

public class VenomMobEffect extends MobEffect {
    public VenomMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.hasEffect(VampiricLlamasEffects.VENOMOUS)) {
            float health = entity.getHealth();
            if (health > 2.0F) {
                Registry<DamageType> damageTypes = entity.damageSources().damageTypes;
                Holder<DamageType> damageTypeReference = damageTypes.getHolder(NeoForgeMod.POISON_DAMAGE)
                        .orElse(damageTypes.getHolderOrThrow(DamageTypes.MAGIC));
                if (entity.getHealth() > 3.0F) {
                    entity.hurt(new DamageSource(damageTypeReference), 2.0F);
                } else {
                    entity.hurt(new DamageSource(damageTypeReference), 1.0F);
                }
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
