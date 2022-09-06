package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleInventory implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(player.hasPermission("event.admin") || PlayerPlus.findPlayerPlus(player).isEventAdmin()) {
				if(Main.isInventory) {
					Main.isInventory = false;
					for (PlayerPlus p : Main.playerPlusList) {
						if(!p.isEventAdmin()) {
							p.setOldInventory(p.getPlayer().getInventory().getContents());
							p.getPlayer().getInventory().setContents(Main.eventInventory.getContents());;
						}
					}
					player.sendMessage(Main.eventMessage + "§6Event-Inventar aktiviert!");
				} else {
					Main.isInventory = true;
					for (PlayerPlus p : Main.playerPlusList) {
						if(!p.isEventAdmin()) {
							p.getPlayer().getInventory().setContents(p.getOldInventory());
						}
					}
					player.sendMessage(Main.eventMessage + "§6Spieler-Inventar aktiviert!");
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
