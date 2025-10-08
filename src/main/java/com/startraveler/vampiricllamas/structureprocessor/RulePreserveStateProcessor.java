package com.startraveler.vampiricllamas.structureprocessor;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class RulePreserveStateProcessor extends RuleProcessor {
    public static final MapCodec<RulePreserveStateProcessor> CODEC = ProcessorRule.CODEC.listOf()
            .fieldOf("rules")
            .xmap(RulePreserveStateProcessor::new, processor -> processor.rules);

    public RulePreserveStateProcessor(List<? extends ProcessorRule> rules) {
        super(rules);
    }

    public static BlockState withPropertiesOf(BlockState input, BlockState propertiesFrom) {
        BlockState output = input;

        for (Property<?> property : propertiesFrom.getBlock().getStateDefinition().getProperties()) {
            if (output.hasProperty(property)) {
                output = copyProperty(propertiesFrom, output, property);
            }
        }

        return output;
    }

    public static <T extends Comparable<T>> BlockState copyProperty(BlockState sourceState, BlockState targetState, Property<T> property) {
        return targetState.setValue(property, sourceState.getValue(property));
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(
            LevelReader level,
            @NotNull BlockPos offset,
            @NotNull BlockPos pos,
            StructureTemplate.@NotNull StructureBlockInfo blockInfo,
            StructureTemplate.StructureBlockInfo relativeBlockInfo,
            @NotNull StructurePlaceSettings settings
    ) {
        RandomSource randomsource = RandomSource.create(Mth.getSeed(relativeBlockInfo.pos()));
        BlockState blockstate = level.getBlockState(relativeBlockInfo.pos());

        for (ProcessorRule processorrule : this.rules) {
            if (processorrule.test(
                    relativeBlockInfo.state(),
                    blockstate,
                    blockInfo.pos(),
                    relativeBlockInfo.pos(),
                    pos,
                    randomsource
            )) {
                return new StructureTemplate.StructureBlockInfo(
                        relativeBlockInfo.pos(),

                        withPropertiesOf(processorrule.getOutputState(), relativeBlockInfo.state()),
                        processorrule.getOutputTag(randomsource, relativeBlockInfo.nbt())
                );
            }
        }

        return relativeBlockInfo;
    }

}
