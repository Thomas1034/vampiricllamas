package com.startraveler.vampiricllamas;

import com.mojang.logging.LogUtils;
import com.startraveler.vampiricllamas.data.*;
import com.startraveler.vampiricllamas.effect.ImmunityMobEffect;
import com.startraveler.vampiricllamas.entity.Llamia;
import com.startraveler.vampiricllamas.entity.VampireLlama;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.equine.Llama;
import net.minecraft.world.entity.animal.equine.TraderLlama;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

// Make sure Gloaming generates in chests correctly.

// Changes:
// Fixed missing translation string for Venomous.
// Higher levels of Venomous now cure Wither.
// Adjusted the trigger for the Totem of Envenomation to also trigger on being shot by an entity.
// Adjusted the trigger for the Totem of Envenomation to be triggered by magic damage.
// Bugfixes:
// Added a texture for the Agile effect.

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(VampiricLlamas.MODID)
public class VampiricLlamas {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "vampiricllamas";
    // Directly reference a slf4j logger
    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final TagKey<Item> INVISIBLE_ARMOR_IN_DARK = TagKey.create(
            Registries.ITEM,
            VampiricLlamas.id("invisible_armor_in_dark")
    );

    public static final TagKey<Item> VAMPIRE_ARMOR = TagKey.create(Registries.ITEM, VampiricLlamas.id("vampire_armor"));

    public static final TagKey<Item> REPAIRS_VAMPIRE_LEATHER_ARMOR = TagKey.create(
            Registries.ITEM,
            VampiricLlamas.id("repairs_vampire_leather_armor")
    );

    public static final TagKey<Item> REPAIRS_VAMPIRE_SCALE_ARMOR = TagKey.create(
            Registries.ITEM,
            VampiricLlamas.id("repairs_vampire_scale_armor")
    );

    public static final TagKey<Enchantment> MAKES_ARMOR_INVISIBLE_IN_DARK = TagKey.create(
            Registries.ENCHANTMENT,
            VampiricLlamas.id("makes_armor_invisible_in_dark")
    );

