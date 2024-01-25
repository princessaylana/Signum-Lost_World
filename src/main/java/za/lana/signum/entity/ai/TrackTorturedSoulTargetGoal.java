package za.lana.signum.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.hostile.TorturedSoulEntity;

import java.util.EnumSet;

public class TrackTorturedSoulTargetGoal
extends TrackTargetGoal {
    private final TorturedSoulEntity torturedSoul;
    @Nullable
    private LivingEntity target;
    private final TargetPredicate targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(64.0);

    public TrackTorturedSoulTargetGoal(TorturedSoulEntity torturedSoul) {
        super(torturedSoul, false, true);
        this.torturedSoul = torturedSoul;
        this.setControls(EnumSet.of(Control.TARGET));
    }

    @Override
    public boolean canStart() {
        Box box = this.torturedSoul.getBoundingBox().expand(10.0, 8.0, 10.0);
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
        this.torturedSoul.setTarget(this.target);
        super.start();
    }
}

