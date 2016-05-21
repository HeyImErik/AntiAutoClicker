package net.heyimerik.aac.sql.sets;

import net.heyimerik.aac.sql.SafeResultSet;

public class ObjectValue implements Cloneable {
	private SafeResultSet set;
	private Object value;

	public ObjectValue(Object value, SafeResultSet set) {
		this.set = set;
		this.value = value;
	}

	public Object value() {
		return this.value(false);
	}

	public Object value(boolean close) {
		if (close) this.set.close();

		return this.value;
	}
}
