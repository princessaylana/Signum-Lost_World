package za.lana.signum.entity.projectile;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class TransmuteBoltEntity extends ThrownItemEntity {
    private static final TrackedData<Boolean> HIT =
            DataTracker.registerData(TransmuteBoltEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int counter = 0;
    protected final float dam = 1.5f * 2;
    protected final int age1 = 200;
    private final TransmuteBoltEntity entity = this;

    public TransmuteBoltEntity(EntityType<TransmuteBoltEntity> type, World world) {
        super(type, world);
    }
    public TransmuteBoltEntity(World world, PlayerEntity player) {
        super(ModEntities.TRANSMUTE_PROJECTILE, world);
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
        World level = getEntityWorld();

        int i = entity instanceof EndermanEntity ? 6 : 0;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), i);

        if (entity instanceof ClientPlayerEntity) {
                //
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.TRANSMUTE_EFFECT, 60 * 2 , 1 / 4)));
            entity.damage(getWorld().getDamageSources().magic(), dam);
            this.entity.playSpawnEffects();
            this.discard();
            }
        if (entity instanceof EnderDragonEntity || entity instanceof WitherEntity || entity instanceof GhastEntity || entity instanceof FrogEntity ){
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.TRANSMUTE_EFFECT, 60 * 2 , 1 / 4)));
            entity.damage(getWorld().getDamageSources().magic(), dam);
            this.entity.playSpawnEffects();
            this.discard();
        }else{
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.TRANSMUTE_EFFECT, 60 * 2 , 1 / 4)));

            if (!this.getWorld().isClient) {
                // spawn a frog #1
                EntityType.FROG.spawn(((ServerWorld) entity.getWorld()), entity.getBlockPos(), SpawnReason.TRIGGERED);
                    // spawn a frog #2
                EntityType.FROG.spawn(((ServerWorld) entity.getWorld()), entity.getBlockPos(), SpawnReason.TRIGGERED);
                    // spawn a frog #3
                EntityType.FROG.spawn(((ServerWorld) entity.getWorld()), entity.getBlockPos(), SpawnReason.TRIGGERED);
                    //
                this.entity.playSpawnEffects();
                entity.damage(getWorld().getDamageSources().magic(), dam);
                entity.discard();
                this.discard();
                }
        }
        if (this.getWorld().isClient){
            level.addParticle(ModParticles.TRANSMUTE_PARTICLE, getX(), getY(), getZ(), 0.0, 2.0, 0.0);
            this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        // EFFECT
        if (!this.getWorld().isClient) {
            //transmute blocks
            Entity entity = this.getOwner();
            if (!(entity instanceof MobEntity) || this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                if (this.getWorld().isAir(blockPos)) {
                this.getWorld().setBlockState(blockPos, ModBlocks.BLIGHT_BLOCK.getDefaultState());
                }
                this.discard();
            }
        }
        // PARTICLES AND SOUND
        if (this.getWorld().isClient()){
            this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            World world = this.getWorld();
            world.addParticle(ModParticles.TRANSMUTE_PARTICLE, this.getX() + 0.5, this.getY() + 2.0, this.getZ() + 0.5,
                    (double)((float)this.getX() + random.nextFloat()) - 0.5,
                    (float)this.getY() - random.nextFloat() - 1.0f,
                    (double)((float)this.getZ() + random.nextFloat()) - 0.5);
        }
    }
    public void playSpawnEffects() {
        if (this.getWorld().isClient) {
            for (int i = 0; i < 20; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                double g = 10.0;
                this.getWorld().addParticle(ParticleTypes.POOF, this.offsetX(1.0) - d * 10.0, this.getRandomBodyY() - e * 10.0, this.getParticleZ(1.0) - f * 10.0, d, e, f);
            }
        } else {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_SPAWN_EFFECTS);
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


