package net.heyimerik.aac.sql.sets;

import net.heyimerik.aac.sql.SafeResultSet;

public class LongValue implements Cloneable {
	private SafeResultSet set;
	private long value;

	public LongValue(long value, SafeResultSet set) {
		this.set = set;
		this.value = value;
	}

	public long value() {
		return this.value(false);
	}

	public long value(boolean close) {
		if (close) this.set.close();

		return this.value;
	}
}
