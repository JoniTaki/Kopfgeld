package de.avenuetv.event.kopfgeld;

import de.avenuetv.event.main.KopfgeldPlayer;
import de.avenuetv.event.main.Main;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("kopfgeld")) {
            new ListGUI().openGUI((Player) sender);
            System.out.println(Main.kopfgeldPlayers.get(0).getCoins());
        }
        return false;
    }
}
