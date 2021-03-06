package com.j0ach1mmall3.jlib.logging;

import com.j0ach1mmall3.jlib.integration.gist.Gist;
import com.j0ach1mmall3.jlib.integration.gist.GistFile;
import com.j0ach1mmall3.jlib.integration.gist.GistFiles;
import com.j0ach1mmall3.jlib.integration.gist.GistUploader;
import com.j0ach1mmall3.jlib.storage.StorageAction;
import com.j0ach1mmall3.jlib.storage.database.CallbackHandler;
import com.j0ach1mmall3.jlib.storage.file.yaml.ConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author j0ach1mmall3 (business.j0ach1mmall3@gmail.com)
 * @since 4/01/16
 */
public final class JLogger {
    private final Plugin plugin;

    /**
     * Constructs a new JLogger instance
     * @param plugin The plugin associated with this JLogger
     */
    public JLogger(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Constructs a new JLogger instance
     */
    public JLogger() {
        this.plugin = Bukkit.getPluginManager().getPlugin("JLib");
    }

    /**
     * Logs a raw message to the Console
     * @param message The message to log
     */
    public void log(String message) {
        ConsoleCommandSender c = this.plugin.getServer().getConsoleSender();
        c.sendMessage('[' + this.plugin.getDescription().getName() + "] " + message);
    }

    /**
     * Logs a debug message
     */
    public void debug() {
        this.log(ChatColor.DARK_PURPLE + "DEBUG: " + new Exception().getStackTrace()[1].toString());
    }

    /**
     * Logs a debug message with data
     * @param data The data
     */
    public void debug(String data) {
        this.log(ChatColor.DARK_PURPLE + "DEBUG: " + new Exception().getStackTrace()[1].toString());
        this.log(ChatColor.DARK_PURPLE + "DEBUG: " + data);
    }

    /**
     * Logs a deprecation message
     */
    public void deprecation() {
        StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
        this.log(ChatColor.GOLD + "WARNING: I'm using deprecated method " + stackTraceElements[1] + " at " + stackTraceElements[2] + '!');
    }

    /**
     * Logs a warning if the method is called Sync
     */
    public void warnIfSync() {
        if(Bukkit.isPrimaryThread()) {
            StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
            this.log(ChatColor.GOLD + "WARNING: " + stackTraceElements[1] + " should be ran Async! (at " + stackTraceElements[2] + ")!");
        }
    }

    /**
     * Logs a warning if the method is called Async
     */
    public void warnIfAsync() {
        if(!Bukkit.isPrimaryThread()) {
            StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
            this.log(ChatColor.GOLD + "WARNING: " + stackTraceElements[1] + " should be ran Sync! (at " + stackTraceElements[2] + ")!");
        }
    }

    /**
     * Dumps and uploads Debug info to Github Gist
     * @param storageActions The StorageActions to add
     * @param configs The Configs to add to the dump
     * @param callbackHandler The CallbackHandler to call back to
     */
    @SuppressWarnings("deprecation")
    public void dumpDebug(final StorageAction[] storageActions, final ConfigLoader[] configs, final CallbackHandler<String> callbackHandler) {
        this.log(ChatColor.GREEN + "Dumping debug info...");
        Bukkit.getScheduler().scheduleAsyncDelayedTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                List<String> lines = new ArrayList<>();
                lines.add("---- " + JLogger.this.plugin.getName() + " v" + JLogger.this.plugin.getDescription().getVersion() + " Debug dump ----");
                lines.add("");
                lines.add("--- GENERAL ---");
                lines.add("BukkitVersion: " + Bukkit.getBukkitVersion());
                lines.add("Version: " + Bukkit.getVersion());
                lines.add("");
                lines.add("--- CONFIGS ---");
                for(ConfigLoader config : configs) {
                    lines.add("-- " + config.getCustomConfig().getFile().getName() + " --");
                    lines.add(JLogger.this.dumpConfig(config));
                    lines.add("");
                }
                lines.add("");
                lines.add("--- WORLDS ---");
                for(World world : Bukkit.getWorlds()) {
                    lines.add(world.getName() + ' ' + world.getWorldType().getName() + ' ' + world.getEnvironment().name());
                }
                lines.add("");
                lines.add("--- PLUGINS ---");
                for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                    if(!JLogger.this.plugin.getName().equals(plugin.getName())) lines.add(plugin.getName() + " v" + plugin.getDescription().getVersion() + " (" + plugin.getDescription().getMain() + ')');
                }
                lines.add("");
                lines.add("--- STORAGE ACTIONS ---");
                for(StorageAction storageAction : storageActions) {
                    lines.add(storageAction.toString());
                }
                StringBuilder payload = new StringBuilder();
                for(String line : lines) {
                    payload.append(line);
                    payload.append('\n');
                }
                new GistUploader(
                        new Gist(JLogger.this.plugin.getName() + " v" + JLogger.this.plugin.getDescription().getVersion() + " Debug dump",
                                false,
                                new GistFiles(new GistFile(payload.toString())))
                ).upload(JLogger.this.plugin, callbackHandler);
            }
        }, 0L);
    }

    /**
     * Dumps all the lines in a Config File to Gist
     * Be carefull with dumping passwords and such!
     * @param config The Config File
     * @return The uploaded config link
     */
    @SuppressWarnings("deprecation")
    public String dumpConfig(ConfigLoader config) {
        this.warnIfSync();
        StringBuilder payload = new StringBuilder();
        File f = config.getCustomConfig().getFile();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                payload.append(line);
                payload.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GistUploader(
                new Gist(JLogger.this.plugin.getName() + " v" + JLogger.this.plugin.getDescription().getVersion() + " Config dump (" + config.getCustomConfig().getName() + ')',
                        false,
                        new GistFiles(new GistFile(payload.toString())))
        ).upload();
    }
}
