package net.epicorp.items;

import org.bukkit.NamespacedKey;
import java.util.function.BiConsumer;

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

	/**
	 * get the custom item associated with the id
	 * @param name "namespace:key"
	 * @return the custom item with that id or null
	 */
	default CustomItem getItem(String name) {
		int split = name.indexOf(':');
		if(split == -1)
			return null;
		return getItem(new NamespacedKey(name.substring(0, split), name.substring(split+1, name.length())));
	}

	void forEach(BiConsumer<? super NamespacedKey, ? super CustomItem> action);
}
