package net.epicorp.items;

import org.bukkit.NamespacedKey;

public interface IItemRegistry {
	/**
	 * registers the item in the registry
	 * @param item
	 */
	void register(CustomItem item);

	/**
	 * get the custom item associated with the id
	 * @param id the id of the item
	 * @return the custom item with that id or null
	 */
	CustomItem getItem(NamespacedKey id);
}
