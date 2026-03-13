package net.soaringpromise.kiisimplexp.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import org.jetbrains.annotations.NotNull;

public class ModBrewing {
    public static void registerBrewing() {
        ItemStack output = new ItemStack(Items.EXPERIENCE_BOTTLE);

        Ingredient ingredient = Ingredient.of(Items.ECHO_SHARD);
        Ingredient inputBase = Ingredient.of(
                Items.POTION,
                Items.SPLASH_POTION,
                Items.LINGERING_POTION
        );

        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(inputBase, ingredient, output) {
            @Override
            public boolean isInput(@NotNull ItemStack stack) {
                return inputBase.test(stack) && PotionUtils.getPotion(stack) == Potions.AWKWARD;
            }
        });
    }
}