/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.constant;

import software.bernie.geckolib.core.animation.RawAnimation;

// Needed to animate items
public final class SignumAnimations {
    public static final RawAnimation TOXICGUN_SHOOT = RawAnimation.begin().thenPlay("animation.toxicgun.shoot");
    public static final RawAnimation TOXICGUN_IDLE = RawAnimation.begin().thenPlay("animation.toxicgun.idle");

    public static final RawAnimation TIBERIUMWORM_SPAWN = RawAnimation.begin().thenPlay("animation.tworm.spawn");
    public static final RawAnimation TIBERIUMWORM_IDLE = RawAnimation.begin().thenPlay("animation.tworm.idle");
    public static final RawAnimation TIBERIUMWORM_ATTACK = RawAnimation.begin().thenPlay("animation.tworm.attack");
    public static final RawAnimation TIBERIUMWORM_WALK = RawAnimation.begin().thenPlay("animation.tworm.walk");



}