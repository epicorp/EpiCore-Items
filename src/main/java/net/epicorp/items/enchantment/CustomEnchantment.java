package net.epicorp.items.enchantment;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import net.epicorp.utilities.objects.InstanceListener;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import java.util.Objects;
import java.util.function.Function;

public abstract class CustomEnchantment implements Listener {
	protected final NamespacedKey id;
	private final String idString;
	public CustomEnchantment(Plugin plugin, String id, Function<Event, ItemStack> converter) {
		this.id = new NamespacedKey(plugin, id);
		this.idString = this.id.toString();
		if(converter != null)
			InstanceListener.register(this, plugin, EnchantmentEventHandler.class, (e) -> getLevel(converter.apply(e)), Objects::nonNull, EnchantmentEventHandler::ignoreCancelled, EnchantmentEventHandler::priority);
	}

	/**
	 * ensures that the specified stack has this custom enchantment on it
	 * @param stack
	 * @return
	 */
	public boolean verify(ItemStack stack) {
		if(stack == null) return false;
		NBTItem item = new NBTItem(stack);
		NBTCompound compound = item.getCompound("epicore.enchantments");
		return compound != null && compound.hasKey(idString);
	}

	/**
	 * get the level enchantment on the itemstack
	 * @param stack
	 * @return
	 */
	public Integer getLevel(ItemStack stack) {
		NBTItem item = new NBTItem(stack);
		NBTCompound compound = item.getCompound("epicore.enchantments");
		if(compound != null && compound.hasKey(idString))
			return compound.getInteger(idString);
		else
			return null;
	}

	public NamespacedKey getId() {
		return id;
	}
}
