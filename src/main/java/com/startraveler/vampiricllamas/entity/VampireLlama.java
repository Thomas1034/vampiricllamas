package com.startraveler.vampiricllamas.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.startraveler.vampiricllamas.VampiricLlamasEntities;
import com.startraveler.vampiricllamas.entity.goal.BoundedDistanceRangedAttackGoal;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VampireLlama extends Llama {

    public static final int LONG_DURATION_RESURRECTION_BUFF = 240;
    public static final int SMOKE_PUFFS_TO_CREATE = 80;
    public static final int SHORT_DURATION_RESURRECTION_BUFF = 60;
    public static final Predicate<LivingEntity> IS_VALID_VAMPIRE_TARGET = Predicates.and(
            VampireLlama::isNotUndead,
            VampireLlama::isNight
    );
    protected static final EntityDataAccessor<Integer> DATA_RESURRECTIONS_ID = SynchedEntityData.defineId(
            VampireLlama.class,
            EntityDataSerializers.INT
    );
    protected static final EntityDataAccessor<Float> DATA_DAMAGE_TAKEN_SINCE_LAST_DEALT_ID = SynchedEntityData.defineId(
            VampireLlama.class,
            EntityDataSerializers.FLOAT
    );
    protected static final float DEFAULT_DAMAGE_TAKEN_SINCE_LAST_DEALT = 0;
    protected static final int DEFAULT_RESURRECTIONS = 1;
    private static final int BATS_TO_SUMMON = 5;
    private static final ItemStack CHORUS_FRUIT_STACK = new ItemStack(Items.CHORUS_FRUIT);

    public VampireLlama(EntityType<? extends VampireLlama> entityType, Level level) {
        super(entityType, level);
        this.setResurrections(level.getDifficulty().getId());
    }

    @SuppressWarnings("unused")
    public VampireLlama(Level level) {
        this(VampiricLlamasEntities.VAMPIRE_LLAMA.get(), level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return createBaseChestedHorseAttributes().add(Attributes.FOLLOW_RANGE, 40.0F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.MAX_HEALTH, 50.0f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5f)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.SAFE_FALL_DISTANCE, 1024)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 10.0);
    }

    protected static boolean isNotUndead(LivingEntity entity) {
        return !entity.getType().is(EntityTypeTags.UNDEAD);
    }

    protected static boolean isNight(LivingEntity entity) {
        return entity.level().isNight();
    }

    // From AbstractVillager
    protected static void addParticlesAroundEntity(LivingEntity entity, ParticleOptions particleOption, int count) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    particleOption,
                    entity.getX(),
                    entity.getY() + entity.getBbHeight() / 2,
                    entity.getZ(),
                    count,
                    entity.getBbWidth() / 4 + 0.125f,
                    entity.getBbHeight() / 4 + 0.125f,
                    entity.getBbWidth() / 4 + 0.125f,
                    0f
            );
        }

    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2));
        this.goalSelector.addGoal(
                2,
                new BoundedDistanceRangedAttackGoal(this, 1.25F, 10, 20, 20.0F, FloatList.of(16, 32))
        );
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 2.0D, true));
        this.goalSelector.addGoal(4, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0F));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.7));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, IS_VALID_VAMPIRE_TARGET)
        );
        this.targetSelector.addGoal(
                4,
                new NearestAttackableTargetGoal<>(this, Llama.class, 10, true, false, IS_VALID_VAMPIRE_TARGET)
        );
        this.targetSelector.addGoal(
                5,
                new NearestAttackableTargetGoal<>(this, TraderLlama.class, 10, true, false, IS_VALID_VAMPIRE_TARGET)
        );
        this.targetSelector.addGoal(
                6,
                new NearestAttackableTargetGoal<>(this, Camel.class, 10, true, false, IS_VALID_VAMPIRE_TARGET)
        );
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_RESURRECTIONS_ID, DEFAULT_RESURRECTIONS);
        builder.define(DATA_DAMAGE_TAKEN_SINCE_LAST_DEALT_ID, DEFAULT_DAMAGE_TAKEN_SINCE_LAST_DEALT);
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

    public int getResurrections() {
        return this.entityData.get(DATA_RESURRECTIONS_ID);
    }

    public void setResurrections(int resurrectionsRemaining) {
        this.entityData.set(DATA_RESURRECTIONS_ID, resurrectionsRemaining);
    }

    public float getDamageTakenSinceLastDealt() {
        return this.entityData.get(DATA_DAMAGE_TAKEN_SINCE_LAST_DEALT_ID);
    }

    public void setDamageTakenSinceLastDealt(float damageTakenSinceLastDealt) {
        this.entityData.set(DATA_DAMAGE_TAKEN_SINCE_LAST_DEALT_ID, damageTakenSinceLastDealt);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (result && this.level() instanceof ServerLevel) {
            // Track how much damage it has taken since it last dealt damage.
            float damageTakenSinceLastDealt = this.getDamageTakenSinceLastDealt();
            // If that exceeds a difficulty-based fraction of its health bar (1/2 on peaceful, 1/3 on easy, 1/4 on normal, 1/5 on hard), randomly teleport to break stunlock/trap.
            if (damageTakenSinceLastDealt > this.getMaxHealth() / (2 + this.level().getDifficulty().getId())) {
                // Use the chorus fruit teleportation logic.
                // TODO hacky solution, I guess?
                Items.CHORUS_FRUIT.finishUsingItem(CHORUS_FRUIT_STACK, this.level(), this);
            }
            // Update the amount of damage taken since it last dealt damage.
            // This means that, yes, it will keep teleporting on taking damage till it can attack
            // successfully. That's intended.
            this.setDamageTakenSinceLastDealt(damageTakenSinceLastDealt + amount);
        }
        return result;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        boolean result = super.doHurtTarget(entity);
        if (result && this.level() instanceof ServerLevel) {
            // Reset the damage-taken counter, since it dealt damage successfully.
            this.setDamageTakenSinceLastDealt(0);
        }

        return result;
    }


    @Override
    public boolean checkTotemDeathProtection(@NotNull DamageSource damageSource) {

        int resurrectionsRemaining = this.getResurrections();

        if (resurrectionsRemaining > 0) {

            resurrectionsRemaining--;
            this.setResurrections(resurrectionsRemaining);

            this.setHealth(this.getMaxHealth());
            this.removeEffectsCuredBy(net.neoforged.neoforge.common.EffectCures.PROTECTED_BY_TOTEM);

            int difficultyScale = this.level().getDifficulty().getId();
            int shortDurationEffectLength = SHORT_DURATION_RESURRECTION_BUFF * difficultyScale;
            int longDurationEffectLength = LONG_DURATION_RESURRECTION_BUFF * difficultyScale;

            this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, shortDurationEffectLength, 0));
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, shortDurationEffectLength, 1));
            this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, longDurationEffectLength, 9));
            this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, longDurationEffectLength, 0));
            this.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, longDurationEffectLength, 0));
            VampireLlama.addParticlesAroundEntity(this, ParticleTypes.CAMPFIRE_COSY_SMOKE, SMOKE_PUFFS_TO_CREATE);
            for (int i = 0; i < BATS_TO_SUMMON; i++) {
                summonBat();
            }
            return true;
        }

        return super.checkTotemDeathProtection(damageSource);
    }

    protected void summonBat() {
        Bat bat; // come sit on my hat
        bat = new Bat(EntityType.BAT, this.level());
        bat.setResting(false);
        bat.setPos(this.position());

        this.level().addFreshEntity(bat);
    }

}
