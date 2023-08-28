/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.block.custom.crystal;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import za.lana.signum.sound.ModSounds;

public class TiberiumBlock
        extends Block {
    public TiberiumBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            world.playSound(null, blockPos, ModSounds.TIBERIUM_HIT, SoundCategory.BLOCKS, 1.0f, 0.5f + world.random.nextFloat() * 1.2f);
            world.playSound(null, blockPos, ModSounds.TIBERIUM_AMBIENT, SoundCategory.BLOCKS, 1.0f, 0.5f + world.random.nextFloat() * 1.2f);
        }
    }
}
