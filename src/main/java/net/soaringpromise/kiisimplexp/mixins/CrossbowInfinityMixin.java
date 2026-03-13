package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowInfinityMixin {
    @Redirect(
            method = "tryLoadProjectiles",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getProjectile(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private static ItemStack kiisimplexp$checkAmmo(LivingEntity shooter, ItemStack crossbow) {
        ItemStack found = shooter.getProjectile(crossbow);
        if (found.isEmpty() && Config.infinityNeedsNoArrows && Config.infinityOnCrossbow) {
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, crossbow) > 0) {
                return new ItemStack(Items.ARROW);
            }
        }
        return found;
    }

    @Redirect(
            method = "loadProjectile",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;split(I)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private static ItemStack kiisimplexp$preventConsumption(ItemStack stack, int amount, LivingEntity shooter, ItemStack crossbow) {
        boolean isInfinite = Config.infinityOnCrossbow &&
                EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, crossbow) > 0;

        if (isInfinite) {
            ItemStack fake = stack.copy();
            fake.setCount(1);
            return fake;
        }
        return stack.split(amount);
    }

    @Inject(method = "getArrow", at = @At("RETURN"))
    private static void kiisimplexp$setPickupStatus(Level level, LivingEntity shooter, ItemStack crossbow, ItemStack ammo, CallbackInfoReturnable<AbstractArrow> cir) {
        if (!Config.infinityOnCrossbow) return;

        AbstractArrow arrowEntity = cir.getReturnValue();
        if (arrowEntity != null)
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, crossbow) > 0)
                arrowEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
    }
}