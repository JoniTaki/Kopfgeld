package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveEvent implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(Main.playerList.contains(player)) {
				
				Main.playerList.remove(player);
				for (PlayerPlus p : Main.playerPlusList) {
					if(p.getPlayer().equals(player)) {
						p.removePlayer(true);
						break;
					}
				}
				
				for(Player p : Main.playerList) {
					p.sendMessage(Main.eventMessage + player.getName() + " hat das Event verlassen!");
				}

				player.sendMessage(Main.eventMessage + "§7Du hast das Event verlassen!");
				
			} else {
				player.sendMessage(Main.eventMessage + "§cDu bist keinem Event beigetreten");
			}	

		} else {
			sender.sendMessage("Dieser Command kann nur von einem Spieler ausgef§hrt werden");
		}
		return false;
	}
}
