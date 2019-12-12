package net.epicorp.items.enchantment;

import org.bukkit.NamespacedKey;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class EnchantmentRegistry implements IEnchantmentRegistry {
	protected Map<NamespacedKey, CustomEnchantment> enchants = new HashMap<>();

	@Override
	public void register(CustomEnchantment item) {
		this.enchants.put(item.id, item);
	}

	@Override
	public CustomEnchantment getEnchantment(NamespacedKey id) {
		return this.enchants.get(id);
	}

	@Override
	public void forEach(BiConsumer<NamespacedKey, CustomEnchantment> consumer) {
		this.enchants.forEach(consumer);
	}
}
