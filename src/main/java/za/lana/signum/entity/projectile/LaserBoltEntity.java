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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.item.ModItems;

public class LaserBoltEntity
        extends ThrownItemEntity {
    private int life;
    private static final TrackedData<ItemStack> ITEM =
            DataTracker.registerData(LaserBoltEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    public LaserBoltEntity(EntityType<LaserBoltEntity> type, World world) {
        super(type, world);
    }
    public LaserBoltEntity(World world, LivingEntity owner) {
        super(ModEntities.LASERBOLT, owner, world);
    }
    protected void age() {
        ++this.life;
        if (this.life >= 1200) {
            this.discard();
        }
    }
    //Customization below
    @Override
    public boolean hasNoGravity() {
        return true;
    }
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult){
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        int i = entity instanceof BlazeEntity ? 3 : 0;
        entity.damage(this.getDamageSources().explosion(this, this.getOwner()), i);

        if (entity instanceof LivingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)

            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 10, 0)));
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 3, 2)));
            
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 3, 0))); // applies a status effect
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 20 * 3, 1))); // applies a status effect
            entity.playSound(SoundEvents.BLOCK_GLASS_HIT, 2F, 2F); // plays a sound for the entity hit only
            entity.damage(getWorld().getDamageSources().magic(), 6.0f * 2); // Applies Damage

        }
    }

    protected ItemStack asItemStack() {
        ItemStack itemStack = new ItemStack(ModItems.LASERBOLT_ITEM);
        return itemStack;
    }

    public void setItem(ItemStack item) {
        if (!item.isOf(this.getDefaultItem()) || item.hasNbt()) {
            this.getDataTracker().set(ITEM, item.copyWithCount(1));
        }
    }
    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            //this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            // world.getEntitiesByClass(LivingEntity.class, entity.getBoundingBox().expand(8.0), e->true).forEach(e->e.setOnFireFor(5));

            this.discard();
        }
    }
    private void transmute(){
        // kill target entity
        // respawn something else
    }

}
