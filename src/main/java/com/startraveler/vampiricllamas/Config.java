package com.startraveler.vampiricllamas;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final int DEFAULT_SMOKE_PUFFS_TO_CREATE = 80;
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.IntValue SMOKE_PUFFS_TO_CREATE = BUILDER.comment(
                    "The number of smoke puff particles that vampire llamas should create when they resurrect.")
            .defineInRange("smokePuffsToCreate", DEFAULT_SMOKE_PUFFS_TO_CREATE, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.BooleanValue SHOULD_OVERRIDE_DIFFICULTY = BUILDER.comment(
                    "Whether the difficulty value of the world or of this configuration file should be used for vampire llamas.")
            .define("shouldOverrideDifficulty", false);
    public static final ModConfigSpec SPEC = BUILDER.build();
    private static final int DEFAULT_BATS_TO_SUMMON = 5;
    public static final ModConfigSpec.IntValue BATS_TO_SUMMON = BUILDER.comment(
                    "The number of bats that vampire llamas should summon when they resurrect.")
            .defineInRange("batsToSummon", DEFAULT_BATS_TO_SUMMON, 0, Integer.MAX_VALUE);
    private static final int DEFAULT_DIFFICULTY_OVERRIDE = 1;
    public static final ModConfigSpec.IntValue DIFFICULTY_OVERRIDE = BUILDER.comment(
                    "The overridden difficulty that vampire llamas will use, if shouldOverrideDifficulty is true. This affects their number of resurrections.")
            .defineInRange("difficultyOverride", DEFAULT_DIFFICULTY_OVERRIDE, 0, Integer.MAX_VALUE);

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(Identifier.parse(itemName));
    }
}
