/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.entity.transport;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.State;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
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
import za.lana.signum.event.KeyInputHandler;
import za.lana.signum.item.ModItems;
import za.lana.signum.screen.gui.AirBalloonDescription;
import za.lana.signum.util.ImplementedInventory;

import java.util.function.ToIntFunction;

public class AirBalloonEntity
        extends AnimalEntity
        implements GeoEntity, ImplementedInventory, NamedScreenHandlerFactory {

    private int fuel;
    private int fuelTime = 0;
    private int maxFuelTime = 2;
    public double U = 0.5f / 16; // entity attribute speed
    public double V = 0.25f;
    public double W = V + U;
    private float lastIntensity;
    private int lastAge;


    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private static final Ingredient ACCEPTABLE_FUEL = Ingredient.ofItems(
            Items.CHARCOAL,
            Items.COAL,
            ModItems.COKECOAL,
            ModItems.ELEMENTZEROCOAL,
            ModItems.TIBERIUMCOAL);

    private static final VoxelShape SHAPE = makeShape();
    private static VoxelShape makeShape(){
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(2, 6.53125, -0.484375, 2.03125, 7.03125, 2.50625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.984375, 6.53125, 2.49375, 2.015625, 7.03125, 2.525));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-1, 6.53125, -0.490625, -0.96875, 7.03125, 2.503125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.984375, 6.53125, -0.5, 2.00625, 7.03125, -0.46875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-1, 6.5, 2.515625, 2.03125, 6.53125, 2.984375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-1, 6.5, -0.96875, 2.03125, 6.53125, -0.5));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-1.484375, 6.5, -0.96875, -0.984375, 6.53125, 2.984375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.9875, 7.0125, -0.46875, 2.003125, 7.04375, 2.5));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(2.015625, 6.5, -0.96875, 2.515625, 6.53125, 2.984375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(2.5, 4.59375, -0.9375, 2.53125, 6.515625, 2.96875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-1.46875, 4.59375, 2.96875, 2.5, 6.515625, 3));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-1.5, 4.59375, -0.953125, -1.46875, 6.515625, 2.984375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-1.46875, 4.59375, -0.9750000000000001, 2.515625, 6.515625, -0.9437500000000001));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.03125, 0.4375, 0, 1.03125, 0.5, 2));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.03125, 0.08125, -0.35624999999999996, 1.03125, 0.14375, 0.14375000000000004));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.03125, 1.43125, -1.0625, 1.03125, 1.49375, -0.5625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.0625, 0, 1.375, 0.125, 2));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.375, 0.0625, 0, 0.125, 0.125, 2));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.09375, 0.125, 0.25, 0.03125, 0.1875, 1.75));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.96875, 0.125, 0.25, 1.09375, 0.1875, 1.75));

        return shape;
    }

    private final boolean isInAir;
    // this does not want to work?
    //private final PropertyDelegate propertyDelegate;

    //todo I cannot get the voxelshape/bounding box to work
    public
    AirBalloonEntity(EntityType<? extends AnimalEntity> entityType, World level) {
        super(entityType, level);
        Box boundingBox = SHAPE.getBoundingBox();
        this.setBoundingBox(boundingBox);
        this.setStepHeight(0.1f);
        this.isInAir = isFallFlying();

        PropertyDelegate propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> AirBalloonEntity.this.fuelTime;
                    case 1 -> AirBalloonEntity.this.maxFuelTime;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AirBalloonEntity.this.fuelTime = value;
                    case 1 -> AirBalloonEntity.this.maxFuelTime = value;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    // BASICS
    public static DefaultAttributeContainer setAttributes() {
        return PathAwareEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1)
                .build();
    }
    @Override
    public boolean isLogicalSideForUpdatingMovement() {
        return true;
    }
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.1F;
    }
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    // SHAPE
    public static boolean canCollide(Entity entity, Entity other) {
        return (other.isCollidable() || other.isPushable()) && !entity.isConnectedThroughVehicle(other);
    }
    public boolean isPushable() {
        return true;
    }
    public boolean collidesWith(Entity other) {
        return canCollide(this, other);
    }

    // DATA
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putDouble("airballoon.V", this.V);
        nbt.putDouble("airballoon.W", this.W);
        nbt.putShort("airballoon.fuel", (short)this.fuel);
        nbt.putInt("airballoon.fuelTime", fuelTime);
        nbt.putInt("airballoon.maxFuelTime", maxFuelTime);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        this.V = nbt.getDouble("airballoon.V");
        this.W = nbt.getDouble("airballoon.W");
        this.fuel = nbt.getShort("airballoon.fuel");
        fuelTime = nbt.getShort("airballoon.fueltime");
        maxFuelTime = nbt.getShort("airballoon.maxfueltime");
    }


    // INVENTORY / STORAGE
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    private static ToIntFunction<State> createLightLevelFromBlockState(int litLevel) {
        return (State) -> (Boolean)State.get(Properties.LIT) ? litLevel : 0;
    }

    // FUEL
    private void consumeFuel() {

        if (ACCEPTABLE_FUEL.test(getStack(0)) && this.fuel + 3600 <= 32000) {
            if(!getStack(0).isEmpty()) {
                this.fuel += 3600;
                this.fuelTime = FuelRegistry.INSTANCE.get(this.removeStack(0, 1).getItem());
                this.maxFuelTime = this.fuelTime;
            }
        }
    }
    private static boolean hasFuel(AirBalloonEntity vehicle) {
        return !vehicle.getStack(0).isEmpty();
    }
    private static boolean burningFuel(AirBalloonEntity vehicle) {
        return vehicle.fuelTime > 0;
    }
    private void resetProgress() {
        this.fuelTime = 0;
    }

    // TRAVEL & CONTROLS
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.hasPassengers()) {
            player.startRiding(this);
            player.sendMessage(Text.literal("Press Y to go Up, B to go Down, and H for Inventory"), false);
        }
        return super.interactMob(player, hand);
    }

    // todo fuel burning not working, cant find the error below:
    @Override
    public void travel(Vec3d pos) {
        if (this.isAlive()) {
            if (this.hasPassengers()) {
                if (burningFuel(this)) {
                    //this.fuelTime--;
                    if(this.fuelTime > 0) {

                        this.setMovementSpeed((float) ((float) V + W));
                        LivingEntity passenger = getControllingPassenger();
                        this.prevYaw = getYaw();
                        this.prevPitch = getPitch();
                        assert passenger != null;
                        setYaw(passenger.getYaw());
                        setPitch(passenger.getPitch() * 0.1f);
                        setRotation(getYaw(), getPitch());
                        this.bodyYaw = this.getYaw();
                        this.headYaw = this.bodyYaw;
                        float x = (float) (passenger.sidewaysSpeed * V / 2);
                        float y = (float) (passenger.upwardSpeed * V / 3);
                        float z = (float) (passenger.forwardSpeed * V / 1.5);
                        if (y <= 0)
                            y *= 0.25f;
                        if (KeyInputHandler.flyUpKey.isPressed()) {
                            y = 0.3f;
                            fuelFlameIncrease(getWorld(), getBlockPos());}
                        if (KeyInputHandler.flyUpKey.isPressed()) {
                            y = 0.5f;
                            fuelFlameIncrease(getWorld(), getBlockPos());
                            // should we only use fuel here?
                            this.fuelTime--;
                                }
                        else if ((KeyInputHandler.flyDownkey.isPressed())) {
                            y = -0.3f;
                            fuelFlameDecrease(getWorld(), getBlockPos());
                        } else {
                            this.fallDistance = (float) (-1.2f + V);
                            y = -0.48f * 2.5f;
                            this.onLanding();
                            this.bounce(this);
                        }
                        this.move(MovementType.SELF, this.getVelocity());
                        super.travel(new Vec3d(x, y, z));

                        } else {
                        if (this.fuelTime < 0) {
                            this.consumeFuel();
                        } else{
                            if (!hasFuel(this)) {
                                this.resetProgress();
                            }
                            // when the fuel is finished
                            this.setVelocity(this.getVelocity().add(0.0, -0.3f, 0.0));
                        }
                    }
                    this.setMovementSpeed((float) ((float) V + W - 0.5f));
                }
            }
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }
    private void bounce(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0) {
            double d = entity instanceof LivingEntity ? 1.0 : 0.8;
            entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
        }
    }

    // PASSENGERS
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return getFirstPassenger() instanceof LivingEntity entity ? entity : null;
    }
    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < this.getMaxPassengers();
    }
    protected int getMaxPassengers() {
        return 2;
    }
    @Override
    public void updatePassengerPosition(Entity entity, PositionUpdater moveFunction) {
        super.updatePassengerPosition(entity, moveFunction);
        if (entity instanceof LivingEntity passenger) {
            moveFunction.accept(entity, getX(), getY() - 0.1f, getZ());
            this.prevPitch = passenger.prevPitch;
        }
    }

    // DROPS AS A ITEM
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
    protected void dropItems(DamageSource player) {
        this.dropItem(this.asItem());
    }
    public Item asItem() {
        Item airballoon;
        airballoon = ModItems.AIRBALOON_SPAWN_EGG;
        return airballoon;
    }
    @Override
    public void remove(Entity.RemovalReason reason) {
        if (!this.getWorld().isClient && reason.shouldDestroy()) {
            ItemScatterer.spawn(this.getWorld(), this, this);
        }
        super.remove(reason);
    }



    // STATE
    // todo I am unsure of how states work with mobs
    // will move lit state with fire particles similar to furnace


    // GECKO ANIMATIONS
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> {
            if (state.isMoving() && getControllingPassenger() != null) {
                return state.setAndContinue(RawAnimation.begin().then("animation.airballoon.fly", Animation.LoopType.LOOP));

            }
            if (this.isInAir && getControllingPassenger() != null) {
                return state.setAndContinue(RawAnimation.begin().then("animation.airballoon.idle", Animation.LoopType.LOOP));
            }
            else {
                return null;
                //return state.setAndContinue(RawAnimation.begin().then("animation.airballoon.idle", Animation.LoopType.LOOP));
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

    // GUI
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        World world = this.getWorld();
        BlockPos pos = this.getBlockPos();
        return new AirBalloonDescription(syncId, inventory, ScreenHandlerContext.create(world, pos));
    }
    @Override
    public Text getDisplayName() {
        return Text.literal("Air Balloon");
    }


    // todo having trouble getting the correct position for the particles to spawn
    // ENTITY FX
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_FIRE_AMBIENT;
    }
    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        //this.playSound(SigSounds.BALLOON_FLY, 0.15f, 1.0f);
    }
    private void playFireAmbientSound() {
        this.lastIntensity *= (float)Math.pow(0.997, this.age - this.lastAge);
        this.lastIntensity = Math.min(1.0f, this.lastIntensity + 0.07f);
        float f = 0.5f + this.lastIntensity * this.random.nextFloat() * 1.2f; // volume?
        float g = 0.1f + this.lastIntensity * 1.2f; // pitch?
        this.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, g, f);
        this.lastAge = this.age;
    }

    private void fuelFlameIncrease(World world, BlockPos pos) {
        this.getWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + 2.2, this.getZ() + 1.5,
                0.0, 1.7, 0.0);
        world.playSound(this.getX(), this.getY() + 0.5, this.getZ() -0.5,
                SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
    }
    private void fuelFlameDecrease(World world, BlockPos pos) {
        this.getWorld().addParticle(ParticleTypes.ASH, this.getX(), this.getY() + 2.2, this.getZ() + 1.5,
                0.0, 0.9, 0.0);
        playFireAmbientSound();
    }
    public boolean canUsePortals() {
        return !this.hasVehicle() && !this.hasPassengers();
    }
}