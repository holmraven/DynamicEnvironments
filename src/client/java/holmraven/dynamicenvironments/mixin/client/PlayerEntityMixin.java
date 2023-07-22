package holmraven.dynamicenvironments.mixin.client;

import holmraven.dynamicenvironments.DynamicEnvironments;
import holmraven.dynamicenvironments.particle.FootprintParticle;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
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
    private boolean isRightFoot;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void footprintTick(CallbackInfo ci) {
        footprintGenerator();
        if (this.fallDistance > 0.5F) {
            genDouble = true;
        }
    }

    @Unique
    private void footprintGenerator() {
        float feetDistanceToCenter = isRightFoot ? -0.15F : 0.15F;
        float getYawRadians = (float) Math.toRadians(MathHelper.wrapDegrees(this.bodyYaw));
        double velocityX = MathHelper.cos(getYawRadians);
        double velocityZ = MathHelper.sin(getYawRadians);
        double x = this.getX() + velocityX * feetDistanceToCenter;
        double y = this.getY() + 0.001F * FootprintParticle.zFighter;
        double z = this.getZ() + velocityZ * feetDistanceToCenter;
        if (this.isOnGround() && !this.isInvisible() && !this.isSubmergedInWater()) {
            if ((int) this.calculateNextStepSoundDistance() == step + 1 && !this.getWorld().getBlockState(new BlockPos((int)x, (int)y - 1, (int)z)).isAir()) {
                this.getWorld().addParticle((ParticleEffect) Registries.PARTICLE_TYPE.get(new Identifier(DynamicEnvironments.MODID, "footprint")), x, y, z, velocityX, 0, velocityZ);
            } else if (genDouble) {
                if (!this.getWorld().getBlockState(new BlockPos((int)x, (int)y - 1, (int)z)).isAir()) {
                    this.getWorld().addParticle((ParticleEffect) Registries.PARTICLE_TYPE.get(new Identifier(DynamicEnvironments.MODID, "footprint")), x, y, z, velocityX, 0, velocityZ);
                }
                x = this.getX() + velocityX * -feetDistanceToCenter;
                z = this.getZ() + velocityZ * -feetDistanceToCenter;
                if (!this.getWorld().getBlockState(new BlockPos((int)x, (int)y - 1, (int)z)).isAir()) {
                    this.getWorld().addParticle((ParticleEffect) Registries.PARTICLE_TYPE.get(new Identifier(DynamicEnvironments.MODID, "footprint")), x, y, z, velocityX, 0, velocityZ);
                }
                genDouble = false;
            }
        }
        if ((int) this.calculateNextStepSoundDistance() == step + 1) {
            step++;
            isRightFoot = !isRightFoot;
        }
    }
}
