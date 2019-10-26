package net.epicorp.items.enchantment;

import org.bukkit.NamespacedKey;
import java.util.function.BiConsumer;

public interface IEnchantmentRegistry {
	/**
	 * registers the enchantment in the registry
	 * @param item
	 */
	void register(CustomEnchantment item);

	/**
	 * get the custom enchantment associated with the id
	 * @param id the id of the item
	 * @return the custom item with that id or null
	 */
	CustomEnchantment getEnchantment(NamespacedKey id);

	/**
	 * get the custom enchantment associated with the id
	 * @param name "namespace:key"
	 * @return the custom item with that id or null
	 */
	default CustomEnchantment getEnchantment(String name) {
		int split = name.indexOf(':');
		return getEnchantment(new NamespacedKey(name.substring(0, split), name.substring(split+1)));
	}

	void forEach(BiConsumer<NamespacedKey, CustomEnchantment> consumer);
}
