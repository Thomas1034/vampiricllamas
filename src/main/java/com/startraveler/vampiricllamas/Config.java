package com.startraveler.vampiricllamas;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final int DEFAULT_BATS_TO_SUMMON = 5;
    private static final int DEFAULT_DIFFICULTY_OVERRIDE = 1;
    public static final int DEFAULT_SMOKE_PUFFS_TO_CREATE = 80;


    public static final ModConfigSpec.IntValue BATS_TO_SUMMON = BUILDER
            .comment("The number of bats that vampire llamas should summon when they resurrect.")
            .defineInRange("batsToSummon", DEFAULT_BATS_TO_SUMMON, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue SMOKE_PUFFS_TO_CREATE = BUILDER
            .comment("The number of smoke puff particles that vampire llamas should create when they resurrect.")
            .defineInRange("smokePuffsToCreate", DEFAULT_SMOKE_PUFFS_TO_CREATE, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.BooleanValue SHOULD_OVERRIDE_DIFFICULTY = BUILDER
            .comment("Whether the difficulty value of the world or of this configuration file should be used for vampire llamas.")
            .define("shouldOverrideDifficulty", false);

    public static final ModConfigSpec.IntValue DIFFICULTY_OVERRIDE = BUILDER
            .comment("The overridden difficulty that vampire llamas will use, if shouldOverrideDifficulty is true. This affects their number of resurrections.")
            .defineInRange("difficultyOverride", DEFAULT_DIFFICULTY_OVERRIDE, 0, Integer.MAX_VALUE);

//    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");
//
//    // a list of strings that are treated as resource locations for items
//    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
//            .comment("A list of items to log on common setup.")
//            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);
//
//    public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
//            .comment("Whether to log the dirt block on common setup")
//            .define("logDirtBlock", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
