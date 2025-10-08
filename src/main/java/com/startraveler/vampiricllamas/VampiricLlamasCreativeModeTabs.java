package com.startraveler.vampiricllamas;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.stream.Collectors;

public class VampiricLlamasCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB,
            VampiricLlamas.MODID
    );

    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> VAMPIRIC_LLAMAS_ITEMS = CREATIVE_MODE_TABS.register(
            "vampiric_llamas_items",
            () -> CreativeModeTab.builder().title(Component.translatable("creativetab.vampiric_llamas.vampiric_llamas_items"))
                    .icon(() -> new ItemStack(VampiricLlamasItems.TOTEM_OF_PERSISTING.get())).displayItems(
                            (parameters, output) -> {
                                // Add any other items as needed.
                                output.acceptAll(VampiricLlamasItems.ITEMS.getEntries()
                                        .stream()
                                        .map(ItemStack::new)
                                        .collect(
                                                Collectors.toSet()));
                            }
                    )
                    .build()
    );


    public static void init(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
