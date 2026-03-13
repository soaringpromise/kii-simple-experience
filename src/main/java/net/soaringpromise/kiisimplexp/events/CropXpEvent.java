package net.soaringpromise.kiisimplexp.events;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.soaringpromise.kiisimplexp.Config;
import net.soaringpromise.kiisimplexp.SimplerExpMod;

@Mod.EventBusSubscriber(modid = SimplerExpMod.MOD_ID)
public class CropXpEvent {

    @SubscribeEvent
    public static void onCropBreak(BlockEvent.BreakEvent event) {
        if (!Config.cropsGiveXp || event.getPlayer().isCreative()) return;

        BlockState state = event.getState();
        boolean isMatureCrop = (state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state));
        boolean isMatureWart = (state.getBlock() instanceof NetherWartBlock && state.getValue(NetherWartBlock.AGE) >= 3);

        if (isMatureCrop || isMatureWart) {
            if (event.getLevel() instanceof net.minecraft.world.level.Level level && !level.isClientSide) {
                level.addFreshEntity(new ExperienceOrb(
                        level,
                        event.getPos().getX() + 0.5,
                        event.getPos().getY() + 0.5,
                        event.getPos().getZ() + 0.5,
                        Config.cropExpDrop
                ));
            }
        }
    }
}