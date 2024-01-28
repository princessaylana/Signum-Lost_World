
// MAIN ENTRYPOINT lOADER
package za.lana.signum.runinit;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.block.entity.ModBlockEntities;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.*;
import za.lana.signum.entity.mob.*;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.entity.transport.AirShipEntity;
import za.lana.signum.entity.transport.CargoDroneEntity;
import za.lana.signum.item.ModFuels;
import za.lana.signum.item.ModItems;
import za.lana.signum.networking.ModMessages;
import za.lana.signum.networking.packet.ABKeyInputC2SPacket;
import za.lana.signum.tag.ModBlockTags;
import za.lana.signum.util.ModCustomTrades;
import za.lana.signum.villager.ModVillagers;

public class RunInitMain {
    public static void registerInits(){
        registerAttributes();
        createLostWorldPortal();
        ModFuels.registerModFuels();
        registerStrippableBlocks();
        registerFlammableBlocks();
        registerBlockEntities();
        ModVillagers.registerVillagers();
        ModCustomTrades.registerCustomTrades();
        registerModTags();
        registerSignumPackets();
        Signum.LOGGER.debug("RUNINIT for " + Signum.MOD_ID);
    }
    private static void createLostWorldPortal(){
        CustomPortalBuilder.beginPortal()
                .frameBlock(ModBlocks.MANGANESE_BLOCK)
                .lightWithItem(ModItems.FIRE_CRYSTAL)
                .destDimID(new Identifier(Signum.MOD_ID, "lost_world"))
                .setPortalSearchYRange(144, 400)
                .tintColor(240, 255, 240)
                .registerPortal();
    }

    private static void registerAttributes() {

        FabricDefaultAttributeRegistry.register(ModEntities.AIRBALLOON, AirBalloonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.AIRSHIP, AirShipEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CARGODRONE, CargoDroneEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.UNICORN, UnicornEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.PIDGEON, PidgeonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CURSED_WOLF, CursedWolfEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.FALLEN_ENTITY, FallenEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ELVE_GUARD_ENTITY, ElveGuardEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.WIZARD_ENTITY, WizardEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.ESPIDER_ENTITY, ESpiderEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GIANTESPIDER_ENTITY, ESpiderEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GHOST, GhostEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TORTURED_SOUL, TorturedSoulEntity.setAttributes());


        FabricDefaultAttributeRegistry.register(ModEntities.SSKELETON_ENTITY, SumSkeletonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TIBSKELETON_ENTITY, TibSkeletonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ICESKELETON_ENTITY, IceSkeletonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.FIRESKELETON_ENTITY, IceSkeletonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ENDERSKELETON_ENTITY, IceSkeletonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DARKSKELETON_ENTITY, IceSkeletonEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(ModEntities.TIBERIUM_FLOATER, TiberiumFloaterEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TIBERIUM_WIZARD_ENTITY, TiberiumWizardEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TTROOPER_ENTITY, TTrooperEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.TIBERIUM_WORM, TiberiumWormEntity.setAttributes());
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
        ModBlockEntities.registerBlockEntities();
        ModBlockEntities.registerLibGuiBlockEntities();
    }
    public static void registerModTags(){
        ModBlockTags.registerModBlockTags();
    }
    public static void registerSignumPackets(){
        ABKeyInputC2SPacket.init();
        ModMessages.registerC2SPackets();
    }
}
