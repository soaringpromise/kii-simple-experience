package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ThrownExperienceBottle.class)
public class FlatExpBottleMixin {

    @ModifyArg(
            method = "onHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"
            ),
            index = 2
    )
    private int applyFlatBottleXp(int originalAmount) {
        if (!Config.flatBottleXpEnabled) return originalAmount;
        return Math.max(0, Config.expBottleXpAmount);
    }
}
