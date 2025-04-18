package com.panita.panitacraft3.util;

import com.panita.panitacraft3.Panitacraft;
import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.identifiers.CommandMeta;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Global {
    public static String RAW_PREFIX = "";
    public static Component PREFIX;
    public static double DIFFICULTY_MULTIPLIER = 1.0;
    public static final Map<String, CommandMeta> ROOT_COMMANDS = new HashMap<>();

    public static void load(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        RAW_PREFIX = plugin.getConfig().getString("prefix", "");
        PREFIX = Messenger.mini(RAW_PREFIX);
        DIFFICULTY_MULTIPLIER = plugin.getConfig().getDouble("difficulty.multiplier", 1.0);
    }

    public static void writeDifficultyMultiplier(double multiplier) {
        Panitacraft plugin = Panitacraft.getPlugin(Panitacraft.class);
        plugin.getConfig().set("difficulty.multiplier", multiplier);
        plugin.saveConfig();
    }

    public static double normalize(double value, double min, double max) {
        if (max == min) return 0.0;
        return Math.max(0.0, Math.min(1.0, (value - min) / (max - min)));
    }
}
