package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WatchEvent implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(Main.eventWatcherSpawn != null) {
				for (int i = 0; i < Main.playerPlusList.size(); i++) {
					if(player.equals(Main.playerPlusList.get(i).getPlayer())) {
						Main.playerPlusList.get(i).removePlayer(false);
						for(Player p : Main.playerList) {
							p.sendMessage(Main.eventMessage + player.getName() + " hat das Event verlassen!");
						}

						player.sendMessage(Main.eventMessage + "§7Du hast das Event verlassen!");
						break;
					}
				}
				player.teleport(Main.eventWatcherSpawn);
			} else {
				player.sendMessage(Main.eventMessage + "§cKein Zuschauer-Spawn platziert!");
			}
			
					
		} else {
			sender.sendMessage("Dieser Command kann nur von einem Spieler ausgef§hrt werden");
		}
		return false;
	}
}
