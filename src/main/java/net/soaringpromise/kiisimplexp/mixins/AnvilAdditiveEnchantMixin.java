package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(AnvilMenu.class)
public class AnvilAdditiveEnchantMixin
{
    @Inject(method = "createResult", at = @At("TAIL"))
    private void kiisimplexp$additiveEnchantMerge(CallbackInfo ci) {
        if (!Config.additiveBookMerging) return;
        AnvilMenu menu = (AnvilMenu) (Object) this;

        ItemStack left = menu.getSlot(0).getItem();
        ItemStack right = menu.getSlot(1).getItem();
        ItemStack output = menu.getSlot(2).getItem();

        if (left.isEmpty() || right.isEmpty() || output.isEmpty()) return;

        Map<Enchantment, Integer> leftEnchants = EnchantmentHelper.getEnchantments(left);
        Map<Enchantment, Integer> rightEnchants = EnchantmentHelper.getEnchantments(right);

        if (leftEnchants.isEmpty() || rightEnchants.isEmpty()) return;

        Map<Enchantment, Integer> newEnchants = new HashMap<>(leftEnchants);

        for (Map.Entry<Enchantment, Integer> entry : rightEnchants.entrySet()) {
            Enchantment enchant = entry.getKey();
            int rightLevel = entry.getValue();
            int leftLevel = newEnchants.getOrDefault(enchant, 0);

            boolean isBook = left.is(Items.ENCHANTED_BOOK) || left.is(Items.BOOK);
            if (!isBook && !enchant.canEnchant(left)) continue;

            if (leftLevel > 0) {
                int summed = leftLevel + rightLevel;
                int max = enchant.getMaxLevel();
                newEnchants.put(enchant, Math.min(summed, max));
            } else {
                newEnchants.put(enchant, rightLevel);
            }
        }
        EnchantmentHelper.setEnchantments(newEnchants, output);
    }
}