package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventAdmin implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(player.hasPermission("event.admin") || PlayerPlus.findPlayerPlus(player).isEventAdmin()) {
				
				if(args.length == 0) {
					PlayerPlus playerPlus = PlayerPlus.findPlayerPlus(player);
					if(playerPlus != null) {
						if(playerPlus.isEventAdmin()) {
							playerPlus.removePlayer(false);
							Main.playerPlusList.add(new PlayerPlus(player, false, false));
							if(!Main.isInventory) {
								playerPlus.getPlayer().getInventory().setContents(Main.eventInventory.getContents());
							}
							playerPlus.getPlayer().sendMessage(Main.eventMessage + "§7Du bist nun kein Event-Admin mehr!");
						} else {
							playerPlus.setEventAdmin(true);
							playerPlus.getPlayer().sendMessage(Main.eventMessage + "§7Du bist nun Event-Admin!");
							if(!Main.isInventory) {
								playerPlus.getPlayer().getInventory().setContents(playerPlus.getOldInventory());
							}
						}
					}
				} else if(args.length == 1) {
					PlayerPlus playerPlus = PlayerPlus.findPlayerPlus(args[0]);
					if(playerPlus != null) {
						if(playerPlus.isEventAdmin()) {
							Player p2 = playerPlus.getPlayer();
							playerPlus.removePlayer(false);
							Main.playerPlusList.add(new PlayerPlus(p2, false, false));
							if(!Main.isInventory) {
								playerPlus.getPlayer().getInventory().setContents(Main.eventInventory.getContents());
							}
							playerPlus.getPlayer().sendMessage(Main.eventMessage + "§7Du bist nun kein Event-Admin mehr!");
							player.sendMessage(Main.eventMessage + playerPlus.getPlayer().getName() + " §7ist nun kein Event-Admin mehr!");
						} else {
							playerPlus.setEventAdmin(true);
							playerPlus.getPlayer().sendMessage(Main.eventMessage + "§7Du bist nun Event-Admin!");
							if(!Main.isInventory) {
								playerPlus.getPlayer().getInventory().setContents(playerPlus.getOldInventory());
							}
							player.sendMessage(Main.eventMessage + playerPlus.getPlayer().getName() + " §7ist nun Event-Admin!");
						}
					}
				}
				
			} else {
				player.sendMessage("§cFuer diesen Befehl hast du keine Berechtigung!");
			}
		
		} else {
			sender.sendMessage("Dieser Command kann nur von einem Spieler ausgefuehrt werden");
		}
		return false;
	}
}
