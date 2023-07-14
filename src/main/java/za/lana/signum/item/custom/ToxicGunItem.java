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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import za.lana.signum.Signum;
import za.lana.signum.client.ToxicGunRenderer;
import za.lana.signum.entity.projectile.ToxicBallEntity;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ToxicGunItem extends Item implements GeoItem {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public ToxicGunItem() {
		super(new Settings().maxCount(1).maxDamage(201));

		// Register our item as server-side handled.
		// This enables both animation data syncing and server-side animation triggering
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
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", state -> PlayState.CONTINUE)
				.triggerableAnim("shoot", RawAnimation.begin().thenPlay("toxicgun.shoot")));
		controllers.add(new AnimationController<>(this, "shoot_controller", state -> PlayState.CONTINUE)
				.triggerableAnim("idle", RawAnimation.begin().thenPlay("toxicgun.idle")));
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

			// Add a cooldown so you can't fire rapidly
			player.getItemCooldownManager().set(this, 5);

			if (!level.isClient) {
				ToxicBallEntity arrow = new ToxicBallEntity(level, player);
				arrow.age = 100;

				arrow.setVelocity(player, player.getPitch(), player.getYaw(), 0, 1, 1);
				arrow.setDamage();
				arrow.hasNoGravity();

				stack.damage(1, shooter, p -> p.sendToolBreakStatus(shooter.getActiveHand()));
				level.spawnEntity(arrow);

				// Trigger our animation
				// We could trigger this outside of the client-side check if only wanted the animation to play for the shooter
				// But we'll fire it on the server so all nearby players can see it
				triggerAnim(player, GeoItem.getOrAssignId(stack, (ServerWorld)level), "shoot_controller", "toxicgun.shoot");
			}
		}
	}

	// Use vanilla animation to 'pull back' the pistol while charging it
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

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
		tooltip.add(Text.translatable("item." + Signum.MOD_ID+ ".ammo",
				stack.getMaxDamage() - stack.getDamage() - 1,
				stack.getMaxDamage() - 1)
				.formatted(Formatting.ITALIC));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}