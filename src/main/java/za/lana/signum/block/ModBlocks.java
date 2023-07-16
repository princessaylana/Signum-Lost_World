/**
 * Registers the mod blocks
 * SIGNUM
 * MIT License
 * Lana
 * */

package za.lana.signum.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import za.lana.signum.Signum;
import za.lana.signum.block.custom.RazorWireBlock;
import za.lana.signum.block.custom.SkyForgeBlock;

//obsidian hardness (50.0f, 1200.0f)
//iron hardness (5.0f, 6.0f)
//steel hardness (unknown)
//the names here needs to match the json files

public class ModBlocks {

    public static final Block MANGANESE_ORE = registerBlock("manganese_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.STONE)
                    .strength(10.0f, 100.0f).requiresTool(), UniformIntProvider.create(3, 6)));
    public static final Block DEEPSLATE_MANGANESE_ORE = registerBlock("deepslate_manganese_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(BlockSoundGroup.DEEPSLATE)
                    .strength(15.0f, 150.0f).requiresTool(), UniformIntProvider.create(3, 6)));
    public static final Block NETHERRACK_MANGANESE_ORE = registerBlock("netherrack_manganese_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.END_STONE).sounds(BlockSoundGroup.NETHER_ORE)
                    .strength(20.0f, 200.0f).requiresTool(), UniformIntProvider.create(3, 6)));
    public static final Block ENDSTONE_MANGANESE_ORE = registerBlock("endstone_manganese_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.END_STONE).sounds(BlockSoundGroup.NETHER_ORE)
                    .strength(25.0f, 250.0f).requiresTool(), UniformIntProvider.create(3, 6)));
    public static final Block MANGANESE_BLOCK = registerBlock("manganese_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE)
                    .strength(30.0f, 600.0f)));
    public static final Block RAZORWIRE_BLOCK = registerBlock("razorwire_block",
            new RazorWireBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(10.0f,250.0f)));
    public static final Block SKYFORGE= registerBlock("skyforge",
            new SkyForgeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(10.0f,250.0f).nonOpaque()));






    //registering blocks
    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Signum.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Signum.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Signum.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Signum.LOGGER.info("Registering ModBlocks for " + Signum.MOD_ID);
    }
}
