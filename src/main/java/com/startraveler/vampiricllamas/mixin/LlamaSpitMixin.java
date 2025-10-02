package com.startraveler.vampiricllamas.mixin;

import com.startraveler.vampiricllamas.entity.LlamaBloodSpit;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.projectile.LlamaSpit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LlamaSpit.class)
public class LlamaSpitMixin {


    @ModifyArg(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"), index = 1 // The damage amount argument is the 2nd parameter (0-based index)
    )
    public float modifySpitDamage(float original) {
        LlamaSpit llamaSpit = (LlamaSpit) (Object) (this);

        if (llamaSpit instanceof LlamaBloodSpit bloodSpit) {
            return bloodSpit.getSpitDamage();
        }
        return original;
    }

    @ModifyArg(method = "recreateFromPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"), index = 0 // The particle type argument is the 1st parameter (0-based index)
    )
    public ParticleOptions modifySpitParticleType(ParticleOptions particleData) {
        LlamaSpit llamaSpit = (LlamaSpit) (Object) (this);
        if (llamaSpit instanceof LlamaBloodSpit bloodSpit) {
            return new DustColorTransitionOptions(
                    LlamaBloodSpit.BLOOD_COLOR1,
                    LlamaBloodSpit.BLOOD_COLOR2,
                    bloodSpit.getRandom().nextFloat() + 0.5f
            );
        }
        return particleData;
    }
}
