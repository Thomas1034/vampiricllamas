package com.startraveler.vampiricllamas;

import com.startraveler.vampiricllamas.structureprocessor.RulePreserveStateProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VampiricLlamasStructureProcessors {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(
            Registries.STRUCTURE_PROCESSOR,
            VampiricLlamas.MODID
    );

    @SuppressWarnings("unused")
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<RulePreserveStateProcessor>> RULE_PRESERVE_STATE = STRUCTURE_PROCESSORS.register(
            "rule_preserve_state",
            () -> () -> RulePreserveStateProcessor.CODEC
    );


    public static void init(IEventBus bus) {
        STRUCTURE_PROCESSORS.register(bus);
    }
}
