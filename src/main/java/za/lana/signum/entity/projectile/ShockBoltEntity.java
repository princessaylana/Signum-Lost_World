/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
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
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class ShockBoltEntity extends ThrownItemEntity {
    private static final TrackedData<Boolean> HIT =
            DataTracker.registerData(ShockBoltEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final int counter = 0;
    protected final float dam = 1.5f * 2;
    protected final int age1 = 200;

    public ShockBoltEntity(EntityType<ShockBoltEntity> type, World world) {
        super(type, world);
    }
    public ShockBoltEntity(World world, PlayerEntity player) {
        super(ModEntities.SHOCK_PROJECTILE, world);
        setOwner(player);
        BlockPos blockpos = player.getBlockPos();
        double d0 = (double)blockpos.getX() + 0.5D;
        double d1 = (double)blockpos.getY() + 1.75D;
        double d2 = (double)blockpos.getZ() + 0.5D;
        this.refreshPositionAndAngles(d0, d1, d2, this.getYaw(), this.getPitch());
        if (this.age >= age1) {
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult){
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        int i = entity instanceof EndermanEntity ? 6 : 0;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), i);

        if (entity instanceof LivingEntity) {
            BlockPos frontOfPlayer = entity.getBlockPos().offset(entity.getHorizontalFacing(), 1);
            //entity to spawn
            World world2 = this.getWorld();
            LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world2);
            lightningBolt.setPosition(frontOfPlayer.toCenterPos());
            world2.spawnEntity(lightningBolt);
            //entity.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            entity.damage(getWorld().getDamageSources().magic(), dam);
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.SHOCK_EFFECT, 60 * 2 , 1 / 4)));
            this.discard();
        }
        for(int x = 0; x < 18; ++x) {
            for(int y = 0; y < 18; ++y) {
                this.getWorld().addParticle(ModParticles.FLAME_PARTICLE, this.getX(), this.getY(), this.getZ(),
                        Math.cos(x*20) * 0.15d, Math.cos(y*20) * 0.15d, Math.sin(x*20) * 0.15d * 0.5f);}
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            Entity entity = this.getOwner();
            if (!(entity instanceof MobEntity) || this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                if (this.getWorld().isAir(blockPos)) {
                    World level = this.getWorld();
                    LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt.setPosition(this.getBlockPos().toCenterPos());
                    level.spawnEntity(lightningBolt);
                }   this.discard();
            }
        }
        for(int x = 0; x < 18; ++x) {
            for(int y = 0; y < 18; ++y) {
                this.getWorld().addParticle(ModParticles.FLAME_PARTICLE, this.getX(), this.getY() + 0.5, this.getZ(),
                        Math.cos(x*20) * 0.15d, Math.cos(y*20) * 0.15d, Math.sin(x*20) * 0.15d * 0.5f);
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


