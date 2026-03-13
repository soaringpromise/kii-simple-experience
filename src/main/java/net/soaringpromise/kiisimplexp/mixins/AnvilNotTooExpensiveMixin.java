package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.inventory.AnvilMenu;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilMenu.class)
public abstract class AnvilNotTooExpensiveMixin {

    @ModifyConstant(
            method = "createResult",
            constant = @Constant(intValue = 40),
            require = 1
    )
    private int kiisimplexp$removeTooExpensiveLimit(int original) {
        return Config.removeTooExpensive ? Integer.MAX_VALUE : original;
    }
}
