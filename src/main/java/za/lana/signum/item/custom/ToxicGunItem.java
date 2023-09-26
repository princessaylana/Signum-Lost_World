/**
 * SIGNUM
 * this is the first animated gun weapon
 * build from Geckolib 4 Pistol Example
 * it shoots toxicballs
 * MIT License
 * Lana
 * */

package za.lana.signum.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import za.lana.signum.Signum;
import za.lana.signum.client.renderer.item.ToxicGunRenderer;
import za.lana.signum.constant.SignumAnimations;
import za.lana.signum.entity.projectile.ToxicBallEntity;
import za.lana.signum.event.KeyInputHandler;
import za.lana.signum.item.ModItems;
import za.lana.signum.sound.ModSounds;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ToxicGunItem extends Item implements GeoItem {
	private final ToolMaterial material;
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public ToxicGunItem(ToolMaterial material, Item.Settings settings) {
		super(settings);
		//super(settings.maxDamageIfAbsent(material.getDurability()).maxCount(1).maxDamage(101));
		this.material = material;
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}
	// Utilise our own render hook to define our custom renderer
	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private ToxicGunRenderer renderer = null;
			@Override
			public BuiltinModelItemRenderer getCustomRenderer() {
				if (this.renderer == null)
					this.renderer = new ToxicGunRenderer();
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

	// Register our animation controllers
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", state -> PlayState.CONTINUE)
				.triggerableAnim("shoot", SignumAnimations.TOXICGUN_SHOOT));
		// We've marked the "shoot" animation as being triggerable from the server
	}

	// Start "using" the item once clicked
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		player.setCurrentHand(hand);
		return TypedActionResult.consume(player.getStackInHand(hand));
	}

	// Fire an arrow and play the animation when releasing the mouse button
	@Override
	public void onStoppedUsing(ItemStack stack, World level, LivingEntity shooter, int ticksRemaining) {

		if (shooter instanceof PlayerEntity player) {
			if (stack.getDamage() >= stack.getMaxDamage() - 1)
				return;
			player.getItemCooldownManager().set(this, 60);
			shooter.playSound(ModSounds.TIBERIUM_BREAK, 2F, 2F);

			if (!level.isClient) {
				ToxicBallEntity arrow = new ToxicBallEntity(level, player);
				//ItemStack istack = (stack.getItem() instanceof ArrowItem ? stack.getItem() : ModItems.TOXICBALL_ITEM).getDefaultStack();

				arrow.setVelocity(player, player.getPitch(), player.getYaw(), 0, 3, 1);
				arrow.setDamage();
				arrow.hasNoGravity();

				stack.damage(1, shooter, p -> p.sendToolBreakStatus(shooter.getActiveHand()));
				level.spawnEntity(arrow);
				arrow.age = 240;

				triggerAnim(player, GeoItem.getOrAssignId(stack, (ServerWorld)level), "shoot_controller", "shoot");
				// will assign a proper reload keybind, this is just for testing
				if (KeyInputHandler.inventKey.isPressed()){
					//need to reload/repair the item
					player.sendMessage(Text.literal("Reloading Key"));
				}

				/***
				istack.decrement(1);
				if (stack.isEmpty()) {
					((PlayerEntity) shooter).getInventory().removeOne(istack);
				}
				 **/
			}
			}
		}
		// need to add the reload
	// max damage - (count the amount of projectiles shot) = remove from stack in inventory and repair gun

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	// Let's add some ammo text to the tooltip
	@Override
	public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
		tooltip.add(Text.translatable("item." + Signum.MOD_ID+ ".toxicgun.ammo",
				stack.getMaxDamage() - stack.getDamage() - 1,
				stack.getMaxDamage() - 1)
				.formatted(Formatting.ITALIC));
	}
	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}