package net.heyimerik.aac.updater;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.heyimerik.aac.util.NumberUtil;

import org.bukkit.plugin.java.JavaPlugin;

public class Updater {
	private final String url = "http://heyimerik.net/downloads/aac/current_version.txt";
	private final String utdJar = "http://heyimerik.net/downloads/aac/latest.jar";
	private String localVersion;
	private String webVersion;

	public Updater(JavaPlugin plugin, String localVersion) {
		this.localVersion = localVersion;
	}

	public void checkLatest(boolean download) throws Exception {
		URL url = new URL(this.url);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		String line = null;
		while ((line = reader.readLine()) != null) {
			this.webVersion = line;
		}

		reader.close();
		connection.disconnect();

		if (download) {
			String webMajor = this.webVersion.split("\\.")[0];
			String webMinor = this.webVersion.split("\\.")[1];
			String localMajor = this.localVersion.split("\\.")[0];
			String localMinor = this.localVersion.split("\\.")[1];

			if (NumberUtil.getInt(webMajor) > NumberUtil.getInt(localMajor) || NumberUtil.getInt(webMinor) > NumberUtil.getInt(localMinor)) {
				File out = new File("plugins/AntiAutoClicker.jar");

				url = new URL(this.utdJar);
				long fileSize = url.openConnection().getContentLength();
				BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
				FileOutputStream output = new FileOutputStream(out);

				byte[] data = new byte[1024];
				int count;
				long downloaded = 0;

				System.out.println("[AntiAutoClicker] Starting update downloading.");

				while ((count = inputStream.read(data, 0, 1024)) != -1) {
					downloaded += count;

					System.out.println("[AntiAutoClicker] Downloaded " + downloaded + "/" + fileSize + " bytes. (" + NumberUtil.format(downloaded * 100.0D / fileSize) + "%)");
					output.write(data, 0, count);
				}

				System.out.println("[AntiAutoClicker] Downloaded version " + this.webVersion);
				System.out.println("[AnitAutoClicker] Reload or restart to update.");

				output.close();
				inputStream.close();
			}
		}
	}
}
