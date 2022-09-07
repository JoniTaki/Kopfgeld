package de.avenuetv.event.kopfgeld;

import de.avenuetv.event.main.HuntingPlayer;
import de.avenuetv.event.main.KopfgeldPlayer;
import de.avenuetv.event.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CancelGUI {

    public void openGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Kopfgeld Menu");
        for (KopfgeldPlayer kopfgeldPlayer : Main.kopfgeldPlayers) {
            for (HuntingPlayer huntingPlayer : kopfgeldPlayer.getHuntingPlayers()) {
                if (huntingPlayer.getPlayer().equals(player)) {
                    ItemStack head = new ListGUI().playerHead(kopfgeldPlayer.getWantedPlayer(), false);
                    ItemMeta meta = head.getItemMeta();
                    meta.setDisplayName("§7Kopfgeld von dir auf§b "+kopfgeldPlayer.getWantedPlayer().getName());
                    List<String> lore = new ArrayList<>();
                    lore.add("§eKopfgeld zurückziehen");
                    lore.add("§7Du hast §a"+huntingPlayer.getCoins()+" §7Coins gesetzt");
                    lore.add("§7Erhalte 75% von deinen gesetzten Coins zurück");
                    meta.setLore(lore);
                    head.setItemMeta(meta);
                    inventory.addItem(head);
                }
            }
        }
        inventory.setItem(26, new EditingGUI().customItemStack("§dZurück", Material.BARRIER));
        player.openInventory(inventory);
    }

}
