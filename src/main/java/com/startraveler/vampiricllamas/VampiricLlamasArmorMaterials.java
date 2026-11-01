package com.startraveler.vampiricllamas;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.Map;

public class VampiricLlamasArmorMaterials {

    public static final ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(
            Identifier.withDefaultNamespace("equipment_asset"));

    public static final ResourceKey<EquipmentAsset> VAMPIRE_LEATHER_CLOAK_KEY = ResourceKey.create(
            ROOT_ID,
            VampiricLlamas.id("vampire_leather_cloak")
    );
    public static final ResourceKey<EquipmentAsset> VAMPIRE_LEATHER_KEY = ResourceKey.create(
            ROOT_ID,
            VampiricLlamas.id("vampire_leather")
    );
    public static final ArmorMaterial VAMPIRE_LEATHER_ARMOR_MATERIAL = new ArmorMaterial(
            20,
            makeDefense(3, 4, 5, 3, 4), 6, SoundEvents.ARMOR_EQUIP_LEATHER,
            0, 0, VampiricLlamas.REPAIRS_VAMPIRE_LEATHER_ARMOR, VAMPIRE_LEATHER_KEY
    );
    public static final ResourceKey<EquipmentAsset> VAMPIRE_SCALE_KEY = ResourceKey.create(
            ROOT_ID,
            VampiricLlamas.id("vampire_scale")
    );
    public static final ArmorMaterial VAMPIRE_SCALE_ARMOR_MATERIAL = new ArmorMaterial(
            20,
            makeDefense(2, 3, 4, 2, 6), 16, SoundEvents.ARMOR_EQUIP_CHAIN,
            0, 0.1f, VampiricLlamas.REPAIRS_VAMPIRE_SCALE_ARMOR, VAMPIRE_SCALE_KEY
    );

    // From Kaupenjoe
    // https://github.com/Tutorials-By-Kaupenjoe/NeoForge-Tutorial-26.X/blob/20-armor/src/main/java/net/kaupenjoe/tutorialmod/item/ModArmorMaterials.java
    private static Map<ArmorType, Integer> makeDefense(int boots, int legs, int chest, int helm, int body) {
        return Maps.newEnumMap(
                Map.of(
                        ArmorType.BOOTS,
                        boots,
                        ArmorType.LEGGINGS,
                        legs,
                        ArmorType.CHESTPLATE,
                        chest,
                        ArmorType.HELMET,
                        helm,
                        ArmorType.BODY,
                        body
                )
        );
    }

    public static void init() {
    }
}
