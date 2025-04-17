package com.panita.panitacraft3.util;

import com.panita.panitacraft3.util.chat.Messenger;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

public class Global {
    public static String RAW_PREFIX = "";
    public static Component PREFIX;

    public static void load(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        RAW_PREFIX = plugin.getConfig().getString("prefix", "");
        PREFIX = Messenger.mini(RAW_PREFIX);
    }
}
