package net.heyimerik.aac.listeners;

import net.heyimerik.aac.AntiAutoClicker;
import net.heyimerik.aac.sql.SQLResponse;
import net.heyimerik.aac.util.TimeUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class EntityLogin implements Listener {
	private AntiAutoClicker aac = AntiAutoClicker.getAAC();

	@EventHandler
	public void a(PlayerLoginEvent event) {
		if (this.aac._database.exists("SELECT `timeout` FROM `bans` WHERE `uuid`='" + event.getPlayer().getUniqueId().toString() + "';").exists(true)) {
			SQLResponse data = this.aac._database.executeQuery("SELECT `timeout` FROM `bans` WHERE `uuid`='" + event.getPlayer().getUniqueId().toString() + "';");

			long timeRemaining = data.getSet().nextLong("timeout").value() - System.currentTimeMillis();
			data.close();

			if (timeRemaining < 0) {
				this.aac._database.executeUpdate("DELETE FROM `bans` WHERE `uuid`='" + event.getPlayer().getUniqueId().toString() + "'").close();
				return;
			}

			String formattedTime = TimeUtil.convert(timeRemaining / 1000L);
			String message = this.aac.getCPSTask().getBanMessage().replaceAll("\\{time\\}", formattedTime);

			event.disallow(Result.KICK_BANNED, message);
		}
	}
}
