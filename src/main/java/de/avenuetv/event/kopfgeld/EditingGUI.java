package de.avenuetv.event.kopfgeld;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditingGUI {

    public ItemStack customItemStack(String displayName, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    public void openGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Kopfgeld Menu");
        int coinsAmount = 25000;
        for (int i = 11; i < 15; i++) {
            String displayName = "§7Erhöhe das Kopfgeld um§6 "+coinsAmount+" §7Coins.";
            inventory.setItem(i, customItemStack(displayName, Material.GOLD_INGOT));
            coinsAmount *= 2;
        }
        inventory.setItem(26, customItemStack("§dZurück", Material.BARRIER));
        player.openInventory(inventory);
    }

}
