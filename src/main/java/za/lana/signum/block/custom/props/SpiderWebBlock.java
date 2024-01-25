package za.lana.signum.block.custom.props;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.ESpiderEntity;

public class SpiderWebBlock
        extends Block {
    private static final int DEGRADE_CHANCE = 5;

    public SpiderWebBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.slowMovement(state, new Vec3d(0.25, 0.05f, 0.25));
    }
    //
    public static BlockState getDegradeState() {
        return Blocks.AIR.getDefaultState();
    }

    protected void degrade(BlockState state, World world, BlockPos pos) {
        if (world.isRaining() || world.isNight()) {
            world.removeBlock(pos, false);
            return;
        }
        world.setBlockState(pos, SpiderWebBlock.getDegradeState());
        world.updateNeighbor(pos, SpiderWebBlock.getDegradeState().getBlock(), pos);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(DEGRADE_CHANCE) != 0) {
            return;
        }
        if (world.getLightLevel(LightType.SKY, pos) <= 6 || world.isRaining() || world.isNight()) {
            ESpiderEntity eSpider = new ESpiderEntity(ModEntities.ESPIDER_ENTITY, world);
            world.spawnEntity(eSpider);
            eSpider.playSpawnEffects();
            eSpider.setBaby(true);
            this.degrade(state, world, pos);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                    SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.NEUTRAL, 0.5f, 0.4f /
                            (world.getRandom().nextFloat() * 0.4f + 0.8f));
        }
    }
}