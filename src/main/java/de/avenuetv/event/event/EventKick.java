package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventKick implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(player.hasPermission("event.admin") || PlayerPlus.findPlayerPlus(player).isEventAdmin()) {
				
				if(args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null && Main.playerList.contains(target)) {
						
						PlayerPlus.findPlayerPlus(target).removePlayer(true);
						for(PlayerPlus p : Main.playerPlusList) {
							p.getPlayer().sendMessage(Main.eventMessage + player.getName() + " hat das Event verlassen!");
						}
						target.sendMessage(Main.eventMessage + "§cDu wurdest von einem Event-Admin aus dem Event gekickt!");
					} else {
						player.sendMessage("§cSpieler nicht gefunden!");
					}
				} else {
					player.sendMessage("§cGebe den Namen eines Spielers ein!");
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
