package com.startraveler.vampiricllamas.entity;

import com.startraveler.vampiricllamas.VampiricLlamasEntities;
import com.startraveler.vampiricllamas.entity.goal.BoundedDistanceRangedAttackGoal;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class VampireLlama extends Llama {

    public VampireLlama(EntityType<? extends VampireLlama> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("unused")
    public VampireLlama(Level level) {
        this(VampiricLlamasEntities.VAMPIRE_LLAMA.get(), level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return createBaseChestedHorseAttributes().add(Attributes.FOLLOW_RANGE, 40.0F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5f)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.SAFE_FALL_DISTANCE, 1024)
                .add(Attributes.ATTACK_SPEED);
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2));
        this.goalSelector.addGoal(
                2,
                new BoundedDistanceRangedAttackGoal(this, 1.25F, 10, 20, 20.0F, FloatList.of(16, 32))
        );
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 2.50D, true));
        this.goalSelector.addGoal(4, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0F));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.7));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Llama.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, TraderLlama.class, true));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Camel.class, true));
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean canMate(@NotNull Animal otherAnimal) {
        return false;
    }

    @Override
    public void spit(LivingEntity target) {
        LlamaBloodSpit llamaSpit = new LlamaBloodSpit(this.level(), this);
        double xDifference = target.getX() - this.getX();
        double yDifference = target.getY(0.3333333333333333) - llamaSpit.getY();
        double zDifference = target.getZ() - this.getZ();
        double adjustmentForRange = Math.sqrt(xDifference * xDifference + zDifference * zDifference) * (llamaSpit.getGravity() * 10f / 3f);
        llamaSpit.shoot(xDifference, yDifference + adjustmentForRange, zDifference, 2.0F, 1.0F);
        if (!this.isSilent()) {
            this.level().playSound(
                    null,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    SoundEvents.LLAMA_SPIT,
                    this.getSoundSource(),
                    1.0F,
                    1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
            );
        }

        this.level().addFreshEntity(llamaSpit);
        this.didSpit = true;
    }

}
