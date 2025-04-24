package com.panita.panitacraft3.listeners;

import com.panita.panitacraft3.util.chat.Messenger;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

public class PlayerResurrectListener implements Listener {
    private final Configuration config;

    public PlayerResurrectListener(Configuration config) {
        this.config = config;
    }

    /**
     * This method is called when an entity is resurrected and has a totem to be used.
     * It checks if the entity is a player and if the resurrection event is enabled in the config.
     * If so, it broadcasts a message to all players.
     **/
    @EventHandler
    public void onEntityResurrect(EntityResurrectEvent event){
        if(!event.isCancelled()){
            LivingEntity entity = event.getEntity();

            if (entity instanceof Player player) {
                String playerName = player.getName();

                if (config.getBoolean("events.entityResurrect.enabled", true)) {
                    String resurrectMessage = config.getString("events.playerResurrect.broadcastMessage");
                    Messenger.prefixedBroadcast(replacePlaceholders(resurrectMessage, playerName));
                }
            }
        }
    }

    private String replacePlaceholders(String input, String playerName) {
        if (input == null) return "";
        return input.replace("%PLAYER%", playerName).replace("<player>", playerName);
    }
}
