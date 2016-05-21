package net.heyimerik.aac.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SuppressWarnings({ "static-access" })
public class Database {
	private static Database database;
	private Connection connection;
	private String username;
	private String password;
	private String uri;
	private String db;

	public static Database getDatabase() {
		return database;
	}

	public Database(String uri, String database, String username, String password) {
		this.uri = uri;
		this.db = database;
		this.username = username;
		this.password = password;

		this.database = this;
	}

	public boolean initialize(String driver) {
		try {
			this.connection = DriverManager.getConnection(driver + this.uri + "/" + this.db + "?autoReconnect=true", this.username, this.password);
			System.out.println("[AntiAutoClicker] Connected to database.");

			if (!this.tableExists("bans")) {
				System.out.println("[AntiAutoClicker] Creating table `bans`.");
				this.createTable("bans", "`id` BIGINT(30) NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(36) NOT NULL, `name` VARCHAR(16) NOT NULL, `timeout` BIGINT(30) NOT NULL, primary key (id)");
			}

			return true;
		} catch (Exception localException) {
			System.out.println("[AntiAutoClicker] Failed to connect to database: " + localException.getMessage());

			return false;
		}
	}

	public boolean tableExists(String name) {
		try {
			ResultSet tables = this.connection.getMetaData().getTables(null, null, name, null);
			boolean hasNext = tables.next();

			tables.close();

			return hasNext;
		} catch (Exception localException) {
			return false;
		}
	}

	public void createTable(String name, String tableStructure) {
		this.executeUpdate("CREATE TABLE `" + name + "`(" + tableStructure + ");").close();
	}

	public SQLResponse exists(String sql) {
		try {
			PreparedStatement statement = this.connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();

			return new SQLResponse(statement, set, set.next());
		} catch (Exception localException) {
			return new SQLResponse(null);
		}
	}

	public SQLResponse executeUpdate(String sql, Object... values) {
		try {
			PreparedStatement statement = this.connection.prepareStatement(sql);
			for (int index = 0; index < values.length; index++) {
				statement.setObject(index + 1, values[index]);
			}

			statement.executeUpdate();

			return new SQLResponse(statement);
		} catch (Exception localException) {
			return new SQLResponse(null);
		}
	}

	public SQLResponse executeQuery(String sql, Object... values) {
		try {
			PreparedStatement statement = this.connection.prepareStatement(sql);
			for (int index = 0; index < values.length; index++) {
				statement.setObject(index + 1, values[index]);
			}

			ResultSet set = statement.executeQuery();

			return new SQLResponse(statement, set);
		} catch (Exception localException) {
			return new SQLResponse(null);
		}
	}
}
