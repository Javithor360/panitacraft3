package com.panita.panitacraft3.listeners;

import com.panita.panitacraft3.util.chat.Messenger;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class PlayerJoinQuitListener implements Listener {
    private final Configuration config;

    public PlayerJoinQuitListener(Configuration config) {
        this.config = config;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (!player.hasPlayedBefore()) {
            if (config.getBoolean("events.playerFirstJoin.enabled", true)) {
                String welcomeMessage = config.getString("events.playerFirstJoin.broadcastMessage");
                Messenger.broadcast(replacePlaceholders(welcomeMessage, playerName));
            }
        } else {
            if (config.getBoolean("events.playerJoin.enabled", true)) {
                String joinMessage = config.getString("events.playerJoin.broadcastMessage");
                Messenger.broadcast(replacePlaceholders(joinMessage, playerName));
            }

            if (!Objects.requireNonNull(config.getString("events.playerJoin.motdMessage")).isEmpty()) {
                String motd = config.getString("events.playerJoin.motdMessage");
                Messenger.send(player, replacePlaceholders(motd, playerName));
            }
        }

        event.joinMessage(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (config.getBoolean("events.playerQuit.enabled", true)) {
            String msg = config.getString("events.playerQuit.broadcastMessage");
            Messenger.broadcast(replacePlaceholders(msg, playerName));
        }

        event.quitMessage(null);
    }

    private String replacePlaceholders(String input, String playerName) {
        if (input == null) return "";
        return input.replace("%PLAYER%", playerName).replace("<player>", playerName);
    }
}
