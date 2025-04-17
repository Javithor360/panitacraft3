package com.panita.panitacraft3.util;

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
}
