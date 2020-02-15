package dev.emi.iteminventory.api;

import net.minecraft.block.Block;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

/**
 * Use this class (or your own child) to define behavior for BlockItems. By default items are stored in NBT like a shulker box item
 * @author Emi
 */
public class BlockItemInventory extends BlockItem implements ItemInventory {
    public final int SIZE;

    public BlockItemInventory(Block block, Settings settings, int size) {
        super(block, settings);
        SIZE = size;
    }

    @Override
    public int getInvSize(ItemStack invItem) {
        return SIZE;
    }

    @Override
    public ItemStack getStack(ItemStack invItem, int index) {
        CompoundTag tag = invItem.getSubTag("BlockEntityTag");
        if (tag != null) {
            DefaultedList<ItemStack> inventory = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);
            if (tag.contains("Items", 9)) {
               Inventories.fromTag(tag, inventory);
            }
            return inventory.get(index);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setStack(ItemStack invItem, int index, ItemStack stack) {
        if (canInsert(invItem, index, stack) && canTake(invItem, index)) {
            CompoundTag tag = invItem.getSubTag("BlockEntityTag");
            if (tag != null) {
                DefaultedList<ItemStack> inventory = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);
                if (tag.contains("Items", 9)) {
                   Inventories.fromTag(tag, inventory);
                }
                inventory.set(index, stack);
                invItem.putSubTag("BlockEntityTag", Inventories.toTag(tag, inventory, false));
            }
        }
    }
}