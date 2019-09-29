package net.epicorp.items.enchantment;

import org.bukkit.event.EventPriority;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * add an integer as the last argument to any event handler be invoked with the level of the enchantment
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
public @interface EnchantmentEventHandler {
	/**
	 * Highest is executed last, monitor should not modify the event, and lowest is executed first
	 * @return the priority of the event
	 */
	EventPriority priority() default EventPriority.NORMAL;

	/**
	 * if the event should still listen to cancelled events
	 * @return
	 */
	boolean ignoreCancelled() default false;
}
