package com.startraveler.vampiricllamas.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TrackingEmitter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;

public class TunableSpeedTrackingEmitter extends TrackingEmitter {
    protected final float maxSpeed;

    @SuppressWarnings("unused")
    public TunableSpeedTrackingEmitter(ClientLevel level, Entity entity, ParticleOptions particleType, float maxSpeed) {
        super(level, entity, particleType);
        this.maxSpeed = maxSpeed;
    }

    public TunableSpeedTrackingEmitter(ClientLevel level, Entity entity, ParticleOptions particleType, float maxSpeed, int lifetime) {
        super(level, entity, particleType, lifetime);
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void tick() {
        float twiceMaxSpeed = this.maxSpeed * 2;
        for (int i = 0; i < 16; i++) {
            double dx = (this.random.nextFloat() * twiceMaxSpeed - this.maxSpeed);
            double dy = (this.random.nextFloat() * twiceMaxSpeed - this.maxSpeed);
            double dz = (this.random.nextFloat() * twiceMaxSpeed - this.maxSpeed);
            if ((dx * dx + dy * dy + dz * dz) <= (this.maxSpeed * this.maxSpeed)) {
                double x = this.entity.getX(dx / 4.0);
                double y = this.entity.getY(0.5 + dy / 4.0);
                double z = this.entity.getZ(dz / 4.0);
                this.level.addParticle(this.particleType, false, x, y, z, dx, dy + 0.2 * this.maxSpeed, dz);
            }
        }

        this.life++;
        if (this.life >= this.lifeTime) {
            this.remove();
        }
    }
}
