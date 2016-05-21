package net.heyimerik.aac;

import net.heyimerik.aac.listeners.EntityInteract;
import net.heyimerik.aac.listeners.EntityLogin;
import net.heyimerik.aac.sql.Database;
import net.heyimerik.aac.updater.Updater;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings({ "static-access" })
public class AntiAutoClicker extends JavaPlugin {
	private static AntiAutoClicker instance;
	private LagMeter lagMeter;
	private CPSTask cpsTask;

	public Database _database;
	public Updater _updater;

	public static AntiAutoClicker getAAC() {
		return instance;
	}

	public void onEnable() {
		this.instance = this;

		super.saveResource("config.yml", false);

		this.lagMeter = new LagMeter();
		this.cpsTask = new CPSTask();

		if (this.getConfig().getBoolean("sql.connect")) {
			this._database = new Database(this.getConfig().getString("sql.uri"), this.getConfig().getString("sql.database"), this.getConfig().getString("sql.username"), this.getConfig().getString("sql.password"));
			this._database.initialize(this.getConfig().getString("sql.driver"));
		}

		super.getServer().getPluginManager().registerEvents(new EntityInteract(), this);
		super.getServer().getPluginManager().registerEvents(new EntityLogin(), this);

		super.getServer().getScheduler().scheduleSyncRepeatingTask(this, this.cpsTask, 20L, 20L);
		super.getServer().getScheduler().scheduleSyncRepeatingTask(this, this.lagMeter, 20L, 20L);

		new Logger();

		this._updater = new Updater(this.getAAC(), "1.0");
		try {
			this._updater.checkLatest(this.getConfig().getBoolean("updater.update"));
		} catch (Exception e) {
			System.out.println("[AntiAutoClicker] Failed to update: " + e.getMessage());
		}

		System.out.println("[AntiAutoClicker] AntiAutoClicker v" + super.getDescription().getVersion() + " enabled.");
	}

	public CPSTask getCPSTask() {
		return this.cpsTask;
	}

	public LagMeter getLagMeter() {
		return this.lagMeter;
	}
}
