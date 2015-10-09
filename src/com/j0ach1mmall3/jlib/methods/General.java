package com.j0ach1mmall3.jlib.methods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class General {
	public static void sendColoredMessage(JavaPlugin plugin, String message, ChatColor color){
		ConsoleCommandSender c = plugin.getServer().getConsoleSender();
		c.sendMessage("[" + plugin.getDescription().getName() + "] " + color + message);
	}

    public static void sendColoredMessage(JavaPlugin plugin, String message) {
        sendColoredMessage(plugin, message, ChatColor.RESET);
    }
	
	public static void playSound(Player p, org.bukkit.Sound s, Location l){
		p.playSound(l, s, 0.5f, 1);
	}
	
	public static void broadcastSound(org.bukkit.Sound s, Location l){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.playSound(l, s, 0.5f, 1);
		}
	}
	
	public static void playSound(Player p, org.bukkit.Sound s){
		playSound(p, s, p.getLocation());
	}
	
	public static void broadcastSound(org.bukkit.Sound s){
		for(Player p : Bukkit.getOnlinePlayers()){
			broadcastSound(s, p.getLocation());
		}
	}
	
	public static void playNote(Player p, Location l, Instrument i, Tone t){
		p.playNote(l, i, Note.natural(1, t));
	}
	
	public static void broadcastNote(Location l, Instrument i, Tone t){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.playNote(l, i, Note.natural(1, t));
		}
	}
	
	public static void playNote(Player p, Instrument i, Tone t){
		p.playNote(p.getLocation(), i, Note.natural(1, t));
	}
	
	public static void broadcastNote(Instrument i, Tone t){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.playNote(p.getLocation(), i, Note.natural(1, t));
		}
	}
}
