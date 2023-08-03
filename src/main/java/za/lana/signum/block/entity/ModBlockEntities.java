/**
 * Registers the mod blocks
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;

public class ModBlockEntities {
    public static BlockEntityType<SkyForgeBlockEntity> SKYFORGE;

    public static void registerBlockEntities(){
        SKYFORGE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Signum.MOD_ID, "skyforge"),
                FabricBlockEntityTypeBuilder.create(SkyForgeBlockEntity::new,
                        ModBlocks.SKYFORGE).build(null));



    }

}
