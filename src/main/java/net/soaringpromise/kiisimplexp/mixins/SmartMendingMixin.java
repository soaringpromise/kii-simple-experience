package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

@Mixin(ExperienceOrb.class)
public class SmartMendingMixin
{
    @ModifyVariable(
            method = "repairPlayerItems",
            at = @At("STORE"),
            ordinal = 0
    )
    private Map.Entry<EquipmentSlot, ItemStack>
        kiisimplexp$overrideMendingItem(Map.Entry<EquipmentSlot, ItemStack> original, Player player)
    {
        if (!Config.smartMending) return original;

        Map.Entry<EquipmentSlot, ItemStack> bestCandidate = null;
        int maxDamage = -1;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);

            if (!stack.isEmpty()
                    && stack.isDamaged()
                    && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, stack) > 0) {

                int currentDamage = stack.getDamageValue();

                if (currentDamage > maxDamage) {
                    maxDamage = currentDamage;
                    bestCandidate = Map.entry(slot, stack);
                }
            }
        }
        return bestCandidate;
    }
}