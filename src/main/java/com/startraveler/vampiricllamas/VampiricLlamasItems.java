package com.startraveler.vampiricllamas;

import com.startraveler.vampiricllamas.item.EffectGivingArmorItem;
import com.startraveler.vampiricllamas.item.EffectGivingSwordItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.TriPredicate;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Function;

public class VampiricLlamasItems {
    public static final TriPredicate<ItemStack, LivingEntity, LivingEntity> IS_UNDEAD = (_, target, _) -> target.is(
            EntityTypeTags.UNDEAD);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.Items.createItems(VampiricLlamas.MODID);
    public static final DeferredItem<Item> VAMPIRE_LLAMA_SPAWN_EGG = registerItem(
            "vampire_llama_spawn_egg",
            Function.identity(),
            (properties) -> new SpawnEggItem(properties.spawnEgg(VampiricLlamasEntities.VAMPIRE_LLAMA.get()))
    );
    public static final DeferredItem<Item> LLAMIA_SPAWN_EGG = registerItem(
            "llamia_spawn_egg",
            Function.identity(),
            (properties) -> new SpawnEggItem(properties.spawnEgg(VampiricLlamasEntities.LLAMIA.get()))
    );
    public static final DeferredItem<Item> VAMPIRE_LEATHER = registerItem(
            "vampire_leather",
            Function.identity(),
            Item::new
    );
    public static final DeferredItem<Item> VAMPIRE_SCALE = registerItem(
            "vampire_scale",
            Function.identity(),
            Item::new
    );
    public static final DeferredItem<Item> FANG_KNIFE = registerItem(
            "fang_knife",
            properties -> properties.sword(VampiricLlamasToolTiers.FANG, 3, -1.5F),
            properties -> new EffectGivingSwordItem(
                    properties, List.of(
                    new EffectGivingSwordItem.SwordEffect(
                            EffectGivingSwordItem.defaultEffect(
                                    MobEffects.REGENERATION,
                                    2
                            ), IS_UNDEAD.negate()
                    ),
                    new EffectGivingSwordItem.SwordEffect(
                            EffectGivingSwordItem.defaultEffect(MobEffects.HUNGER),
                            IS_UNDEAD.negate()
                    )
            )
            )
    );
    public static final DeferredItem<Item> LLAMA_FANG = registerItem("llama_fang", Function.identity(), Item::new);
    public static final DeferredItem<Item> VAMPIRE_LEATHER_HELMET = registerItem(
            "vampire_leather_helmet", Function.identity(), properties -> new EffectGivingArmorItem(
                    properties.humanoidArmor(
                            VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL,
                            ArmorType.HELMET
                    ), List.of(
                    new EffectGivingArmorItem.ArmorEffect(
                            EffectGivingArmorItem.defaultEffect(MobEffects.INVISIBILITY),
                            EffectGivingArmorItem.inDarkness()
                    ), new EffectGivingArmorItem.ArmorEffect(
                            EffectGivingArmorItem.defaultEffect(MobEffects.NIGHT_VISION),
                            EffectGivingArmorItem.inDarkness()
                                    .and(EffectGivingArmorItem.fullSuit(VampiricLlamas.VAMPIRE_ARMOR))
                    )
            )
            )
    );
    public static final DeferredItem<Item> VAMPIRE_LEATHER_CHESTPLATE = registerItem(
            "vampire_leather_chestplate", Function.identity(), properties -> new EffectGivingArmorItem(
                    properties.humanoidArmor(
                            VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL,
                            ArmorType.CHESTPLATE
                    ),
                    EffectGivingArmorItem.defaultEffect(MobEffects.RESISTANCE),
                    EffectGivingArmorItem.inDarkness()
            )
    );
    public static final DeferredItem<Item> VAMPIRE_CLOAK = registerItem(
            "vampire_cloak", Function.identity(), properties -> new EffectGivingArmorItem(
                    properties.humanoidArmor(
                                    VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL,
                                    ArmorType.CHESTPLATE
                            )
                            .component(
                                    DataComponents.EQUIPPABLE,
                                    Equippable.builder(EquipmentSlot.CHEST)
                                            .setEquipSound(VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL.equipSound())
                                            .setAsset(VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_CLOAK_KEY)
                                            .setDamageOnHurt(true)
                                            .build()
                            )
                            .component(DataComponents.GLIDER, Unit.INSTANCE), List.of(
                    new EffectGivingArmorItem.ArmorEffect(
                            EffectGivingArmorItem.defaultEffect(VampiricLlamasEffects.BAT_CURSE),
                            EffectGivingArmorItem.alwaysTrue()
                    ), new EffectGivingArmorItem.ArmorEffect(
                            EffectGivingArmorItem.defaultEffect(MobEffects.WEAKNESS),
                            EffectGivingArmorItem.inDarkness().negate()
                    )
            )
            )
    );
    public static final DeferredItem<Item> VAMPIRE_LEATHER_LEGGINGS = registerItem(
            "vampire_leather_leggings", Function.identity(), properties -> new EffectGivingArmorItem(
                    properties.humanoidArmor(
                            VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL,
                            ArmorType.LEGGINGS
                    ),
                    EffectGivingArmorItem.defaultEffect(MobEffects.SPEED),
                    EffectGivingArmorItem.inDarkness()
            )
    );
    public static final DeferredItem<Item> VAMPIRE_LEATHER_BOOTS = registerItem(
            "vampire_leather_boots", Function.identity(), properties -> new EffectGivingArmorItem(
                    properties.humanoidArmor(
                            VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL,
                            ArmorType.BOOTS
                    ),
                    EffectGivingArmorItem.defaultEffect(VampiricLlamasEffects.FAR_FALLING),
                    EffectGivingArmorItem.inDarkness()
            )
    );

