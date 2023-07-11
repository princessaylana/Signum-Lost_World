/**
 * SIGNUM
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 * adapted from SnowballEntity, using
 * the Fabric projectile tutorial
 * MIT License
 * Lana
 *
 * problems with the constructor
 **/
package za.lana.signum.entity.projectile;

import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.item.ModItems;

public class ToxicBallEntity extends ThrownItemEntity {

    public ToxicBallEntity(World world, LivingEntity owner) {
        super((EntityType<? extends ThrownItemEntity>)ModEntities.TOXICBALL, owner, world);
    }
    public ToxicBallEntity(World world, double x, double y, double z) {
        super((EntityType<? extends ThrownItemEntity>)ModEntities.TOXICBALL, x, y, z, world);
    }
    public ToxicBallEntity(EntityType<?extends ToxicBallEntity> entityEntityType, World world) {
        super((EntityType<? extends ThrownItemEntity>)entityEntityType, world);

    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.TOXICBALL_ITEM;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
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
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        int i = entity instanceof BlazeEntity ? 3 : 0;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), i);
        //adds damage
        if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 3, 0))); // applies a status effect
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 2))); // applies a status effect
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 20 * 3, 1))); // applies a status effect
            livingEntity.playSound(SoundEvents.BLOCK_GLASS_HIT, 2F, 1F); // plays a sound for the entity hit only
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
}




