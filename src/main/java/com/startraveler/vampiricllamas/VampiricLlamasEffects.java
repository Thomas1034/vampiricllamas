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
                    VampiricLlamas.location("effect.agile.step_height"),
                    1.0F,
                    AttributeModifier.Operation.ADD_VALUE
            ).addAttributeModifier(
                    Attributes.MOVEMENT_SPEED,
                    VampiricLlamas.location("effect.agile.movement_speed"),
                    0.05F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ).addAttributeModifier(
                    Attributes.WATER_MOVEMENT_EFFICIENCY,
                    VampiricLlamas.location("effect.agile.water_movement_efficiency"),
                    0.33333334F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
    );

    public static final DeferredHolder<MobEffect, MobEffect> SLITHERING = MOB_EFFECTS.register(
            "slithering", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x6e6103).addAttributeModifier(
                    Attributes.SNEAKING_SPEED,
                    VampiricLlamas.location("effect.slithering.sneaking_speed"),
                    0.45F,
                    AttributeModifier.Operation.ADD_VALUE
            ).addAttributeModifier(
                    Attributes.MOVEMENT_SPEED,
                    VampiricLlamas.location("effect.slithering.movement_speed"),
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
                    List.of(Pair.of(0, MobEffects.POISON), Pair.of(0, VENOM))
            )
    );

    public static final DeferredHolder<MobEffect, MobEffect> BAT_CURSE = MOB_EFFECTS.register(
            "bat_curse",
            () -> new MobEffect(
                    MobEffectCategory.HARMFUL,
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

    public static final DeferredHolder<MobEffect, MobEffect> BLOODTHIRSTY = MOB_EFFECTS.register(
            "bloodthirsty",
            () -> new MobEffect(
                    MobEffectCategory.BENEFICIAL,
                    MobEffects.DAMAGE_BOOST.value().getColor()
            ).addAttributeModifier(
                    Attributes.ATTACK_DAMAGE,
                    VampiricLlamas.location("effect.bloodthirsty.attack_damage"),
                    2.0F,
                    AttributeModifier.Operation.ADD_VALUE
            ).addAttributeModifier(
                    Attributes.SWEEPING_DAMAGE_RATIO,
                    VampiricLlamas.location("effect.bloodthirsty.sweeping_damage_ratio"),
                    0.125F,
                    AttributeModifier.Operation.ADD_VALUE
            ).addAttributeModifier(
                    Attributes.MOVEMENT_SPEED,
                    VampiricLlamas.location("effect.bloodthirsty.movement_speed"),
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
                    VampiricLlamas.location("effect.far_falling.fall_damage"),
                    -0.1F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ).addAttributeModifier(
                    Attributes.SAFE_FALL_DISTANCE,
                    VampiricLlamas.location("effect.far_falling.safe_fall_distance"),
                    1.0F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            )
    );

    public static void init(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }


}
