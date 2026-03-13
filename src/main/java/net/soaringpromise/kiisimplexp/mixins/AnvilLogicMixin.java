package net.soaringpromise.kiisimplexp.mixins;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.soaringpromise.kiisimplexp.Config;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilLogicMixin {

    @Shadow private String itemName;
    @Final @Shadow private DataSlot cost;

    @Inject(method = "createResult", at = @At("TAIL"))
    private void kiisimplexp$modifyAnvilLogic(CallbackInfo ci) {
        AnvilMenu menu = (AnvilMenu) (Object) this;

        ItemStack left  = menu.getSlot(0).getItem();
        ItemStack right = menu.getSlot(1).getItem();
        ItemStack output = menu.getSlot(2).getItem();

        if (output.isEmpty()) return;

        if (Config.removeAnvilPenalty) output.setRepairCost(0);

        boolean isRename =
                !left.isEmpty()
                        && right.isEmpty()
                        && itemName != null
                        && !itemName.equals(left.getHoverName().getString());

        if (Config.cheapRenaming && isRename) this.cost.set(1);
    }
}
