package net.soaringpromise.kiisimplexp.events;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.soaringpromise.kiisimplexp.Config;
import net.soaringpromise.kiisimplexp.SimplerExpMod;

@Mod.EventBusSubscriber(modid = SimplerExpMod.MOD_ID)
public class VillagerTradeEvents {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (!Config.removeExpBottleFromVillagers) return;
        if (event.getType() != VillagerProfession.CLERIC) return;

        RandomSource dummyRandom = RandomSource.create();

        event.getTrades().forEach((level, trades) ->
                trades.removeIf(trade -> {
                    MerchantOffer offer = trade.getOffer(null, dummyRandom);

                    return offer != null && offer.getResult().is(Items.EXPERIENCE_BOTTLE);
                })
        );
    }
}