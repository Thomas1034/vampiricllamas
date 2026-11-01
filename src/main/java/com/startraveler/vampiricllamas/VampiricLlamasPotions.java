package com.startraveler.vampiricllamas;

import com.startraveler.vampiricllamas.effect.ImmunityMobEffect;
import com.startraveler.vampiricllamas.effect.VenomMobEffect;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class VampiricLlamasPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(
            Registries.POTION,
            VampiricLlamas.MODID
    );

    public static final DeferredHolder<Potion, Potion> VENOMOUS = POTIONS.register(
            "venomous",
            () -> new Potion("venomous", new MobEffectInstance(VampiricLlamasEffects.VENOMOUS, 1800, 1))
    );
    public static final DeferredHolder<Potion, Potion> LONG_VENOMOUS = POTIONS.register(
            "long_venomous",
            () -> new Potion("venomous", new MobEffectInstance(VampiricLlamasEffects.VENOMOUS, 4800, 1))
    );
    public static final Holder<Potion> STRONG_VENOMOUS = POTIONS.register(
            "strong_venomous",
            () -> new Potion("venomous", new MobEffectInstance(VampiricLlamasEffects.VENOMOUS, 400, 2))
    );

    public static final DeferredHolder<Potion, Potion> VENOM = POTIONS.register(
            "venom",
            () -> new Potion("venom", new MobEffectInstance(VampiricLlamasEffects.VENOM, 1800, 1))
    );
    public static final DeferredHolder<Potion, Potion> LONG_VENOM = POTIONS.register(
            "long_venom",
            () -> new Potion("venom", new MobEffectInstance(VampiricLlamasEffects.VENOM, 4800, 1))
    );
    public static final Holder<Potion> STRONG_VENOM = POTIONS.register(
            "strong_venom",
            () -> new Potion("venom", new MobEffectInstance(VampiricLlamasEffects.VENOM, 432, 2))
    );

    public static void init(IEventBus bus) {
        POTIONS.register(bus);
    }


}
