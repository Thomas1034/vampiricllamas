package com.startraveler.vampiricllamas;

import com.mojang.logging.LogUtils;
import com.startraveler.vampiricllamas.data.VampiricLlamasEnglishUSLanguageProvider;
import com.startraveler.vampiricllamas.data.VampiricLlamasEntityTypeTagProvider;
import com.startraveler.vampiricllamas.entity.VampireLlama;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(VampiricLlamas.MODID)
public class VampiricLlamas {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "vampiricllamas";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public VampiricLlamas(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Registers to the mod event bus so objects get rendered.
        VampiricLlamasBlocks.init(modEventBus);
        VampiricLlamasItems.init(modEventBus);
        VampiricLlamasCreativeModeTabs.init(modEventBus);
        VampiricLlamasEntities.init(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (VampiricLlamas) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerEntityAttributes);
        modEventBus.addListener(this::gatherData);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    // On the mod event bus
    public void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // Other providers here
        event.getGenerator().addProvider(event.includeServer(), new VampiricLlamasEnglishUSLanguageProvider(output));
        event.getGenerator().addProvider(
                event.includeServer(),
                new VampiricLlamasEntityTypeTagProvider(output, lookupProvider, existingFileHelper)
        );
    }

    public void onStruckByLightning(EntityStruckByLightningEvent event) {
        // TODO
        // llamas turn to vampires.

    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        // LOGGER.info("HELLO FROM COMMON SETUP");

        /*
        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
        */
    }


    public void registerEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(VampiricLlamasEntities.VAMPIRE_LLAMA.get(), VampireLlama.createAttributes().build());
    }

    // Add items to creative mode tabs
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        // LOGGER.info("HELLO from server starting");
    }
}
