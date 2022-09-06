package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartEvent implements CommandExecutor{
	
	static boolean isCounting;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			int seconds;
			
			if(player.hasPermission("event.admin") || PlayerPlus.findPlayerPlus(player).isEventAdmin()) {
				
				player.sendMessage(Main.eventMessage + "§7Event wird gestartet!"); 
				
				if(Main.isJoinable == true) {
					Main.isJoinable = false;
					Bukkit.broadcastMessage(Main.eventMessage + "§7Das joinen zum Server-Event wurde gesschlossen!");
				}
				
				if(args.length == 1) {
					seconds = Integer.parseInt(args[0]);
				} else {
					seconds = 0; 	
				}
				
				BukkitRunnable runnable = new BukkitRunnable() {
				int time = seconds;
				boolean started = false;
					@Override
					public void run() {
						
						isCounting = true;
						if(started == false && time > 0) {
							for(Player p : Main.playerList) {
								p.sendMessage(Main.eventMessage + "§7Event startet in §5" + (time) + " §6Sekunden!");
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
							}
						} else if(time % 60 == 0 && time > 30) {
							for(Player p : Main.playerList) {
								p.sendMessage(Main.eventMessage + "§7Event startet in §5" + (time) + " §6Sekunden!");
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
							}
						} else if(time % 10 == 0 && time <= 30 && time > 10) {
							for(Player p : Main.playerList) {
								p.sendMessage(Main.eventMessage + "§7Event startet in §5" + (time) + " §6Sekunden!");
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
							}
						} else if(time > 0 && time <= 10) {
							for(Player p : Main.playerList) {
								p.sendMessage(Main.eventMessage + "§7Event startet in §5" + (time) + " §6Sekunden!");
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
							}
						} else if (time == 0){
							for(Player p : Main.playerList) {
								p.sendMessage(Main.eventMessage + "§6Das Event startet!");
								p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1);
							}
							isCounting = false;
							cancel();
							return;
						}
							
						time--;
						started = true;		
					}
				};
				
				if(isCounting == false) {
					runnable.runTaskTimer(Main.getPlugin(), 0, 20);
				} else {
					player.sendMessage(Main.eventMessage + "§cCountdown l§uft bereits!!"); 
				}
				
				
				Main.isStarted = true;
				
			} else {
				player.sendMessage("§cF§r diesen Befehl hast du keine Berechtigung!");
			}
			
		} else {
			sender.sendMessage("Dieser Command kann nur von einem Spieler ausgef§hrt werden");
		}
		return false;
	}
}
