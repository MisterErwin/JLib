package com.j0ach1mmall3.jlib.storage.database.mysql;

import com.j0ach1mmall3.jlib.storage.StorageLoader;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author j0ach1mmall3 (business.j0ach1mmall3@gmail.com)
 * @since 5/11/15
 */
public abstract class MySQLLoader extends StorageLoader {
    protected final MySQL mySQL;

    /**
     * Constructs a new MySQLLoader, use this by extending the MySQLLoader
     * @param plugin The JavaPlugin associated with the MySQL Database
     * @param hostName The host name of the MySQL Server
     * @param port The port of the MySQL Server
     * @param database The name of the MySQL Database
     * @param user The user to use
     * @param password The password to use
     * @see MySQL
     */
    protected MySQLLoader(JavaPlugin plugin, String hostName, int port, String database, String user, String password) {
        super(new MySQL(plugin, hostName, port, database, user, password));
        this.mySQL = (MySQL) this.storage;
    }
}
