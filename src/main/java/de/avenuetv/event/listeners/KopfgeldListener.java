package de.avenuetv.event.listeners;

import Coinsystem.PlayerHasNotEnoughCoinsException;
import Coinsystem.Selector;
import Coinsystem.Spieler;
import de.avenuetv.event.kopfgeld.CancelGUI;
import de.avenuetv.event.kopfgeld.EditingGUI;
import de.avenuetv.event.kopfgeld.ListGUI;
import de.avenuetv.event.main.HuntingPlayer;
import de.avenuetv.event.main.KopfgeldPlayer;
import de.avenuetv.event.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KopfgeldListener implements Listener {

    private HashMap lastClicked = new HashMap<Player, Player>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Main.hunterWasNotOnline.get(p) != null) {
            if (new ListGUI().inventoryHasSpace(p.getInventory())) {
                p.getInventory().addItem((ItemStack) Main.hunterWasNotOnline.get(p));
                p.updateInventory();
                p.sendMessage("§aWährend deiner Abwesenheit wurde eine deiner Kopfgelder eingesammelt, der Kopf liegt nun in deinem Inventar.");
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (e.getClickedInventory() != null) {
                if (e.getView().getTitle() != null) {
                    String title = e.getView().getTitle().toString();
                    if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)) {
                        if (title.equalsIgnoreCase("Kopfgeld Menu")) {
                            e.setCancelled(true);
                            String buttonText = e.getCurrentItem().getItemMeta().getDisplayName();
                            if (buttonText.startsWith("§7Kopfgeld von dir auf§b ")) {
                                for (KopfgeldPlayer kopfgeldPlayer : Main.kopfgeldPlayers) {
                                    if (kopfgeldPlayer.getWantedPlayerName().equalsIgnoreCase(buttonText.split(" ")[4])) {
                                        kopfgeldPlayer.cancelHunting(kopfgeldPlayer.find(new HuntingPlayer(p.getName())));
                                        p.sendMessage("§aKopfgeld wurde zurück gezogen.");
                                        p.closeInventory();
                                    }
                                }
                            } else if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                                clickHead(e.getCurrentItem(), p);
                            }
                            if (buttonText.equalsIgnoreCase("§dZurück")) {
                                p.closeInventory();
                                new ListGUI().openGUI(p);
                            }
                            if (buttonText.equalsIgnoreCase("§cAlle meine ausgesetzten Kopfgelder")) {
                                new CancelGUI().openGUI(p);
                            }
                            if (buttonText.startsWith("§7Erhöhe das Kopfgeld um§6 ")) {
                                increaseKopfgeld(buttonText, p);
                            }
                        }
                    }
                }
            }
        }
    }
    public void increaseKopfgeld(String buttonText, Player p) {
        int amount = Integer.parseInt(buttonText.split(" ")[4]);
        p.closeInventory();
        Spieler spieler = new Selector().selectSpieler(p.getName());
        try {
            spieler.removeCoins(amount);
            Player clicked = (Player) lastClicked.get(p);
            KopfgeldPlayer kopfgeldPlayer = null;
           if (Main.kopfgeldPlayersName.contains(clicked.getName())) {
               for (KopfgeldPlayer fromList : Main.kopfgeldPlayers) {
                   if (fromList.getWantedPlayerName().equalsIgnoreCase(clicked.getName())) {
                       kopfgeldPlayer = fromList;
                   }
               }
            } else {
               kopfgeldPlayer = new KopfgeldPlayer((Player) lastClicked.get(p), new HuntingPlayer(p.getName(), amount));
            }
            Main.kopfgeldPlayers.add(kopfgeldPlayer);
            if (kopfgeldPlayer.getHuntingPlayers().get(0).getHuntingPlayerName().equals(p.getName())) {
                p.sendMessage("§aDu hast ein Kopfgeld auf §5"+kopfgeldPlayer.getWantedPlayerName()+" §aausgesetzt.");
                Bukkit.broadcastMessage("§6§l"+p.getName()+" §5hat ein Kopfgeld auf §c§l"+kopfgeldPlayer.getWantedPlayerName()+" §5ausgesetzt.");
            } else {
                p.sendMessage("§aDu hast das Kopfgeld auf §5"+kopfgeldPlayer.getWantedPlayerName()+" §aerhöht.");
                Bukkit.broadcastMessage("§6§l"+p.getName()+" §5hat das Kopfgeld auf §c§l"+kopfgeldPlayer.getWantedPlayerName()+" §5erhöht.");
            }
        } catch (PlayerHasNotEnoughCoinsException ex) {
            p.sendMessage("§aDu hast nicht genug Coins um ein Kopfgeld aus zu setzen.");
        }
    }

    public void clickHead(ItemStack head, Player p) {
        String displayName = head.getItemMeta().getDisplayName().split(" ")[2];
        if (Bukkit.getPlayer(displayName).isOnline()) {
            Player clickedPlayer = Bukkit.getPlayer(displayName);
            lastClicked.put(p, clickedPlayer);
            p.closeInventory();
            new EditingGUI().openGUI(p);
        } else {
            p.sendMessage("§aDer Spieler hat so eben den Server verlassen.");
        }
    }
}
