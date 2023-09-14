package za.lana.signum.entity.projectile;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class IceBoltEntity extends ThrownItemEntity {
    private static final TrackedData<Boolean> HIT =
            DataTracker.registerData(IceBoltEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int counter = 0;
    protected final float dam = 1.5f * 2;
    protected final int age1 = 200;

    public IceBoltEntity(EntityType<IceBoltEntity> type, World world) {
        super(type, world);
    }
    public IceBoltEntity(World world, PlayerEntity player) {
        super(ModEntities.ICE_PROJECTILE, world);
        setOwner(player);
        BlockPos blockpos = player.getBlockPos();
        double d0 = (double)blockpos.getX() + 0.5D;
        double d1 = (double)blockpos.getY() + 1.75D;
        double d2 = (double)blockpos.getZ() + 0.5D;
        this.refreshPositionAndAngles(d0, d1, d2, this.getYaw(), this.getPitch());
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult){
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        int i = entity instanceof EndermanEntity ? 6 : 0;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), i);
        if (entity instanceof LivingEntity) {
            // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.FREEZE_EFFECT, 60 * 2 , 1 / 4)));
            // plays a sound for the entity hit only
            entity.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            entity.damage(getWorld().getDamageSources().magic(), dam);
            this.discard();
        }
        for(int x = 0; x < 18; ++x) {
            for(int y = 0; y < 18; ++y) {
                this.getWorld().addParticle(ModParticles.FREEZE_PARTICLE, this.getX(), this.getY(), this.getZ(),
                        Math.cos(x*20) * 0.15d, Math.cos(y*20) * 0.15d, Math.sin(x*20) * 0.15d * 0.5f);}
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            //this.getWorld().addParticle(ModParticles.FREEZE_PARTICLE, this.getX(), this.getY() + 0.5, this.getZ(), 0.5, 0.5 * 0.25d * 0.5f, 0.5);
            // creates ice blocks
            Entity entity = this.getOwner();
            if (!(entity instanceof MobEntity) || this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                if (this.getWorld().isAir(blockPos)) {
                    this.getWorld().setBlockState(blockPos, Blocks.ICE.getDefaultState());
                }   this.discard();
            }
        }
        for(int x = 0; x < 18; ++x) {
            for(int y = 0; y < 18; ++y) {
                this.getWorld().addParticle(ModParticles.FREEZE_PARTICLE, this.getX(), this.getY() + 0.5, this.getZ(),
                        Math.cos(x*20) * 0.15d, Math.cos(y*20) * 0.15d, Math.sin(x*20) * 0.15d * 0.5f);
            }
        }
    }

    private static boolean isSnowOrIce(BlockState state) {
        return state.isOf(Blocks.PACKED_ICE) || state.isOf(Blocks.SNOW_BLOCK) || state.isOf(Blocks.BLUE_ICE);
    }
    private void bounce() {
        Vec3d vec3d = this.getVelocity();

        if (vec3d.y < 0.0) {
            double d = 0.5;
            this.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
        }
        if (vec3d.x < 0.0) {
            double d = 1.0;
            this.setVelocity(-vec3d.x * d, vec3d.y, vec3d.z);
        }
        if (vec3d.z < 0.0) {
            double d = 1.0;
            this.setVelocity(vec3d.x, vec3d.y, -vec3d.z *d);
        }
        if (this.age >= age1) {
            this.remove(RemovalReason.DISCARDED);
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


