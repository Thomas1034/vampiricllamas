package com.startraveler.vampiricllamas.data;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.VampiricLlamasItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;

public class VampiricLlamasItemModelProvider extends ItemModelProvider {
    // From Kaupenjoe
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> TRIM_MATERIALS = new LinkedHashMap<>();

    static {
        TRIM_MATERIALS.put(TrimMaterials.QUARTZ, 0.1F);
        TRIM_MATERIALS.put(TrimMaterials.IRON, 0.2F);
        TRIM_MATERIALS.put(TrimMaterials.NETHERITE, 0.3F);
        TRIM_MATERIALS.put(TrimMaterials.REDSTONE, 0.4F);
        TRIM_MATERIALS.put(TrimMaterials.COPPER, 0.5F);
        TRIM_MATERIALS.put(TrimMaterials.GOLD, 0.6F);
        TRIM_MATERIALS.put(TrimMaterials.EMERALD, 0.7F);
        TRIM_MATERIALS.put(TrimMaterials.DIAMOND, 0.8F);
        TRIM_MATERIALS.put(TrimMaterials.LAPIS, 0.9F);
        TRIM_MATERIALS.put(TrimMaterials.AMETHYST, 1.0F);
    }


    public VampiricLlamasItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, VampiricLlamas.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        spawnEggItem(VampiricLlamasItems.VAMPIRE_LLAMA_SPAWN_EGG.get());
        spawnEggItem(VampiricLlamasItems.LLAMIA_SPAWN_EGG.get());
        basicItem(VampiricLlamasItems.VAMPIRE_LEATHER.get());
        basicItem(VampiricLlamasItems.VAMPIRE_SCALE.get());
        basicItem(VampiricLlamasItems.LLAMA_FANG.get());
        basicItem(VampiricLlamasItems.TOTEM_OF_PERSISTING.get());
        basicItem(VampiricLlamasItems.TOTEM_OF_ENVENOMATION.get());
        handheldItem(VampiricLlamasItems.FANG_KNIFE.get());
        trimmedLayeredArmorItem(VampiricLlamasItems.VAMPIRE_LEATHER_HELMET);
        trimmedLayeredArmorItem(VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE);
        trimmedLayeredArmorItem(VampiricLlamasItems.VAMPIRE_CLOAK);
        trimmedLayeredArmorItem(VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS);
        trimmedLayeredArmorItem(VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS);
        trimmedArmorItem(VampiricLlamasItems.VAMPIRE_SCALE_HELMET);
        trimmedArmorItem(VampiricLlamasItems.VAMPIRE_SCALE_CHESTPLATE);
        trimmedArmorItem(VampiricLlamasItems.VAMPIRE_SCALE_LEGGINGS);
        trimmedArmorItem(VampiricLlamasItems.VAMPIRE_SCALE_BOOTS);
    }

    @SuppressWarnings("unused")
    // From Kaupenjoe
    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MOD_ID = VampiricLlamas.MODID; // Change this to your mod id

        if (itemDeferredItem.get() instanceof ArmorItem armorItem) {
            TRIM_MATERIALS.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName).parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(), mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue)
                        .end()
                        .texture(
                                "layer0",
                                ResourceLocation.fromNamespaceAndPath(
                                        MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()
                                )
                        );
            });
        }
    }


    // Modified from Kaupenjoe
    // Shoutout to El_Redstoniano for making the original
    private void trimmedLayeredArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MOD_ID = VampiricLlamas.MODID; // Change this to your mod id

        if (itemDeferredItem.get() instanceof ArmorItem armorItem) {
            TRIM_MATERIALS.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName).parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture(
                                "layer1",
                                armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath() + "_overlay"
                        )
                        .texture("layer2", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(), mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue)
                        .end()
                        .texture(
                                "layer0",
                                ResourceLocation.fromNamespaceAndPath(
                                        MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()
                                )
                        )
                        .texture(
                                "layer1",
                                armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath() + "_overlay"
                        );
            });
        }
    }
}
