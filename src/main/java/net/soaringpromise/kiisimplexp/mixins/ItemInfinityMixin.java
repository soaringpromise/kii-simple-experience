package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArrowItem.class)
public class ItemInfinityMixin {
    @Inject(method = "isInfinite", at = @At("HEAD"), cancellable = true, remap = false)
    private void kiisimplexp$forceInfiniteTypes(ItemStack stack, ItemStack bow, Player player, CallbackInfoReturnable<Boolean> cir)
    {
        int infinityLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);

        if (infinityLevel > 0)
            if (Config.infinityUsesAnyArrow)
                cir.setReturnValue(true);
    }
}