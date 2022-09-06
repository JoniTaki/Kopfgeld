package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventList implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(player.hasPermission("event.admin") || PlayerPlus.findPlayerPlus(player).isEventAdmin()) {
				
				if(Main.playerPlusList != null) {
					for(PlayerPlus p : Main.playerPlusList) {
						if(p.isEventAdmin()) {
							player.sendMessage(Main.eventMessage + "§7" + p.getPlayer().getName() + " (Event-Admin)");
						} else {
							player.sendMessage(Main.eventMessage + "§7" + p.getPlayer().getName());
						}
					}
					player.sendMessage(Main.eventMessage + "§7" + Main.playerPlusList.size() + " Event-Teilnehmer!");
				} else {
					player.sendMessage("§cListe ist leer!");
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
