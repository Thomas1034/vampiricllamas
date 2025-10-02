package com.startraveler.vampiricllamas;

import com.startraveler.vampiricllamas.client.renderer.LlamaBloodSpitRenderer;
import com.startraveler.vampiricllamas.client.renderer.VampireLlamaRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = VampiricLlamas.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = VampiricLlamas.MODID, value = Dist.CLIENT)
public class VampiricLlamasClient {
    public VampiricLlamasClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {
            EntityRenderers.register(
                    VampiricLlamasEntities.VAMPIRE_LLAMA.get(),
                    context -> new VampireLlamaRenderer(context, ModelLayers.LLAMA)
            );
            EntityRenderers.register(
                    VampiricLlamasEntities.LLAMA_BLOOD_SPIT.get(),
                    LlamaBloodSpitRenderer::new
            );
        });

        // Some client setup code
        VampiricLlamas.LOGGER.info("HELLO FROM CLIENT SETUP");
        VampiricLlamas.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
