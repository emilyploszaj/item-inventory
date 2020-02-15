# Item Inventory
An API for mod developers who want to read the inventories stored inside items and developers who want to provide a way for their items with inventories to be read.

## Developers
To add Item Inventory to your project you need to add jitpack to your repositories in your build.gradle
```
repositories {
	maven {
		url = "https://jitpack.io"
	}
}
```
And then to add Item Inventory you add it as a dependency in your build.gradle
```
dependencies {
	include "com.github.emilyploszaj:item-inventory:v1.0.0"
}
```
## Implementing an ItemInventory
Here's an example of an inventory item that implements ItemInventory.
```java
public class MyBackpack extends Item implements ItemInventory {

	//Normal item code

	@Override
	public int getInvSize(ItemStack invItem) {
		//Return the amount of slots your inventory has
	}

	@Override
	public ItemStack getStack(ItemStack invItem, int index) {
		//Deserialize your inventory and return an ItemStack
		//For instance, read the nbt storing the items and return the relevant one
	}

	@Override
	public void setStack(ItemStack invItem, int index, ItemStack stack) {
		//Check to make sure the provided variables are valid
		if (canInsert(invItem, index, stack)) {
			//Serialize the change and store it back in your format
		}
	}

	@Override
    public default boolean canInsert(ItemStack invItem, int index, ItemStack stack) {
		//Only allow unstackable items into the inventory
        return canTake(invItem, index) && stack.getItem().getMaxCount() == 1;
    }
}
```
## Using the API
Here's an example of a recursive function that returns how many items are stored in the provided list of items, including those inside of those items' inventories (excluding empty slots) and the inventories of the items inside items with inventories etc.
```java
public int getAmountOfItems(List<ItemStack> items) {
	int amount = 0;
	for (int i = 0; i < items.size(); i++) {
		ItemStack stack = items.get(i);

		if (!stack.isEmpty()) {
			amount++;

			if (stack instanceof ItemInventory) {
				List<ItemStack> innerStacks = new ArrayList<ItemStack>();
				ItemInventory inv = (ItemInventory) stack;

				for (int j = 0; j < inv.getInvSize; j++) {
					innerStacks.add(inv.getStack());
				}
				amount += getAmountOfItems(innerStacks);
			}
		}
	}
	return amount;
}
```