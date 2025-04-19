package com.panita.panitacraft3.difficulty.util;

import com.panita.panitacraft3.chat.commands.panitacraft.difficulty.DebugCommand;
import com.panita.panitacraft3.util.chat.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class DifficultyDebugUtil {
    private static final double DEBUG_RADIUS = 10.0;

    public static List<Player> getDebugPlayersNearby(Location location) {
        return Bukkit.getOnlinePlayers().stream()
                .filter(p -> DebugCommand.debugPlayers.containsKey(p.getUniqueId()))
                .filter(p -> p.getLocation().getWorld().equals(location.getWorld()))
                .filter(p -> p.getLocation().distanceSquared(location) <= DEBUG_RADIUS * DEBUG_RADIUS)
                .collect(Collectors.toList());
    }

    public static void sendDebugMessage(Location location, String message) {
        for (Player player : getDebugPlayersNearby(location)) {
            Messenger.prefixedSend(player, message);
        }
    }
}
