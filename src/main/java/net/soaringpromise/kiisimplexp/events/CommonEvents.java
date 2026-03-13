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

            int oldXp = original.totalExperience;
            int keepXp = Math.round(oldXp * (Config.deathXpRetentionPercent / 100.0f));

            newPlayer.giveExperiencePoints(keepXp);
        }
    }
}