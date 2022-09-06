package de.avenuetv.event.kopfgeld;

import de.avenuetv.event.main.KopfgeldPlayer;
import de.avenuetv.event.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ListGUI {

    public void openGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Kopfgeld Menu");
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (inventoryHasSpace(inventory)) inventory.addItem(playerHead(onlinePlayer, true));
        }
        inventory.setItem(53, new EditingGUI().customItemStack("§cAlle meine ausgesetzten Kopfgelder", Material.LEGACY_BOOK_AND_QUILL));
        player.openInventory(inventory);
    }

    public ItemStack playerHead(Player player, boolean isGUI) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getName()));
        skullMeta.setDisplayName("§7Kopf von§6 "+player.getName());
        if (isGUI) {
            skullMeta.setLore(getInfosForLore(player));
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public List<String> getInfosForLore(Player player) {
        List lore = new ArrayList<String>();
        for (KopfgeldPlayer kopfgeldPlayer : Main.kopfgeldPlayers) {
            if (kopfgeldPlayer.getWantedPlayer().equals(player)) {
                if (kopfgeldPlayer.getCoins() > 0) {
                    lore.add("§6§lWANTED");
                    lore.add("§7Auf diesen Spieler ist ein Kopfgeld");
                    lore.add("7in Höhe von §a§l"+kopfgeldPlayer.getCoins()+" §7ausgesetzt.");
                    lore.add("§7um das Kopfgeld zu erhöhen §bklick hier");
                    return lore;
                }
            }
        }
        lore.add("§7Auf diesen Spieler ist kein Kopfgeld");
        lore.add("§7ausgesetzt. Neues Kopfgeld ausetzen: §bklick");
        return lore;
    }

    public boolean inventoryHasSpace(Inventory inventory) {
        if (inventory.firstEmpty() == -1) {
            return false;
        }
        return true;
    }
}
