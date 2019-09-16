package net.epicorp.items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public abstract class CustomItem {
	protected final NamespacedKey id;

	public CustomItem(NamespacedKey id) {
		this.id = id;
	}

	/**
	 * creates a new base itemstack for this item
	 * @return
	 */
	protected abstract ItemStack baseStack();

	/**
	 * creates a new ItemStack with the item id NBT included
	 * @return
	 */
	public final ItemStack createNewStack() {
		NBTItem stack = new NBTItem(baseStack());
		stack.setString("net.epicorp.items", getId());
		return stack.getItem();
	}

	protected final String getId() {
		return id.toString();
	}

	/**
	 * returns true if the stack is an instance of the custom item
	 * @param istack
	 * @return
	 */
	public boolean verify(ItemStack istack) {
		NBTItem stack = new NBTItem(istack);
		String string = stack.getString("net.epicorp.items");
		return getId().equalsIgnoreCase(string);
	}
}
