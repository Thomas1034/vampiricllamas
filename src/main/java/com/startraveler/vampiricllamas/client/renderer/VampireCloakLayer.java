package com.startraveler.vampiricllamas.client.renderer;

import com.startraveler.vampiricllamas.VampiricLlamas;
import com.startraveler.vampiricllamas.VampiricLlamasItems;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class VampireCloakLayer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraLayer<T, M> {
    private static final ResourceLocation BAT_WINGS_LOCATION = VampiricLlamas.location(
            "textures/entity/vampire_cloak.png");

    public VampireCloakLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
        super(renderer, modelSet);
    }

    @Override
    public boolean shouldRender(@NotNull ItemStack itemStack, @NotNull T entity) {
        final Level level = entity.level();
        final BlockPos blockPos = entity.blockPosition();
        boolean isInvisibleArmor = VampiricLlamas.isInvisibleArmor(itemStack, level.registryAccess());
        boolean armorIsInvisible = isInvisibleArmor && VampiricLlamas.isDarkEnoughForVampireEffects(level, blockPos);
        boolean entityIsFlying = entity.isFallFlying();
        return itemStack.is(VampiricLlamasItems.VAMPIRE_CLOAK) && !armorIsInvisible && entityIsFlying;
    }

    @Override
    public @NotNull ResourceLocation getElytraTexture(@NotNull ItemStack stack, @NotNull T entity) {
        return BAT_WINGS_LOCATION;
    }
}
