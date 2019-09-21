package net.epicorp.items.enchantment;

import org.bukkit.NamespacedKey;
import java.util.HashMap;
import java.util.Map;

public class EnchantmentRegistry implements IEnchantmentRegistry {
	protected Map<NamespacedKey, CustomEnchantment> enchants = new HashMap<>();

	@Override
	public void register(CustomEnchantment item) {
		enchants.put(item.id, item);
	}

	@Override
	public CustomEnchantment getEnchantment(NamespacedKey id) {
		return enchants.get(id);
	}
}
