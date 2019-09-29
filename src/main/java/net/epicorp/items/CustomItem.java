package net.epicorp.items;

import de.tr7zw.nbtapi.NBTItem;
import net.epicorp.utilities.objects.InstanceListener;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import java.util.function.Function;

public abstract class CustomItem implements Listener {
	protected final NamespacedKey id;

	public CustomItem(Plugin plugin, String id, Function<Event, ItemStack> converter) {
		this.id = new NamespacedKey(plugin, id);
		if(converter != null)
			InstanceListener.register(this, plugin, ItemEventHandler.class, converter, this::verify, ItemEventHandler::ignoreCancelled, ItemEventHandler::priority);
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

	public final String getId() {
		return id.toString();
	}

	/**
	 * returns true if the stack is an instance of the custom item
	 * @param istack
	 * @return
	 */
	public boolean verify(ItemStack istack) {
		if(istack == null) return false;
		NBTItem stack = new NBTItem(istack);
		String string = stack.getString("net.epicorp.items");
		return getId().equalsIgnoreCase(string);
	}

	@Override
	public String toString() {
		return getId();
	}
}
