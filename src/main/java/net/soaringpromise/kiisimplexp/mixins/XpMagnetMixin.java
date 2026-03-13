package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.entity.ExperienceOrb;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ExperienceOrb.class)
public class XpMagnetMixin
{
    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 8.0D))
    private double kiisimplexp$increaseXpRange(double constant) {
        return Config.xpMagnetRange;
    }
}