package com.panita.panitacraft3.difficulty.listeners;

import com.panita.panitacraft3.difficulty.DifficultyService;
import com.panita.panitacraft3.difficulty.modifiers.MobModifierPool;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawnListener implements Listener {
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        double difficulty = DifficultyService.getLocalDifficulty(entity.getLocation());

        MobModifierPool.applyModifiers(entity, difficulty);
    }
}
