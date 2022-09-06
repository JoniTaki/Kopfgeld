package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class EndEvent implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(player.hasPermission("event.admin") || PlayerPlus.findPlayerPlus(player).isEventAdmin()) {
				
				player.sendMessage(Main.eventMessage + "§7Du hast das Event beendet!");
				
				if(Main.playerPlusList.size() > 0) {
					for(PlayerPlus p : Main.playerPlusList) {
						p.getPlayer().sendMessage(Main.eventMessage + "§6Das Event wurde beendet!");
						p.endEvent();
					}
				}
				System.out.println("Hallo");
				Main.playerList = null;
				Main.playerPlusList = null;
				Main.isJoinable = false;
				Main.ispvp = false;
				Main.eventSpawn = null;
				Main.eventWatcherSpawn = null;
				Main.isStarted = false;
				Main.eventInventory = Bukkit.createInventory(null, InventoryType.PLAYER);
				
			} else {
				player.sendMessage("§cF§r diesen Befehl hast du keine Berechtigung!");
			}
							
		} else {
			sender.sendMessage("Dieser Command kann nur von einem Spieler ausgef§hrt werden");
		}
		return false;
	}
}
