package com.panita.panitacraft3.chat.commands.panitacraft;

import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.dynamic.TabSuggestingCommand;
import com.panita.panitacraft3.util.commands.identifiers.CommandMeta;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SubCommandSpec(
        parent = "panitacraft",
        name = "give",
        description = "Gives an item to a player.",
        syntax = "/panitacraft give <player> <item>"
)
public class PanitacraftGiveCommand implements AdvancedCommand, TabSuggestingCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Uso correcto: /panitacraft give <player> <item>");
            return;
        }

        String playerName = args[0];
        String itemName = args[1];
        Player target = Bukkit.getPlayer(playerName);

        if (target == null) {
            sender.sendMessage("❌ El jugador '" + playerName + "' no está conectado.");
            return;
        }

        Material material = Material.matchMaterial(itemName);
        if (material == null) {
            sender.sendMessage("❌ El material '" + itemName + "' no existe.");
            return;
        }

        target.getInventory().addItem(new ItemStack(material));
        sender.sendMessage("✅ Le diste " + material + " a " + target.getName());
    }

    @Override
    public void applySuggestions(CommandMeta meta) {
        meta.setArgumentSuggestion(0, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(current))
                    .collect(Collectors.toList());
        });

        meta.setArgumentSuggestion(1, context -> {
            String current = context.getCurrentArg().toLowerCase();
            return Arrays.stream(Material.values())
                    .map(Material::name)
                    .filter(name -> name.toLowerCase().startsWith(current))
                    .limit(20)
                    .collect(Collectors.toList());
        });
    }
}