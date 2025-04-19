package com.panita.panitacraft3.chat.commands.panitacraft.difficulty;

import com.panita.panitacraft3.difficulty.DifficultyService;
import com.panita.panitacraft3.difficulty.calculators.EquipmentScoreCalculator;
import com.panita.panitacraft3.difficulty.calculators.IndividualDifficultyCalculator;
import com.panita.panitacraft3.difficulty.calculators.ChronologicDifficultyCalculator;
import com.panita.panitacraft3.difficulty.util.BiomeDanger;
import com.panita.panitacraft3.difficulty.util.DifficultyConfig;
import com.panita.panitacraft3.util.Global;
import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

@SubCommandSpec(
        parent = "panitacraft",
        name = "difficulty trace",
        description = "Displays a detailed report of the current difficulty settings.",
        syntax = "/panitacraft difficulty trace",
        permission = "panitacraft.admin.command.panitacraft.difficulty.trace"
)
public class TraceCommand implements AdvancedCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            Messenger.prefixedSend(sender, "<red>Este comando solo puede ser ejecutado por jugadores.</red>");
            return;
        }

        if (!DifficultyConfig.isEnabled()) {
            Messenger.send(player, "<gray>========[ <gradient:#9E73DE:#5900DD>DIFICULTAD - TRAZADO DETALLADO</gradient> ]========</gray>");
            Messenger.send(player, "<red>❌ El sistema de dificultad dinámica está actualmente <bold>desactivado</bold>.</red>");
            Messenger.send(player, "<red>Actívalo en el archivo <white>config.yml</white> para comenzar a escalar dinámicamente.</red>");
            Messenger.send(player, "<gray>=====================================================</gray>");
            return;
        }

        Messenger.send(player, "<gray>========[ <gradient:#9E73DE:#5900DD>DIFICULTAD - TRAZADO DETALLADO</gradient> ]========</gray>");

        // ========== CONFIG GLOBAL ==========
        Messenger.send(player, "<gold>⚙ CONFIGURACIÓN GLOBAL</gold>");
        Messenger.send(player, " <gray>Radio de dificultad grupal:</gray> <aqua>" + DifficultyConfig.getGroupRadius() + " bloques</aqua>");
        Messenger.send(player, " <gray>Máxima dificultad escalable:</gray> <aqua>" + DifficultyConfig.getMaxDifficultyScale() + "</aqua>");
        Messenger.send(player, " <gray>Multiplicador manual:</gray> <aqua>" + DifficultyConfig.getManualMultiplier() + "x</aqua>");
        Messenger.send(player, "");

        // ========== CHRONOLOGIC ==========
        double dg = DifficultyService.getChronologicDifficulty();
        int days = (int) (Bukkit.getWorlds().getFirst().getFullTime() / 24000);
        int online = Bukkit.getOnlinePlayers().size();
        int maxPlayers = Bukkit.getMaxPlayers();
        int totalChunks = Bukkit.getWorlds().stream().mapToInt(w -> w.getLoadedChunks().length).sum();

        double normDays = Global.normalize(days, DifficultyConfig.getChronoWorldDaysMin(), DifficultyConfig.getChronoWorldDaysMax());
        double normOnline = Global.normalize(online, DifficultyConfig.getChronoOnlinePlayersMin(), DifficultyConfig.getChronoOnlinePlayersMax(maxPlayers));
        double normChunks = Global.normalize(totalChunks, DifficultyConfig.getChronoChunksMin(), DifficultyConfig.getChronoChunksMax());
        double eventPenalty = ChronologicDifficultyCalculator.getGlobalEventPenalty();

        Messenger.send(player, "<gold>🌍 DIFICULTAD CRONOLÓGICA</gold>");
        Messenger.send(player, " <gray>Fórmula:</gray> (días*" + fmt(DifficultyConfig.getChronoWorldDaysWeight()) +
                " + jugadores*" + fmt(DifficultyConfig.getChronoOnlinePlayersWeight()) +
                " + chunks*" + fmt(DifficultyConfig.getChronoChunksWeight()) +
                " + eventos*" + fmt(DifficultyConfig.getChronoEventsWeight()) + ") * max");

        Messenger.send(player, " <yellow>Días:</yellow> <aqua>" + days + "</aqua> " + detailedNorm(normDays, DifficultyConfig.getChronoWorldDaysMin(), DifficultyConfig.getChronoWorldDaysMax(), DifficultyConfig.getChronoWorldDaysWeight()));
        Messenger.send(player, " <yellow>Jugadores:</yellow> <aqua>" + online + "/" + maxPlayers + "</aqua> " + detailedNorm(normOnline, DifficultyConfig.getChronoOnlinePlayersMin(), DifficultyConfig.getChronoOnlinePlayersMax(maxPlayers), DifficultyConfig.getChronoOnlinePlayersWeight()));
        Messenger.send(player, " <yellow>Chunks cargados:</yellow> <aqua>" + totalChunks + "</aqua> " + detailedNorm(normChunks, DifficultyConfig.getChronoChunksMin(), DifficultyConfig.getChronoChunksMax(), DifficultyConfig.getChronoChunksWeight()));
        Messenger.send(player, " <yellow>Penalización eventos:</yellow> <aqua>" + fmt(eventPenalty) + "</aqua>");
        Messenger.send(player, " <green>Dificultad Cronológica:</green> <white>" + fmt(dg) + "</white>");
        Messenger.send(player, "");

        // ========== INDIVIDUAL ==========
        double timeDeath = Global.ticksToHours(player.getStatistic(Statistic.TIME_SINCE_DEATH));
        double normDeathTime = Global.normalize(timeDeath, DifficultyConfig.getIndividualTimeSinceDeathMin(), DifficultyConfig.getIndividualTimeSinceDeathMax());

        double timeRest = Global.ticksToHours(player.getStatistic(Statistic.TIME_SINCE_REST));
        double normRest = Global.normalize(timeRest, DifficultyConfig.getIndividualTimeSinceRestMin(), DifficultyConfig.getIndividualTimeSinceRestMax());

        double playHours = Global.ticksToHours(player.getStatistic(Statistic.PLAY_ONE_MINUTE));
        double normPlay = Global.normalize(playHours, DifficultyConfig.getIndividualPlaytimeMin(), DifficultyConfig.getIndividualPlaytimeMax());

        int mobsKilled = player.getStatistic(Statistic.MOB_KILLS);
        double normMobs = Global.normalize(mobsKilled, DifficultyConfig.getIndividualMobsKilledMin(), DifficultyConfig.getIndividualMobsKilledMax());

        int xp = player.getLevel();
        double normXP = Global.normalize(xp, DifficultyConfig.getIndividualXpMin(), DifficultyConfig.getIndividualXpMax());

        int deaths = player.getStatistic(Statistic.DEATHS);
        double normDeaths = Global.normalize(deaths, DifficultyConfig.getIndividualDeathCountMin(), DifficultyConfig.getIndividualDeathCountMax());

        int equipScore = EquipmentScoreCalculator.calculate(player);
        double normEquip = Global.normalize(equipScore, DifficultyConfig.getIndividualEquipmentMin(), DifficultyConfig.getIndividualEquipmentMax());

        double biome = BiomeDanger.getDangerLevel(player.getLocation().getBlock().getBiome());
        double dimMult = IndividualDifficultyCalculator.getDimensionMultiplier(player);
        double di = DifficultyService.getIndividualDifficulty(player);

        Messenger.send(player, "<gold>👤 DIFICULTAD INDIVIDUAL</gold>");
        Messenger.send(player, " <gray>Fórmula:</gray> [(factores normalizados * pesos)] * (biome * dimension) * max");

        Messenger.send(player, " 💀 Última muerte: <aqua>" + fmt(timeDeath) + "h</aqua> " + detailedNorm(normDeathTime, DifficultyConfig.getIndividualTimeSinceDeathMin(), DifficultyConfig.getIndividualTimeSinceDeathMax(), DifficultyConfig.getIndividualTimeSinceDeathWeight()));
        Messenger.send(player, " 🛏 Último descanso: <aqua>" + fmt(timeRest) + "h</aqua> " + detailedNorm(normRest, DifficultyConfig.getIndividualTimeSinceRestMin(), DifficultyConfig.getIndividualTimeSinceRestMax(), DifficultyConfig.getIndividualTimeSinceRestWeight()));
        Messenger.send(player, " ⏱ Tiempo jugado: <aqua>" + fmt(playHours) + "h</aqua> " + detailedNorm(normPlay, DifficultyConfig.getIndividualPlaytimeMin(), DifficultyConfig.getIndividualPlaytimeMax(), DifficultyConfig.getIndividualPlaytimeWeight()));
        Messenger.send(player, " ☠ Mobs asesinados: <aqua>" + mobsKilled + "</aqua> " + detailedNorm(normMobs, DifficultyConfig.getIndividualMobsKilledMin(), DifficultyConfig.getIndividualMobsKilledMax(), DifficultyConfig.getIndividualMobsKilledWeight()));
        Messenger.send(player, " 🧪 XP nivel: <aqua>" + xp + "</aqua> " + detailedNorm(normXP, DifficultyConfig.getIndividualXpMin(), DifficultyConfig.getIndividualXpMax(), DifficultyConfig.getIndividualXpWeight()));
        Messenger.send(player, " 💀 Muertes totales: <aqua>" + deaths + "</aqua> " + detailedNorm(normDeaths, DifficultyConfig.getIndividualDeathCountMin(), DifficultyConfig.getIndividualDeathCountMax(), DifficultyConfig.getIndividualDeathCountWeight()));
        Messenger.send(player, " 🛡 Equipo Score: <aqua>" + equipScore + "</aqua> " + detailedNorm(normEquip, DifficultyConfig.getIndividualEquipmentMin(), DifficultyConfig.getIndividualEquipmentMax(), DifficultyConfig.getIndividualEquipmentWeight()));
        Messenger.send(player, " 🧭 Multiplicador de entorno: <aqua>" + fmt(biome * dimMult) + "</aqua> (Bioma: <aqua>" + fmt(biome) + "</aqua> | Dimensión: <aqua>" + fmt(dimMult) + "</aqua>)");
        Messenger.send(player, " <green>Dificultad Individual:</green> <white>" + fmt(di) + "</white>");
        Messenger.send(player, "");

        // ========== GROUP ==========
        Collection<Player> groupPlayers = player.getWorld().getNearbyPlayers(player.getLocation(), DifficultyConfig.getGroupRadius());
        StringBuilder formulaBuilder = new StringBuilder();
        double totalGroupDI = 0;
        for (Player nearby : groupPlayers) {
            double nearbyDI = DifficultyService.getIndividualDifficulty(nearby);
            totalGroupDI += nearbyDI;
            formulaBuilder.append(fmt(nearbyDI)).append(" (").append(nearby.getName()).append(") + ");
        }

        double dgP = groupPlayers.isEmpty() ? 0 : totalGroupDI / groupPlayers.size();
        String groupFormula = formulaBuilder.length() > 3 ? formulaBuilder.substring(0, formulaBuilder.length() - 3) : "<italic>Ninguno</italic>";

        Messenger.send(player, "<gold>👥 DIFICULTAD GRUPAL</gold>");
        Messenger.send(player, " <gray>Jugadores en radio:</gray> <aqua>" + groupPlayers.size() + "</aqua>");
        Messenger.send(player, " <gray>Fórmula:</gray> " + groupFormula + " <gray>/</gray> <white>" + groupPlayers.size() + "</white>");
        Messenger.send(player, " <green>Dificultad Grupal:</green> <white>" + fmt(dgP) + "</white>");
        Messenger.send(player, "");

        // ========== AUTOGENERATED ==========
        double autoGenerated = DifficultyService.getAutoGeneratedDifficulty(player.getLocation());
        Messenger.send(player, "<gold>🧠 DIFICULTAD AUTOGENERADA</gold>");
        Messenger.send(player, " <gray>Fórmula:</gray> [(DG * " + fmt(DifficultyConfig.getChronologicWeight()) +
                ") + (DGp * " + fmt(DifficultyConfig.getGroupWeight()) +
                ")] * (1 + jugadoresOnline)");
        Messenger.send(player, " <green>AutoGenerada:</green> <white>" + fmt(autoGenerated) + "</white>");
        Messenger.send(player, "");

        // ========== FINAL ==========
        double finalDifficulty = DifficultyService.getLocalDifficulty(player.getLocation());
        Messenger.send(player, "<gold>💥 DIFICULTAD FINAL</gold>");
        Messenger.send(player, " <gray>Fórmula:</gray> autogenerada * multiplicador manual");
        Messenger.send(player, " <green>Total:</green> <gradient:#ff5f6d:#ffc371>" + fmt(finalDifficulty) + "</gradient>");
        Messenger.send(player, "");
        Messenger.send(player, "<gray>=====================================================</gray>");
    }

    private String detailedNorm(double norm, double min, double max, double weight) {
        return "<gray>(<yellow>Norm: " + fmt(norm) + "</yellow>, <red>Min: " + fmt(min) + "</red>, <green>Max: " + fmt(max) + "</green>, <gold>W: " + fmt(weight) + "</gold>)</gray>";
    }

    private String fmt(double num) {
        return String.format("%.2f", num);
    }
}
