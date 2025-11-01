package com.startraveler.vampiricllamas;

import com.startraveler.vampiricllamas.item.EffectGivingArmorItem;
import com.startraveler.vampiricllamas.item.EffectGivingSwordItem;
import com.startraveler.vampiricllamas.item.VampireCloakItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.util.TriPredicate;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Function;

public class VampiricLlamasItems {
    public static final TriPredicate<ItemStack, LivingEntity, LivingEntity> IS_UNDEAD = (stack, target, attacker) -> target.getType()
            .is(EntityTypeTags.UNDEAD);
    public static final int VAMPIRE_LEATHER_ARMOR_DURABILITY_MULTIPLIER = 18;
    public static final int VAMPIRE_SCALE_ARMOR_DURABILITY_MULTIPLIER = 22;
    public static final DeferredRegister.Items ITEMS = DeferredRegister.Items.createItems(VampiricLlamas.MODID);
    public static final DeferredItem<Item> VAMPIRE_LLAMA_SPAWN_EGG = registerItem(
            "vampire_llama_spawn_egg",
            Function.identity(),
            (properties) -> new DeferredSpawnEggItem(
                    VampiricLlamasEntities.VAMPIRE_LLAMA,
                    0x141418,
                    0x484848,
                    properties
            )
    );
    public static final DeferredItem<Item> LLAMIA_SPAWN_EGG = registerItem(
            "llamia_spawn_egg",
            Function.identity(),
            (properties) -> new DeferredSpawnEggItem(VampiricLlamasEntities.LLAMIA, 0xC09E7D, 0xA8C848, properties)
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
            properties -> properties.attributes(SwordItem.createAttributes(VampiricLlamasToolTiers.FANG, 3, -1.5F)),
            properties -> new EffectGivingSwordItem(
                    VampiricLlamasToolTiers.FANG, properties, List.of(
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
    public static final DeferredItem<ArmorItem> VAMPIRE_LEATHER_HELMET = registerItem(
            "vampire_leather_helmet", Function.identity(), properties -> new EffectGivingArmorItem(
                    VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL,
                    ArmorItem.Type.HELMET,
                    properties.durability(ArmorItem.Type.HELMET.getDurability(
                            VAMPIRE_LEATHER_ARMOR_DURABILITY_MULTIPLIER)),
                    List.of(
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
    public static final DeferredItem<ArmorItem> VAMPIRE_LEATHER_CHESTPLATE = registerItem(
            "vampire_leather_chestplate", Function.identity(), properties -> new EffectGivingArmorItem(
                    VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    properties.durability(ArmorItem.Type.CHESTPLATE.getDurability(
                            VAMPIRE_LEATHER_ARMOR_DURABILITY_MULTIPLIER)),
                    EffectGivingArmorItem.defaultEffect(MobEffects.DAMAGE_RESISTANCE),
                    EffectGivingArmorItem.inDarkness()
            )
    );
    public static final DeferredItem<ArmorItem> VAMPIRE_CLOAK = registerItem(
            "vampire_cloak", Function.identity(), properties -> new VampireCloakItem(
                    VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    properties.durability(ArmorItem.Type.CHESTPLATE.getDurability(
                            VAMPIRE_LEATHER_ARMOR_DURABILITY_MULTIPLIER)),
                    List.of(
                            new EffectGivingArmorItem.ArmorEffect(
                                    EffectGivingArmorItem.defaultEffect(VampiricLlamasEffects.BAT_CURSE),
                                    EffectGivingArmorItem.alwaysTrue()
                            ),
                            new EffectGivingArmorItem.ArmorEffect(
                                    EffectGivingArmorItem.defaultEffect(MobEffects.WEAKNESS),
                                    EffectGivingArmorItem.inDarkness().negate()
                            )
                    )
            )
    );
    public static final DeferredItem<ArmorItem> VAMPIRE_LEATHER_LEGGINGS = registerItem(
            "vampire_leather_leggings", Function.identity(), properties -> new EffectGivingArmorItem(
                    VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL,
                    ArmorItem.Type.LEGGINGS,
                    properties.durability(ArmorItem.Type.LEGGINGS.getDurability(
                            VAMPIRE_LEATHER_ARMOR_DURABILITY_MULTIPLIER)),
                    EffectGivingArmorItem.defaultEffect(MobEffects.MOVEMENT_SPEED),
                    EffectGivingArmorItem.inDarkness()
            )
    );
    public static final DeferredItem<ArmorItem> VAMPIRE_LEATHER_BOOTS = registerItem(
            "vampire_leather_boots", Function.identity(), properties -> new EffectGivingArmorItem(
                    VampiricLlamasArmorMaterials.VAMPIRE_LEATHER_ARMOR_MATERIAL,
                    ArmorItem.Type.BOOTS,
                    properties.durability(ArmorItem.Type.BOOTS.getDurability(VAMPIRE_LEATHER_ARMOR_DURABILITY_MULTIPLIER)),
                    EffectGivingArmorItem.defaultEffect(VampiricLlamasEffects.FAR_FALLING),
                    EffectGivingArmorItem.inDarkness()
            )
    );

    public static final DeferredItem<ArmorItem> VAMPIRE_SCALE_HELMET = registerItem(
            "vampire_scale_helmet", Function.identity(), properties -> new EffectGivingArmorItem(
                    VampiricLlamasArmorMaterials.VAMPIRE_SCALE_ARMOR_MATERIAL,
                    ArmorItem.Type.HELMET,
                    properties.durability(ArmorItem.Type.HELMET.getDurability(
                            VAMPIRE_SCALE_ARMOR_DURABILITY_MULTIPLIER)),
                    List.of(
                            new EffectGivingArmorItem.ArmorEffect(
                                    EffectGivingArmorItem.defaultEffect(MobEffects.NIGHT_VISION),
                                    EffectGivingArmorItem.inDarkness()
                            ), new EffectGivingArmorItem.ArmorEffect(
                                    EffectGivingArmorItem.defaultEffect(MobEffects.DIG_SPEED),
                                    EffectGivingArmorItem.inDarkness()
                                            .and(EffectGivingArmorItem.fullSuit(VampiricLlamas.VAMPIRE_ARMOR))
                            ), new EffectGivingArmorItem.ArmorEffect(
                                    EffectGivingArmorItem.defaultEffect(MobEffects.DIG_SLOWDOWN),
                                    EffectGivingArmorItem.inDarkness()
                                            .and(EffectGivingArmorItem.fullSuit(VampiricLlamas.VAMPIRE_ARMOR))
                                            .negate()
                            )
                    )
            )
    );
    public static final DeferredItem<ArmorItem> VAMPIRE_SCALE_CHESTPLATE = registerItem(
            "vampire_scale_chestplate", Function.identity(), properties -> new EffectGivingArmorItem(
                    VampiricLlamasArmorMaterials.VAMPIRE_SCALE_ARMOR_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    properties.durability(ArmorItem.Type.CHESTPLATE.getDurability(
                            VAMPIRE_SCALE_ARMOR_DURABILITY_MULTIPLIER)),
                    List.of(
                            new EffectGivingArmorItem.ArmorEffect(
                                    EffectGivingArmorItem.defaultEffect(VampiricLlamasEffects.BLOODTHIRSTY),
                                    EffectGivingArmorItem.inDarkness()
                            ),
                            new EffectGivingArmorItem.ArmorEffect(
                                    EffectGivingArmorItem.defaultEffect(MobEffects.HUNGER),
                                    EffectGivingArmorItem.inDarkness().negate()
                            )
                    )
            )
    );
    public static final DeferredItem<ArmorItem> VAMPIRE_SCALE_LEGGINGS = registerItem(
            "vampire_scale_leggings", Function.identity(), properties -> new EffectGivingArmorItem(
                    VampiricLlamasArmorMaterials.VAMPIRE_SCALE_ARMOR_MATERIAL,
                    ArmorItem.Type.LEGGINGS,
                    properties.durability(ArmorItem.Type.LEGGINGS.getDurability(
                            VAMPIRE_SCALE_ARMOR_DURABILITY_MULTIPLIER)),
                    EffectGivingArmorItem.defaultEffect(VampiricLlamasEffects.SLITHERING, 0),
                    EffectGivingArmorItem.inDarkness()
            )
    );
    public static final DeferredItem<ArmorItem> VAMPIRE_SCALE_BOOTS = registerItem(
            "vampire_scale_boots", Function.identity(), properties -> new EffectGivingArmorItem(
                    VampiricLlamasArmorMaterials.VAMPIRE_SCALE_ARMOR_MATERIAL,
                    ArmorItem.Type.BOOTS,
                    properties.durability(ArmorItem.Type.BOOTS.getDurability(VAMPIRE_SCALE_ARMOR_DURABILITY_MULTIPLIER)),
                    EffectGivingArmorItem.defaultEffect(VampiricLlamasEffects.AGILE),
                    EffectGivingArmorItem.inDarkness()
            )
    );
    public static final DeferredItem<Item> TOTEM_OF_ENVENOMATION = registerItem(
            "totem_of_envenomation", properties -> properties.durability(128), Item::new
    );
    private static final float VAMPIRE_SMOKE_PUFF_SPEED = 0.01f;
    public static final DeferredItem<Item> TOTEM_OF_PERSISTING = registerItem(
            "totem_of_persisting", properties -> properties.component(
                    VampiricLlamasDataComponents.PARTICLE_OPTIONS,
                    new VampiricLlamasDataComponents.ParticleOptionsData(
                            ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            ParticleTypes.SMOKE,
                            VAMPIRE_SMOKE_PUFF_SPEED
                    )
            ).stacksTo(1), Item::new
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
