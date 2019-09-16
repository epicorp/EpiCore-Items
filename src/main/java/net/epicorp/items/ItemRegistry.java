package net.epicorp.items;

import org.bukkit.NamespacedKey;
import java.util.HashMap;
import java.util.Map;

public class ItemRegistry implements IItemRegistry {
	protected Map<NamespacedKey, CustomItem> customItemMap = new HashMap<>();

	@Override
	public void register(CustomItem item) {
		customItemMap.put(item.id, item);
	}

	@Override
	public CustomItem getItem(NamespacedKey id) {
		return customItemMap.get(id);
	}
}
