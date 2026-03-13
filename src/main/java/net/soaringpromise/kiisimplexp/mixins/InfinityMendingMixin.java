package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.item.enchantment.ArrowInfiniteEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArrowInfiniteEnchantment.class)
public class InfinityMendingMixin {

    @Inject(method = "checkCompatibility", at = @At("HEAD"), cancellable = true)
    private void kiisimplexp$allowMending(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.noInfinityMendingMutex) return;

        if (other == Enchantments.MENDING) cir.setReturnValue(true);
    }
}