package com.startraveler.vampiricllamas;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VampiricLlamasEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
            Registries.MOB_EFFECT,
            VampiricLlamas.MODID
    );

    public static final DeferredHolder<MobEffect, MobEffect> BAT_CURSE = MOB_EFFECTS.register(
            "bat_curse",
            () -> new MobEffect(
                    MobEffectCategory.BENEFICIAL,
                    MobEffects.BLINDNESS.value().getColor()
            ).addAttributeModifier(
                    Attributes.GRAVITY,
                    VampiricLlamas.location("effect.bat_curse.gravity"),
                    -0.2F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).addAttributeModifier(
                    Attributes.JUMP_STRENGTH,
                    VampiricLlamas.location("effect.bat_curse.jump_strength"),
                    -0.1F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).addAttributeModifier(
                    Attributes.ATTACK_DAMAGE,
                    VampiricLlamas.location("effect.bat_curse.weakness"),
                    -2.0,
                    AttributeModifier.Operation.ADD_VALUE
            )
    );

    public static final DeferredHolder<MobEffect, MobEffect> FAR_FALLING = MOB_EFFECTS.register(
            "far_falling",
            () -> new MobEffect(
                    MobEffectCategory.BENEFICIAL,
                    MobEffects.SLOW_FALLING.value().getColor()
            ).addAttributeModifier(
                    Attributes.FALL_DAMAGE_MULTIPLIER,
                    VampiricLlamas.location("effect.far_falling"),
                    -0.1F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).addAttributeModifier(
                    Attributes.SAFE_FALL_DISTANCE,
                    VampiricLlamas.location("effect.far_falling"),
                    1.0F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );


    public static void init(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }
}
