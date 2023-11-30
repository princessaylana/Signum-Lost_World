/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.entity.projectile.IceBoltEntity;
import za.lana.signum.sound.ModSounds;

import java.util.List;

public class IceStaff
        extends Item {
    private final ToolMaterial material;
    private final int durability;
    private final int coolDown;
    private float attackDamage = 2.0f;
    // default is 40 = 2 seconds
    private static final int STAFFCOOLDOWN = 40;
    public IceStaff(ToolMaterial material, Settings settings) {
        super(settings.maxDamageIfAbsent(material.getDurability()));
        this.material = material;
        this.attackDamage = attackDamage + material.getAttackDamage();
        this.durability = this.material.getDurability()/10;
        this.coolDown = STAFFCOOLDOWN /20;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemstack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.TIBERIUM_HIT, SoundCategory.NEUTRAL,1.5F, 1F);
        user.getItemCooldownManager().set(this, STAFFCOOLDOWN);
        if (!world.isClient()) {
            IceBoltEntity projectile = new IceBoltEntity(world, user);
            projectile.setVelocity(user, user.getPitch(), user.getYaw(), 0, 3, 1);
            world.spawnEntity(projectile);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        // BREAK TOOL
        if (!user.getAbilities().creativeMode) {
            itemstack.damage(1, user, p -> p.sendToolBreakStatus(hand));
        }
        return TypedActionResult.success(itemstack, world.isClient());
    }
    // REPAIR ITEM WITH MOD MATERIAL
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
            tooltip.add(Text.translatable("item.signum.freeze_staff.info")
                    .fillStyle(Style.EMPTY.withColor(Formatting.DARK_BLUE).withBold(true)));
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

