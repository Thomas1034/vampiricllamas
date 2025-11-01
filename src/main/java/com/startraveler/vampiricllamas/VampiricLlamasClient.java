package com.startraveler.vampiricllamas;

import com.startraveler.vampiricllamas.client.renderer.LlamaBloodSpitRenderer;
import com.startraveler.vampiricllamas.client.renderer.LlamiaRenderer;
import com.startraveler.vampiricllamas.client.renderer.VampireCloakLayer;
import com.startraveler.vampiricllamas.client.renderer.VampireLlamaRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.player.PlayerHeartTypeEvent;
import org.jetbrains.annotations.NotNull;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = VampiricLlamas.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = VampiricLlamas.MODID, value = Dist.CLIENT)
public class VampiricLlamasClient {
    public static final int DEFAULT_BLACK_TINT = 0xFF2f2f2f;

    public VampiricLlamasClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);


    }


    // With persistent gratitude to ChiefArug and Oritech
    // All credits and no blame to https://github.com/ChiefArug/Oritech/blob/mixin-fixin/neoforge%2Fsrc%2Fmain%2Fjava%2Frearth%2Foritech%2Fneoforge%2Fclient%2FOritechClientNeoForge.java#L85-L99
    @SuppressWarnings({"rawtypes", "unchecked"})
    @SubscribeEvent
    static void addVampireCloakElytraLayer(EntityRenderersEvent.AddLayers event) {
        // add to all player models
        for (PlayerSkin.Model skin : event.getSkins()) {
            if (event.getSkin(skin) instanceof PlayerRenderer pr) {
                pr.addLayer(new VampireCloakLayer<>(pr, event.getEntityModels()));
            }
        }
        // add to all humanoid entities (which have vanilla's elytra layer by default)
        for (EntityType<?> entityType : event.getEntityTypes()) {
            if (event.getRenderer(entityType) instanceof HumanoidMobRenderer<?, ?> hmr) {
                hmr.addLayer(new VampireCloakLayer(hmr, event.getEntityModels()));
            }
        }
        // add to armor stands
        if (event.getRenderer(EntityType.ARMOR_STAND) instanceof LivingEntityRenderer<?, ?> ler) {
            ler.addLayer(new VampireCloakLayer(ler, event.getEntityModels()));
        }
    }

    @SubscribeEvent
    @SuppressWarnings({"unchecked", "rawtypes"})
    static void addElytraLayer(EntityRenderersEvent.AddLayers event) {

        EntityRenderer<Player> entityRenderer = event.getRenderer(EntityType.PLAYER);
        if (entityRenderer instanceof LivingEntityRenderer livingEntityRenderer) {
            VampiricLlamas.LOGGER.warn("\n\n\n\n\nENTITY RENDER LAYER BEING ADDED\n\n\n\n\n");
            livingEntityRenderer.addLayer(new VampireCloakLayer<>(
                    livingEntityRenderer,
                    event.getContext().getModelSet()
            ));
        }
    }

    @SubscribeEvent
    static void setVenomHearts(PlayerHeartTypeEvent event) {
        if (event.getOriginalType() != Gui.HeartType.POISIONED && event.getEntity()
                .hasEffect(VampiricLlamasEffects.VENOM)) {
            event.setType(Gui.HeartType.POISIONED);
        }

    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {
            EntityRenderers.register(
                    VampiricLlamasEntities.VAMPIRE_LLAMA.get(),
                    context -> new VampireLlamaRenderer(context, ModelLayers.LLAMA)
            );
            EntityRenderers.register(
                    VampiricLlamasEntities.LLAMIA.get(),
                    context -> new LlamiaRenderer(context, ModelLayers.LLAMA)
            );
            EntityRenderers.register(VampiricLlamasEntities.LLAMA_BLOOD_SPIT.get(), LlamaBloodSpitRenderer::new);
        });

    }

    @SubscribeEvent
    static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
                new IClientItemExtensions() {
                    public int getDefaultDyeColor(@NotNull ItemStack stack) {
                        return stack.is(ItemTags.DYEABLE) ? FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(
                                stack,
                                VampiricLlamasClient.DEFAULT_BLACK_TINT
                        )) : 0xFFFFFFFF;
                    }
                },
                VampiricLlamasItems.VAMPIRE_LEATHER_HELMET.get(),
                VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE.get(),
                VampiricLlamasItems.VAMPIRE_CLOAK.get(),
                VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS.get(),
                VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS.get()
        );
    }

    @SubscribeEvent
    static void setupColorHandlers(RegisterColorHandlersEvent.Item event) {

        event.register(
                (stack, layer) -> layer > 0 ? -1 : DyedItemColor.getOrDefault(stack, DEFAULT_BLACK_TINT),
                VampiricLlamasItems.VAMPIRE_LEATHER_HELMET,
                VampiricLlamasItems.VAMPIRE_LEATHER_CHESTPLATE,
                VampiricLlamasItems.VAMPIRE_CLOAK,
                VampiricLlamasItems.VAMPIRE_LEATHER_LEGGINGS,
                VampiricLlamasItems.VAMPIRE_LEATHER_BOOTS
        );
    }
}
