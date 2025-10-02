package com.startraveler.vampiricllamas.entity;

import com.startraveler.vampiricllamas.VampiricLlamasEntities;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class LlamaBloodSpit extends LlamaSpit {
    public LlamaBloodSpit(EntityType<? extends LlamaSpit> entityType, Level level) {
        super(entityType, level);
    }

    public LlamaBloodSpit(Level level, Llama spitter) {
        this(VampiricLlamasEntities.LLAMA_BLOOD_SPIT.get(), level);
        this.setOwner(spitter);
        this.setPos(
                spitter.getX() - (double) (spitter.getBbWidth() + 1.0F) * (double) 0.5F * (double) Mth.sin(spitter.yBodyRot * ((float) Math.PI / 180F)),
                spitter.getEyeY() - (double) 0.1F,
                spitter.getZ() + (double) (spitter.getBbWidth() + 1.0F) * (double) 0.5F * (double) Mth.cos(spitter.yBodyRot * ((float) Math.PI / 180F))
        );
    }

    @Override
    protected double getDefaultGravity() {
        return 0.03;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity entity && entity.isAlive()) {
            entity.heal(2f);
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0));
        }
    }

}
