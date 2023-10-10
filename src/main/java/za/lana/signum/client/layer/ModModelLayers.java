/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.layer;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public class ModModelLayers {

    public static final EntityModelLayer UNICORN =
            new EntityModelLayer(new Identifier(Signum.MOD_ID, "unicorn"), "mainbody");
    public static final EntityModelLayer TIBERIUM_BOLT =
            new EntityModelLayer(new Identifier(Signum.MOD_ID, "tiberium_bolt"), "main");
    public static final EntityModelLayer ICE_BOLT =
            new EntityModelLayer(new Identifier(Signum.MOD_ID, "ice_bolt"), "main");
    public static final EntityModelLayer TRANSMUTE_BOLT =
            new EntityModelLayer(new Identifier(Signum.MOD_ID, "transmute_bolt"), "main");
    public static final EntityModelLayer FIRE_BOLT =
            new EntityModelLayer(new Identifier(Signum.MOD_ID, "fire_bolt"), "main");
    public static final EntityModelLayer SHOCK_BOLT =
            new EntityModelLayer(new Identifier(Signum.MOD_ID, "shock_bolt"), "main");
}
