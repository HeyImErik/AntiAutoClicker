package net.heyimerik.aac;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import net.heyimerik.aac.util.TimeUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CPSTask implements Runnable {
	private Map<String, Integer> previousClicks = new ConcurrentHashMap<String, Integer>();
	private Map<String, Integer> clicks = new ConcurrentHashMap<String, Integer>();
	private Map<String, Integer> vl = new ConcurrentHashMap<String, Integer>();
	private AntiAutoClicker aac = AntiAutoClicker.getAAC();

	private String staffPermission;
	private boolean executeBans;
	private String banMessage;
	private String message;
	private double minTPS;
	private long duration;
	private TimeUnit unit;
	private int minCPS;
	private int maxVl;

	public CPSTask() {
		this.staffPermission = this.aac.getConfig().getString("messages.permission");
		this.message = ChatColor.translateAlternateColorCodes('&', this.aac.getConfig().getString("messages.message"));
		this.minTPS = this.aac.getConfig().getDouble("min-tps");
		this.maxVl = this.aac.getConfig().getInt("max-vl");
		this.minCPS = this.aac.getConfig().getInt("min-cps");

		this.unit = TimeUnit.valueOf(this.aac.getConfig().getString("bans.unit"));
		if (this.unit == null) this.unit = TimeUnit.DAYS;
		this.duration = this.aac.getConfig().getLong("bans.duration");
		this.executeBans = this.aac.getConfig().getBoolean("bans.execute");

		this.banMessage = "";
		for (String part : this.aac.getConfig().getStringList("bans.message")) {
			this.banMessage += ChatColor.translateAlternateColorCodes('&', part) + "\n";
		}
	}

	public void run() {
		for (String playerName : this.previousClicks.keySet()) {
			if (this.previousClicks.get(playerName) >= this.vl.get(playerName)) {
				this.vl.remove(playerName);
				this.previousClicks.remove(playerName);
			} else {
				int last = this.previousClicks.get(playerName);
				this.previousClicks.put(playerName, last + 1);
			}
		}

		for (String playerName : this.clicks.keySet()) {
			int clicks = this.clicks.get(playerName);
			this.clicks.remove(playerName);

			Player player = this.aac.getServer().getPlayer(playerName);

			if (clicks >= this.minCPS) {
				int vl = 1;
				if (this.vl.containsKey(playerName)) {
					vl += this.vl.get(playerName);
					this.vl.remove(playerName);
				}

				String message = this.message.replaceAll("\\{player\\}", player.getName()).replaceAll("\\{amount\\}", Integer.toString(clicks));
				for (Player onlinePlayer : this.aac.getServer().getOnlinePlayers()) {
					if (!onlinePlayer.hasPermission(this.staffPermission)) continue;
					onlinePlayer.sendMessage(message);
				}

				Logger.getLogger().log(player.getName() + " reached " + clicks + " CPS, VL=" + vl);

				if (vl >= this.maxVl) {
					if (this.aac.getLagMeter().getCurrent() > this.minTPS) {
						this.clicks.remove(playerName);
						this.previousClicks.remove(playerName);

						if (this.executeBans && this.aac.getConfig().getBoolean("sql.connect")) {
							long time = System.currentTimeMillis() + this.unit.toMillis(this.duration);
							String formattedTime = TimeUtil.convert((time - System.currentTimeMillis()) / 1000L);

							this.aac._database.executeUpdate("INSERT INTO `bans`(`uuid`, `name`, `timeout`) VALUES('" + player.getUniqueId().toString() + "', '" + player.getName() + "', '" + time + "');");
							Logger.getLogger().log(player.getName() + " banned for Macroing/Auto Clicking.");

							player.kickPlayer(this.banMessage.replaceAll("\\{time\\}", formattedTime));
						}
						return;
					}

					this.clicks.clear();
					this.previousClicks.clear();
					this.vl.clear();
				} else this.vl.put(playerName, vl);

				this.previousClicks.put(playerName, vl - 1);
			}
		}
	}

	public void addClick(Player player) {
		int clicks = 1;
		if (this.clicks.containsKey(player.getName().toLowerCase())) {
			clicks += this.clicks.get(player.getName().toLowerCase());
		}

		this.clicks.put(player.getName().toLowerCase(), clicks);
	}

	public String getBanMessage() {
		return this.banMessage;
	}
}
