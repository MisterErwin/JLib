package com.j0ach1mmall3.jlib.storage.database.mysql;

import com.j0ach1mmall3.jlib.methods.General;
import com.j0ach1mmall3.jlib.storage.database.SQLDatabase;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author j0ach1mmall3 (business.j0ach1mmall3@gmail.com)
 * @since Unknown
 */
public final class MySQL extends SQLDatabase {
    /**
     * Constructs a new MySQL instance, shouldn't be used externally, use {@link MySQLLoader} instead
     */
	MySQL(JavaPlugin plugin, String hostName, int port, String database, String user, String password) {
        super(plugin, hostName, port, database, user, password);
	}

    /**
     * Returns the Connection for the MySQL Database
     * @return The Connection
     * @see Connection
     */
    protected Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + hostName + ":" + port + "/" + this.database, this.user, this.password);
        } catch(SQLException e) {
            General.sendColoredMessage(plugin, "Failed to connect to the MySQL Database using following credentials:", ChatColor.RED);
            General.sendColoredMessage(plugin, "HostName: " + hostName, ChatColor.GOLD);
            General.sendColoredMessage(plugin, "Port: " + port, ChatColor.GOLD);
            General.sendColoredMessage(plugin, "Database: " + database, ChatColor.GOLD);
            General.sendColoredMessage(plugin, "User: " + user, ChatColor.GOLD);
            General.sendColoredMessage(plugin, "Password: =REDACTED=", ChatColor.GOLD);
            return null;
        }
    }
}