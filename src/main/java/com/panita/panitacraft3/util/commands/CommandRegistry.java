package com.panita.panitacraft3.util.commands;

import com.panita.panitacraft3.util.commands.dynamic.DynamicBukkitCommand;
import com.panita.panitacraft3.util.commands.identifiers.CommandMeta;
import com.panita.panitacraft3.util.commands.identifiers.CommandSpec;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.*;

/**
 * CommandRegistry is a utility class for registering commands in a Bukkit plugin.
 * It uses reflection to scan for classes annotated with @CommandSpec and @SubCommandSpec,
 * and registers them with the Bukkit command map.
 */
public class CommandRegistry {
    private final JavaPlugin plugin;
    // Map to hold subcommands for each command
    private final Map<String, Map<String, CommandMeta>> subCommands = new HashMap<>();

    /**
     * Constructor for CommandRegistry.
     *
     * @param plugin The JavaPlugin instance to register commands with.
     */
    public CommandRegistry(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers all commands and subcommands found within the specified base package.
     * It scans for classes annotated with @CommandSpec (main commands) and @SubCommandSpec (subcommands).
     *
     * @param basePackage The base package where commands and subcommands are located.
     */
    public void registerAll(String basePackage) {
        // Use Reflections to scan for classes in the specified package
        Reflections reflections = new Reflections(basePackage);

        // Get all classes annotated with @CommandSpec (main commands)
        Set<Class<?>> rootCommandClasses = reflections.getTypesAnnotatedWith(CommandSpec.class);
        // Get all classes annotated with @SubCommandSpec (subcommands)
        Set<Class<?>> subCommandClasses = reflections.getTypesAnnotatedWith(SubCommandSpec.class);

        // First, register all subcommands grouped by their parent command
        for (Class<?> clazz : subCommandClasses) {
            try {
                // Check if the class is annotated with @SubCommandSpec
                SubCommandSpec spec = clazz.getAnnotation(SubCommandSpec.class);
                // Check if the class implements AdvancedCommand
                AdvancedCommand cmd = (AdvancedCommand) clazz.getDeclaredConstructor().newInstance();

                // Register the subcommand in the map, using the parent command name as the key
                subCommands.computeIfAbsent(spec.parent(), k -> new HashMap<>())
                        .put(spec.name().toLowerCase(), new CommandMeta(cmd, spec.permission(), spec.playerOnly(), spec.syntax(), spec.description()));
            } catch (Exception e) {
                plugin.getLogger().warning("[ERROR] Error in subcommand: " + e.getMessage());
            }
        }

        // Then register the main commands
        for (Class<?> clazz : rootCommandClasses) {
            try {
                // Check if the class is annotated with @CommandSpec
                CommandSpec spec = clazz.getAnnotation(CommandSpec.class);
                // Check if the class implements AdvancedCommand
                AdvancedCommand cmd = (AdvancedCommand) clazz.getDeclaredConstructor().newInstance();
                // Instance to hold the command metadata
                CommandMeta meta = new CommandMeta(cmd, spec.permission(), spec.playerOnly(), spec.syntax(), spec.description());

                // Check if the command has subcommands
                boolean hasSubs = subCommands.containsKey(spec.name());

                if (hasSubs) {
                    // Register the command with its subcommands
                    registerBukkitCommand(spec.name(), meta, subCommands.get(spec.name()), spec.aliases());
                    plugin.getLogger().info("[INFO] Registered root command with subcommands: /" + spec.name());
                } else {
                    // Register the command without subcommands
                    registerBukkitCommand(spec.name(), meta, null, spec.aliases());
                    plugin.getLogger().info("[INFO] Registered standalone command: /" + spec.name());
                }
            } catch (Exception e) {
                plugin.getLogger().warning("[ERROR] Error while registering command: " + e.getMessage());
            }
        }
    }

    /**
     * Registers a command with Bukkit, including its aliases and subcommands.
     *
     * @param name The name of the command.
     * @param meta The CommandMeta instance containing command metadata.
     * @param subs A map of subcommands for this command, or null if there are none.
     * @param aliases An array of aliases for the command.
     */
    private void registerBukkitCommand(String name, CommandMeta meta, Map<String, CommandMeta> subs, String[] aliases) {
        try {
            // Define the command map field in the Bukkit server class
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true); // Make the field accessible
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer()); // Get the command map instance

            // Create a new dynamic command with its metadata and subcommands (if any)
            DynamicBukkitCommand dynamicCommand = new DynamicBukkitCommand(name, meta, subs != null ? subs : new HashMap<>());
            dynamicCommand.setAliases(Arrays.asList(aliases)); // Set the command aliases
            commandMap.register(plugin.getName(), dynamicCommand); // Register the command with the command map
            plugin.getLogger().info("[INFO] Registered command /" + name);
        } catch (Exception e) {
            plugin.getLogger().warning("[ERROR] Couldn't register command /" + name + ": " + e.getMessage());
        }
    }
}
