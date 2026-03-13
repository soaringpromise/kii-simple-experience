package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerInfinityMixin {
    @Inject(method = "getProjectile", at = @At("RETURN"), cancellable = true)
    private void kiisimplexp$provideFakeArrow(ItemStack weapon, CallbackInfoReturnable<ItemStack> cir) {
        if (!cir.getReturnValue().isEmpty()) return;

        if (!Config.infinityNeedsNoArrows) return;

        boolean isBow = weapon.getItem() instanceof BowItem;
        boolean isCrossbow = (weapon.getItem() instanceof CrossbowItem) && Config.infinityOnCrossbow;

        if (isBow || isCrossbow)
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, weapon) > 0)
                cir.setReturnValue(new ItemStack(Items.ARROW));
    }
}