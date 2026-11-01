package com.startraveler.vampiricllamas;

import com.startraveler.vampiricllamas.client.renderer.LlamaBloodSpitRenderer;
import com.startraveler.vampiricllamas.client.renderer.VampireLlamaRenderer;
import com.startraveler.vampiricllamas.entity.Llamia;
import com.startraveler.vampiricllamas.entity.VampireLlama;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.PlayerHeartTypeEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = VampiricLlamas.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = VampiricLlamas.MODID, value = Dist.CLIENT)
public class VampiricLlamasClient {
    public static final int DEFAULT_BLACK_TINT = 0xFF2f2f2f;

    public VampiricLlamasClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);


    }

    @SubscribeEvent
    static void setVenomHearts(PlayerHeartTypeEvent event) {
        if (event.getOriginalType() != Gui.HeartType.POISIONED && event.getEntity()
                .hasEffect(VampiricLlamasEffects.VENOM)) {
            event.setType(Gui.HeartType.POISIONED);
        }

    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {
            EntityRenderers.register(
                    VampiricLlamasEntities.VAMPIRE_LLAMA.get(),
                    context -> new VampireLlamaRenderer<>(
                            context, ModelLayers.LLAMA, ModelLayers.LLAMA_BABY,
                            VampireLlama.RED_VAMPIRE_EYES
                    )
            );
            EntityRenderers.register(
                    VampiricLlamasEntities.LLAMIA.get(),
                    context -> new VampireLlamaRenderer<>(
                            context, ModelLayers.LLAMA, ModelLayers.LLAMA_BABY,
                            Llamia.YELLOW_VAMPIRE_EYES
                    )
            );
            EntityRenderers.register(VampiricLlamasEntities.LLAMA_BLOOD_SPIT.get(), LlamaBloodSpitRenderer::new);
        });

    }

    @SubscribeEvent
    static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
                new IClientItemExtensions() {
                    public int getDefaultDyeColor(@NotNull ItemStack stack) {
                        return DyedItemColor.getOrDefault(
                                stack,
                                VampiricLlamasClient.DEFAULT_BLACK_TINT
                        );
                    }
                },
                VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get(),
                VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get(),
                VampiricLlamasItems.VAMPIRE_CLOAK.get(),
                VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get(),
                VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get()
        );
    }
}
