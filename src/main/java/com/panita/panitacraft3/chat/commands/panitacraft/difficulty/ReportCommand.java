package com.panita.panitacraft3.chat.commands.panitacraft.difficulty;

import com.panita.panitacraft3.difficulty.DifficultyService;
import com.panita.panitacraft3.difficulty.calculators.EquipmentScoreCalculator;
import com.panita.panitacraft3.difficulty.calculators.IndividualDifficultyCalculator;
import com.panita.panitacraft3.difficulty.calculators.ChronologicDifficultyCalculator;
import com.panita.panitacraft3.difficulty.util.BiomeDanger;
import com.panita.panitacraft3.util.Global;
import com.panita.panitacraft3.util.chat.Messenger;
import com.panita.panitacraft3.util.commands.dynamic.AdvancedCommand;
import com.panita.panitacraft3.util.commands.identifiers.SubCommandSpec;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommandSpec(
        parent = "panitacraft",
        name = "difficulty trace",
        description = "Displays a detailed report of the current difficulty settings.",
        syntax = "/panitacraft difficulty trace",
        permission = "panitacraft.admin.command.panitacraft.difficulty.trace"
)
public class ReportCommand implements AdvancedCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            Messenger.prefixedSend(sender, "<red>Este comando solo puede ser ejecutado por jugadores.</red>");
            return;
        }

        // Global difficulty (DG)
        double dg = DifficultyService.getChronologicDifficulty();
        int days = Bukkit.getWorlds().getFirst().getFullTime() > 0
                ? (int) (Bukkit.getWorlds().getFirst().getFullTime() / 24000) : 0;
        double normDays = Global.normalize(days, 0, 1000);
        double normOnline = Global.normalize(Bukkit.getOnlinePlayers().size(), 0, Bukkit.getMaxPlayers());
        double eventPenalty = ChronologicDifficultyCalculator.getGlobalEventPenalty();

        // Individual difficulty (DI)
        double timeSinceDeath = Global.ticksToHours(player.getStatistic(Statistic.TIME_SINCE_DEATH));
        double normTimeSinceDeath = Global.normalize(timeSinceDeath, 0, 48);

        double timeSinceRest = Global.ticksToHours(player.getStatistic(Statistic.TIME_SINCE_REST));
        double normTimeSinceRest = Global.normalize(timeSinceRest, 0, 12);

        double playTime = Global.ticksToHours(player.getStatistic(Statistic.PLAY_ONE_MINUTE));
        double normPlayTime = Global.normalize(playTime, 0, 250 * 3600);

        int mobsKilled = player.getStatistic(Statistic.MOB_KILLS);
        double normMobsKilled = Global.normalize(mobsKilled, 0, 5000);

        int xpLevel = player.getLevel();
        double normXP = Global.normalize(xpLevel, 0, 500);

        int deaths = player.getStatistic(Statistic.DEATHS);
        double normDeaths = Global.normalize(deaths, 0, 250);

        int equipScore = EquipmentScoreCalculator.calculate(player);
        double normEquip = Global.normalize(equipScore, 0, 500);

        double biomeDanger = BiomeDanger.getDangerLevel(player.getLocation().getBlock().getBiome());
        double dimMultiplier = IndividualDifficultyCalculator.getDimensionMultiplier(player);

        double di = DifficultyService.getIndividualDifficulty(player);
        double dgP = DifficultyService.getGroupDifficulty(player.getLocation());
        double autoGenerated = DifficultyService.getAutoGeneratedDifficulty(player.getLocation());
        double multiplier = DifficultyService.getManualDifficultyMultiplier();
        double total = DifficultyService.getTotalDifficulty(player.getLocation());

        // VISUAL
        Messenger.send(player, "<gray>========[ <gradient:#9E73DE:#5900DD>DIFICULTAD - TRAZADO DETALLADO</gradient> ]========</gray>");

        Messenger.send(player, "<gold>🌍 DIFICULTAD GLOBAL</gold>");
        Messenger.send(player, " <gray>Fórmula:</gray> (días*0.5 + jugadores*0.25 + eventos*0.15) * max");
        Messenger.send(player, " <yellow>Días:</yellow> <aqua>" + days + "</aqua> <gray>(Norm:</gray> " + fmt(normDays) + ")");
        Messenger.send(player, " <yellow>Jugadores:</yellow> <aqua>" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + "</aqua> <gray>(Norm:</gray> " + fmt(normOnline) + ")");
        Messenger.send(player, " <yellow>Penalización de eventos:</yellow> <aqua>" + fmt(eventPenalty) + "</aqua>");
        Messenger.send(player, " <green>Dificultad Global:</green> <white>" + fmt(dg) + "</white>");
        Messenger.send(player, "");
        Messenger.send(player, "<gold>👤 DIFICULTAD INDIVIDUAL</gold>");
        Messenger.send(player, " <gray>Fórmula:</gray> [(deathTime*0.1)+(restTime*0.1)+(play*0.4)+(mobs*0.3)+(equip*0.5)+(xp*0.2)-(deaths*0.4)] * (bioma*dim) * max");
        Messenger.send(player, " 💀 <yellow>Tiempo desde última muerte:</yellow> <aqua>" + fmt(timeSinceDeath) + "h</aqua> <gray>(Norm:</gray> " + fmt(normTimeSinceDeath) + ")");
        Messenger.send(player, " 🛏 <yellow>Tiempo desde última cama:</yellow> <aqua>" + fmt(timeSinceRest) + "h</aqua> <gray>(Norm:</gray> " + fmt(normTimeSinceRest) + ")");
        Messenger.send(player, " ⏱ <yellow>Tiempo jugado:</yellow> <aqua>" + fmt(playTime) + "h</aqua> <gray>(Norm:</gray> " + fmt(normPlayTime) + ")");
        Messenger.send(player, " ☠ <yellow>Mobs asesinados:</yellow> <aqua>" + mobsKilled + "</aqua> <gray>(Norm:</gray> " + fmt(normMobsKilled) + ")");
        Messenger.send(player, " 🧪 <yellow>XP Level:</yellow> <aqua>" + xpLevel + "</aqua> <gray>(Norm:</gray> " + fmt(normXP) + ")");
        Messenger.send(player, " 💀 <yellow>Muertes totales:</yellow> <aqua>" + deaths + "</aqua> <gray>(Norm:</gray> " + fmt(normDeaths) + ")");
        Messenger.send(player, " 🛡 <yellow>Equipo Score:</yellow> <aqua>" + equipScore + "</aqua> <gray>(Norm:</gray> " + fmt(normEquip) + ")");
        Messenger.send(player, " 🧭 <yellow>Bioma peligro:</yellow> <aqua>" + fmt(biomeDanger) + "</aqua>");
        Messenger.send(player, " 🌌 <yellow>Multiplicador de dimensión:</yellow> <aqua>" + fmt(dimMultiplier) + "</aqua>");
        Messenger.send(player, " <green>Dificultad Individual:</green> <white>" + fmt(di) + "</white>");
        Messenger.send(player, "");
        Messenger.send(player, "<gold>👥 DIFICULTAD GRUPAL</gold>");
        Messenger.send(player, " <gray>Promedio de DI en radio</gray>");
        Messenger.send(player, " <green>Dificultad Grupal:</green> <white>" + fmt(dgP) + "</white>");
        Messenger.send(player, "");
        Messenger.send(player, "<gold>🧠 DIFICULTAD AUTOGENERADA</gold>");
        Messenger.send(player, " <gray>Fórmula:</gray> [(DG * 0.4) + (DGp * 0.6)] * (1 + jugadoresOnline)");
        Messenger.send(player, " <green>AutoGenerada:</green> <white>" + fmt(autoGenerated) + "</white>");
        Messenger.send(player, "");
        Messenger.send(player, "<gold>💥 DIFICULTAD FINAL</gold>");
        Messenger.send(player, " <gray>Fórmula:</gray> autogenerada * multiplicador manual");
        Messenger.send(player, " <yellow>Multiplicador:</yellow> <aqua>" + fmt(multiplier) + "x</aqua>");
        Messenger.send(player, " <green>Total:</green> <gradient:#ff5f6d:#ffc371>" + fmt(total) + "</gradient>");
        Messenger.send(player, "");
        Messenger.send(player, "<gray>=====================================================</gray>");
    }

    private String fmt(double num) {
        return String.format("%.2f", num);
    }
}
