/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.projectile;

import net.minecraft.block.AbstractBlock;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.mob.ElveGuardEntity;
import za.lana.signum.entity.mob.WizardEntity;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class SpellBoltEntity
        extends ProjectileEntity {
    protected final int age1 = 200;
    protected final float dam = 1.5f * 3;
    private final SpellBoltEntity entity = this;

    public SpellBoltEntity(EntityType<? extends SpellBoltEntity> entityType, World world) {
        super(entityType, world);
    }
    //
    public SpellBoltEntity(World world, WizardEntity owner) {
        this(ModEntities.SPELLBOLT_ENTITY, world);
        this.setOwner(owner);

        BlockPos blockpos = owner.getBlockPos();
        double d0 = (double)blockpos.getX() + 0.5D;
        double d1 = (double)blockpos.getY() + 1.75D;
        double d2 = (double)blockpos.getZ() + 0.5D;
        this.refreshPositionAndAngles(d0, d1, d2, this.getYaw(), this.getPitch());
        if (this.age >= age1) {
            this.discard();
        }
        if (this.getWorld().isClient) {
            for (int j = 0; j < 2; ++j) {
                this.getWorld().addParticle(ModParticles.WHITE_SHROOM_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
        this.setPosition(
                owner.getX() - (double)(owner.getWidth() + 1.0f) * 0.5 * (double)MathHelper.sin(owner.bodyYaw * ((float)Math.PI / 180)),
                owner.getEyeY() - (double)0.1f,
                owner.getZ() + (double)(owner.getWidth() + 1.0f) * 0.5 * (double)MathHelper.cos(owner.bodyYaw * ((float)Math.PI / 180)));
    }

    @Override
    protected void initDataTracker() {
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
            this.setVelocity(this.getVelocity().add(0.0, -0.016f, 0.0));
            //this.setVelocity(this.getVelocity().add(0.0, -0.03f, 0.0));
        }
        if (this.getWorld().isClient) {
            for (int j = 0; j < 2; ++j) {
                this.getWorld().addParticle(ModParticles.TRANSMUTE_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
        this.setPosition(d, e, f);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult){
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        World level = getEntityWorld();

        int i = entity instanceof EndermanEntity ? 6 : 0;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), i);

        if (entity instanceof ClientPlayerEntity) {
            // PLAYER
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.TRANSMUTE_EFFECT, 60 * 2 , 1 / 4)));
            entity.damage(getWorld().getDamageSources().magic(), dam);
            this.entity.playSpawnEffects();
            this.discard();
        }
        if (entity instanceof EnderDragonEntity || entity instanceof WitherEntity || entity instanceof GhastEntity || entity instanceof FrogEntity || entity instanceof WardenEntity){
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.TRANSMUTE_EFFECT, 60 * 2 , 1 / 4)));
            entity.damage(getWorld().getDamageSources().magic(), dam);
            this.entity.playSpawnEffects();
            this.discard();
        }
        if (entity instanceof WizardEntity || entity instanceof ElveGuardEntity || entity instanceof VillagerEntity ){
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.HEALING_EFFECT, 60, 1/4)));
            this.discard();
        }
        else{
            // JUST PURE MAGIC DAMAGE
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.TRANSMUTE_EFFECT, 60 * 2 , 1 / 4)));
            entity.damage(getWorld().getDamageSources().magic(), dam);
            this.entity.playSpawnEffects();
            this.discard();
        }
        if (this.getWorld().isClient) {
            for (int j = 0; j < 2; ++j) {
                this.getWorld().addParticle(ModParticles.WHITE_SHROOM_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
                this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            }
        }
    }

    // TODO NEED TO CHANGE THIS ?
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        // EFFECT - DISABLED FOR NOW
        if (!this.getWorld().isClient) {
            this.discard();
        }
        // PARTICLES AND SOUND
        if (this.getWorld().isClient) {
            for (int j = 0; j < 2; ++j) {
                this.getWorld().addParticle(ModParticles.WHITE_SHROOM_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
                this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            }
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        double d = packet.getVelocityX();
        double e = packet.getVelocityY();
        double f = packet.getVelocityZ();
        for (int i = 0; i < 7; ++i) {
            double g = 0.4 + 0.1 * (double)i;
            this.getWorld().addParticle(ModParticles.WHITE_SHROOM_PARTICLE, this.getX(), this.getY(), this.getZ(), d * g, e, f * g);
        }
        this.setVelocity(d, e, f);
    }
    public void playSpawnEffects() {
        if (this.getWorld().isClient) {
            for(int i = 0; i < 20; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                double g = 10.0;
                this.getWorld().addParticle(ParticleTypes.POOF, this.offsetX(1.0) - d * 10.0, this.getRandomBodyY() - e * 10.0, this.getParticleZ(1.0) - f * 10.0, d, e, f);
            }
        } else {
            this.getWorld().sendEntityStatus(this, (byte)20);
        }
    }
}

