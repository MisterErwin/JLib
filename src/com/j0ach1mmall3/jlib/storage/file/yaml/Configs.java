package com.j0ach1mmall3.jlib.storage.file.yaml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

/**
 * @author j0ach1mmall3 (business.j0ach1mmall3@gmail.com)
 * @since Unknown
 * @deprecated Replaced by ConfigLoader
 */
@Deprecated
public final class Configs {
    public static FileConfiguration getConfig(String name, Plugin plugin) {
		File configfile = new File(plugin.getDataFolder(), name);
		return YamlConfiguration.loadConfiguration(configfile);
	}

    @SuppressWarnings("deprecation")
	public static void saveConfig(String name, Plugin plugin) {
		File configfile = new File(plugin.getDataFolder(), name);
        try {
	        getConfig(name, plugin).save(configfile);
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }
	}

	public static void saveDefaultConfig(String name, Plugin plugin) {
		File configfile = new File(plugin.getDataFolder(), name);
        if (!configfile.exists()) {
	         plugin.saveResource(name, false);
	    }
	}

    @SuppressWarnings("deprecation")
	public static void reloadConfig(String name, Plugin plugin) {
		File configfile = new File(plugin.getDataFolder(), name);
        FileConfiguration config = YamlConfiguration.loadConfiguration(configfile);
	 
	    InputStream stream = plugin.getResource(name);
	    if (stream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(stream);
	        config.setDefaults(defConfig);
	    }
	}
}