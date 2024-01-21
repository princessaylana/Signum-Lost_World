/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.entity.transport;

import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.client.networking.AirballoonVec3SyncPacket;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.event.KeyInputHandler;
import za.lana.signum.item.ModItems;
import za.lana.signum.networking.packet.ABKeyInputSyncS2CPacket;
import za.lana.signum.networking.packet.AirballoonVec3SyncS2CPacket;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.screen.AirBalloonScreenHandler;
import za.lana.signum.sound.ModSounds;
import za.lana.signum.util.ImplementedInventory;

import java.util.Iterator;
import java.util.List;

public class AirBalloonEntity
        extends AnimalEntity
        implements ImplementedInventory, NamedScreenHandlerFactory {
    // floating
    public double T = 0.50f;        // up 0.75f def
    public double S = -0.12f;       // Floating Down
    public double W = 0.36f;        // Floating total -0.48f
    public double U = -0.9f;        // Down   -1.5f * 8
    public double V = 0.25f;        //
    public boolean isFlyUpPressed = false;
    public boolean isFlyDownPressed = false;
    public boolean onGround;
    private int lastAge;
    private int fuel;
    public int fuelTime = 0;
    private int maxFuelTime = 4;
    public int fuelAniTimeout = 0;
    public int flyUpTimeout = 0;
    public int flyDownTimeout = 0;
    public int fuelEmptyAniTimeout = 0;
    private float lastIntensity;
    protected final PropertyDelegate propertyDelegate;
    public AnimationState landAniState = new AnimationState();
    public AnimationState floatingFuelEmptyAniState = new AnimationState();
    public AnimationState floatingFuelAniState = new AnimationState();
    public AnimationState flyUpAniState = new AnimationState();
    public AnimationState flyDownAniState = new AnimationState();
    private final float volume = 0.5f;
    public static int ambientChance = 2;
    private final boolean isInAir;
    public static final String AIRBALLOON_MOUNT = "message.signum.airballoon.mount";
    public static final String AIRBALLOON_WIND = "message.signum.airballoon.wind";
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private static final TrackedData<Boolean> BURNINGFUEL = DataTracker.registerData(AirBalloonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
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

    public boolean isOnGround() {
        return this.onGround;
    }
    //public boolean isInAir() {return !this.isOnGround();}

    // VANILLA ANIMATIONS
    private void setupAnimationStates(){
        this.floatingAnimation();
        this.burnFuelAnimation();
        this.flyUpAnimation();
        this.flyDownAnimation();
    }
    // TODO FLOATING ANIMATION NOT WORKING
    private void floatingAnimation(){
        if (!this.isBurningFuel()) {
            floatingFuelAniState.stop();
            fuelEmptyAniTimeout = 40;
            this.floatingFuelAniState.start(this.age);
        }
        else {
            --this.fuelEmptyAniTimeout;
        }

    }
    // TODO BURNING ANIMATION NOT WORKING
    private void burnFuelAnimation(){
        if (this.isBurningFuel() && fuelAniTimeout <= 0) {
            floatingFuelEmptyAniState.stop();
            fuelAniTimeout = 40;
            this.floatingFuelAniState.start(this.age);
        }
        else {
            --this.fuelAniTimeout;
        }
        if(!this.isBurningFuel()) {
            floatingFuelAniState.stop();
        }
    }
    private void flyUpAnimation(){
        if (this.isFlyUpPressed && isBurningFuel() && flyUpTimeout <= 0) {
            flyUpTimeout = 40;
            this.flyUpAniState.start(this.age);
        }
        else {
            --this.flyUpTimeout;
        }
        if(!this.isFlyUpPressed) {
            flyUpAniState.stop();
        }
    }
    private void flyDownAnimation(){
        floatingFuelAniState.stop();
        if (this.isFlyDownPressed && flyDownTimeout <= 0) {
            flyDownTimeout = 40;
            this.flyDownAniState.start(this.age);
        }
        else {
            --this.flyDownTimeout;
        }
        if(!this.isFlyDownPressed) {
            flyDownAniState.stop();
        }
    }
    // INIT DATA
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BURNINGFUEL, false);
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putDouble("airballoon.V", V);
        nbt.putInt("airballoon.fuel", fuel);
        nbt.putInt("airballoon.fuelTime", fuelTime);
        nbt.putInt("airballoon.maxFuelTime", maxFuelTime);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        this.V = nbt.getDouble("airballoon.V");
        fuel = nbt.getInt("airballoon.fuel");
        fuelTime = nbt.getInt("airballoon.fueltime");
        maxFuelTime = nbt.getInt("airballoon.maxfueltime");
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
        return 2.0F;
    }
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
    // SHAPE
    public static boolean canCollide(Entity entity, Entity other) {
        if (other.isCollidable() || other.isPushable()){
            other.pushAwayFrom(entity);
            other.velocityModified = true;
            return true;
            /**
            if ((other instanceof LivingEntity)) {
                other.damage(entity.getDamageSources().mobAttack((LivingEntity) entity), 6.0F);
                return true;
            }
             **/
        }
        return false;
    }
    public boolean isPushable() {
        return false;
    }
    public boolean collidesWith(Entity other) {
        return canCollide(this, other);
    }
    public EntityGroup getGroup() {
        return ModEntityGroup.TEAM_LIGHT;
    }
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == ModEffects.TRANSMUTE_EFFECT) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }
    // INVENTORY / STORAGE
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    // FUEL
    private static boolean hasFuel(AirBalloonEntity airBalloon) {
        return !airBalloon.getStack(0).isEmpty();
    }
    private void consumeFuel(AirBalloonEntity airBalloon) {
        if(!getStack(0).isEmpty()) {
            this.fuelTime = FuelRegistry.INSTANCE.get(this.removeStack(0, 1).getItem());
            this.maxFuelTime = this.fuelTime;
            this.fuel = this.fuelTime;
        }
    }
    private static boolean burningFuel(AirBalloonEntity airBalloon) {
        return airBalloon.fuelTime > 0;
    }
    public boolean isBurningFuel() {
        return this.dataTracker.get(BURNINGFUEL);
    }
    private void setBurningFuel(){
        if(burningFuel(this)){
            this.dataTracker.set(BURNINGFUEL, true);
        }
    }
    // TRAVEL
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
    @Override
    public void tick() {
        super.tick();
        this.setBurningFuel();
        if (!this.getWorld().isClient()) {
            if (burningFuel(this)){
                --this.fuelTime;
            }
            else if (hasFuel(this) && !burningFuel(this)){
                consumeFuel(this);
            }
        }
        if (getWorld().isClient() && getControllingPassenger() == MinecraftClient.getInstance().player) {
            isFlyUpPressed = KeyInputHandler.flyUpKey.isPressed();
            isFlyDownPressed = KeyInputHandler.flyDownkey.isPressed();
            if (getWorld() instanceof ServerWorld world && hasPassengers())
                for (ServerPlayerEntity player : PlayerLookup.tracking(world, getBlockPos())) {
                    ABKeyInputSyncS2CPacket.send(player, this);
                    AirballoonVec3SyncS2CPacket.send(player, this);
                }
        }
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }
    @Override
    public void travel(Vec3d pos) {
        if (isAlive()) {
            if (hasPassengers()) {
                Vec3d vec3d2 = this.getVelocity();
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
                float x = (float) (passenger.sidewaysSpeed * V / 2.0f); // 0.5f
                float y = (float) (passenger.upwardSpeed * V / 2.5f); // 0.5
                float z = (float) (passenger.forwardSpeed * V / 0.25f);
                //
                // y is less than or equal to
                if (y <= 0)
                    // y * float (0.25f)
                    y *= (float) V;
                //
                boolean isFlyDownPressed = this.isFlyDownPressed;
                if (isFlyDownPressed) {
                    // U = -0.9f
                    y = (float) U;
                    this.setVelocity(vec3d2.x, U * 0.12, vec3d2.z);
                    airDeflate(getEntityWorld(), getBlockPos());
                }
                else if (fuelTime > 0) {
                    boolean isFlyUpPressed = this.isFlyUpPressed;
                    if (isFlyUpPressed) {
                        // T = 0.50f
                        y = (float) T;
                        increaseHeat(getEntityWorld(), getBlockPos());
                        //
                    } if (isFlyUpPressed && this.isOnGround()){
                        this.jump();
                        return;
                    }
                }
                // Float slowly down in Air
                else {
                    // s = -0.12f
                    y = (float) S;
                    //this.setVelocity(vec3d2.x, S * 0.12, vec3d2.z);
                    this.onLanding();
                    this.bounce(this);
                }
                super.travel(new Vec3d(x, y, z));
            }
            // Float down when empty
            else if (!hasPassengers()) {
                Vec3d vec3d2 = this.getVelocity();
                // W = 0.36f
                // 0.9f
                super.travel(new Vec3d(vec3d2.x, vec3d2.y - (0.60 + W), vec3d2.z));
                //super.travel(new Vec3d(vec3d2.x, vec3d2.y + U , vec3d2.z));
            }
        }
        this.onLanding();
        this.bounce(this);
    }
    @Override
    protected float getVelocityMultiplier() {
        return this.isInAir && this.hasPassengers() || this.isFallFlying() ? 1.0f : super.getVelocityMultiplier();
    }
    // LANDING
    @Override
    public boolean hasNoGravity() {
        return true;
    }
    private void bounce(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0) {
            double d = entity instanceof AirBalloonEntity ? 1.0 : 0.8;
            entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
        }
    }
    // PASSENGERS
    // TODO CHECK IF THE BALLLOON CAN TAKE TWO PASSENGERS
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return getFirstPassenger() instanceof LivingEntity entity ? entity : null;
    }
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < this.getMaxPassengers() && !this.isSubmergedIn(FluidTags.WATER);
    }
    protected int getMaxPassengers() {
        return 2;
    }
    protected void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater) {
        super.updatePassengerPosition(passenger, positionUpdater);
        passenger.setYaw(passenger.getYaw());
        passenger.setHeadYaw(passenger.getHeadYaw());
        //
        this.prevPitch = passenger.prevPitch;
        positionUpdater.accept(passenger, getX(), getY() - 0.1f, getZ());
        this.clampPassengerYaw(passenger);

        if (passenger instanceof AnimalEntity && this.getPassengerList().size() == this.getMaxPassengers()) {
            int i = passenger.getId() % 2 == 0 ? 90 : 270;
            passenger.setBodyYaw(((AnimalEntity)passenger).bodyYaw + (float)i);
            passenger.setHeadYaw(passenger.getHeadYaw() + (float)i);
            positionUpdater.accept(passenger, getX(), getY() - 0.1f, getZ() - 1.0f);
        }
    }
    protected Vec3d positionInPortal(Direction.Axis portalAxis, BlockLocating.Rectangle portalRect) {
        return LivingEntity.positionInPortal(super.positionInPortal(portalAxis, portalRect));
    }
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = getPassengerDismountOffset((double)(this.getWidth() * MathHelper.SQUARE_ROOT_OF_TWO), (double)passenger.getWidth(), passenger.getYaw());
        double d = this.getX() + vec3d.x;
        double e = this.getZ() + vec3d.z;
        BlockPos blockPos = BlockPos.ofFloored(d, this.getBoundingBox().maxY, e);
        BlockPos blockPos2 = blockPos.down();
        if (!this.getWorld().isWater(blockPos2)) {
            List<Vec3d> list = Lists.newArrayList();
            double f = this.getWorld().getDismountHeight(blockPos);
            if (Dismounting.canDismountInBlock(f)) {
                list.add(new Vec3d(d, (double)blockPos.getY() + f, e));
            }

            double g = this.getWorld().getDismountHeight(blockPos2);
            if (Dismounting.canDismountInBlock(g)) {
                list.add(new Vec3d(d, (double)blockPos2.getY() + g, e));
            }

            UnmodifiableIterator var14 = passenger.getPoses().iterator();

            while(var14.hasNext()) {
                EntityPose entityPose = (EntityPose)var14.next();
                Iterator var16 = list.iterator();

                while(var16.hasNext()) {
                    Vec3d vec3d2 = (Vec3d)var16.next();
                    if (Dismounting.canPlaceEntityAt(this.getWorld(), vec3d2, passenger, entityPose)) {
                        passenger.setPose(entityPose);
                        return vec3d2;
                    }
                }
            }
        }

        return super.updatePassengerForDismount(passenger);
    }
    protected void clampPassengerYaw(Entity passenger) {
        passenger.setBodyYaw(this.getYaw());
        float f = MathHelper.wrapDegrees(passenger.getYaw() - this.getYaw());
        float g = MathHelper.clamp(f, -105.0F, 105.0F);
        passenger.prevYaw += g - f;
        passenger.setYaw(passenger.getYaw() + g - f);
        passenger.setHeadYaw(passenger.getYaw());
    }
    public void onPassengerLookAround(Entity passenger) {
        this.clampPassengerYaw(passenger);
    }

    /**
     *     @Override
     *     public void updatePassengerPosition(Entity entity, PositionUpdater moveFunction) {
     *         super.updatePassengerPosition(entity, moveFunction);
     *         if (entity instanceof LivingEntity passenger) {
     *             int i = this.getPassengerList().indexOf(passenger);
     *             moveFunction.accept(entity, getX(), getY() - 0.1f, getZ());
     *             this.prevPitch = passenger.prevPitch;
     *         }
     *     }
     */

    // DROPS
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
    // VANILLA GUI
    @Override
    public Text getDisplayName() {
        return Text.translatable("entity.signum.airballoon");
    }
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AirBalloonScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
    //SOUND
    public void randomSoundTick(){
        if (this.random.nextInt(ambientChance) != 0) {
            return;
        }
        World level = this.getWorld();;
        Random random = this.random;
        double d = this.getX();
        double e = this.getY();
        double f = this.getZ();
        if (random.nextDouble() < 0.1) {
            level.playSound(null, d, e, f, ModSounds.SNOWY_WIND,
                    SoundCategory.NEUTRAL,1.0f, 0.5f + this.random.nextFloat() * 1.2f);
        }
    }
    @Override
    protected SoundEvent getAmbientSound() {
        this.randomSoundTick();
        return SoundEvents.BLOCK_FIRE_AMBIENT;
    }
    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        //this.playSound(SigSounds.BALLOON_FLY, 0.15f, 1.0f);
    }
    private void playFireSound() {
        this.lastIntensity *= (float)Math.pow(0.997, this.age - this.lastAge);
        this.lastIntensity = Math.min(1.0f, this.lastIntensity + 0.07f);
        float f = 0.5f + this.lastIntensity * this.random.nextFloat() * 1.2f; // pitch
        float g = 0.1f + this.lastIntensity * 1.2f; // volume
        //
        this.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, g, f);
        this.lastAge = this.age;
    }
    private void playDeflateSound() {
        this.lastIntensity *= (float)Math.pow(0.997, this.age - this.lastAge);
        this.lastIntensity = Math.min(1.0f, this.lastIntensity + 0.07f);
        float f = 0.5f + this.lastIntensity * this.random.nextFloat() * 1.2f; // pitch
        float g = 0.1f + this.lastIntensity * volume; // volume
        //
        this.playSound(ModSounds.AIRBALLOON_DOWN, g, f);

        this.lastAge = this.age;
    }
    private void increaseHeat(World world, BlockPos pos) {
        playFireSound();
        if (world.isClient){
            ParticleUtil.spawnParticle(getWorld(), BlockPos.ofFloored(getPos()), random, ModParticles.BLACK_SHROOM_PARTICLE);
        }
    }
    private void airDeflate(World world, BlockPos pos) {
        playDeflateSound();
    }
}
