package net.epicorp.items.enchantment;

import org.bukkit.event.EventPriority;

/**
 * add an integer as the last argument to any event handler be invoked with the level of the enchantment
 */
public @interface EnchantmentEventHandler {
	/**
	 * Highest is executed last, monitor should not modify the event, and lowest is executed first
	 * @return the priority of the event
	 */
	EventPriority priority();

	/**
	 * if the event should still listen to cancelled events
	 * @return
	 */
	boolean ignoreCancelled();
}
