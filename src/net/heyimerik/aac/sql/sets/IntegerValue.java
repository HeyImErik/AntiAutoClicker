package net.heyimerik.aac.sql.sets;

import net.heyimerik.aac.sql.SafeResultSet;

public class IntegerValue implements Cloneable {
	private SafeResultSet set;
	private int value;

	public IntegerValue(int value, SafeResultSet set) {
		this.set = set;
		this.value = value;
	}

	public int value() {
		return this.value(false);
	}

	public int value(boolean close) {
		if (close) this.set.close();

		return this.value;
	}
}
