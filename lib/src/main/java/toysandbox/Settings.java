package toysandbox;

import java.util.Map;

public class Settings {
	private final static Map<String, Object> settings = Map.ofEntries(
		Map.entry("request_per_second", Long.valueOf(Settings.getFromJVMOrDefault("request.per.second", "200"))),
		Map.entry("normal_request_base_penalty_milli", 0L),
		Map.entry("crimimal_request_base_penalty_milli", 5000L)
	);

	public static Object getSetting(String name) {
		assert Settings.settings.containsKey(name) : "invalid setting name: " + name;
		return Settings.settings.get(name);
	}

	public static String getFromJVMOrDefault(String key, String defaultSetting) {
		String setting = System.getProperty(key);
		if (setting == null) {
			setting = defaultSetting;
		}
		return setting;
	}
}