    public static final DeferredItem<Item> VAMPIRE_SCALE_HELMET = registerItem(
            "vampire_scale_helmet", Function.identity(), properties -> new EffectGivingArmorItem(
                    properties.humanoidArmor(
                            VampiricLlamasArmorMaterials.VAMPIRE_SCALE_ARMOR_MATERIAL,
                            ArmorType.HELMET
                    ), List.of(
                    new EffectGivingArmorItem.ArmorEffect(
                            EffectGivingArmorItem.defaultEffect(MobEffects.NIGHT_VISION),
                            EffectGivingArmorItem.inDarkness()
                    ), new EffectGivingArmorItem.ArmorEffect(
                            EffectGivingArmorItem.defaultEffect(MobEffects.HASTE),
                            EffectGivingArmorItem.inDarkness()
                                    .and(EffectGivingArmorItem.fullSuit(VampiricLlamas.VAMPIRE_ARMOR))
                    ), new EffectGivingArmorItem.ArmorEffect(
                            EffectGivingArmorItem.defaultEffect(MobEffects.MINING_FATIGUE),
                            EffectGivingArmorItem.inDarkness()
                                    .and(EffectGivingArmorItem.fullSuit(VampiricLlamas.VAMPIRE_ARMOR))
                                    .negate()
                    )
            )
            )
    );
    public static final DeferredItem<Item> VAMPIRE_SCALE_CHESTPLATE = registerItem(
            "vampire_scale_chestplate", Function.identity(), properties -> new EffectGivingArmorItem(
                    properties.humanoidArmor(
                            VampiricLlamasArmorMaterials.VAMPIRE_SCALE_ARMOR_MATERIAL,
                            ArmorType.CHESTPLATE
                    ), List.of(
                    new EffectGivingArmorItem.ArmorEffect(
                            EffectGivingArmorItem.defaultEffect(VampiricLlamasEffects.BLOODTHIRSTY),
                            EffectGivingArmorItem.inDarkness()
                    ), new EffectGivingArmorItem.ArmorEffect(
                            EffectGivingArmorItem.defaultEffect(MobEffects.HUNGER),
                            EffectGivingArmorItem.inDarkness().negate()
                    )
            )
            )
    );
    public static final DeferredItem<Item> VAMPIRE_SCALE_LEGGINGS = registerItem(
            "vampire_scale_leggings", Function.identity(), properties -> new EffectGivingArmorItem(
                    properties.humanoidArmor(
                            VampiricLlamasArmorMaterials.VAMPIRE_SCALE_ARMOR_MATERIAL,
                            ArmorType.LEGGINGS
                    ),
                    EffectGivingArmorItem.defaultEffect(VampiricLlamasEffects.SLITHERING, 0),
                    EffectGivingArmorItem.inDarkness()
            )
    );
    public static final DeferredItem<Item> VAMPIRE_SCALE_BOOTS = registerItem(
            "vampire_scale_boots", Function.identity(), properties -> new EffectGivingArmorItem(
                    properties.humanoidArmor(
                            VampiricLlamasArmorMaterials.VAMPIRE_SCALE_ARMOR_MATERIAL,
                            ArmorType.BOOTS
                    ),
                    EffectGivingArmorItem.defaultEffect(VampiricLlamasEffects.AGILE),
                    EffectGivingArmorItem.inDarkness()
            )
    );
    public static final DeferredItem<Item> TOTEM_OF_ENVENOMATION = registerItem(
            "totem_of_envenomation",
            properties -> properties.durability(128),
            Item::new
    );
    public static final DeathProtection TOTEM_OF_PERSISTING_DEATH_PROTECTION = new DeathProtection(List.of(
            new ClearAllStatusEffectsConsumeEffect(),
            new ApplyStatusEffectsConsumeEffect(List.of(
                    new MobEffectInstance(MobEffects.REGENERATION, 900, 2),
                    new MobEffectInstance(MobEffects.REGENERATION, 40, 4),
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0),
                    new MobEffectInstance(VampiricLlamasEffects.BAT_CURSE, 900, 0)
            ))
    ));
    private static final float VAMPIRE_SMOKE_PUFF_SPEED = 0.01f;
    public static final DeferredItem<Item> TOTEM_OF_PERSISTING = registerItem(
            "totem_of_persisting",
            properties -> properties.component(
                            DataComponents.DEATH_PROTECTION,
                            TOTEM_OF_PERSISTING_DEATH_PROTECTION
                    ) // TODO
                    .component(
                            VampiricLlamasDataComponents.PARTICLE_OPTIONS,
                            new VampiricLlamasDataComponents.ParticleOptionsData(
                                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                                    ParticleTypes.SMOKE,
                                    VAMPIRE_SMOKE_PUFF_SPEED
                            )
                    ).stacksTo(1),
            Item::new
    );

    public static <T extends Item> DeferredItem<T> registerItem(String name, Function<Item.Properties, Item.Properties> propertyModifiers, Function<Item.Properties, T> itemFactory) {

        // Create the item instance.
        Function<Item.Properties, T> fromBaseProperties = itemFactory.compose(propertyModifiers);
        // Register the item.
        return ITEMS.registerItem(name, fromBaseProperties);
    }


    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
