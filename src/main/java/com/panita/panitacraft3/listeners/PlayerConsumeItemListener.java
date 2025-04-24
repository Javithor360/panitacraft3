package com.panita.panitacraft3.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerConsumeItemListener implements Listener {
    public PlayerConsumeItemListener() {}

    /**
     * This method is called when a player consumes an item.
     * It triggers different effects based on the item consumed.
     **/
    @EventHandler
    public void onConsumeItem(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        switch (item.getType()) {
            case GLOW_BERRIES:
                PotionEffectType glowing = PotionEffectType.GLOWING;
                int effectDuration = 60 * 20; /* Convert the seconds to ticks */

                PotionEffect glowingEffect = new PotionEffect(glowing, effectDuration, 1);
                player.addPotionEffect(glowingEffect);
        }
    }
}
