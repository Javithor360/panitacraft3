package com.panita.panitacraft3;

import com.panita.panitacraft3.difficulty.DifficultyUpdateCron;
import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import com.panita.panitacraft3.util.Config;
import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.CommandRegistry;
import com.panita.panitacraft3.util.listeners.ListenerRegistry;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public final class Panitacraft extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Panitacraft is starting up!");

        // Register commands
        new CommandRegistry(this).registerAll("com.panita.panitacraft3.chat.commands");

        // Initialize MiniMessage and BukkitAudiences
        BukkitAudiences adventure = BukkitAudiences.create(this);
        Messenger.init(adventure);

        new ListenerRegistry(this, getConfig()).registerAll("com.panita.panitacraft3.listeners");
        // new ListenerRegistry(this, getConfig()).registerAll("com.panita.panitacraft3.difficulty.listeners");

        // Load configuration
        saveDefaultConfig();
        Config.load(this);

        //
        DifficultyConfig.load(getConfig());
        DifficultyUpdateCron.start(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Panitacraft is shutting down!");
    }
}
