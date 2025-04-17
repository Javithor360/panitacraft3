package com.panita.panitacraft3.util.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Set;

public class ListenerRegistry {
    private final Plugin plugin;
    private final Configuration config;

    public ListenerRegistry(Plugin plugin, Configuration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void registerAll(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<? extends Listener>> listeners = reflections.getSubTypesOf(Listener.class);

        for (Class<? extends Listener> listenerClass : listeners) {
            try {
                Listener listener;

                // Si tiene constructor con Configuration, lo usamos
                Constructor<?>[] constructors = listenerClass.getConstructors();
                if (constructors.length > 0 && constructors[0].getParameterCount() == 1 &&
                        constructors[0].getParameterTypes()[0] == Configuration.class) {
                    listener = (Listener) constructors[0].newInstance(config);
                } else {
                    listener = listenerClass.getDeclaredConstructor().newInstance();
                }

                Bukkit.getPluginManager().registerEvents(listener, plugin);
                plugin.getLogger().info("[INFO] Registered listener: " + listenerClass.getSimpleName());
            } catch (Exception e) {
                plugin.getLogger().warning("[ERROR] Failed to register listener " + listenerClass.getSimpleName() + ": " + e.getMessage());
            }
        }
    }
}
