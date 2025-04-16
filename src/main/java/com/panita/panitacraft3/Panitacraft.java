package com.panita.panitacraft3;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Panitacraft extends JavaPlugin {

    private final Logger logger = getLogger();

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("Panitacraft is starting up!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Panitacraft is shutting down!");
    }
}
