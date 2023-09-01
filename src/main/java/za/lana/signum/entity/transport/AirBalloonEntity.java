/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.entity.transport;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
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
import za.lana.signum.networking.ModMessages;
import za.lana.signum.screen.gui.AirBalloonDescription;
import za.lana.signum.screen.gui.AirBalloonScreen;
import za.lana.signum.util.ImplementedInventory;

import java.util.OptionalInt;


public class AirBalloonEntity
        extends AnimalEntity
        implements GeoEntity, ImplementedInventory, NamedScreenHandlerFactory {

    private int fuel;
    public double pushX;
    public double pushZ;
    public double pushY = upwardSpeed;
    private int fuelTime = 0;
    private int maxFuelTime = 4;

    // Average particle gravity 0.06f

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private static final TrackedData<Boolean> LIT = DataTracker.registerData(AirBalloonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final Ingredient ACCEPTABLE_FUEL = Ingredient.ofItems(
            Items.CHARCOAL,
            Items.COAL,
            ModItems.COKECOAL,
            ModItems.ELEMENTZEROCOAL,
            ModItems.TIBERIUMCOAL);

    public AirBalloonEntity(EntityType<? extends AnimalEntity> entityType, World level) {
        super(entityType, level);
        this.setStepHeight(0.1f);
    }


    public static DefaultAttributeContainer setAttributes() {
        return PathAwareEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5F)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1)
                .build();
    }

    // need to add a screen with fuelslot inventory to load and burn fuel
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.hasPassengers()) {
            player.startRiding(this);
            player.sendMessage(Text.literal("Press Y to go Up, B to go Down, and H for Inventory"), false);

            }
        return super.interactMob(player, hand);
    }


    private void consumeFuel() {
        if (ACCEPTABLE_FUEL.test(getStack(0)) && this.fuel + 3600 <= 32000) {
            if(!getStack(0).isEmpty()) {
                this.fuel += 3600;
                this.fuelTime = FuelRegistry.INSTANCE.get(this.removeStack(0, 1).getItem());
                this.maxFuelTime = this.fuelTime;
            }
        }
    }

    // sounds to be added later
    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        //this.playSound(SigSounds.BALLOON_FLY, 0.15f, 1.0f);
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_FIRE_AMBIENT;
    }
    public boolean hasFuel() {
        //super.tick();
        if (!this.getWorld().isClient()) {
            if (this.fuel > 0) {
                --this.fuel;
                this.consumeFuel();
            }
            LivingEntity passenger = getControllingPassenger();

            pushX = passenger.sidewaysSpeed;
            pushY = passenger.upwardSpeed;
            pushZ = passenger.forwardSpeed;

            if (this.fuel <= 0) {
                this.pushX = 0.0;
                this.pushY = 0.5F;
                this.pushZ = 0.0;
            }
            this.setLit(this.fuel > 0);
        }
        if (this.isLit() && this.random.nextInt(4) == 0) {
            this.getWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + 0.9F, this.getZ(),
                    0.0, 0.6F, 0.0);
            return true;
        }
       else if (this.fuel < 0){
            return this.setLit(false);

        }
        return true;
    }

    // Controls the balloon, airballoon control simulation wip
    // need to add fake gravity
    // when the fuel is up
    @Override
    public void travel(Vec3d pos) {
        if (this.isAlive()) {
            if (this.hasPassengers()) {
                if (this.hasFuel()) {

                LivingEntity passenger = getControllingPassenger();
                this.prevYaw = getYaw();
                this.prevPitch = getPitch();

                setYaw(passenger.getYaw());
                setPitch(passenger.getPitch() * 0.1f);
                setRotation(getYaw(), getPitch());

                this.bodyYaw = this.getYaw();
                this.headYaw = this.bodyYaw;
                float x = passenger.sidewaysSpeed * 0.3F;
                float y = passenger.upwardSpeed * 0.3F;
                float z = passenger.forwardSpeed * 0.3F;

                if (y <= 0)
                    y *= 0.25f;
                if (KeyInputHandler.flyUpKey.isPressed()){
                    y = 0.5f;
                    //for testing will be removed
                    spawnParticles(getWorld(),getBlockPos());
                }
                else if ((KeyInputHandler.flyDownkey.isPressed())){
                    y = -0.6f;
                }
                else{
                    y = -0.30f;
                    this.fallDistance = 1.0f;
                    this.onLanding();{
                        this.bounce(this);
                    }
                }
                this.setMovementSpeed(0.3f);
                super.travel(new Vec3d(x, y, z));
                }
            }
        }
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        World world = this.getWorld();
        BlockPos pos = this.getBlockPos();
        return new AirBalloonDescription(syncId, inventory, ScreenHandlerContext.create(world, pos));
    }

    //this is added so the airballoon doesnt break on landing.
    private void bounce(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0) {
            double d = entity instanceof LivingEntity ? 1.0 : 0.8;
            entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
        }
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Air Balloon");
    }
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    // Get the controlling passenger
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return getFirstPassenger() instanceof LivingEntity entity ? entity : null;
    }
    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < this.getMaxPassengers() && !this.isSubmergedIn(FluidTags.WATER);
    }
    protected int getMaxPassengers() {
        return 2;
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

    // Add our idle/moving animation controller
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> {
            if (state.isMoving() && getControllingPassenger() != null) {
                return state.setAndContinue(RawAnimation.begin().then("animation.airballoon.fly", Animation.LoopType.LOOP));
            }
            else {
                return state.setAndContinue(RawAnimation.begin().then("animation.airballoon.idle", Animation.LoopType.LOOP));
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

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.1F;
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(LIT, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putDouble("airballoon.PushX", this.pushX);
        nbt.putDouble("airballoon.PushY", this.pushY);
        nbt.putDouble("airballoon.PushZ", this.pushZ);
        nbt.putShort("airballoon.Fuel", (short)this.fuel);
        nbt.putInt("airballoon.fuelTime", fuelTime);
        nbt.putInt("airballoon.maxFuelTime", maxFuelTime);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        this.pushX = nbt.getDouble("airballoon.PushX");
        this.pushY = nbt.getDouble("airballoon.PushY");
        this.pushZ = nbt.getDouble("airballoon.PushZ");
        this.fuel = nbt.getShort("airballoon.Fuel");
        fuelTime = nbt.getShort("airballoon.fueltime");
        maxFuelTime = nbt.getShort("airballoon.maxfueltime");
    }

    private boolean isLit() {
        return this.dataTracker.get(LIT);
    }

    private boolean setLit(boolean lit) {
        this.dataTracker.set(LIT, lit);
        return lit;
    }
    @Override
    public boolean hasNoGravity() {
        return true;
    }

    //
    private static void spawnParticles(World world, BlockPos pos) {

        // need to enable it when state is LIT only
        // and fix the effect
        double d = 0.5625;
        Random random = world.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            // if airballoon state is LIT will be added later
            double e = (double)pos.getX() + 0.5;
            double f = (double)pos.getY() + 0.7;
            double g = (double)pos.getZ() + 0.5;
            world.addParticle(ParticleTypes.FLAME, d, e, f, 0.0, 0.5, 0.0);
            //world.addParticle(this.particle, d, e, f, 0.0, 0.0, 0.0);
            world.playSound(d, e, f, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }



    }

}