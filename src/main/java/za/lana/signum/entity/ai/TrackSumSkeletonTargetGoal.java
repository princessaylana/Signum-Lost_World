/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package za.lana.signum.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.hostile.SumSkeletonEntity;

import java.util.EnumSet;
import java.util.List;

public class TrackSumSkeletonTargetGoal
extends TrackTargetGoal {
    private final SumSkeletonEntity sskeleton;
    @Nullable
    private LivingEntity target;
    private final TargetPredicate targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(64.0);

    public TrackSumSkeletonTargetGoal(SumSkeletonEntity sskeleton) {
        super(sskeleton, false, true);
        this.sskeleton = sskeleton;
        this.setControls(EnumSet.of(Control.TARGET));
    }

    @Override
    public boolean canStart() {
        Box box = this.sskeleton.getBoundingBox().expand(10.0, 8.0, 10.0);
        //List<PlayerEntity> list = this.sskeleton.getWorld().getPlayers(this.targetPredicate, this.sskeleton, box);
        if (this.target == null) {
            return false;
        }
        if (target.getGroup() == ModEntityGroup.GOLDEN_KINGDOM) {
            return false;
        }
        if (target.getType().getSpawnGroup() == SpawnGroup.CREATURE) {
            return false;
        }
        return !(this.target instanceof PlayerEntity) || !this.target.isSpectator() && !((PlayerEntity)this.target).isCreative();
    }

    @Override
    public void start() {
        this.sskeleton.setTarget(this.target);
        super.start();
    }
}

