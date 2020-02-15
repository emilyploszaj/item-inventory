package dev.emi.iteminventory.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.emi.iteminventory.api.BlockItemInventory;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;

/**
 * Adds ItemInventory functionality to shulker boxes
 * @author Emi
 */
@Mixin(Items.class)
public abstract class ItemsMixin {

    @Shadow
    protected static Item register(Block block, Item item) {
        return null;
    }

    @Inject(at = @At("HEAD"), method = "register(Lnet/minecraft/item/BlockItem;)Lnet/minecraft/item/Item;", cancellable = true)
    private static void register(BlockItem item, CallbackInfoReturnable<Item> info) {
        if (item.getBlock() instanceof ShulkerBoxBlock) {
            info.setReturnValue(register(item.getBlock(), new BlockItemInventory(item.getBlock(), new Item.Settings().maxCount(1).group(ItemGroup.DECORATIONS), 27)));
        }
    }
}