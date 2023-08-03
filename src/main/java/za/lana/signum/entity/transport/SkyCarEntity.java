/**
 * SIGNUM
 * MIT License
 * This is the first flying transport, a futuristic flying car
 * It should use element zero and tiberium fuel cells or something
 * as a longterm fuel source.
 * or a futuristic battery, starting with a manganese battery
 *
 * The controls should also be made custom, so clients can change
 * the keybinds if they want to.
 * Lana
 * */
package za.lana.signum.entity.transport;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import za.lana.signum.item.ModItems;

/**
 * README
 * this entity controls are unstable,
 * need to fix it so it flies as a big drone
 * similar to the airdrone entity movecontrol
 * need to fix this.
 *
 * */

public class SkyCarEntity extends FlyingEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean pressingLeft;
    private boolean pressingRight;
    private boolean pressingForward;
    private boolean pressingBack;
    private double skyYaw;
    private double skyPitch;
    private double skyRoll;

    public SkyCarEntity(EntityType<? extends SkyCarEntity> entityType, World level) {
        super(entityType, level);
    }
    public static DefaultAttributeContainer setAttributes() {
        return PathAwareEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1)
                .build();
    }


    // Let the player ride the entity
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.hasPassengers()) {
            player.startRiding(this);
            return super.interactMob(player, hand);
        }
        return super.interactMob(player, hand);
    }

    // Turn off step sounds
    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
    }

    // stop entity
    // this.setVelocity(Vec3d.ZERO);


    @Override
    public boolean hasNoGravity() {
        return true;
    }
    protected float getGravity() {
        return -0.04f;
    }
    public void setInputs(boolean pressingLeft, boolean pressingRight, boolean pressingForward, boolean pressingBack) {
        this.pressingLeft = pressingLeft;
        this.pressingRight = pressingRight;
        this.pressingForward = pressingForward;
        this.pressingBack = pressingBack;
    }
    private static float getMovementMultiplier(boolean positive, boolean negative) {
        if (positive == negative) {
            return 0.0f;
        }
        return positive ? 1.0f : -1.0f;
    }
// Apply player-controlled movement
    @Override
    public void travel(Vec3d pos) {
        if (this.isAlive()) {
            if (this.hasPassengers()) {
                LivingEntity passenger = getControllingPassenger();
                this.prevYaw = getYaw();
                this.prevPitch = getPitch();
                setYaw(passenger.getYaw());
                setPitch(passenger.getPitch() * 0.5F);
                setRotation(getYaw(), getPitch());
                //float w = passenger.fallDistance; // * 0.2f;
                //float y = passenger.upwardSpeed * 0.5f;

                float x = passenger.sidewaysSpeed; //* 0.5F;
                float z = passenger.forwardSpeed; //* 2.0F;
                if (z <= 0)
                    z *= 0.25F;

                if (this.hasPassengers()) {
                    float f = 0.0F;
                    if (this.pressingLeft) {
                        --this.bodyYaw;
                    }
                    if (this.pressingRight) {
                        ++this.bodyYaw;
                    }
                    if (this.pressingRight != this.pressingLeft && !this.pressingForward && !this.pressingBack) {
                        f += 0.0F;
                    }
                    this.setYaw(this.getYaw() + this.forwardSpeed);
                    if (this.pressingForward) {
                        f += 0.04F;
                    }
                    if (this.pressingBack) {
                        f -= 0.005F;
                    }
                    this.setMovementSpeed(4F);
                    super.travel(new Vec3d(x, pos.y, z));

                    /**
                    Vec3d vec3d = new Vec3d(this.getX(), this.getY(), this.getZ());
                    double d = vec3d.length();
                    vec3d = vec3d.normalize();
                    this.setVelocity(this.getVelocity().add(vec3d.multiply(0.0025f)));
                     **/

                    /**
                     * need to fix the pitch with custom keybinds
                     *  this.setVelocity(this.getVelocity().add(0.0, 0.3, 0.0));
                     * */

                }
            }
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return getFirstPassenger() instanceof LivingEntity entity ? entity : null;
    }

    @Override
    public boolean isLogicalSideForUpdatingMovement() {
        return true;
    }

    // Adjust the rider's position while riding
    @Override
    public void updatePassengerPosition(Entity entity, PositionUpdater moveFunction) {
        super.updatePassengerPosition(entity, moveFunction);

        if (entity instanceof LivingEntity passenger) {
            moveFunction.accept(entity, getX(), getY() - 0.1f, getZ());
            this.prevPitch = passenger.prevPitch;
        }
    }
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < this.getMaxPassengers();
    }
    protected int getMaxPassengers() {
        return 2;
    }

    @Override
    public void setPitch(float pitch) {
        float loops = (float) (Math.floor((pitch + 180f) / 360f) * 360f);
        pitch -= loops;
        prevPitch -= loops;
        super.setPitch(pitch);
    }
    //so a player can pickup the entity after travelling
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.getWorld().isClient && !this.isRemoved()) {
            this.scheduleVelocityUpdate();
            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
            boolean bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).getAbilities().creativeMode;
            if (!bl && this.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                this.dropItems(source);
            }
            this.discard();
        }
        return true;
    }
    protected void dropItems(DamageSource source) {
        this.dropItem(this.asItem());
    }
    public Item asItem() {
        Item skycar;
        skycar = ModItems.SKYCAR_SPAWN_EGG;
        return skycar;
    }
    public boolean collidesWith(Entity other) {
        return canCollide(this, other);
    }

    public static boolean canCollide(Entity entity, Entity other) {
        return (other.isCollidable() || other.isPushable()) && !entity.isConnectedThroughVehicle(other);
    }

    public boolean isCollidable() {
        return true;
    }

    public boolean isPushable() {
        return true;
    }
    public boolean canUsePortals() {
        return !this.hasVehicle() && !this.hasPassengers();
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.5F;
    }

    // Add our idle/moving animation controller
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //controllers.add(new AnimationController(this, "controller", 0, this::predicate));
        controllers.add(new AnimationController<>(this, "controller", 0, state -> {
            if (state.isMoving() && getControllingPassenger() != null) {
                return state.setAndContinue(RawAnimation.begin().then("animation.skycar.fly", Animation.LoopType.LOOP));
            }
            else {
                return state.setAndContinue(RawAnimation.begin().then("animation.skycar.idle", Animation.LoopType.LOOP));
            }
            // Handle the sound keyframe that is part of our animation json
        }).setSoundKeyframeHandler(event -> {
            // We don't have a sound for this yet :(
        }));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }


}
