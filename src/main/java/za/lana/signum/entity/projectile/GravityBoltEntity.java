/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

import java.util.List;

public class GravityBoltEntity extends ThrownItemEntity {
    private BlockState pushedBlock = Blocks.AIR.getDefaultState();
    private static final ThreadLocal<Direction> entityMovementDirection = ThreadLocal.withInitial(() -> null);
    private static final TrackedData<Boolean> HIT =
            DataTracker.registerData(GravityBoltEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected final float dam = 1.5f;
    protected int age1 = 200;
    protected final float strength = 8.0f;
    protected int boxSize = 5;


    public GravityBoltEntity(EntityType<GravityBoltEntity> type, World world) {
        super(type, world);
    }
    public GravityBoltEntity(World world, PlayerEntity player) {
        super(ModEntities.GRAVITY_PROJECTILE, world);
        setOwner(player);
        BlockPos blockpos = player.getBlockPos();
        double d0 = (double)blockpos.getX() + 0.5D;
        double d1 = (double)blockpos.getY() + 1.75D;
        double d2 = (double)blockpos.getZ() + 0.5D;
        this.refreshPositionAndAngles(d0, d1, d2, this.getYaw(), this.getPitch());
        this.entityTick();
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
        if (!entity.canFreeze()){
            entity.damage(getWorld().getDamageSources().magic(), dam * 2);
            this.discard();
        }
        if (entity instanceof LivingEntity) {
            BlockPos blockPos = entity.getBlockPos();
            World level = this.getWorld();
            this.pushEntities(level, blockPos, strength, this, (LivingEntity) entity);
            level.getEntitiesByClass(LivingEntity.class, entity.getBoundingBox().expand(boxSize), e->true).forEach(e->e
                    //
                    .addStatusEffect(new StatusEffectInstance(ModEffects.GRAVITY_EFFECT, 60, 3), entity));
            entity.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            entity.damage(getWorld().getDamageSources().magic(), dam);
            this.discard();
        }
        for(int x = 0; x < 18; ++x) {
            for(int y = 0; y < 18; ++y) {
                this.getWorld().addParticle(ModParticles.BlUE_DUST_PARTICLE, this.getX(), this.getY(), this.getZ(),
                        Math.cos(x*20) * 0.15d, Math.cos(y*20) * 0.15d, Math.sin(x*20) * 0.15d * 0.5f);}
        }
    }

    // TODO WIP - MOVING BLOCKS
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            Entity entity = this.getOwner();
            if (!(entity instanceof MobEntity) || this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
                World level = this.getWorld();
                BlockState blockState = Block.postProcessState(this.pushedBlock, level, blockPos);
                setWorld(level);
                level.setBlockState(blockPos, this.pushedBlock, Block.NO_REDRAW | Block.FORCE_STATE | Block.MOVED);
                Block.replace(this.pushedBlock, blockState, level, blockPos, 3);
                level.setBlockState(blockPos, blockState, Block.NOTIFY_ALL);
                level.updateNeighbor(blockPos, blockState.getBlock(), blockPos);
                this.discard();
            }
        }
        for(int x = 0; x < 18; ++x) {
            for(int y = 0; y < 18; ++y) {
                this.getWorld().addParticle(ModParticles.BlUE_DUST_PARTICLE, this.getX(), this.getY() + 0.5, this.getZ(),
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

    // PUSH DYNAMICS
    public void pushEntities(World world, BlockPos pos, float f, GravityBoltEntity gravityBolt, LivingEntity target) {
        Direction direction = gravityBolt.getMovementDirection();
        Box box = this.getBoundingBox().expand(8.0, 1.0, 8.0);
        double d = f - strength;
        List<Entity> list = world.getOtherEntities(null, Boxes.stretch(box, direction, d).union(box));
        if (list.isEmpty()) {
            return;
        }
        if (target instanceof ServerPlayerEntity){
            Vec3d vec3d = target.getVelocity();
            double e = vec3d.x;
            double g = vec3d.y;
            double h = vec3d.z;
            switch (direction.getAxis()) {
                case X: {
                    e = direction.getOffsetX();
                    break;
                }
                case Y: {
                    g = direction.getOffsetY();
                    break;
                }
                case Z: {
                    h = direction.getOffsetZ();
                }
            }
            target.setVelocity(e, g, h);
        }
        double i = 0.0;
        i = Math.min(i, d) + 0.01;
        moveEntity(direction, target, i, direction);
        push(pos, target, direction, d);
    }
    private static void push(BlockPos pos, Entity entity, Direction direction, double amount) {
        Direction direction2;
        double d;
        Box box2;
        Box box = entity.getBoundingBox();
        if (box.intersects(box2 = VoxelShapes.fullCube().getBoundingBox().offset(pos)) && Math.abs((
                d = getIntersectionSize(box2, direction2 = direction.getOpposite(), box) + 0.01) - (getIntersectionSize(box2, direction2, box.intersection(box2)) + 0.01)) < 0.01) {
            d = Math.min(d, amount) + 0.01;
            moveEntity(direction, entity, d, direction2);
        }
    }
    private static double getIntersectionSize(Box box, Direction direction, Box box2) {
        switch (direction) {
            case EAST: {
                return box.maxX - box2.minX;
            }
            case WEST: {
                return box2.maxX - box.minX;
            }
            default: {
                return box.maxY - box2.minY;
            }
            case DOWN: {
                return box2.maxY - box.minY;
            }
            case SOUTH: {
                return box.maxZ - box2.minZ;
            }
            case NORTH:
        }
        return box2.maxZ - box.minZ;
    }
    private static void moveEntity(Direction direction, Entity entity, double distance, Direction movementDirection) {
        entityMovementDirection.set(direction);
        entity.move(MovementType.PISTON, new Vec3d(distance * (double)movementDirection.getOffsetX(), distance * (double)movementDirection.getOffsetY(), distance * (double)movementDirection.getOffsetZ()));
        entityMovementDirection.remove();
    }
    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        if (world.createCommandRegistryWrapper(RegistryKeys.BLOCK).getOptional(this.pushedBlock.getBlock().getRegistryEntry().registryKey()).isEmpty()) {
            this.pushedBlock = Blocks.AIR.getDefaultState();
        }
    }
    public void entityTick(){
        --this.age1;
        if(this.age1 == 0)
            this.discard();
    }
}


