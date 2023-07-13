/**
 * SIGNUM
 * this is the first animated gun weapon renderer
 * build from Geckolib 4 Pistol Example
 * MIT License
 * Lana
 * */
package za.lana.signum.client;

import net.minecraft.util.Identifier;
import software.bernie.example.item.PistolItem;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import za.lana.signum.Signum;
import za.lana.signum.item.custom.ToxicGunItem;


public class ToxicGunRenderer extends GeoItemRenderer<ToxicGunItem> {
	public ToxicGunRenderer() {
		super(new DefaultedItemGeoModel<>(new Identifier(Signum.MOD_ID, "toxicgun")));
	}
}