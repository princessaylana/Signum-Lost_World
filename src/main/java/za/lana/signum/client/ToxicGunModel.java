// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLibMod
// Paste this class into your mod and follow the documentation for GeckoLibMod to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package za.lana.signum.client;
/**
 * built from geckolib example
 * */

import net.minecraft.util.Identifier;
import software.bernie.example.item.PistolItem;
import software.bernie.geckolib.model.GeoModel;
import za.lana.signum.Signum;


public class ToxicGunModel extends GeoModel<PistolItem> {


	@Override
	public Identifier getModelResource(PistolItem animatable) {
		return new Identifier(Signum.MOD_ID, "geo/weapon/toxicgun.geo.json");
	}

	@Override
	public Identifier getTextureResource(PistolItem animatable) {
		return new Identifier(Signum.MOD_ID, "textures/item/weapon/toxicgun_texture.png");
	}

	@Override
	public Identifier getAnimationResource(PistolItem animatable) {
		return new Identifier(Signum.MOD_ID, "animations/weapon/toxicgun.animation.json");
	}
}
