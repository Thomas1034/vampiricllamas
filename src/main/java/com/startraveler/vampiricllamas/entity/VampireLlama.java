package com.startraveler.vampiricllamas.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.startraveler.vampiricllamas.Config;
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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.Container;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
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
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class VampireLlama extends Llama {

    public static final int LONG_DURATION_RESURRECTION_BUFF = 240;
    public static final int SHORT_DURATION_RESURRECTION_BUFF = 60;
    public static final Predicate<LivingEntity> IS_VALID_VAMPIRE_TARGET = Predicates.and(
            VampireLlama::isNotUndead,
            VampireLlama::isNight
    );
    protected static final EntityDataAccessor<Integer> DATA_RESURRECTIONS_ID = SynchedEntityData.defineId(
            VampireLlama.class,
            EntityDataSerializers.INT
    );
    protected static final EntityDataAccessor<Boolean> DATA_IS_ATTACKING_ID = SynchedEntityData.defineId(
            VampireLlama.class,
            EntityDataSerializers.BOOLEAN
    );

    protected static final int DEFAULT_RESURRECTIONS = 1;
    private static final ItemStack CHORUS_FRUIT_STACK = new ItemStack(Items.CHORUS_FRUIT);

    public VampireLlama(EntityType<? extends VampireLlama> entityType, Level level) {
        super(entityType, level);
        this.setResurrections(this.getEffectiveDifficulty());
        this.setPersistenceRequired();
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
                .add(Attributes.ENTITY_INTERACTION_RANGE, 10.0)
                .add(Attributes.STEP_HEIGHT, 1.75);
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

    protected static int getBatsToSummon() {
        return Config.BATS_TO_SUMMON.getAsInt();
    }

    protected static int getSmokePuffsToCreate() {
        return Config.SMOKE_PUFFS_TO_CREATE.getAsInt();
    }

    protected static void addEffectsForDeathProtection(Entity e, int shortDuration, int longDuration) {
        if (e instanceof LivingEntity le) {
            // Give invisibility, a health boost, and resistance to various environmental effects
            // (fire, water, etc.)
            le.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, shortDuration, 0));
            le.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, longDuration, 9));
            le.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, longDuration, 0));
            le.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, longDuration, 0));
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
        }
    }

    public int getEffectiveDifficulty() {
        if (Config.SHOULD_OVERRIDE_DIFFICULTY.getAsBoolean()) {
            return Config.DIFFICULTY_OVERRIDE.getAsInt();
        }
        return this.level().getDifficulty().getId();
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

                result = convertLlamaToVampire(level, llama, result);
            }
        }

        return result;
    }

    public static boolean convertLlamaToVampire(@NotNull ServerLevel level, Llama llama, boolean result) {
        VampireLlama vampireLlama = llama.convertTo(VampiricLlamasEntities.VAMPIRE_LLAMA.get(), true);
        if (vampireLlama != null) {
            vampireLlama.finalizeSpawn(
                    level,
                    level.getCurrentDifficultyAt(vampireLlama.blockPosition()),
                    MobSpawnType.CONVERSION,
                    new LlamaGroupData(llama.getVariant())
            );
            vampireLlama.setStrength(llama.getStrength());
            // Copy chestedness
            vampireLlama.setChest(llama.hasChest());
            if (vampireLlama.hasChest()) {
                vampireLlama.createInventory();

            }
            // Copy inventory.
            Container llamaInventory = llama.getInventory();
            Container vampireInventory = vampireLlama.getInventory();
            int containerSize = llamaInventory.getContainerSize();
            for (int i = 0; i < containerSize; i++) {
                vampireInventory.setItem(i, llamaInventory.getItem(i));
            }

            net.neoforged.neoforge.event.EventHooks.onLivingConvert(llama, vampireLlama);
            if (!llama.isSilent()) {
                level.levelEvent(null, LevelEvent.SOUND_ZOMBIE_INFECTED, llama.blockPosition(), 0);
            }

            result = false;
        }
        return result;
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));
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
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, VampireLlama.class));
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
        builder.define(DATA_IS_ATTACKING_ID, false);
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

    public boolean isAttacking() {
        return this.entityData.get(DATA_IS_ATTACKING_ID);
    }

    public void setAttacking(boolean isAttacking) {
        this.entityData.set(DATA_IS_ATTACKING_ID, isAttacking);
    }

    @Override
    public boolean checkTotemDeathProtection(@NotNull DamageSource damageSource) {

        int resurrectionsRemaining = this.getResurrections();

        if (resurrectionsRemaining > 0 && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {

            resurrectionsRemaining--;
            this.setResurrections(resurrectionsRemaining);

            this.setHealth(this.getMaxHealth());
            this.removeEffectsCuredBy(net.neoforged.neoforge.common.EffectCures.PROTECTED_BY_TOTEM);


            // Get the difficulty...
            int difficultyScale = this.getEffectiveDifficulty();

            // Scale buffs by the difficulty...
            int shortDurationEffectLength = SHORT_DURATION_RESURRECTION_BUFF * difficultyScale;
            int longDurationEffectLength = LONG_DURATION_RESURRECTION_BUFF * difficultyScale;

            // Make rider(s) buffed too.
            this.getPassengersAndSelf()
                    .forEach(entity -> VampireLlama.addEffectsForDeathProtection(
                            entity,
                            shortDurationEffectLength,
                            longDurationEffectLength
                    ));

            // Cosmetics; smoke and bats.
            this.addCosmeticPhaseChangeEffects();

            // If the difficulty is above easy, the final resurrection is different.
            // This one teleports it and buffs it more.
            if (resurrectionsRemaining == 0 && difficultyScale > 1) {
                // Splash blindness.
                this.makeAreaOfEffectCloud(this.getBlindnessPotion());
                // Teleport.
                Items.CHORUS_FRUIT.finishUsingItem(CHORUS_FRUIT_STACK, this.level(), this);

            } else {
                // Give speed so it can better keep up.
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, shortDurationEffectLength, 1));
            }

            return true;
        }

        return super.checkTotemDeathProtection(damageSource);
    }

    private PotionContents getBlindnessPotion() {
        return new PotionContents(
                Optional.empty(),
                Optional.of(0x0),
                List.of(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0))
        );
    }

    // From ThrownPotion.
    private void makeAreaOfEffectCloud(PotionContents potionContents) {

        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());

        areaeffectcloud.setOwner(this);

        areaeffectcloud.setRadius(3.0F);
        areaeffectcloud.setRadiusOnUse(-0.5F);

        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());

        areaeffectcloud.setPotionContents(potionContents);

        this.level().addFreshEntity(areaeffectcloud);
    }

    private void addCosmeticPhaseChangeEffects() {
        // Add puffs of smoke and bats, for confusion.
        VampireLlama.addParticlesAroundEntity(
                this,
                ParticleTypes.CAMPFIRE_COSY_SMOKE,
                VampireLlama.getSmokePuffsToCreate()
        );
        for (int i = 0; i < VampireLlama.getBatsToSummon(); i++) {
            summonBat();
        }
    }

    protected void summonBat() {
        Bat bat; // come under on my hat
        bat = new Bat(EntityType.BAT, this.level());
        bat.setResting(false);
        bat.setPos(this.position());

        this.level().addFreshEntity(bat);
    }

}
