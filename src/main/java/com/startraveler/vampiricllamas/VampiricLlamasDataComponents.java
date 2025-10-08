package com.startraveler.vampiricllamas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VampiricLlamasDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(
            Registries.DATA_COMPONENT_TYPE,
            VampiricLlamas.MODID
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ParticleOptionsData>> PARTICLE_OPTIONS = DATA_COMPONENT_TYPES.register(
            "particle_options",
            () -> DataComponentType.<ParticleOptionsData>builder()
                    .persistent(ParticleOptionsData.CODEC)
                    .networkSynchronized(ParticleOptionsData.STREAM_CODEC)
                    .build()
    );

    public static void init(IEventBus bus) {
        DATA_COMPONENT_TYPES.register(bus);
    }

    public record ParticleOptionsData(ParticleOptions options, ParticleOptions localOptions, float speed) {
        public static final Codec<ParticleOptionsData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ParticleTypes.CODEC.fieldOf("particle").forGetter(ParticleOptionsData::options),
                ParticleTypes.CODEC.fieldOf("localParticle").forGetter(ParticleOptionsData::localOptions),
                Codec.FLOAT.fieldOf("speed").forGetter(ParticleOptionsData::speed)
        ).apply(instance, ParticleOptionsData::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ParticleOptionsData> STREAM_CODEC = StreamCodec.composite(
                ParticleTypes.STREAM_CODEC,
                ParticleOptionsData::options,
                ParticleTypes.STREAM_CODEC,
                ParticleOptionsData::localOptions,
                ByteBufCodecs.FLOAT,
                ParticleOptionsData::speed,
                ParticleOptionsData::new
        );
    }
}
