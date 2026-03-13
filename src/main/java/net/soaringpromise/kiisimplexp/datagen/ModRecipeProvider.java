package net.soaringpromise.kiisimplexp.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.soaringpromise.kiisimplexp.SimplerExpMod;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder
{
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, Items.ECHO_SHARD)
                .pattern(" S ")
                .pattern("SAS")
                .pattern(" S ")
                .define('S', Items.SCULK)
                .define('A', Items.AMETHYST_SHARD)
                .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .save(pWriter, SimplerExpMod.MOD_ID + ":craftable_echo_shard");


        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.ENCHANTED_GOLDEN_APPLE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('B', Items.EXPERIENCE_BOTTLE)
                .define('A', Items.GOLDEN_APPLE)
                .unlockedBy("has_golden_apple", has(Items.GOLDEN_APPLE))
                .save(pWriter, SimplerExpMod.MOD_ID + ":craftable_enchanted_gapple");


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NAME_TAG)
                .pattern("  n")
                .pattern(" P ")
                .define('n', Ingredient.of(Items.IRON_NUGGET, Items.GOLD_NUGGET))
                .define('P', Items.PAPER)
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(pWriter, SimplerExpMod.MOD_ID + ":craftable_name_tag");
    }
}
