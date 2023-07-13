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
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import za.lana.signum.item.ModItems;


import static za.lana.signum.entity.ModEntities.TOXICBALL;

public class ToxicBallEntity
        extends ThrownItemEntity {
    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(ToxicBallEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public ToxicBallEntity(EntityType<ToxicBallEntity> type, World world) {
        super(type, world);
    }
    public ToxicBallEntity(World world, LivingEntity owner) {
        super((EntityType<? extends ThrownItemEntity>) TOXICBALL, owner, world);
    }



    @Override
    protected Item getDefaultItem() {
        return ModItems.TOXICBALL_ITEM;
    }
    //Customization below
    @Override
    public boolean hasNoGravity() {
        return true;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.SMOKE: new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }
    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult){

        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        int i = entity instanceof EndermanEntity ? 6 : 0;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), i);

        if (entity instanceof LivingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)

            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 10, 0)));
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.WEAKNESS, 20 *3, 3)));
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 3, 0))); // applies a status effect
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 2))); // applies a status effect
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 20 * 6, 3))); // applies a status effect
            entity.playSound(SoundEvents.BLOCK_GLASS_HIT, 2F, 2F); // plays a sound for the entity hit only
            //this.explode();
            this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            this.remove(Entity.RemovalReason.DISCARDED);
            }
        }



    //when it hits another entity
    //@Override
    //protected void onCollision(HitResult hitResult) {
    //    super.onCollision(hitResult);
    //    if (!this.getWorld().isClient) {
    //        this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
     //       this.discard();
     //   }
    //}

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.setDamage();
            this.bounce();
            //this.getWorld().createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 0.0F, Explosion.DestructionType.KEEP);
            //this.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    private void bounce() {
        Vec3d vec3d = this.getVelocity();
        if (vec3d.y < 0.0) {
            double d = 1.0;
           this.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
        }
    }

    //I am not sure how this is used below
    public void setDamage() {

    }
    public void setItem(ItemStack item) {
        if (!item.isOf(this.getDefaultItem()) || item.hasNbt()) {
            this.getDataTracker().set(ITEM, item.copyWithCount(1));
        }
    }
}
