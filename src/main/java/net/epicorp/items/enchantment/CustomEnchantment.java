package net.epicorp.items.enchantment;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import net.epicorp.utilities.objects.InstanceListener;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class CustomEnchantment implements Listener {
	public static final String ENCHANTMENT_NBT = "epicore.enchantments";
	protected final NamespacedKey id;
	private final String idString;

	public CustomEnchantment(Plugin plugin, String id, Function<Event, ItemStack> converter) {
		this.id = new NamespacedKey(plugin, id);
		this.idString = this.id.toString();
		if (converter != null)
			InstanceListener.register(this, plugin, EnchantmentEventHandler.class, (e) -> this.getLevel(converter.apply(e)), Objects::nonNull, EnchantmentEventHandler::ignoreCancelled, EnchantmentEventHandler::priority);
	}

	/**
	 * ensures that the specified stack has this custom enchantment on it
	 *
	 * @param stack
	 * @return
	 */
	public boolean verify(ItemStack stack) {
		if (stack == null) return false;
		NBTItem item = new NBTItem(stack);
		NBTCompound compound = item.getCompound(ENCHANTMENT_NBT);
		return compound != null && compound.hasKey(this.idString);
	}

	/**
	 * get the level enchantment on the itemstack
	 *
	 * @param stack
	 * @return
	 */
	public Integer getLevel(ItemStack stack) {
		NBTItem item = new NBTItem(stack);
		NBTCompound compound = item.getCompound(ENCHANTMENT_NBT);
		if (compound != null && compound.hasKey(this.idString)) return compound.getInteger(this.idString);
		else return null;
	}

	public NamespacedKey getId() {
		return this.id;
	}

	public ItemStack enchant(ItemStack item, int level) {
		NBTItem nbt = new NBTItem(item);
		NBTCompound compound;
		if (!nbt.hasKey(ENCHANTMENT_NBT)) compound = nbt.addCompound(ENCHANTMENT_NBT);
		else compound = nbt.getCompound(ENCHANTMENT_NBT);

		if (!compound.hasKey(this.idString)) compound.setInteger(this.idString, level);
		else compound.setInteger(this.idString, compound.getInteger(this.idString) + level);
		return nbt.getItem();
	}

	/**
	 * merges the enchantments from the provider onto the main
	 *
	 * @param mainStack the itemstack recieving the enchants
	 * @param providerStack the itemstack providing the enchants
	 * @return the combined item (mainStack is not changed)
	 */
	public static ItemStack mergeEnchantments(ItemStack mainStack, ItemStack providerStack, IEnchantmentRegistry registry) {
		for (Map.Entry<CustomEnchantment, Integer> entry : getEnchantments(providerStack, registry).entrySet())
			mainStack = entry.getKey().enchant(mainStack, entry.getValue());
		return mainStack.clone();
	}

	public static Map<CustomEnchantment, Integer> getEnchantments(ItemStack stack, IEnchantmentRegistry registry) {
		NBTItem stacknbt = new NBTItem(stack);
		if (stacknbt.hasKey(ENCHANTMENT_NBT)) {
			HashMap<CustomEnchantment, Integer> enchants = new HashMap<>();
			NBTCompound compound = stacknbt.getCompound(ENCHANTMENT_NBT);
			compound.getKeys().forEach(k -> enchants.put(registry.getEnchantment(k), compound.getInteger(k)));
			return enchants;
		}
		return Collections.emptyMap();
	}
}
