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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import za.lana.signum.Signum;
import za.lana.signum.event.KeyInputHandler;
import za.lana.signum.item.ModItems;
import za.lana.signum.screen.AirBalloonScreenHandler;
import za.lana.signum.util.ImplementedInventory;

public class AirBalloonEntity
        extends AnimalEntity
        implements GeoEntity, ImplementedInventory, NamedScreenHandlerFactory {

    protected final PropertyDelegate propertyDelegate;
    private int fuel;
    private int fuelTime = 0;
    private int maxFuelTime = 4;
    public double U = 0.05f;
    public double V = 0.25f;
    public double W = V + U; // 0.30f
    /** QUICK CALC
     U = 0.05f;
     V = 0.25f;
     W = 0.30f;
     **/
    private float lastIntensity;
    private int lastAge;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public static final String TPATH = "signum_dim";


    private final boolean isInAir;
    public static final String AIRBALLOON_MOUNT = "message.signum.airballoon.mount";
    public static final String AIRBALLOON_EMPTY = "message.signum.airballoon.empty";
    //todo I cannot get the voxelshape/bounding box to work
    public
    AirBalloonEntity(EntityType<? extends AnimalEntity> entityType, World level) {
        super(entityType, level);
        this.setStepHeight(0.1f);
        this.isInAir = isFallFlying();
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return AirBalloonEntity.this.fuelTime;
                    case 1: return AirBalloonEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: AirBalloonEntity.this.fuelTime = value; break;
                    case 1: AirBalloonEntity.this.maxFuelTime = value; break;
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
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.hasPassengers()) {
            player.startRiding(this);
            World world = player.getWorld();
            {
                if(world.isClient){
                    player.sendMessage(Text.translatable(AIRBALLOON_MOUNT).fillStyle(
                            Style.EMPTY.withColor(Formatting.YELLOW)), false);
                }
            }
        }
        return super.interactMob(player, hand);
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
        nbt.putDouble("airballoon.V", V);
        nbt.putDouble("airballoon.W", W);
        nbt.putInt("airballoon.fuel", fuel);
        nbt.putInt("airballoon.fuelTime", fuelTime);
        nbt.putInt("airballoon.maxFuelTime", maxFuelTime);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        this.V = nbt.getDouble("airballoon.V");
        this.W = nbt.getDouble("airballoon.W");
        fuel = nbt.getInt("airballoon.fuel");
        fuelTime = nbt.getInt("airballoon.fueltime");
        maxFuelTime = nbt.getInt("airballoon.maxfueltime");
    }

    // INVENTORY / STORAGE
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    // FUEL
    private static boolean hasFuel(AirBalloonEntity vehicle) {
        return !vehicle.getStack(0).isEmpty();
    }

    private void consumeFuel(AirBalloonEntity airBalloon) {
        if(!getStack(0).isEmpty()) {
            this.fuelTime = FuelRegistry.INSTANCE.get(this.removeStack(0, 1).getItem());
            this.maxFuelTime = this.fuelTime;
            this.fuel = this.fuelTime;
        }
    }

    private static boolean burningFuel(AirBalloonEntity vehicle) {
        return vehicle.fuelTime > 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            if (burningFuel(this)){
                --this.fuelTime;
            }
            else if (hasFuel(this) && !burningFuel(this)){
                consumeFuel(this);
            }
        }
    }
    @Override
    public void travel(Vec3d pos) {
        if (isAlive()) {
            if (hasPassengers()) {
                this.setMovementSpeed((float) V);
                LivingEntity passenger = getControllingPassenger();
                this.prevYaw = getYaw();
                this.prevPitch = getPitch();
                assert passenger != null;
                setYaw(passenger.getYaw());
                setPitch(passenger.getPitch() * 0.1f);
                setRotation(getYaw(), getPitch());
                this.bodyYaw = this.getYaw();
                this.headYaw = this.bodyYaw;
                float x = (float) (passenger.sidewaysSpeed * V / 2.0f);
                float y = (float) (passenger.upwardSpeed * V / 2.5f);
                float z = (float) (passenger.forwardSpeed * V / 0.25f);
                if (y <= 0)
                    y *= 0.25f;
                if ((KeyInputHandler.flyDownkey.isPressed())) {
                    y = -5.0f * 2;
                    fuelFlameDecrease(getWorld(), getBlockPos());
                }
                else if (fuelTime > 0) {
                    if (KeyInputHandler.flyUpKey.isPressed()) {
                        y = 1.0f;
                        fuelFlameIncrease(getWorld(), getBlockPos());
                    }
                }
                // Float Air
                else {
                    y = -0.48f * 2.5f;
                    this.onLanding();
                    this.bounce(this);
                }
                super.travel(new Vec3d(x, y, z));
            }
            else if (!hasPassengers()) {
                Vec3d vec3d2 = this.getVelocity();
                super.travel(new Vec3d(vec3d2.x, vec3d2.y - 0.08f, vec3d2.z));
            }
        }
        this.onLanding();
        this.bounce(this);
    }

    // LANDING
    // gravity seems to break the dynamics.
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
    public void onPassengerLookAround(Entity passenger) {
    }
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
                // no animation when its on the ground
                return null;
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
    public Text getDisplayName() {
        return Text.literal("Air Balloon");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AirBalloonScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
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

    /***
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world instanceof ServerWorld && entity.canUsePortals() && VoxelShapes.matchesAnywhere(VoxelShapes.cuboid(entity.getBoundingBox().offset(-pos.getX(), -pos.getY(), -pos.getZ())), state.getOutlineShape(world, pos), BooleanBiFunction.AND)) {
            String signumDim = "signum_dim";

            RegistryKey<World> registryKey = world.getRegistryKey() == (Signum.MOD_ID, "signum_dim") ? World.OVERWORLD : World.END;
            //RegistryKey<World> registryKey = world.getRegistryKey() == World.END ? World.OVERWORLD : World.END;
            ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);
            if (serverWorld == null) {
                return;
            }
            entity.moveToWorld(serverWorld);
        }
    }
    **/

}