package za.lana.signum.entity.projectile;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.FireSkeletonEntity;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class FireBoltEntity extends ThrownItemEntity {
    private static final TrackedData<Boolean> HIT =
            DataTracker.registerData(FireBoltEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int counter = 0;
    protected final float dam = 1.5f * 2;
    protected final int age1 = 200;

    public FireBoltEntity(EntityType<FireBoltEntity> type, World world) {
        super(type, world);
    }
    public FireBoltEntity(World world, PlayerEntity player) {
        super(ModEntities.FIRE_PROJECTILE, world);
        setOwner(player);
        BlockPos blockpos = player.getBlockPos();
        double d0 = (double)blockpos.getX() + 0.5D;
        double d1 = (double)blockpos.getY() + 1.75D;
        double d2 = (double)blockpos.getZ() + 0.5D;
        this.refreshPositionAndAngles(d0, d1, d2, this.getYaw(), this.getPitch());
        if (this.age >= age1) {
            this.discard();
        }
        if (this.getWorld().isClient) {
            for (int j = 0; j < 2; ++j) {
                this.getWorld().addParticle(ModParticles.FLAME_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult){
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        int i = entity instanceof EndermanEntity ? 6 : 0;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), i);
        if (entity.isFireImmune()){
            entity.damage(getWorld().getDamageSources().magic(), dam * 2);
            this.discard();
        }
        if (entity instanceof FireSkeletonEntity){
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.HEALING_EFFECT, 60, 1/4)));
        }
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.BURN_EFFECT, 60 * 2 , 1 / 4)));
            entity.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            entity.damage(getWorld().getDamageSources().magic(), dam);
            this.discard();
        }
        if (this.getWorld().isClient) {
            for (int j = 0; j < 2; ++j) {
                this.getWorld().addParticle(ModParticles.FLAME_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
                this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            Entity entity = this.getOwner();
            // Starts fires
            if (!(entity instanceof MobEntity) || this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                if (this.getWorld().isAir(blockPos)) {
                    this.getWorld().setBlockState(blockPos, AbstractFireBlock.getState(this.getWorld(), blockPos));
                }   this.discard();
            }
        }
        if (this.getWorld().isClient) {
            for (int j = 0; j < 2; ++j) {
                this.getWorld().addParticle(ModParticles.FLAME_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
                this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            }
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HIT, false);
    }
}


