package net.heyimerik.aac;

import java.io.File;
import java.io.FileWriter;

import net.heyimerik.aac.util.DateUtil;

@SuppressWarnings({ "static-access" })
public class Logger {
	private static Logger instance;
	private String hourFormat;
	private String dayFormat;
	private String timeZone;
	private File txtFile;
	private int lines;

	public static Logger getLogger() {
		return instance;
	}

	public Logger() {
		this.instance = this;
		this.lines = 0;

		AntiAutoClicker aac = AntiAutoClicker.getAAC();
		
		new File(aac.getDataFolder(), "logs/").mkdirs();
		this.txtFile = new File(aac.getDataFolder(), "logs/log" + DateUtil.getFileDate(true, null) + ".txt");

		this.timeZone = aac.getConfig().getString("logger.time-zone");
		this.dayFormat = aac.getConfig().getString("logger.day-format");
		this.hourFormat = aac.getConfig().getString("logger.hour-format");
	}

	public int getWrittenLines() {
		return this.lines;
	}

	public void log(String msg) {
		try {
			if (!this.txtFile.exists()) {
				this.txtFile.createNewFile();
			}
			String filePath = this.txtFile.getPath();
			FileWriter fileWriter = new FileWriter(filePath, true);
			fileWriter.write(this.now() + msg + "\n");
			fileWriter.close();

			this.lines += 1;
		} catch (Exception localException) {
		}
	}

	private String now() {
		return "[" + DateUtil.getDate(true, this.timeZone, this.dayFormat, this.hourFormat) + "] ";
	}
}
