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
    public static final Map<String, CommandMeta> ROOT_COMMANDS = new HashMap<>();

    public static void load(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        RAW_PREFIX = plugin.getConfig().getString("prefix", "");
        PREFIX = Messenger.mini(RAW_PREFIX);
    }

    public static double normalize(double value, double min, double max) {
        if (max == min) return 0.0;
        return Math.max(0.0, Math.min(1.0, (value - min) / (max - min)));
    }

    public static double ticksToGameDays(long ticks) {
        return (double) ticks / 24000; // 24,000 ticks = 1 game day
    }

    public static double ticksToHours(long ticks) {
        return (double) ticks / 72000; // 72,000 ticks = 1 hour
    }

    public static double hoursToTicks(double hours) {
        return hours * 72000; // 1 hour = 72,000 ticks
    }

    public static double ticksToSeconds(long ticks) {
        return (double) ticks / 20; // 20 ticks = 1 second
    }

    public static double secondsToTicks(double seconds) {
        return seconds * 20; // 1 second = 20 ticks
    }

    public static double ticksToMinutes(long ticks) {
        return (double) ticks / 1200; // 1200 ticks = 1 minute
    }

    public static double minutesToTicks(double minutes) {
        return minutes * 1200; // 1 minute = 1200 ticks
    }
}
