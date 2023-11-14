package za.lana.signum.runinit;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.block.entity.ExampleBlockEntity;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.*;
import za.lana.signum.entity.mob.UnicornEntity;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.entity.transport.SkyCarEntity;
import za.lana.signum.item.ModFuels;
import za.lana.signum.item.ModItems;

public class RunInitMain {


    public static void registerInits(){
        registerAttributes();
        createPortal();
        ModFuels.registerModFuels();
        registerStrippableBlocks();
        registerFlammableBlocks();
        registerBlockEntities();

        Signum.LOGGER.debug("RUNINIT for " + Signum.MOD_ID);
    }
    private static void createPortal(){
        CustomPortalBuilder.beginPortal()
                .frameBlock(ModBlocks.MANGANESE_BLOCK)
                .lightWithItem(ModItems.FIRE_CRYSTAL)
                .destDimID(new Identifier(Signum.MOD_ID, "lost_world"))
                .setPortalSearchYRange(256, 328)
                .tintColor(240, 255, 240)
                .registerPortal();
    }


    private static void registerAttributes() {

        FabricDefaultAttributeRegistry.register(ModEntities.UNICORN, UnicornEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ESPIDER_ENTITY, ESpiderEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.SSKELETON_ENTITY, SumSkeletonEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.TTROOPER_ENTITY, TTrooperEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TCOMMANDER_ENTITY, TCommanderEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.TIBERIUM_WORM, TiberiumWormEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.AIRBALLOON, AirBalloonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TIBERIUM_SKELETON, TiberiumSkeletonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TIBERIUM_FLOATER, TiberiumFloaterEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.ELVE_ENTITY, ElveEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.GHOST, GhostEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.AIRDRONE, AirDroneEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SIGALIEN, SigAlienEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SKYCAR, SkyCarEntity.setAttributes());
    }

    public static void registerStrippableBlocks(){
        StrippableBlockRegistry.register(ModBlocks.SOULWOOD_LOG, ModBlocks.STRIPPED_SOULWOOD_LOG);
        StrippableBlockRegistry.register(ModBlocks.SOULWOOD_WOOD, ModBlocks.STRIPPED_SOULWOOD_WOOD);

    }
    public static void registerFlammableBlocks(){
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.SOULWOOD_LOG, 5 , 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.SOULWOOD_WOOD, 5 , 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_SOULWOOD_WOOD, 5 , 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.SOULWOOD_PLANKS, 5 , 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.SOULWOOD_LEAVES, 30 , 60);

    }
    public static void registerBlockEntities(){

    }






}
