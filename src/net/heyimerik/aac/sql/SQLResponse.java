package net.heyimerik.aac.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLResponse implements Cloneable {
	private PreparedStatement statement;
	private SafeResultSet set;
	private boolean exists;

	public SQLResponse(PreparedStatement statement) {
		this(statement, null);
	}

	public SQLResponse(PreparedStatement statement, ResultSet set) {
		this(statement, set, false);
	}

	public SQLResponse(PreparedStatement statement, ResultSet set, boolean exists) {
		this.statement = statement;
		this.set = new SafeResultSet(set);
		this.exists = exists;
	}

	public boolean exists(boolean close) {
		if (close) this.close();

		return this.exists;
	}

	public SafeResultSet getSet() {
		return this.set;
	}

	public void close() {
		try {
			if (this.set.getSet() != null) this.set.close();
			if (this.statement != null) this.statement.close();
		} catch (Exception localException) {
		}
	}
}
