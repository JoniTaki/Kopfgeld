package de.avenuetv.event.main;

import de.avenuetv.event.event.*;
import de.avenuetv.event.kopfgeld.Command;
import de.avenuetv.event.kopfgeld.KopfgeldLoader;
import de.avenuetv.event.listeners.DeathListener;
import de.avenuetv.event.listeners.EventListener;
import de.avenuetv.event.listeners.KopfgeldListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

	public static boolean isJoinable;
	public static boolean ispvp;
	public static boolean isStarted;
	public static boolean isInventory;
	public static String eventMessage = "§8[§5Event§8] ";
	public static List<PlayerPlus> playerPlusList = new ArrayList<>();
	public static List<Player> playerList = new ArrayList<>();
	public static Location eventSpawn;
	public static Location eventWatcherSpawn;
	private static Main plugin;
	public static Inventory eventInventory = Bukkit.createInventory(null, InventoryType.PLAYER);


	public static List<KopfgeldPlayer> kopfgeldPlayers = new ArrayList<>();
	public static List<OfflinePlayer> wantedPlayers = new ArrayList<>();
	public static HashMap hunterWasNotOnline = new HashMap<Player, ItemStack>();
	
	public void onEnable() {
		plugin = this;
		isJoinable = false;
		ispvp = false;
		isStarted = false;
		isInventory = false;
		
		getCommand("eventopen").setExecutor(new OpenEvent());
		getCommand("eventclose").setExecutor(new CloseEvent());
		getCommand("eventjoin").setExecutor(new JoinEvent());
		getCommand("eventleave").setExecutor(new LeaveEvent());
		getCommand("eventsetspawn").setExecutor(new SetEventSpawn());
		getCommand("eventend").setExecutor(new EndEvent());
		getCommand("eventstart").setExecutor(new StartEvent());
		getCommand("eventkick").setExecutor(new EventKick());
		getCommand("eventlist").setExecutor(new EventList());
		getCommand("eventmsg").setExecutor(new EventMessage());
		getCommand("eventsetWatcherSpawn").setExecutor(new SetEventWatcherSpawn());
		getCommand("eventwatch").setExecutor(new WatchEvent());
		getCommand("eventtogglepvp").setExecutor(new TogglePvP());
		getCommand("eventhealall").setExecutor(new EventHeal());
		getCommand("eventadmin").setExecutor(new EventAdmin());
		getCommand("eventtoggleinv").setExecutor(new ToggleInventory());
		getCommand("eventsetinv").setExecutor(new SetInventory());
		getCommand("kopfgeld").setExecutor(new Command());
		
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new EventListener(), this);
		pluginManager.registerEvents(new KopfgeldListener(), this);
		pluginManager.registerEvents(new DeathListener(), this);
	}
	
	public void onDisable() {
		for (int i = 0; i < Main.playerPlusList.size(); i++) {
			new EndEvent();
		}
	}

	public static Main getPlugin() {
		return plugin;
	}
}
