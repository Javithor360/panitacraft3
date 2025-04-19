package com.panita.panitacraft3.chat.commands.panitacraft.difficulty;

import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@SubCommandSpec(
        parent = "panitacraft difficulty",
        name = "debug",
        description = "Execute a debug command for difficulty.",
        syntax = "/panitacraft difficulty debug",
        permission = "panitacraft.admin.command.panitacraft.difficulty.debug"
)
public class DebugCommand implements AdvancedCommand {
    public static final ConcurrentHashMap<UUID, Long> debugPlayers = new ConcurrentHashMap<>();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            Messenger.consoleSend(sender, "<red>Este comando solo puede ser usado por jugadores.</red>");
            return;
        }

        UUID uuid = player.getUniqueId();
        if (debugPlayers.containsKey(uuid)) {
            debugPlayers.remove(uuid);
            Messenger.prefixedSend(player, "<gray>🔕 Modo debug de dificultad desactivado.</gray>");
        } else {
            debugPlayers.put(uuid, System.currentTimeMillis());
            Messenger.prefixedSend(player, "<green>🧪 Modo debug activado.</green> <gray>Se mostrarán los mobs modificados cerca de ti (5 bloques).</gray>");
        }
    }
}
