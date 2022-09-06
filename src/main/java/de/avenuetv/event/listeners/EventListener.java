package de.avenuetv.event.listeners;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class EventListener implements Listener{
	
	@EventHandler
	public void onRespawn (PlayerRespawnEvent event) {
		if(Main.playerList.contains(event.getPlayer())) {
			event.setRespawnLocation(Main.eventSpawn);
		}
	}
	
	@EventHandler
	public void onPlayerLeave (PlayerQuitEvent event) {
		for (int i = 0; i < Main.playerPlusList.size(); i++) {
			if(event.getPlayer().equals(Main.playerPlusList.get(i).getPlayer())) {
				Main.playerPlusList.get(i).removePlayer(true);
				
				for(Player p : Main.playerList) {
					p.sendMessage(Main.eventMessage + event.getPlayer().getName() + " hat das Event verlassen!");
				}
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		for (int i = 0; i < Main.playerPlusList.size(); i++) {
			if(event.getPlayer().equals(Main.playerPlusList.get(i).getPlayer())) {
				Main.playerPlusList.get(i).removePlayer(true);
			}
		}
		for(Player p : Main.playerList) {
			p.sendMessage(Main.eventMessage + event.getPlayer().getName() + " hat das Event verlassen!");
		}
	}
	

	@EventHandler
	public void onDroppingItem(PlayerDropItemEvent event) {
		Player p = event.getPlayer();
		
		if(Main.playerList.contains(p) && !Main.isInventory && !PlayerPlus.findPlayerPlus(p).isEventAdmin()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event){
		Player p = event.getPlayer();

		if(Main.playerList.contains(p) && !PlayerPlus.findPlayerPlus(p).isEventAdmin() && !event.getMessage().split(" ")[0].equalsIgnoreCase("/eventleave")
				&& !event.getMessage().split(" ")[0].equalsIgnoreCase("/eventadmin") && !event.getMessage().split(" ")[0].equalsIgnoreCase("/msg")){
			p.sendMessage(Main.eventMessage + "§cDu musst das Event mit /eventleave verlassen, um diesen Befehl nutzen zu koennen!");
			event.setCancelled(true);
		}
	}
	
//	@EventHandler
//	public void onPlayerTeleport(PlayerTeleportEvent event) {
//		if(Main.isJoinable || Main.isStarted) {
//			for (int i = 0; i < Main.playerPlusList.size(); i++) {
//				if(event.getPlayer().equals(Main.playerPlusList.get(i).getPlayer()) && !Main.playerPlusList.get(i).isEventAdmin()) {
//					Main.playerPlusList.get(i).removePlayer();
//					
//					for(Player p : Main.playerList) {
//						p.sendMessage(Main.eventMessage + event.getPlayer().getName() + " hat das Event verlassen!");
//					}
//				}
//			}
//		}
//	}
}
