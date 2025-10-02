package com.startraveler.vampiricllamas;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VampiricLlamasBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.Blocks.createBlocks(VampiricLlamas.MODID);


    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
