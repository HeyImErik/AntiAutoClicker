package net.heyimerik.aac.sql.sets;

import net.heyimerik.aac.sql.SafeResultSet;

public class StringValue implements Cloneable {
	private SafeResultSet set;
	private String value;

	public StringValue(String value, SafeResultSet set) {
		this.set = set;
		this.value = value;
	}

	public String value() {
		return this.value(false);
	}

	public String value(boolean close) {
		if (close) this.set.close();

		return this.value;
	}
}
