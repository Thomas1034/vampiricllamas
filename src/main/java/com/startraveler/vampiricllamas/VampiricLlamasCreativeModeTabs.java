package com.startraveler.vampiricllamas;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VampiricLlamasCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB,
            VampiricLlamas.MODID
    );


    public static void init(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
