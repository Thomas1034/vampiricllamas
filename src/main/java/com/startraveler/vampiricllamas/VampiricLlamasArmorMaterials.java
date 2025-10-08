package com.startraveler.vampiricllamas;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class VampiricLlamasArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(
            Registries.ARMOR_MATERIAL,
            VampiricLlamas.MODID
    );

    // ARMOR_MATERIALS is a DeferredRegister<ArmorMaterial>

    // We place copper somewhere between chainmail and iron.
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> VAMPIRE_LEATHER_ARMOR_MATERIAL =
            ARMOR_MATERIALS.register(
                    "vampire_leather", () -> new ArmorMaterial(
                            // Determines the defense value of this armor material, depending on what armor piece it is.
                            Util.make(
                                    new EnumMap<>(ArmorItem.Type.class), map -> {
                                        map.put(ArmorItem.Type.BOOTS, 3);
                                        map.put(ArmorItem.Type.LEGGINGS, 4);
                                        map.put(ArmorItem.Type.CHESTPLATE, 5);
                                        map.put(ArmorItem.Type.HELMET, 3);
                                        map.put(ArmorItem.Type.BODY, 4);
                                    }
                            ),
                            // Determines the enchantability of the tier. This represents how good the enchantments on this armor will be.
                            6,
                            // Determines the sound played when equipping this armor.
                            // This is wrapped with a Holder.
                            SoundEvents.ARMOR_EQUIP_LEATHER,
                            // Determines the repair item for this armor.
                            () -> Ingredient.of(Tags.Items.LEATHERS),
                            // Determines the texture locations of the armor to apply when rendering
                            List.of(
                                    // Creates a new armor texture that will be located at:
                                    // - 'assets/mod_id/textures/models/armor/copper_layer_1.png' for the outer texture
                                    // - 'assets/mod_id/textures/models/armor/copper_layer_2.png' for the inner texture (only legs)
                                    new ArmorMaterial.Layer(
                                            VampiricLlamas.location("vampire_leather"), "", true
                                    ),
                                    // Creates a new armor texture that will be rendered on top of the previous at:
                                    // - 'assets/mod_id/textures/models/armor/copper_layer_1_overlay.png' for the outer texture
                                    // - 'assets/mod_id/textures/models/armor/copper_layer_2_overlay.png' for the inner texture (only legs)
                                    // 'true' means that the armor material is dyeable; however, the item must also be added to the 'minecraft:dyeable' tag
                                    new ArmorMaterial.Layer(
                                            VampiricLlamas.location("vampire_leather"), "_overlay", false
                                    )
                            ),
                            0,
                            0
                    )
            );

    public static void init(IEventBus bus) {
        ARMOR_MATERIALS.register(bus);
    }
}