    public static final int IS_DARK_FOR_VAMPIRE_EFFECTS = 7;
    public static final TagKey<MobEffect> FLIGHT_DISABLING_EFFECTS = TagKey.create(
            Registries.MOB_EFFECT,
            VampiricLlamas.id("flight_disabling_effects")
    );

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public VampiricLlamas(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Registers to the mod event bus so objects get rendered.
        VampiricLlamasArmorMaterials.init();
        VampiricLlamasBlocks.init(modEventBus);
        VampiricLlamasCreativeModeTabs.init(modEventBus);
        VampiricLlamasDataComponents.init(modEventBus);
        VampiricLlamasEntities.init(modEventBus);
        VampiricLlamasItems.init(modEventBus);
        VampiricLlamasStructureProcessors.init(modEventBus);
        VampiricLlamasEffects.init(modEventBus);
        VampiricLlamasPotions.init(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (VampiricLlamas) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register events
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerEntityAttributes);
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(this::registerSpawns);

        NeoForge.EVENT_BUS.addListener(this::onStruckByLightning);
        NeoForge.EVENT_BUS.addListener(this::canEffectBeApplied);
        NeoForge.EVENT_BUS.addListener(this::registerBrewingRecipes);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static boolean isInvisibleArmor(@NotNull ItemStack stack, @NotNull RegistryAccess access) {
        return stack.is(VampiricLlamas.INVISIBLE_ARMOR_IN_DARK) || stack.getAllEnchantments(access.lookupOrThrow(
                        Registries.ENCHANTMENT))
                .entrySet()
                .stream()
                .anyMatch(holderEntry -> holderEntry.getKey().is(MAKES_ARMOR_INVISIBLE_IN_DARK));
    }

    public static boolean isDarkEnoughForVampireEffects(int lightLevel) {
        return lightLevel < VampiricLlamas.IS_DARK_FOR_VAMPIRE_EFFECTS;
    }

    public static boolean isDarkEnoughForVampireEffects(@NotNull Level level, @NotNull BlockPos blockPos) {
        int i = level.getRawBrightness(blockPos, level.getSkyDarken());
        return isDarkEnoughForVampireEffects(i);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    // Copied from Monster, with one small change.
    public static boolean checkMonsterSpawnRules(EntityType<? extends Entity> type, ServerLevelAccessor level, EntitySpawnReason spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL && (EntitySpawnReason.ignoresLightRequirements(spawnType) || Monster.isDarkEnoughToSpawn(
                level,
                pos,
                random
        )) && VampiricLlamas.checkMobSpawnRules(type, level, spawnType, pos, random);
    }

    // Copied from Mob, with one small change.
    public static boolean checkMobSpawnRules(EntityType<? extends Entity> type, LevelAccessor level, EntitySpawnReason spawnType, BlockPos pos, @SuppressWarnings("unused") RandomSource random) {
        BlockPos blockpos = pos.below();
        return spawnType == EntitySpawnReason.SPAWNER || level.getBlockState(blockpos)
                .isValidSpawn(level, blockpos, type);
    }

    public void registerSpawns(RegisterSpawnPlacementsEvent event) {
        event.register(
                VampiricLlamasEntities.VAMPIRE_LLAMA.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                VampiricLlamas::checkMonsterSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.OR
        );
        event.register(
                VampiricLlamasEntities.LLAMIA.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                VampiricLlamas::checkMonsterSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.OR
        );
    }

    @SubscribeEvent
    public void onEntityTakeDamage(LivingDamageEvent.Post event) {
        LivingEntity hurtEntity = event.getEntity();

        EquipmentSlot slot = null;
        if (hurtEntity.getMainHandItem().is(VampiricLlamasItems.TOTEM_OF_ENVENOMATION)) {
            slot = EquipmentSlot.MAINHAND;
        } else if (hurtEntity.getOffhandItem().is(VampiricLlamasItems.TOTEM_OF_ENVENOMATION)) {
            slot = EquipmentSlot.OFFHAND;
        }
        if (slot != null) {
            if (event.getSource().getEntity() instanceof LivingEntity || event.getSource()
                    .is(DamageTypeTags.WITCH_RESISTANT_TO)) {

                // Make hurt entity immune.
                hurtEntity.addEffect(new MobEffectInstance(VampiricLlamasEffects.VENOMOUS, 400, 1), hurtEntity);
                // Add potion cloud.
                AreaEffectCloud areaEffectCloud = new AreaEffectCloud(
                        hurtEntity.level(),
                        hurtEntity.getX(),
                        hurtEntity.getY(),
                        hurtEntity.getZ()
                );
                areaEffectCloud.setOwner(hurtEntity);

                areaEffectCloud.setRadius(4.0F);
                areaEffectCloud.setRadiusOnUse(-0.5F);
                areaEffectCloud.setWaitTime(10);
                areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / (float) areaEffectCloud.getDuration());
                areaEffectCloud.setPotionContents(new PotionContents(
                        Optional.empty(),
                        Optional.of(0x2a730d),
                        List.of(new MobEffectInstance(VampiricLlamasEffects.VENOM, 900, 0)),
                        Optional.empty()
                ));
                hurtEntity.level().addFreshEntity(areaEffectCloud);

                // Damage totem
                hurtEntity.getItemBySlot(slot).hurtAndBreak(1, hurtEntity, slot);
            }
        }
    }

    // On the mod event bus
    public void gatherData(GatherDataEvent.Server event) {
        final DataGenerator generator = event.getGenerator();
        final PackOutput output = generator.getPackOutput();
        final CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        final boolean run = event.includeDev();

        // Other providers
        generator.addProvider(run, new VampiricLlamasModelProvider(output));
        final BlockTagsProvider blockTagsProvider = new VampiricLlamasBlockTagProvider(output, lookupProvider);
        generator.addProvider(run, blockTagsProvider);
        generator.addProvider(run, new VampiricLlamasMobEffectTagProvider(output, lookupProvider));
        generator.addProvider(run, new VampiricLlamasEnglishUSLanguageProvider(output));
        generator.addProvider(run, new VampiricLlamasEntityTypeTagProvider(output, lookupProvider));
        generator.addProvider(run, new VampiricLlamasItemTagProvider(output, lookupProvider));
        generator.addProvider(run, new VampiricLlamasRecipeProvider.Runner(output, lookupProvider));
    }

    public void onStruckByLightning(EntityStruckByLightningEvent event) {
        Entity e = event.getEntity();
        if (e.getType() == EntityType.LLAMA && e instanceof Llama llama && llama.level() instanceof ServerLevel serverLevel) {
            VampireLlama.convertLlamaToVampire(serverLevel, llama, false);
        } else if (e.getType() == EntityType.TRADER_LLAMA && e instanceof TraderLlama llama && llama.level() instanceof ServerLevel serverLevel) {
            VampireLlama.convertLlamaToLlamia(serverLevel, llama, false);
        }
    }

    public void canEffectBeApplied(MobEffectEvent.Applicable event) {
        MobEffectInstance newEffect = event.getEffectInstance();
        LivingEntity entity = event.getEntity();
        Collection<MobEffectInstance> activeEffects = entity.getActiveEffects();
        for (MobEffectInstance currentEffect : activeEffects) {
            if (currentEffect.getEffect() instanceof ImmunityMobEffect immunityEffect && immunityEffect.canCureEffectInstance(
                    newEffect,
                    currentEffect.getAmplifier()
            )) {
                event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            }
        }
    }

    public void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(
                Potions.AWKWARD,
                VampiricLlamasItems.VAMPIRE_SCALE.get(),
                VampiricLlamasPotions.VENOM.getDelegate()
        );
        builder.addMix(
                VampiricLlamasPotions.VENOM.getDelegate(),
                Items.REDSTONE,
                VampiricLlamasPotions.LONG_VENOM.getDelegate()
        );
        builder.addMix(
                VampiricLlamasPotions.VENOM.getDelegate(),
                Items.GLOWSTONE_DUST,
                VampiricLlamasPotions.STRONG_VENOM.getDelegate()
        );

        builder.addMix(
                VampiricLlamasPotions.VENOM.getDelegate(),
                Items.FERMENTED_SPIDER_EYE,
                VampiricLlamasPotions.VENOMOUS.getDelegate()
        );
        builder.addMix(
                VampiricLlamasPotions.LONG_VENOM.getDelegate(),
                Items.FERMENTED_SPIDER_EYE,
                VampiricLlamasPotions.LONG_VENOMOUS.getDelegate()
        );
        builder.addMix(
                VampiricLlamasPotions.STRONG_VENOM.getDelegate(),
                Items.FERMENTED_SPIDER_EYE,
                VampiricLlamasPotions.STRONG_VENOMOUS.getDelegate()
        );
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }


    public void registerEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(VampiricLlamasEntities.VAMPIRE_LLAMA.get(), VampireLlama.createAttributes().build());
        event.put(VampiricLlamasEntities.LLAMIA.get(), Llamia.createAttributes().build());
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
