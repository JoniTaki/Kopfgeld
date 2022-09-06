package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class OpenEvent implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(player.hasPermission("event.admin") || PlayerPlus.findPlayerPlus(player).isEventAdmin()) {
				
				if(Main.isJoinable == false) {
					Main.isJoinable = true;
					
					if(Main.eventSpawn != null) {
						
						if(!Main.playerList.contains(player)) {
							Main.playerPlusList.add(new PlayerPlus(player, true, false));
							player.sendMessage(Main.eventMessage + "§7Du bist dem Event beigetreten!");
						}
						
					} else {
						
						if(!Main.playerList.contains(player)) {
							Main.playerPlusList.add(new PlayerPlus(player, true, false));
							player.sendMessage(Main.eventMessage + "§7Du bist dem Event beigetreten!");
						}
						
						Main.eventSpawn = player.getLocation();
						player.sendMessage(Main.eventMessage + "§aEvent Spawnpoint gesetzt!");
					}
					
					BukkitRunnable runnable = new BukkitRunnable() {
						@Override
						public void run() {				
							if (Main.isJoinable == true) {
								Bukkit.broadcastMessage(Main.eventMessage 
										+ "§7Ein Server-Event wurde erstellt! Gebe §6/eventjoin §7ein, um dem Event beizutreten oder §6/eventwatch §7um Zuschauer zu sein!");
							} else {
								cancel();
								return;
							}	
						}			
					};
					
					runnable.runTaskTimer(Main.getPlugin(), 0, 2400);
					
				} else {
					player.sendMessage(Main.eventMessage + "§cEvent bereits ge§ffnet!");
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
