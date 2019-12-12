package net.epicorp.items;

import org.bukkit.NamespacedKey;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ItemRegistry implements IItemRegistry {
	protected Map<NamespacedKey, CustomItem> customItemMap = new HashMap<>();

	@Override
	public void register(CustomItem item) {
		this.customItemMap.put(item.id, item);
	}

	@Override
	public CustomItem getItem(NamespacedKey id) {
		return this.customItemMap.get(id);
	}

	@Override
	public void forEach(BiConsumer<? super NamespacedKey, ? super CustomItem> action) {this.customItemMap.forEach(action);}
}
