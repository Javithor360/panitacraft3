package com.panita.panitacraft3;

import com.panita.panitacraft3.util.commands.CommandRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class Panitacraft extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Panitacraft is starting up!");

        // Register commands
        new CommandRegistry(this).registerAll("com.panita.panitacraft3.chat.commands");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Panitacraft is shutting down!");
    }
}
