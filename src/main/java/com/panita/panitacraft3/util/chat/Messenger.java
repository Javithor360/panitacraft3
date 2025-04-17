package com.panita.panitacraft3.util.chat;

import com.panita.panitacraft3.util.Global;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Messenger {
    private static final MiniMessage mini = MiniMessage.miniMessage();
    private static BukkitAudiences audiences;

    /**
     * Initializes the Messenger class with the BukkitAudiences instance.
     *
     * @param adventure The BukkitAudiences instance to use for sending messages.
     */
    public static void init(BukkitAudiences adventure) {
        audiences = adventure;
    }

    // ----> Basic Mini <----

    /**
     * Converts a raw message to a MiniMessage component.
     *
     * @param msg The raw message to convert.
     * @return The MiniMessage component.
     */
    public static Component mini(String msg) {
        if (msg == null) return Component.empty();
        String converted = LegacyToMiniConverter.convert(msg);
        return MiniMessage.miniMessage().deserialize(converted);
    }

    /**
     * Converts a raw message to a MiniMessage component with the plugin prefix.
     *
     * @param msg The raw message to convert.
     * @return The MiniMessage component with the plugin prefix.
     */
    public static Component miniPrefixed(String msg) {
        String prefixMini = LegacyToMiniConverter.convert(Global.RAW_PREFIX);
        String bodyMini = LegacyToMiniConverter.convert(msg);
        String combined = prefixMini + bodyMini;

        return MiniMessage.miniMessage().deserialize(combined);
    }

    // ----> Senders <----

    /**
     * Sends a message to a command sender.
     *
     * @param sender The command sender to send the message to.
     * @param msg The raw message to send.
     */
    public static void send(CommandSender sender, String msg) {
        audiences.sender(sender).sendMessage(mini(msg));
    }

    /**
     * Sends a message to a command sender with the plugin prefix.
     *
     * @param sender The command sender to send the message to.
     * @param msg The raw message to send.
     */
    public static void prefixedSend(CommandSender sender, String msg) {
        audiences.sender(sender).sendMessage(miniPrefixed(msg));
    }

    /**
     * Sends a message to a player.
     *
     * @param player The player to send the message to.
     * @param raw The raw message to send.
     */
    public static void send(Player player, String raw) {
        audiences.player(player).sendMessage(mini(raw));
    }

    /**
     * Sends a message to a player with the plugin prefix.
     *
     * @param player The player to send the message to.
     * @param raw The raw message to send.
     */
    public static void prefixedSend(Player player, String raw) {
        audiences.player(player).sendMessage(miniPrefixed(raw));
    }

    // ----> Broadcast <----
    /**
     * Broadcasts a message to all players.
     *
     * @param msg The raw message to send.
     */
    public static void broadcast(String msg) {
        Component message = mini(msg);
        audiences.all().sendMessage(message);
    }

    /**
     * Broadcasts a message to all players with the plugin prefix.
     *
     * @param msg The raw message to send.
     */
    public static void prefixedBroadcast(String msg) {
        Component message = miniPrefixed(msg);
        audiences.all().sendMessage(message);
    }

    // ---> Extra <----

    /**
     * Sends a message to the console if the sender is not a player.
     *
     * @param sender The command sender (should be a console).
     * @param msg The raw message to send.
     */
    public static void consoleSend(CommandSender sender, String msg) {
        if (!(sender instanceof Player)) {
            send(sender, msg);
        }
    }
}
