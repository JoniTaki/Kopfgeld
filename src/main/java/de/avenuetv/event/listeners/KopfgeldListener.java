package de.avenuetv.event.listeners;

import Coinsystem.PlayerHasNotEnoughCoinsException;
import Coinsystem.Selector;
import Coinsystem.Spieler;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KopfgeldListener implements Listener {

    private HashMap lastClicked = new HashMap<Player, Player>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getPlayer();
        if (p.getKiller() instanceof Player) {
            Player killer = p.getKiller();
            int rewardCoins = 0;
            KopfgeldPlayer kopfgeldPlayer = null;
            for (KopfgeldPlayer kopfgeldPlayerFromList : Main.kopfgeldPlayers) {
                if (kopfgeldPlayerFromList.getWantedPlayer().equals(p)) {
                    rewardCoins = kopfgeldPlayerFromList.getCoins();
                    kopfgeldPlayer = kopfgeldPlayerFromList;
                }
            }
            if (kopfgeldPlayer == null) return;
            Player firstHuntingPlayer = kopfgeldPlayer.getHuntingPlayers().get(0).getPlayer();
            //Gebe rewardCoins an killer, muss noch eingefügt werden.
            Main.kopfgeldPlayers.remove(kopfgeldPlayer);
            Bukkit.broadcastMessage("§6§l"+killer.getName()+" §5hat §c§l"+p.getName()+" §5 eliminiert! Und hat ein Kopfgeld " +
                    "in höhe von §a§l"+rewardCoins+" §5Coins erhalten.");
            if (firstHuntingPlayer.isOnline()) {
                if (new ListGUI().inventoryHasSpace(firstHuntingPlayer.getInventory())) {
                    firstHuntingPlayer.getInventory().addItem(new ListGUI().playerHead(p, false));
                    p.updateInventory();
                    firstHuntingPlayer.sendMessage("§aDer Kopf wurde dir ins Inventar gelegt.");
                    return;
                }
            }
            Main.hunterWasNotOnline.put(firstHuntingPlayer, new ListGUI().playerHead(p, false));
        }
    }

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
                            /*
                            Wenn ein Player_Head geklickt wird, öffnet sich das Editing GUI
                            Dannach wird der geklickte Player auf lastClicked hinzugefügt.
                             */
                            if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                                String displayName = e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[2];
                                if (Bukkit.getPlayer(displayName).isOnline()) {
                                    Player clickedPlayer = Bukkit.getPlayer(displayName);
                                    lastClicked.put(p, clickedPlayer);
                                    p.closeInventory();
                                    new EditingGUI().openGUI(p);
                                } else {
                                    p.sendMessage("§aDer Spieler hat so eben den Server verlassen.");
                                }
                            }
                            /*
                            Alle anderen Buttons werden hier geregelt.
                             */
                            String buttonText = e.getCurrentItem().getItemMeta().getDisplayName();
                            if (buttonText.equalsIgnoreCase("§dZurück")) {
                                p.closeInventory();
                                new ListGUI().openGUI(p);
                            }
                            if (buttonText.equalsIgnoreCase("§cAlle meine ausgesetzten Kopfgelder")) {
                                //Hier muss noch eine GUI zum zurückziehen der Kopfgelder aufgerufen werden.
                            }
                            if (buttonText.startsWith("§7Eröhe das Kopfgeld um§6 ")) {
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
        KopfgeldPlayer kopfgeldPlayer = new KopfgeldPlayer((Player) lastClicked.get(p), new HuntingPlayer(p, amount));
        Main.kopfgeldPlayers.add(kopfgeldPlayer);
        p.closeInventory();
        Spieler spieler = new Selector().selectSpieler(p.getName());
        try {
            spieler.removeCoins(amount);

            if (kopfgeldPlayer.getHuntingPlayers().get(0).getPlayer().equals(p)) {
                p.sendMessage("§aDu hast ein Kopfgeld auf §5"+kopfgeldPlayer.getWantedPlayer().getName()+" §aausgesetzt.");
                Bukkit.broadcastMessage("§6§l"+p.getName()+" §5hat ein Kopfgeld auf §c§l"+kopfgeldPlayer.getWantedPlayer().getName()+" §5ausgesetzt.");
            } else {
                p.sendMessage("§aDu hast das Kopfgeld auf §5"+kopfgeldPlayer.getWantedPlayer().getName()+" §aerhöht.");
                Bukkit.broadcastMessage("§6§l"+p.getName()+" §5hat das Kopfgeld auf §c§l"+kopfgeldPlayer.getWantedPlayer().getName()+" §5erhöht.");
            }
        } catch (PlayerHasNotEnoughCoinsException ex) {
            p.sendMessage("§aDu hast nicht genug Coins um ein Kopfgeld aus zu setzen.");
        }
    }
}
