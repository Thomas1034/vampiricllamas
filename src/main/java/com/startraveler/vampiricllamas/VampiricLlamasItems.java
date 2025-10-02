package com.startraveler.vampiricllamas;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VampiricLlamasItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.Items.createItems(VampiricLlamas.MODID);


    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
