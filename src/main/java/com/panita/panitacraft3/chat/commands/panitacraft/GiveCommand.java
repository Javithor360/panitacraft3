package com.panita.panitacraft3.chat.commands.panitacraft;

import com.panita.panitacraft3.util.CommandUtils;
import com.panita.panitacraft3.util.chat.Messenger;
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
import java.util.stream.Collectors;

@SubCommandSpec(
        parent = "panitacraft",
        name = "give",
        description = "Gives an item to a player.",
        syntax = "/panitacraft give <player> <item>",
        permission = "panitacraft.admin.command.panitacraft.give"
)
public class GiveCommand implements AdvancedCommand, TabSuggestingCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!CommandUtils.checkArgsOrUsage(sender, args, 2, this.getClass())) return;

        String playerName = args[0];
        String itemName = args[1];
        Player target = Bukkit.getPlayer(playerName);

        if (target == null) {
            Messenger.prefixedSend(sender, "&cEl jugador &4'" + playerName + "' &cno está conectado.");
            return;
        }

        Material material = Material.matchMaterial(itemName);
        if (material == null) {
            Messenger.prefixedSend(sender, "&cEl material &4'" + itemName + "' &cno es válido.");
            return;
        }

        target.getInventory().addItem(new ItemStack(material));
        Messenger.prefixedSend(sender, "&aHas dado a &e" + playerName + " &aun &e" + itemName + "&a.");
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
                    .filter(Material::isItem)
                    .map(Material::name)
                    .filter(name -> name.toLowerCase().startsWith(current))
                    .limit(50)
                    .collect(Collectors.toList());
        });
    }
}