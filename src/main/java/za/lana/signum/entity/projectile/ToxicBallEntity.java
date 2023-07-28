/**
 * SIGNUM
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 * adapted from SnowballEntity, using
 * the Fabric projectile tutorial
 * MIT License
 * Lana
 **/
package za.lana.signum.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import za.lana.signum.item.ModItems;

import java.util.List;

import static za.lana.signum.entity.ModEntities.TOXICBALL;

public class ToxicBallEntity
        extends ThrownItemEntity {
    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(ToxicBallEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private int life;

    public ToxicBallEntity(EntityType<ToxicBallEntity> type, World world) {
        super(type, world);
    }
    public ToxicBallEntity(World world, LivingEntity owner) {

        super(TOXICBALL, owner, world);
    }
    @Override
    protected Item getDefaultItem() {

        return ModItems.TOXICBALL_ITEM;
    }
    public void initFromStack(ItemStack stack){

    }


    protected void age() {
        ++this.life;
        if (this.life >= 2400) {
            this.discard();
        }
    }
    //Customization below
    @Override
    public boolean hasNoGravity() {
        return true;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.FIREWORK: new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }
    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.5, 0.0);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult){

        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        int i = entity instanceof EndermanEntity ? 6 : 0;
        List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(4.0, 2.0, 4.0));
        // AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());

        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), i);
        if (entity instanceof LivingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)

            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 10, 0)));
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 3, 0))); // applies a status effect
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 20 * 6, 6))); // applies a status effect
            entity.playSound(SoundEvents.BLOCK_GLASS_HIT, 2F, 2F); // plays a sound for the entity hit only
            entity.damage(getWorld().getDamageSources().cactus(), 6.0f * 2);
           /**
            * //need to adjust this for green toxic clouds
            areaEffectCloudEntity.setParticleType(ParticleTypes.DRAGON_BREATH);
            areaEffectCloudEntity.setRadius(5.0f);
            areaEffectCloudEntity.setDuration(20);
            areaEffectCloudEntity.setRadiusGrowth((3.0f - areaEffectCloudEntity.getRadius()) / (float)areaEffectCloudEntity.getDuration());
            areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 2, 5));

            if (!list.isEmpty()) {
                for (LivingEntity livingEntity : list) {
                    double d = this.squaredDistanceTo(livingEntity);
                    if (!(d < 16.0)) continue;
                    areaEffectCloudEntity.setPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    break;
                }
            }
            this.getWorld().syncWorldEvent(WorldEvents.DRAGON_BREATH_CLOUD_SPAWNS, this.getBlockPos(), this.isSilent() ? -1 : 1);
            this.getWorld().spawnEntity(areaEffectCloudEntity);
            this.discard();
            **/
            this.discard();
            }

        }


    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.bounce();
            this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.5, 0.5, 0.5);
        }
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
            this.age = 120;
            this.discard();

        }
    }

    //I am not sure how this is used below
    public void setDamage() {


    }

}
