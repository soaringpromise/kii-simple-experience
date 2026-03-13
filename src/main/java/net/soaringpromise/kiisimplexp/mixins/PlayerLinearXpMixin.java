package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.entity.player.Player;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerLinearXpMixin
{
    @Inject(method = "getXpNeededForNextLevel()I", at = @At("HEAD"), cancellable = true)
    private void applyLinearXp(CallbackInfoReturnable<Integer> cir) {
        if (!Config.linearXpEnabled) return;

        int xpPerLevel = Math.max(1, Config.linearXpPerLevel);
        cir.setReturnValue(xpPerLevel);
    }
}
