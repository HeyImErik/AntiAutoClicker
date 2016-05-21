package net.heyimerik.aac.listeners;

import net.heyimerik.aac.AntiAutoClicker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EntityInteract implements Listener {
	private AntiAutoClicker aac = AntiAutoClicker.getAAC();

	@EventHandler
	public void a(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR) this.aac.getCPSTask().addClick(event.getPlayer());
	}
}
