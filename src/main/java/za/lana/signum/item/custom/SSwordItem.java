/**
 * SIGNUM
 * MIT License
 * Lana
 * 2024
 * */
package za.lana.signum.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.TorturedSoulEntity;
import za.lana.signum.item.ModItems;

public class SSwordItem
extends ToolItem
implements Vanishable {
    private final float attackDamage;
    private final static int duration = 60 * 2;
    private final static int amplifier = 2;

    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    // TODO WIP
    public SSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, settings);
        this.attackDamage = (float)attackDamage + toolMaterial.getAttackDamage();
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",
                this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
                        attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)) {
            return 15.0f;
        }
        return state.isIn(BlockTags.SWORD_EFFICIENT) ? 1.5f : 1.0f;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World level = attacker.getWorld();
        Random random = attacker.getRandom();
        if (target instanceof LivingEntity) {

            if (stack.isOf(ModItems.BLACK_DIAMOND_SWORD)){
                TorturedSoulEntity torturedSoul = ModEntities.TORTURED_SOUL.spawn(((ServerWorld) attacker.getWorld()), target.getBlockPos(), SpawnReason.TRIGGERED);
                assert torturedSoul != null;
                if (!(target instanceof TorturedSoulEntity)
                        && !(target instanceof EnderDragonEntity)
                        && !(target instanceof WitherEntity)
                        && !(target instanceof PlayerEntity)){
                    if (random.nextFloat() < 0.25) {
                        level.spawnEntity(torturedSoul);
                        torturedSoul.setOwner((PlayerEntity) attacker);
                        target.discard();
                    }
                }if (target instanceof TorturedSoulEntity || target instanceof PlayerEntity || target instanceof EnderDragonEntity || target instanceof WitherEntity){
                    target.addStatusEffect((new StatusEffectInstance(ModEffects.DEATH_EFFECT, duration, amplifier)));
                }
            }
            if (stack.isOf(ModItems.ELEMENT_ZERO_SWORD)){
                target.addStatusEffect((new StatusEffectInstance(ModEffects.GRAVITY_EFFECT, duration, amplifier)));
            }
            if (stack.isOf(ModItems.EXOTIC_CRYSTAL_SWORD)){
                target.addStatusEffect((new StatusEffectInstance(ModEffects.TRANSMUTE_EFFECT, duration, amplifier)));
            }
            if (stack.isOf(ModItems.FIRE_CRYSTAL_SWORD)){
                target.addStatusEffect((new StatusEffectInstance(ModEffects.BURN_EFFECT, duration, amplifier)));
            }
            if (stack.isOf(ModItems.ICE_CRYSTAL_SWORD)){
                target.addStatusEffect((new StatusEffectInstance(ModEffects.FREEZE_EFFECT, duration, amplifier)));
            }
            if (stack.isOf(ModItems.QUARTZ_CRYSTAL_SWORD)){
                target.addStatusEffect((new StatusEffectInstance(ModEffects.SHOCK_EFFECT, duration, amplifier)));
            }
            if (stack.isOf(ModItems.TIBERIUM_SWORD)){
            target.addStatusEffect((new StatusEffectInstance(ModEffects.TIBERIUM_POISON, duration, amplifier)));
            }
            if (stack.isOf(ModItems.MOISSANITE_SWORD)){
                // ADD STATUS TO ATTACKER
                attacker.addStatusEffect((new StatusEffectInstance(ModEffects.HEALING_EFFECT, duration, amplifier)));
            }
            target.playSound(SoundEvents.ENTITY_ARROW_HIT, 2F, 2F); // plays a sound for the entity hit only
        }
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0f) {
            stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isOf(Blocks.COBWEB);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

}

