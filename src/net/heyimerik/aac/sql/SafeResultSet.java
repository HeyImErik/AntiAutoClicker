package net.heyimerik.aac.sql;

import java.sql.ResultSet;

import net.heyimerik.aac.sql.sets.IntegerValue;
import net.heyimerik.aac.sql.sets.LongValue;
import net.heyimerik.aac.sql.sets.ObjectValue;
import net.heyimerik.aac.sql.sets.StringValue;

public class SafeResultSet implements Cloneable {
	private ResultSet set;

	public SafeResultSet(ResultSet set) {
		this.set = set;
	}

	public ResultSet getSet() {
		return this.set;
	}

	public String getString(int column) {
		try {
			return this.set.getString(column);
		} catch (Exception localException) {
			localException.printStackTrace();
			return null;
		}
	}

	public String getString(String column) {
		try {
			return this.set.getString(column);
		} catch (Exception localException) {
			localException.printStackTrace();
			return null;
		}
	}

	public long getLong(String column) {
		try {
			return this.set.getLong(column);
		} catch (Exception localException) {
			localException.printStackTrace();
			return 0L;
		}
	}

	public int getInt(int column) {
		try {
			return this.set.getInt(column);
		} catch (Exception localException) {
			localException.printStackTrace();
			return 0;
		}
	}

	public int getInt(String column) {
		try {
			return this.set.getInt(column);
		} catch (Exception localException) {
			localException.printStackTrace();
			return 0;
		}
	}

	public double getDouble(int column) {
		try {
			return this.set.getDouble(column);
		} catch (Exception localException) {
			localException.printStackTrace();
			return 0;
		}
	}

	public double getDouble(String column) {
		try {
			return this.set.getDouble(column);
		} catch (Exception localException) {
			localException.printStackTrace();
			return 0;
		}
	}

	public Object getObject(String column) {
		try {
			return this.set.getObject(column);
		} catch (Exception localException) {
			localException.printStackTrace();
			return null;
		}
	}

	public void close() {
		try {
			this.set.close();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public IntegerValue nextInt(String column) {
		this.next();

		return new IntegerValue(this.getInt(column), this);
	}

	public StringValue nextString(String column) {
		this.next();

		return new StringValue(this.getString(column), this);
	}

	public ObjectValue nextObject(String column) {
		this.next();

		return new ObjectValue(this.getObject(column), this);
	}

	public LongValue nextLong(String column) {
		this.next();

		return new LongValue(this.getLong(column), this);
	}

	public boolean next() {
		try {
			return this.set.next();
		} catch (Exception localException) {
			localException.printStackTrace();
			return false;
		}
	}
}
