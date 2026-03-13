package net.soaringpromise.kiisimplexp;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = SimplerExpMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // EXP progression
    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_LINEAR_XP = BUILDER
            .comment("If enabled, replaces Minecraft's exponential XP level curve with a flat, linear progression.")
            .define("enable-linear-xp", true);

    public static final ForgeConfigSpec.ConfigValue<Integer> LINEAR_XP_PER_LEVEL = BUILDER
            .comment("Amount of experience required per level when linear XP progression is enabled.")
            .defineInRange("linear-xp-per-level", 30, 30, 100);


   // EXP retention on death
    public static final ForgeConfigSpec.ConfigValue<Boolean> KEEP_XP_ON_DEATH = BUILDER
            .comment("If enabled, players retain a percentage of their experience after death.")
            .define("keep-xp-on-death", true);

    public static final ForgeConfigSpec.ConfigValue<Integer> DEATH_XP_RETENTION_PERCENT = BUILDER
            .comment("Percentage of experience retained after death when XP retention is enabled.")
            .defineInRange("death-xp-retention-percent", 100, 0, 100);


    // Bottle O' Enchanting tweaks
    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_FLAT_BOTTLE_XP = BUILDER
            .comment("If enabled, makes Bottles o' Enchanting grant a fixed amount of experience instead of a random value.")
            .define("enable-flat-bottle-xp", true);

    public static final ForgeConfigSpec.ConfigValue<Integer> EXP_BOTTLE_XP_AMOUNT = BUILDER
            .comment("Amount of experience granted by each Bottle o' Enchanting.")
            .defineInRange("exp-bottle-xp-amount", 15, 0, 100);


    // Additive Books on Anvils
    public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ADDITIVE_BOOK_MERGING = BUILDER
            .comment("If enabled, allows enchanted books of different levels to be combined additively (e.g. I + II = III).")
            .define("enable-additive-book-merging", true);


    // Villagers
    public static final ForgeConfigSpec.ConfigValue<Boolean> REMOVE_EXP_BOTTLE_FROM_VILLAGERS = BUILDER
            .comment("If enabled, removes Bottles o' Enchanting from Cleric villager trade pools.")
            .define("remove-exp-bottle-from-villagers", true);


    // Arrow weapon and Enchant behavior
    public static final ForgeConfigSpec.ConfigValue<Boolean> INFINITY_NEEDS_NO_ARROWS = BUILDER
            .comment("If enabled, allows bows and crossbows with Infinity to fire even with no arrows in the inventory.")
            .define("infinity-needs-no-arrows", true);

    public static final ForgeConfigSpec.ConfigValue<Boolean> INFINITY_ANY_ARROW = BUILDER
            .comment("If enabled, allows the Infinity enchantment to use any type of arrow.")
            .define("infinity-any-arrow", true);

    public static final ForgeConfigSpec.ConfigValue<Boolean> INFINITY_ON_CROSSBOW = BUILDER
            .comment("If enabled, allows for Infinity to be applied to crossbows as well.")
            .define("infinity-on-crossbow", true);

    public static final ForgeConfigSpec.ConfigValue<Boolean> REMOVE_INFINITY_MENDING_MUTEX = BUILDER
            .comment("If enabled, removes mutual exclusivity between Infinity and Mending.")
            .define("remove-infinity-mending-mutex", true);


    // Anvils
    public static final ForgeConfigSpec.ConfigValue<Boolean> REMOVE_ANVIL_PENALTY = BUILDER
            .comment("If enabled, removes the cumulative XP cost penalty for repairing items multiple times.")
            .define("remove-anvil-penalty", true);

    public static final ForgeConfigSpec.ConfigValue<Boolean> CHEAP_RENAMING = BUILDER
            .comment("Renaming an item will always cost 1 level.")
            .define("free-renaming", true);

    public static final ForgeConfigSpec.ConfigValue<Boolean> DISABLE_TOO_EXPENSIVE = BUILDER
            .comment("If enabled, disables the 'Too Expensive!' limit of anvils.")
            .define("disable-too-expensive", true);


    // Smart Mending
    public static final ForgeConfigSpec.ConfigValue<Boolean> SMART_MENDING = BUILDER
            .comment("If enabled, Mending will always prioritize the item with the most damage, rather than picking randomly.")
            .define("smart-mending", true);


    // Safe Furnaces
    public static final ForgeConfigSpec.ConfigValue<Boolean> FURNACE_XP_DROP = BUILDER
            .comment("If enabled, breaking a furnace will drop any XP stored from smelted items.")
            .define("furnace-xp-drop", true);


    // Experience Magnet
    public static final ForgeConfigSpec.ConfigValue<Double> XP_MAGNET_RANGE = BUILDER
            .comment("The range (in blocks) at which XP orbs will start flying towards the player. Vanilla is 8.0.")
            .defineInRange("exp-bottle-xp-amount", 12.0, 8.0, 24.0);


    // Crop Experience
    public static final ForgeConfigSpec.ConfigValue<Boolean> CROPS_GIVE_XP = BUILDER
            .comment("If enabled, harvesting fully grown crops (Wheat, Carrots, Nether Wart, etc.).")
            .define("crops-give-xp", true);

    public static final ForgeConfigSpec.ConfigValue<Integer> CROP_EXP_DROP = BUILDER
            .comment("Amount of experience granted by harvesting fully grown crops.")
            .defineInRange("crops-xp-drops", 3, 0, 10);


    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean linearXpEnabled;
    public static int linearXpPerLevel;
    public static boolean keepXpOnDeath;
    public static int deathXpRetentionPercent;
    public static boolean flatBottleXpEnabled;
    public static int expBottleXpAmount;
    public static boolean additiveBookMerging;
    public static boolean removeExpBottleFromVillagers;
    public static boolean infinityNeedsNoArrows;
    public static boolean infinityUsesAnyArrow;
    public static boolean infinityOnCrossbow;
    public static boolean noInfinityMendingMutex;
    public static boolean removeAnvilPenalty;
    public static boolean cheapRenaming;
    public static boolean smartMending;
    public static boolean furnaceXpDrop;
    public static double xpMagnetRange;
    public static boolean cropsGiveXp;
    public static int cropExpDrop;
    public static boolean removeTooExpensive;

    @SubscribeEvent
    static void onConfigLoad(final ModConfigEvent event)
    {
        if (event.getConfig().getSpec() != SPEC) return;
        bakeConfig();
    }

    @SubscribeEvent
    static void onConfigReload(final ModConfigEvent.Reloading event)
    {
        if (event.getConfig().getSpec() != SPEC) return;
        bakeConfig();
    }

    private static void bakeConfig()
    {
        linearXpEnabled = ENABLE_LINEAR_XP.get();
        linearXpPerLevel = LINEAR_XP_PER_LEVEL.get();
        keepXpOnDeath = KEEP_XP_ON_DEATH.get();
        deathXpRetentionPercent = DEATH_XP_RETENTION_PERCENT.get();
        flatBottleXpEnabled = ENABLE_FLAT_BOTTLE_XP.get();
        expBottleXpAmount = EXP_BOTTLE_XP_AMOUNT.get();
        additiveBookMerging = ENABLE_ADDITIVE_BOOK_MERGING.get();
        removeExpBottleFromVillagers = REMOVE_EXP_BOTTLE_FROM_VILLAGERS.get();
        infinityNeedsNoArrows = INFINITY_NEEDS_NO_ARROWS.get();
        removeAnvilPenalty = REMOVE_ANVIL_PENALTY.get();
        cheapRenaming = CHEAP_RENAMING.get();
        smartMending = SMART_MENDING.get();
        furnaceXpDrop = FURNACE_XP_DROP.get();
        xpMagnetRange = XP_MAGNET_RANGE.get();
        cropsGiveXp = CROPS_GIVE_XP.get();
        cropExpDrop = CROP_EXP_DROP.get();
        removeTooExpensive = DISABLE_TOO_EXPENSIVE.get();
        infinityUsesAnyArrow = INFINITY_ANY_ARROW.get();
        infinityOnCrossbow = INFINITY_ON_CROSSBOW.get();
        noInfinityMendingMutex = REMOVE_INFINITY_MENDING_MUTEX.get();
    }
}
