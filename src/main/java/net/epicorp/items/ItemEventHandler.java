package net.epicorp.items;

import org.bukkit.event.EventPriority;

/**
 * add an ItemStack as the last parameter in a annotated handler method to be invoked with the item stack performing the action
 */
public @interface ItemEventHandler {
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
