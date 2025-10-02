package com.startraveler.vampiricllamas;

import com.startraveler.vampiricllamas.entity.LlamaBloodSpit;
import com.startraveler.vampiricllamas.entity.VampireLlama;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VampiricLlamasEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
            Registries.ENTITY_TYPE,
            VampiricLlamas.MODID
    );

    public static final DeferredHolder<EntityType<?>, EntityType<VampireLlama>> VAMPIRE_LLAMA = ENTITIES.register(
            "vampire_llama",
            () -> EntityType.Builder.<VampireLlama>of(VampireLlama::new, MobCategory.MONSTER)
                    .sized(0.9F, 1.87F)
                    .eyeHeight(1.7765F)
                    .passengerAttachments(new Vec3(0.0F, 1.37, -0.3))
                    .clientTrackingRange(10)
                    .build("vampire_llama")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<LlamaBloodSpit>> LLAMA_BLOOD_SPIT = ENTITIES.register(
            "llama_blood_spit",
            () -> EntityType.Builder.<LlamaBloodSpit>of(LlamaBloodSpit::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("llama_blood_spit")
    );

    public static void init(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
