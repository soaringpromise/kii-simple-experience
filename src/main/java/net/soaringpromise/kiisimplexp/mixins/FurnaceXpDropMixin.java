package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlock.class)
public class FurnaceXpDropMixin {

    @Inject(method = "onRemove", at = @At("HEAD"))
    private void kiisimplexp$dropStoredXp(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo ci) {
        if (!Config.furnaceXpDrop || state.is(newState.getBlock())) return;

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof AbstractFurnaceBlockEntity furnace && level instanceof ServerLevel serverLevel)
            furnace.getRecipesToAwardAndPopExperience(serverLevel, Vec3.atCenterOf(pos));
    }
}