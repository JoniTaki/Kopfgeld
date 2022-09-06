package de.avenuetv.event.main;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerPlus {
	private Player player;
	private Location oldLocation = null;
	ItemStack[] oldInventory; 
	private boolean isEventAdmin;
	
	public PlayerPlus (Player player, boolean isEventAdmin, boolean saveLocation) {
		this.player = player;
		if(saveLocation) {
			this.oldLocation = player.getLocation();
		}
		this.oldInventory = player.getInventory().getContents();
		this.isEventAdmin = isEventAdmin;
		Main.playerList.add(player);
	}
	
	public boolean isEventAdmin() {
		return isEventAdmin;
	}

	public void setEventAdmin(boolean isEventAdmin) {
		this.isEventAdmin = isEventAdmin;
	}

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Location getOldLocation() {
		return oldLocation;
	}
	public void setOldLocation(Location oldLocation) {
		this.oldLocation = oldLocation;
	}
	
	public ItemStack[] getOldInventory() {
		return oldInventory;
	}

	public void setOldInventory(ItemStack[] oldInventory) {
		this.oldInventory = oldInventory;
	}

	public void removePlayer(boolean teleport) {
		if(!this.isEventAdmin) {
			if(!Main.isInventory) {
				getPlayer().getInventory().setContents(oldInventory);
			}
			if(this.oldLocation != null && teleport == true) {
				getPlayer().teleport(this.getOldLocation());
			}
		}
		Main.playerList.remove(getPlayer());
		Main.playerPlusList.remove(this);
	}
	
	public void endEvent() {
		if(!this.isEventAdmin && !Main.isInventory) {
			getPlayer().getInventory().setContents(oldInventory);
			if(this.oldLocation != null) {
				getPlayer().teleport(this.getOldLocation());
			}
		}
	}
	
	public static PlayerPlus findPlayerPlus(Player player) {
		if(Main.playerPlusList != null) {
			for (PlayerPlus p : Main.playerPlusList) {
				if(p.getPlayer().equals(player)) {
					return p;
				}
			}
		}
		return null;
	}
	
	public static PlayerPlus findPlayerPlus(String name) {
		if(Main.playerPlusList != null) {
			for (PlayerPlus p : Main.playerPlusList) {
				if(p.getPlayer().getName().equals(name)) {
					return p;
				}
			}
		}
		return null;
	}
	
	public static void aktualisiereInventar() {
		for(PlayerPlus p: Main.playerPlusList) {
			if(!p.isEventAdmin && !Main.isInventory) {
				p.getPlayer().getInventory().setContents(Main.eventInventory.getContents());
			}
		}
	}

	public void test(){

	}
}
