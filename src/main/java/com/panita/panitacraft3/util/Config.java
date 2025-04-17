package com.panita.panitacraft3.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    private static FileConfiguration config;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static void load(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
        Global.load(plugin);
    }

    public static String getString(String path, String def) {
        return config.getString(path, def);
    }

    public static Component getMini(String path) {
        String raw = config.getString(path, "");
        return miniMessage.deserialize(raw != null ? raw : "");
    }

    public static FileConfiguration raw() {
        return config;
    }
}
