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
    public static BlockEntityType<VaultBlockEntity> VAULT_BLOCK;
    public static BlockEntityType<DroneBoxBlockEntity> DRONEBOX_BLOCK_ENTITY;
    public static BlockEntityType<ExampleBlockEntity> EXAMPLE_BLOCK_ENTITY;
   // public static BlockEntityType<ExampleBlockEntity> EXAMPLE_BLOCK_ENTITY;

    public static void registerBlockEntities(){
        SKYFORGE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Signum.MOD_ID, "skyforge"),
                FabricBlockEntityTypeBuilder.create(SkyForgeBlockEntity::new,
                        ModBlocks.SKYFORGE).build(null));

        VAULT_BLOCK = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Signum.MOD_ID, "vault_block"),
                FabricBlockEntityTypeBuilder.create(VaultBlockEntity::new,
                        ModBlocks.VAULT_BLOCK).build(null));

        /**
        DRONEBOX_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Signum.MOD_ID, "dronebox_block_entity"),
                FabricBlockEntityTypeBuilder.create(DroneBoxBlockEntity::new,
                        ModBlocks.DRONEBOX_BLOCK).build(null));
         **/


    }
    public static void registerLibGuiBlockEntities(){
        EXAMPLE_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(ExampleBlockEntity::new, ModBlocks.EXAMPLE_BLOCK).build(null);
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Signum.MOD_ID, "example_block_entity"), EXAMPLE_BLOCK_ENTITY);

        DRONEBOX_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(DroneBoxBlockEntity::new, ModBlocks.DRONEBOX_BLOCK).build(null);
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Signum.MOD_ID, "dronebox_block_entity"), DRONEBOX_BLOCK_ENTITY);

    }
}
