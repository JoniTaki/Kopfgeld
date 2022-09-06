package de.avenuetv.event.event;

import de.avenuetv.event.main.Main;
import de.avenuetv.event.main.PlayerPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetInventory implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
		
			Player player = (Player) sender;
			
			if(player.hasPermission("event.admin") || PlayerPlus.findPlayerPlus(player).isEventAdmin()) {
				Main.eventInventory.setContents(player.getInventory().getContents());
				PlayerPlus.aktualisiereInventar();
				player.sendMessage(Main.eventMessage + "§bEvent-Inventar festgelegt!");
			} else {
				player.sendMessage("§cF§r diesen Befehl hast du keine Berechtigung!");
			}
			
		} else {
			sender.sendMessage("Dieser Command kann nur von einem Spieler ausgef§hrt werden");
		}
		return false;
	}
}
