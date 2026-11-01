package com.startraveler.vampiricllamas;

import com.startraveler.vampiricllamas.effect.ImmunityMobEffect;
import com.startraveler.vampiricllamas.effect.VenomMobEffect;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class VampiricLlamasEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
            Registries.MOB_EFFECT,
            VampiricLlamas.MODID
    );

    public static final DeferredHolder<MobEffect, MobEffect> AGILE = MOB_EFFECTS.register(
            "agile", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x3ede6b).addAttributeModifier(
                    Attributes.STEP_HEIGHT,
                    VampiricLlamas.id("effect.agile.step_height"),
                    1.0F,
                    AttributeModifier.Operation.ADD_VALUE
            ).addAttributeModifier(
                    Attributes.MOVEMENT_SPEED,
                    VampiricLlamas.id("effect.agile.movement_speed"),
                    0.05F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ).addAttributeModifier(
                    Attributes.WATER_MOVEMENT_EFFICIENCY,
                    VampiricLlamas.id("effect.agile.water_movement_efficiency"),
                    0.33333334F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
    );

    public static final DeferredHolder<MobEffect, MobEffect> SLITHERING = MOB_EFFECTS.register(
            "slithering", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x6e6103).addAttributeModifier(
                    Attributes.SNEAKING_SPEED,
                    VampiricLlamas.id("effect.slithering.sneaking_speed"),
                    0.45F,
                    AttributeModifier.Operation.ADD_VALUE
            ).addAttributeModifier(
                    Attributes.MOVEMENT_SPEED,
                    VampiricLlamas.id("effect.slithering.movement_speed"),
                    -0.05F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
    );

    public static final DeferredHolder<MobEffect, MobEffect> VENOM = MOB_EFFECTS.register(
            "venom",
            () -> new VenomMobEffect(MobEffectCategory.HARMFUL, 0x5cd62b)
    );

    public static final DeferredHolder<MobEffect, MobEffect> VENOMOUS = MOB_EFFECTS.register(
            "venomous",
            () -> new ImmunityMobEffect(
                    MobEffectCategory.BENEFICIAL,
                    0x5cd62b,
                    List.of(Pair.of(0, VENOM), Pair.of(1, MobEffects.POISON), Pair.of(2, MobEffects.WITHER))
            )
    );

    public static final DeferredHolder<MobEffect, MobEffect> BAT_CURSE = MOB_EFFECTS.register(
            "bat_curse",
            () -> new MobEffect(
                    MobEffectCategory.HARMFUL,
                    MobEffects.BLINDNESS.value().getColor()
            ).addAttributeModifier(
                    Attributes.GRAVITY,
                    VampiricLlamas.id("effect.bat_curse.gravity"),
                    -0.2F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).addAttributeModifier(
                    Attributes.JUMP_STRENGTH,
                    VampiricLlamas.id("effect.bat_curse.jump_strength"),
                    -0.1F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).addAttributeModifier(
                    Attributes.ATTACK_DAMAGE,
                    VampiricLlamas.id("effect.bat_curse.weakness"),
                    -2.0,
                    AttributeModifier.Operation.ADD_VALUE
            )
    );

    public static final DeferredHolder<MobEffect, MobEffect> BLOODTHIRSTY = MOB_EFFECTS.register(
            "bloodthirsty",
            () -> new MobEffect(
                    MobEffectCategory.BENEFICIAL,
                    MobEffects.STRENGTH.value().getColor()
            ).addAttributeModifier(
                    Attributes.ATTACK_DAMAGE,
                    VampiricLlamas.id("effect.bloodthirsty.attack_damage"),
                    2.0F,
                    AttributeModifier.Operation.ADD_VALUE
            ).addAttributeModifier(
                    Attributes.SWEEPING_DAMAGE_RATIO,
                    VampiricLlamas.id("effect.bloodthirsty.sweeping_damage_ratio"),
                    0.125F,
                    AttributeModifier.Operation.ADD_VALUE
            ).addAttributeModifier(
                    Attributes.MOVEMENT_SPEED,
                    VampiricLlamas.id("effect.bloodthirsty.movement_speed"),
                    0.10F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
    );

    public static final DeferredHolder<MobEffect, MobEffect> FAR_FALLING = MOB_EFFECTS.register(
            "far_falling",
            () -> new MobEffect(
                    MobEffectCategory.BENEFICIAL,
                    MobEffects.SLOW_FALLING.value().getColor()
            ).addAttributeModifier(
                    Attributes.FALL_DAMAGE_MULTIPLIER,
                    VampiricLlamas.id("effect.far_falling.fall_damage"),
                    -0.1F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).addAttributeModifier(
                    Attributes.SAFE_FALL_DISTANCE,
                    VampiricLlamas.id("effect.far_falling.safe_fall_distance"),
                    1.0F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );

    public static void init(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }


}
