
package za.lana.signum.entity.projectile;

import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.*;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class TiberiumSpitEntity
        extends ProjectileEntity {
    public TiberiumSpitEntity(EntityType<? extends TiberiumSpitEntity> entityType, World world) {
        super(entityType, world);
    }

    //
    public TiberiumSpitEntity(World world, TiberiumFloaterEntity owner) {
        this(ModEntities.TIBERIUMSPIT_PROJECTILE, world);
        this.setOwner(owner);
        if (this.getWorld().isClient) {
            for (int j = 0; j < 2; ++j) {
                this.getWorld().addParticle(ModParticles.TIBERIUM_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
        this.setPosition(
                owner.getX() - (double)(owner.getWidth() + 1.0f) * 0.5 * (double)MathHelper.sin(owner.bodyYaw * ((float)Math.PI / 180)),
                owner.getEyeY() - (double)0.1f,
                owner.getZ() + (double)(owner.getWidth() + 1.0f) * 0.5 * (double)MathHelper.cos(owner.bodyYaw * ((float)Math.PI / 180)));
    }

    //
    @Override
    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        float g = 0.99f;
        float h = 0.06f;
        if (this.getWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.discard();
            return;
        }
        if (this.isInsideWaterOrBubbleColumn()) {
            this.discard();
            return;
        }
        this.setVelocity(vec3d.multiply(0.99f));
        if (!this.hasNoGravity()) {
            // y default is 0.06
            this.setVelocity(this.getVelocity().add(0.0, -0.03f, 0.0));
        }
        this.setPosition(d, e, f);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity target = entityHitResult.getEntity();
        if (target instanceof TibSkeletonEntity || target instanceof TiberiumWormEntity || target instanceof TiberiumFloaterEntity || target instanceof TiberiumWizardEntity){
            ((LivingEntity) target).addStatusEffect((new StatusEffectInstance(ModEffects.HEALING_EFFECT, 60, 1/4)));
        }
        if (target instanceof LivingEntity livingEntity && !(target instanceof TiberiumFloaterEntity)) {
            ((LivingEntity) target).addStatusEffect((new StatusEffectInstance(ModEffects.TIBERIUM_POISON, 60 * 2 , 1 / 4)));
            entityHitResult.getEntity().damage(this.getDamageSources().mobProjectile(this, livingEntity), 1.5f * 2);
        }
        if (this.getWorld().isClient) {
            for (int j = 0; j < 2; ++j) {
                this.getWorld().addParticle(ModParticles.TIBERIUM_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
                this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            this.getWorld().addParticle(ModParticles.TIBERIUM_PARTICLE, this.getX(), this.getY() + 0.5, this.getZ(),
                    0.5, 0.5 * 0.25d * 0.5f, 0.5);
            // spawn tiberium fire
            Entity entity = this.getOwner();
            if (!(entity instanceof MobEntity) || this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                if (this.getWorld().isAir(blockPos)) {
                    this.getWorld().setBlockState(blockPos, ModBlocks.TIBERIUM_FIRE.getDefaultState());
                }   this.discard();
            }
        }
        if (this.getWorld().isClient) {
            for (int j = 0; j < 2; ++j) {
                this.getWorld().addParticle(ModParticles.TIBERIUM_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
                this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            }
        }
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        double d = packet.getVelocityX();
        double e = packet.getVelocityY();
        double f = packet.getVelocityZ();
        for (int i = 0; i < 7; ++i) {
            double g = 0.4 + 0.1 * (double)i;
            this.getWorld().addParticle(ModParticles.TIBERIUM_PARTICLE, this.getX(), this.getY(), this.getZ(), d * g, e, f * g);
        }
        this.setVelocity(d, e, f);
    }
}

