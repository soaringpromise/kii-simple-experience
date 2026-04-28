package net.soaringpromise.kiisimplexp.events;

import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.soaringpromise.kiisimplexp.Config;
import net.soaringpromise.kiisimplexp.SimplerExpMod;

@Mod.EventBusSubscriber(modid = SimplerExpMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEvents {

    @SubscribeEvent
    public static void onPlayerDropXp(LivingExperienceDropEvent event) {
        if (!Config.keepXpOnDeath) return;

        if (event.getEntity() instanceof Player player) {
            if (player.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) return;
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!Config.keepXpOnDeath) return;

        if (event.isWasDeath()) {
            Player original = event.getOriginal();
            Player newPlayer = event.getEntity();

            if (original.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) return;

            int oldXp = getTrueCurrentXp(original);
            int keepXp = Math.round(oldXp * (Config.deathXpRetentionPercent / 100.0f));

            newPlayer.giveExperiencePoints(keepXp);
        }
    }

    private static int getTrueCurrentXp(Player player) {
        int level = player.experienceLevel;
        int xpInCurrentLevel = Math.round(player.experienceProgress * player.getXpNeededForNextLevel());

        if (Config.linearXpEnabled) {
            int xpPerLevel = Math.max(1, Config.linearXpPerLevel);
            return (level * xpPerLevel) + xpInCurrentLevel;
        } else {
            int totalXpForLevel;
            if (level <= 15) {
                totalXpForLevel = level * level + 6 * level;
            } else if (level <= 30) {
                totalXpForLevel = (int) (2.5 * level * level - 40.5 * level + 360);
            } else {
                totalXpForLevel = (int) (4.5 * level * level - 162.5 * level + 2220);
            }
            return totalXpForLevel + xpInCurrentLevel;
        }
    }
}