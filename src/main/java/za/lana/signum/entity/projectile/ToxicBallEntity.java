/**
 * SIGNUM
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 * adapted from SnowballEntity, using
 * the Fabric projectile tutorial
 * MIT License
 * Lana
 **/
package za.lana.signum.entity.projectile;

import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.item.ModItems;

import java.util.List;

import static za.lana.signum.entity.ModEntities.TOXICBALL;

public class ToxicBallEntity
        extends ThrownItemEntity {
    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(ToxicBallEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private int life;
    protected final int age1 = 200;

    public ToxicBallEntity(EntityType<ToxicBallEntity> type, World world) {
        super(type, world);
        if (this.age >= age1) {
            this.discard();
        }
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
        World world = getWorld();
        BlockPos pos = getBlockPos();

        int i = entity instanceof EndermanEntity ? 6 : 0;
        //List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(4.0, 2.0, 4.0));
       // entity.damage(this.getDamageSources().thrown(this, this.getOwner()), i);
        if (entity instanceof LivingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 10, 0)));
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(ModEffects.TIBERIUM_POISON, 60 * 2 , 1 / 4)));
            entity.playSound(SoundEvents.BLOCK_GLASS_HIT, 2F, 2F); // plays a sound for the entity hit only
            entity.damage(getWorld().getDamageSources().magic(), 1.5f * 2);
            this.discard();
            }
        }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.bounce();
            this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.5, 0.5, 0.5);
            // world.getEntitiesByClass(LivingEntity.class, entity.getBoundingBox().expand(8.0), e->true).forEach(e->e.setOnFireFor(5));
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
    // this seems to be needed?
    public void setDamage() {
    }
    // will add a custom particle here in future
    private void spawnSparkParticle(ItemUsageContext pContext, BlockPos positionClicked) {
        for(int i = 0; i < 360; i++) {
            if(i % 20 == 0) {
                pContext.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK,
                        positionClicked.getX() + 0.5d, positionClicked.getY() + 1, positionClicked.getZ() + 0.5d,
                        Math.cos(i) * 0.25d, 0.15d, Math.sin(i) * 0.25d);
            }
        }
    }


}
