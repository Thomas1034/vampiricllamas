package com.startraveler.vampiricllamas;

import com.mojang.logging.LogUtils;
import com.startraveler.vampiricllamas.data.*;
import com.startraveler.vampiricllamas.entity.VampireLlama;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

// TODO
// Idea: add "Gloaming" exclusive enchantment making ordinary armor invisible in low light?


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(VampiricLlamas.MODID)
public class VampiricLlamas {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "vampiricllamas";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final TagKey<Item> INVISIBLE_ARMOR_IN_DARK = TagKey.create(
            Registries.ITEM,
            VampiricLlamas.location("invisible_armor_in_dark")
    );
    public static final TagKey<Item> VAMPIRE_ARMOR = TagKey.create(
            Registries.ITEM,
            VampiricLlamas.location("vampire_armor")
    );

    public static final TagKey<Item> ACTS_AS_TOTEM_OF_UNDYING = TagKey.create(
            Registries.ITEM,
            VampiricLlamas.location("acts_as_totem_of_undying")
    );


    public static final TagKey<Enchantment> MAKES_ARMOR_INVISIBLE_IN_DARK = TagKey.create(
            Registries.ENCHANTMENT,
            VampiricLlamas.location("makes_armor_invisible_in_dark")
    );

    public static final int IS_DARK_FOR_VAMPIRE_EFFECTS = 7;
    public static final TagKey<MobEffect> FLIGHT_DISABLING_EFFECTS = TagKey.create(
            Registries.MOB_EFFECT,
            VampiricLlamas.location("flight_disabling_effects")
    );

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public VampiricLlamas(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Registers to the mod event bus so objects get rendered.
        VampiricLlamasArmorMaterials.init(modEventBus);
        VampiricLlamasBlocks.init(modEventBus);
        VampiricLlamasCreativeModeTabs.init(modEventBus);
        VampiricLlamasDataComponents.init(modEventBus);
        VampiricLlamasEntities.init(modEventBus);
        VampiricLlamasItems.init(modEventBus);
        VampiricLlamasStructureProcessors.init(modEventBus);
        VampiricLlamasEffects.init(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (VampiricLlamas) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register events
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerEntityAttributes);
        modEventBus.addListener(this::gatherData);

        NeoForge.EVENT_BUS.addListener(this::onStruckByLightning);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static boolean isInvisibleArmor(ItemStack stack, RegistryAccess access) {
        return stack.is(VampiricLlamas.INVISIBLE_ARMOR_IN_DARK) || stack.getAllEnchantments(access.lookupOrThrow(
                        Registries.ENCHANTMENT))
                .entrySet()
                .stream()
                .anyMatch(holderEntry -> holderEntry.getKey().is(MAKES_ARMOR_INVISIBLE_IN_DARK));
    }

    public static boolean isDarkEnoughForVampireEffects(Level level, BlockPos blockPos) {
        int blockLightLevel = level.getBrightness(LightLayer.BLOCK, blockPos);
        int skyLightLevel = level.getBrightness(LightLayer.SKY, blockPos);
        int packedLight = LightTexture.pack(blockLightLevel, skyLightLevel);
        return isDarkEnoughForVampireEffects(level, packedLight);
    }

    public static boolean isDarkEnoughForVampireEffects(Level level, int packedLight) {
        int blockLightLevel = LightTexture.block(packedLight);
        int skyLightLevel = LightTexture.sky(packedLight);
        boolean isDay = isDay(level);
        boolean isSkyLightDarkEnough = skyLightLevel <= VampiricLlamas.IS_DARK_FOR_VAMPIRE_EFFECTS;
        boolean isBlockLightDarkEnough = blockLightLevel <= VampiricLlamas.IS_DARK_FOR_VAMPIRE_EFFECTS;
        boolean isNoSunlight = !isDay || isSkyLightDarkEnough;
        return isNoSunlight && isBlockLightDarkEnough;
    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static int getSkyDarken(@NotNull Level level) {
        double d0 = 1.0 - (double) (level.getRainLevel(1.0F) * 5.0F) / 16.0;
        double d1 = 1.0 - (double) (level.getThunderLevel(1.0F) * 5.0F) / 16.0;
        double d2 = 0.5 + 2.0 * Mth.clamp(Mth.cos(level.getTimeOfDay(1.0F) * (float) (Math.PI * 2)), -0.25, 0.25);
        return (int) ((1.0 - d2 * d0 * d1) * 11.0);
    }

    public static boolean isDay(@Nullable Level level) {
        if (level == null) {
            return true;
        }
        return VampiricLlamas.getSkyDarken(level) < 4;
    }

    // On the mod event bus
    public void gatherData(GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        final PackOutput output = generator.getPackOutput();
        final CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        final boolean run = event.includeServer();

        // Other providers
        generator.addProvider(run, new VampiricLlamasItemModelProvider(output, existingFileHelper));
        final BlockTagsProvider blockTagsProvider = new VampiricLlamasBlockTagProvider(
                output,
                lookupProvider,
                existingFileHelper
        );
        generator.addProvider(run, blockTagsProvider);
        generator.addProvider(
                run, new VampiricLlamasMobEffectTagProvider(
                        output,
                        lookupProvider,
                        existingFileHelper
                )
        );
        generator.addProvider(run, new VampiricLlamasEnglishUSLanguageProvider(output));
        generator.addProvider(run, new VampiricLlamasEntityTypeTagProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(
                run,
                new VampiricLlamasItemTagProvider(
                        output,
                        lookupProvider,
                        blockTagsProvider.contentsGetter(),
                        existingFileHelper
                )
        );
        generator.addProvider(run, new VampiricLlamasRecipeProvider(output, lookupProvider));
    }

    public void onStruckByLightning(EntityStruckByLightningEvent event) {
        // TODO
        // llamas turn to vampires.
        Entity e = event.getEntity();
        if (e.getType() == EntityType.LLAMA && e instanceof Llama llama && llama.level() instanceof ServerLevel serverLevel) {
            VampireLlama.convertLlamaToVampire(serverLevel, llama, false);
        }

    }

    private void commonSetup(FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            Map<Item, CauldronInteraction> water = CauldronInteraction.WATER.map();
            water.put(VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get(), CauldronInteraction.DYED_ITEM);
            water.put(VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get(), CauldronInteraction.DYED_ITEM);
            water.put(VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get(), CauldronInteraction.DYED_ITEM);
            water.put(VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get(), CauldronInteraction.DYED_ITEM);
        });
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
