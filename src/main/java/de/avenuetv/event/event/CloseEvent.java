package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CloseEvent implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(player.hasPermission("event.admin") || PlayerPlus.findPlayerPlus(player).isEventAdmin()) {
				
				if(Main.isJoinable == true) {
					Main.isJoinable = false;
					Bukkit.broadcastMessage(Main.eventMessage + "§7Das joinen zum Server-Event wurde gesschlossen!");
				} else {
					player.sendMessage(Main.eventMessage + "§cEvent bereits geschlossen!");
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