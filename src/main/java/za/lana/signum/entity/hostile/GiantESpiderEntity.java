/**
 * SIGNUM
 * MIT License
 * Lana
 * 2024
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class GiantESpiderEntity extends ESpiderEntity{
    // CHANGE THIS TO CHANGE THE SCALE
    public static final float SCALED = 3.0f;
    private final ServerBossBar bossBar = (ServerBossBar)new ServerBossBar(this.getDisplayName(), BossBar.Color.RED, BossBar.Style.PROGRESS).setDarkenSky(true);
    public GiantESpiderEntity(EntityType<? extends ESpiderEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 25;
        this.bossBar.setPercent(100.0f);
        this.setHealth(this.getMaxHealth());
        this.setCanPickUpLoot(true);
    }
    public static DefaultAttributeContainer.Builder setAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 50.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 150.0);
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        //1.2f - original.
        return 1.2f * SCALED;
    }

    protected float getUnscaledRidingOffset(Entity vehicle) {
        return -3.75F;
        //return 3.75F;
    }

    @Override
    public Vec3d getPassengerRidingPos(Entity passenger) {
        return new Vec3d(this.getPassengerAttachmentPos(passenger, this.getDimensions(EntityPose.STANDING),
                SCALED).rotateY(-this.getBodyYaw() * ((float)Math.PI / 180))).add(this.getPos());
    }

    /**
    @Override
    public void updatePassengerPosition(Entity entity, PositionUpdater moveFunction) {
        super.updatePassengerPosition(entity, moveFunction);
        // def : "position": [0, 1.2, -0.4]
        if (entity instanceof LivingEntity passenger) {
            moveFunction.accept(entity, getX(), getY() + SCALED, getZ());
            //this.prevPitch = passenger.prevPitch;
        }
    }
    **/

    // BOSSBAR
    @Override
    protected void mobTick(){
        World level = this.getWorld();
        if (level.isNight()){
            if (this.age % 60 == 0) {
                this.heal(1.0f);
            }
         }
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        this.shouldRenderOverlay();
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }
    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    public void shouldRenderOverlay() {
        this.getHealth();
        this.getMaxHealth();
    }
    // IF BELOW IS ADDED THEN GIANT DOES NOTHING
    /**
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getPhototaxisFavor(pos);
    }
     **/

}
