package holmraven.dynamicenvironments.mixin.client;

import holmraven.dynamicenvironments.DynamicEnvironments;
import holmraven.dynamicenvironments.particle.FootprintParticle;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Unique
    private int step = 1;

    @Unique
    private boolean genDouble = false;

    @Unique
    private boolean isRightFoot = false;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void footprintTick(CallbackInfo ci) {
        float getYawRadians = (float)Math.toRadians(MathHelper.wrapDegrees(this.bodyYaw));
        double velX = MathHelper.cos(getYawRadians);
        double velZ = MathHelper.sin(getYawRadians);
        double y = this.getY() + 0.001F * FootprintParticle.zFighter;
        if (this.isOnGround() && !this.isInvisible() && !this.isSubmergedInWater()) {
            if ((int)this.calculateNextStepSoundDistance() == step + 1) {
                addFootprint(isRightFoot ? calculateRight(velX, velZ)[0] : calculateLeft(velX, velZ)[0], y, isRightFoot ? calculateRight(velX, velZ)[1] : calculateLeft(velX, velZ)[1], velX, velZ);
            } else if (genDouble) {
                addFootprint(calculateLeft(velX, velZ)[0], y, calculateLeft(velX, velZ)[1], velX, velZ);
                addFootprint(calculateRight(velX, velZ)[0], y, calculateRight(velX, velZ)[1], velX, velZ);
                genDouble = false;
            }
        }
        if ((int)this.calculateNextStepSoundDistance() == step + 1) {
            step++;
            isRightFoot = !isRightFoot;
        }
        if (this.fallDistance > 1.0F) {
            genDouble = true;
        }
    }

    @Unique
    private double[] calculateLeft(double velX, double velZ) {
        double x = this.getX() + velX * 0.15F;
        double z = this.getZ() + velZ * 0.15F;
        return new double[] {x, z};
    }

    @Unique
    private double[] calculateRight(double velX, double velZ) {
        double x = this.getX() + velX * -0.15F;
        double z = this.getZ() + velZ * -0.15F;
        return new double[] {x, z};
    }

    @Unique
    private void addFootprint(double x, double y, double z, double velX, double velZ) {
        this.getWorld().addParticle((ParticleEffect) Registries.PARTICLE_TYPE.get(new Identifier(DynamicEnvironments.MODID, "footprint")), x, y, z, velX, 0, velZ);
    }
}