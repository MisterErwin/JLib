package com.j0ach1mmall3.jlib.minigameapi.arena;

import org.bukkit.Location;

/**
 * Created by j0ach1mmall3 on 19:13 4/09/2015 using IntelliJ IDEA.
 */
public final class Arena {
    private final String identifier;
    private final String name;
    private final ArenaBlockRestorer restorer;
    private final ArenaSelection selection;

    public Arena(String identifier, String name, Location l1, Location l2) {
        this.identifier = identifier;
        this.name = name;
        this.restorer = new ArenaBlockRestorer();
        this.selection = new ArenaSelection(l1, l2);
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return this.name;
    }

    public ArenaBlockRestorer getRestorer() {
        return this.restorer;
    }

    public ArenaSelection getSelection() {
        return this.selection;
    }
}
