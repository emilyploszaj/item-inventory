package dev.emi.iteminventory.api;

import net.minecraft.item.ItemStack;

/**
 * An interface that Items should implement if they contain items inside of them (shulker box, backpacks etc.).
 * All stacks returned from this interface should be assumed to be immutable, to change a stack call setStack
 * 
 * @author Emi
 */
public interface ItemInventory {
    
    /**
     * @param invItem The ItemStack with the inventory to access
     * @return The number of slots in the inventory
     */
    public int getInvSize(ItemStack invItem);

    /**
     * @param invItem The ItemStack with the inventory to access
     * @param index The slot in the inventory to get
     * @return The ItemStack in the provided slot
     */
    public ItemStack getStack(ItemStack invItem, int index);

    /**
     * Setting a stack can only be done if a non-air ItemStack can be removed from it and the provided stack can be inserted
     * @param invItem The ItemStack with the inventory to access
     * @param index The slot in the inventory to set
     * @param stack The ItemStack to be placed in the provided slot
     */
    public void setStack(ItemStack invItem, int index, ItemStack stack);

    /**
     * @param invItem The ItemStack with the inventory to access
     * @param index The slot in the inventory to be checked
     * @return Whether the ItemStack in the provided slot can be removed. If false, attempts to set an item in this slot will fail
     */
    public default boolean canTake(ItemStack invItem, int index) {
        return true;
    }

    /**
     * @param invItem The ItemStack with the inventory to access
     * @param index The slot in the inventory to be checked
     * @param stack The ItemStack to check
     * @return Whether
     */
    public default boolean canInsert(ItemStack invItem, int index, ItemStack stack) {
        return canTake(invItem, index);
    }
}