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
	public static final String ENCHANTMENT_NBT = "epicore.enchantments";
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
		NBTCompound compound = item.getCompound(ENCHANTMENT_NBT);
		return compound != null && compound.hasKey(idString);
	}

	/**
	 * get the level enchantment on the itemstack
	 * @param stack
	 * @return
	 */
	public Integer getLevel(ItemStack stack) {
		NBTItem item = new NBTItem(stack);
		NBTCompound compound = item.getCompound(ENCHANTMENT_NBT);
		if(compound != null && compound.hasKey(idString))
			return compound.getInteger(idString);
		else
			return null;
	}

	public NamespacedKey getId() {
		return id;
	}

	public ItemStack enchant(ItemStack item, int level) {
		NBTItem nbt = new NBTItem(item);
		NBTCompound compound;
		if(!nbt.hasKey(ENCHANTMENT_NBT))
			compound = nbt.addCompound(ENCHANTMENT_NBT);
		else
			compound = nbt.getCompound(ENCHANTMENT_NBT);

		if(!compound.hasKey(idString))
			compound.setInteger(idString, level);
		else
			compound.setInteger(idString, compound.getInteger(idString) + level);
		return nbt.getItem();
	}

	/**
	 * merges the enchantments from the provider onto the main
	 * @param mainStack the itemstack recieving the enchants
	 * @param providerStack the itemstack providing the enchants
	 * @return the combined item (mainStack is not changed)
	 */
	public static ItemStack mergeEnchantments(ItemStack mainStack, ItemStack providerStack) {
		NBTItem providernbt = new NBTItem(providerStack);
		if(providernbt.hasKey(ENCHANTMENT_NBT)) {
			NBTItem mainnbt = new NBTItem(mainStack);
			NBTCompound compound;
			if(!mainnbt.hasKey(ENCHANTMENT_NBT))
				compound = mainnbt.addCompound(ENCHANTMENT_NBT);
			else
				compound = mainnbt.getCompound(ENCHANTMENT_NBT);

			// TODO proper merge
			compound.mergeCompound(providernbt.getCompound(ENCHANTMENT_NBT));

			return mainnbt.getItem();
		}
		return mainStack.clone();
	}
}
