package com.startraveler.vampiricllamas.entity;

import com.startraveler.vampiricllamas.VampiricLlamasEffects;
import com.startraveler.vampiricllamas.VampiricLlamasEntities;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
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
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Llamia extends Llama implements VampiresThatAttack {

    protected static final EntityDataAccessor<Boolean> DATA_IS_ATTACKING_ID = SynchedEntityData.defineId(
            Llamia.class,
            EntityDataSerializers.BOOLEAN
    );

    public Llamia(EntityType<? extends Llamia> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("unused")
    public Llamia(Level level) {
        this(VampiricLlamasEntities.LLAMIA.get(), level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return createBaseChestedHorseAttributes().add(Attributes.FOLLOW_RANGE, 40.0F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.MAX_HEALTH, 100.0f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.85f)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.SAFE_FALL_DISTANCE, 1024)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 10.0)
                .add(Attributes.STEP_HEIGHT, 1.75);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (!super.doHurtTarget(entity)) {
            return false;
        } else {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(VampiricLlamasEffects.VENOM, 200, 0), this);
            }

            return true;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level() instanceof ServerLevel) {
            boolean isAttacking = (this.getTarget() != null || this.isAggressive());

            if (this.isAttacking() != isAttacking) {
                this.setAttacking(isAttacking);
            }

            if (this.tickCount % 20 == 0) {

                this.heal(2.0f);
            }
        }
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 2.0D, true));
        this.goalSelector.addGoal(4, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0F));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.7));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, VampireLlama.class, Llamia.class));
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(
                        this,
                        Player.class,
                        10,
                        true,
                        false,
                        VampireLlama.IS_VALID_VAMPIRE_TARGET
                )
        );
        this.targetSelector.addGoal(
                4,
                new NearestAttackableTargetGoal<>(
                        this,
                        Llama.class,
                        10,
                        true,
                        false,
                        VampireLlama.IS_VALID_VAMPIRE_TARGET
                )
        );
        this.targetSelector.addGoal(
                5,
                new NearestAttackableTargetGoal<>(
                        this,
                        TraderLlama.class,
                        10,
                        true,
                        false,
                        VampireLlama.IS_VALID_VAMPIRE_TARGET
                )
        );
        this.targetSelector.addGoal(
                6,
                new NearestAttackableTargetGoal<>(
                        this,
                        Camel.class,
                        10,
                        true,
                        false,
                        VampireLlama.IS_VALID_VAMPIRE_TARGET
                )
        );
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_ATTACKING_ID, false);
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.addEffect(new MobEffectInstance(
                VampiricLlamasEffects.VENOMOUS,
                MobEffectInstance.INFINITE_DURATION,
                MobEffectInstance.MIN_AMPLIFIER, true, false
        ));
        this.setPersistenceRequired();
        return result;
    }

    @Override
    public boolean canMate(@NotNull Animal otherAnimal) {
        return false;
    }

    // From Zombie, loosely.
    @Override
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity entity) {
        boolean result = super.killedEntity(level, entity);
        if (level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD) {
            if (entity instanceof Llama llama && (llama.getType() == EntityType.LLAMA) && net.neoforged.neoforge.event.EventHooks.canLivingConvert(
                    entity, VampiricLlamasEntities.VAMPIRE_LLAMA.get(), (timer) -> {
                    }
            )) {
                if (level.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
                    return result;
                }

                result = VampireLlama.convertLlamaToVampire(level, llama, result);
            } else if (entity instanceof TraderLlama llama && (llama.getType() == EntityType.TRADER_LLAMA) && net.neoforged.neoforge.event.EventHooks.canLivingConvert(
                    entity, VampiricLlamasEntities.LLAMIA.get(), (timer) -> {
                    }
            )) {
                if (level.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
                    return result;
                }

                result = VampireLlama.convertLlamaToLlamia(level, llama, result);
            }
        }

        return result;
    }

    public boolean isAttacking() {
        return this.entityData.get(DATA_IS_ATTACKING_ID);
    }

    public void setAttacking(boolean isAttacking) {
        this.entityData.set(DATA_IS_ATTACKING_ID, isAttacking);
    }
}
