package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventMessage implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			String message = "";
			
			if(player.hasPermission("event.admin") || PlayerPlus.findPlayerPlus(player).isEventAdmin()) {
				
				for(String s : args) {
					message += s;
					message += " ";
				}
				
				for(Player p : Main.playerList) {
					p.sendMessage(Main.eventMessage + "§8" + player.getName() + "§6: " + message);
				}
				
			} else {
				player.sendMessage("§cF§r diesen Befehl hast du keine Berechtigung!");
			}
					
		} else {
			sender.sendMessage("Dieser Command kann nur von einem Spieler ausgef§hrt werden");
		}
		return false;
	}
}
