package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinEvent implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(!Main.playerList.contains(player)) {
				
				if(Main.isJoinable == true) {
					
					Main.playerPlusList.add(new PlayerPlus(player, false, true));
					player.teleport(Main.eventSpawn);
					if(!Main.isInventory) {
						player.getInventory().setContents(Main.eventInventory.getContents());;
					}
					
					for(Player p : Main.playerList) {
						p.sendMessage(Main.eventMessage + player.getName() + " ist dem Event beigetreten!");
					}
					
					player.sendMessage(Main.eventMessage + "§7Du bist dem Event beigetreten!");
				} else {
					player.sendMessage(Main.eventMessage + "§cEvent nicht beitretbar");
				}
				
			} else {
				player.sendMessage(Main.eventMessage + "§cDu bist dem Event bereits beigetreten");
			}
			
			

		} else {
			sender.sendMessage("Dieser Command kann nur von einem Spieler ausgef§hrt werden");
		}
		return false;
	}
}
