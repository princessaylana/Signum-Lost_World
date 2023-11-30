/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.SumSkeletonEntity;
import za.lana.signum.sound.ModSounds;

import java.util.List;

public class DeathStaff
        extends Item {

    private final int durability;
    private final int coolDown;
    private static final int STAFFCOOLDOWN = 1200;
    private final EntityType<?> type = ModEntities.SSKELETON_ENTITY;

    private final ToolMaterial material;
    private float attackDamage = 2.0f;

    public DeathStaff(ToolMaterial material, Settings settings) {
        super(settings.maxDamageIfAbsent(material.getDurability()));
        this.material = material;
        this.attackDamage = attackDamage + material.getAttackDamage();
        this.durability = this.material.getDurability()/10;
        this.coolDown = STAFFCOOLDOWN /20;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemstack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.GHOST_AMBIENT, SoundCategory.NEUTRAL,1.5F, 1F);
        user.getItemCooldownManager().set(this, STAFFCOOLDOWN);

        if (!world.isClient()) {
            BlockPos BlockPos = user.getBlockPos();
            spawnMonster(world, BlockPos, user);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemstack.damage(10, user, p -> p.sendToolBreakStatus(hand));
        }
        return TypedActionResult.success(itemstack, world.isClient());
    }
    // Spawns and tames the entity
    private void spawnMonster(World world, BlockPos pos, PlayerEntity user) {
        BlockHitResult blockPos = SpawnEggItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        SumSkeletonEntity sskeleton = (SumSkeletonEntity) type.spawnFromItemStack((ServerWorld)world, this.getDefaultStack(), user, blockPos.getBlockPos(), SpawnReason.SPAWN_EGG, false, false);
        if (sskeleton != null) {
            sskeleton.refreshPositionAndAngles((double)pos.getX() + 0.05, pos.getY(), (double)pos.getZ() + 0.05, 0.0f, 0.0f);
            world.spawnEntity(sskeleton);
            //getRequiredFeatures();
            sskeleton.setOwner(user);
            sskeleton.setTarget(null);
            sskeleton.playSpawnEffects();
        }
    }
    @Override
    public FeatureSet getRequiredFeatures() {
        return this.type.getRequiredFeatures();
    }
    public ToolMaterial getMaterial() {
        return this.material;
    }
    @Override
    public int getEnchantability() {
        return this.material.getEnchantability();
    }
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.signum.death_staff.info")
                    .fillStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY).withBold(true)));
            tooltip.add(Text.literal("Repairable"));
            tooltip.add(Text.literal(this.coolDown+" sec Recharge Time"));
            tooltip.add(Text.literal(this.durability+" Total Uses"));
        }else {
            tooltip.add(Text.translatable("key.signum.shift")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}

