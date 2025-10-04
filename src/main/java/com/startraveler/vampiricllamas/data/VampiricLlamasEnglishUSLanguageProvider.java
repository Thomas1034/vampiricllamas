package com.startraveler.vampiricllamas.data;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.VampiricLlamasEntities;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VampiricLlamasEnglishUSLanguageProvider extends LanguageProvider {

    private final Set<Block> excludedBlocks;
    private final Set<Item> excludedItems;
    private final Set<TagKey<?>> excludedTags;
    private final Set<MobEffect> excludedEffects;
    private final Set<EntityType<?>> excludedEntityTypes;

    public VampiricLlamasEnglishUSLanguageProvider(PackOutput output) {
        super(output, VampiricLlamas.MODID, "en_us");


        this.add("vampiricllamas.configuration.title", "Vampiric Llamas Configs");
        this.add("vampiricllamas.configuration.section.vampiricllamas.common.toml", "Vampiric Llamas Configs");
        this.add("vampiricllamas.configuration.section.vampiricllamas.common.toml.title", "Vampiric Llamas Configs");
        this.add("vampiricllamas.configuration.smokePuffsToCreate", "Smoke Puffs to Create");
        this.add("vampiricllamas.configuration.batsToSummon", "Bats to Summon");
        this.add("vampiricllamas.configuration.shouldOverrideDifficulty", "Override Difficulty?");
        this.add("vampiricllamas.configuration.difficultyOverride", "Overridden Difficulty");
        excludedBlocks = new HashSet<>();
        excludedItems = new HashSet<>();
        excludedTags = new HashSet<>();
        excludedEffects = new HashSet<>();
        excludedEntityTypes = new HashSet<>();
    }


    @Override
    protected void addTranslations() {

        // Delete all superfluous entries and manual override as need be.


        // Now, do all the rest automagically.
        this.addBlockTranslations(List.of());
        this.addTagTranslations(List.of());
        this.addItemTranslations(List.of());
        this.addEffectTranslations(List.of());
        this.addEntityTranslations(VampiricLlamasEntities.ENTITIES.getEntries());

    }

    private void exclude(EntityType<?> key, String name) {
        this.excludedEntityTypes.add(key);
        super.add(key, name);
    }

    public void exclude(Block key, String name) {
        this.excludedBlocks.add(key);
        super.add(key, name);
    }

    public void exclude(Item key, String name) {
        this.excludedItems.add(key);
        super.add(key, name);
    }

    public void exclude(MobEffect key, String name) {
        this.excludedEffects.add(key);
        super.add(key, name);
    }

    public void exclude(TagKey<?> tagKey, String name) {
        this.excludedTags.add(tagKey);
        super.add(tagKey, name);
    }

    protected void addTagTranslations(List<TagKey<?>> tags) {
        tags.forEach(tag -> {
            if (!excludedTags.contains(tag)) {
                this.addTag(
                        () -> tag,
                        WordUtils.capitalize(tag.location().getPath().replace('/', ' ').replace('_', ' '))
                );
            }
        });
    }

    protected void addBlockTranslations(Collection<? extends Holder<Block>> blocks) {
        blocks.forEach(holder -> {
            if (!excludedBlocks.contains(holder.value())) {
                this.addBlock(
                        holder::value,
                        WordUtils.capitalize(holder.getKey().location().getPath().replace('/', ' ').replace('_', ' '))
                );
            }
        });
    }

    protected void addEffectTranslations(Collection<? extends Holder<MobEffect>> effects) {
        effects.forEach(holder -> {
            if (!excludedEffects.contains(holder.value())) {
                this.addEffect(
                        holder::value,
                        WordUtils.capitalize(holder.getKey().location().getPath().replace('/', ' ').replace('_', ' '))
                );
            }
        });
    }

    protected void addEntityTranslations(Collection<? extends Holder<EntityType<?>>> entities) {
        entities.forEach(holder -> {
            if (!excludedEntityTypes.contains(holder.value())) {
                this.addEntityType(
                        holder::value,
                        WordUtils.capitalize(holder.getKey().location().getPath().replace('/', ' ').replace('_', ' '))
                );
            }
        });
    }

    protected void addItemTranslations(Collection<? extends Holder<Item>> items) {
        items.forEach(holder -> {

            if (!excludedItems.contains(holder.value()) && !(holder.value().getDescriptionId().startsWith("block"))) {
                this.addItem(
                        holder::value,
                        WordUtils.capitalize(holder.getKey().location().getPath().replace('/', ' ').replace('_', ' '))
                );
            }
        });
    }
}