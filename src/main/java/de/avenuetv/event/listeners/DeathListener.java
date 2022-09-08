package de.avenuetv.event.listeners;

import Coinsystem.Selector;
import Coinsystem.Spieler;
import de.avenuetv.event.kopfgeld.ListGUI;
import de.avenuetv.event.main.KopfgeldPlayer;
import de.avenuetv.event.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getPlayer();
        if (p.getKiller() instanceof Player) {
            Player killer = p.getKiller();
            if (killer.getName().equalsIgnoreCase(p.getName())) return;
            int rewardCoins = 0;
            KopfgeldPlayer kopfgeldPlayer = null;
            for (KopfgeldPlayer kopfgeldPlayerFromList : Main.kopfgeldPlayers) {
                if (kopfgeldPlayerFromList.getWantedPlayerName().equals(p.getName())) {
                    rewardCoins = kopfgeldPlayerFromList.getCoins();
                    kopfgeldPlayer = kopfgeldPlayerFromList;
                }
            }
            if (kopfgeldPlayer == null) return;
            OfflinePlayer firstHuntingPlayer = Bukkit.getOfflinePlayer(kopfgeldPlayer.getHuntingPlayers().get(0).getHuntingPlayerName());
            Spieler spielerWhoKilled = new Selector().selectSpieler(killer.getName());
            spielerWhoKilled.addCoins(rewardCoins);
            Main.kopfgeldPlayers.remove(kopfgeldPlayer);
            Bukkit.broadcastMessage("§6§l"+killer.getName()+" §5hat §c§l"+p.getName()+" §5 eliminiert! Und hat ein Kopfgeld " +
                    "in höhe von §a§l"+rewardCoins+" §5Coins erhalten.");
            if (firstHuntingPlayer.isOnline()) {
                Player onlineHuntingPlayer = (Player) firstHuntingPlayer;
                if (new ListGUI().inventoryHasSpace(onlineHuntingPlayer.getInventory())) {
                    onlineHuntingPlayer.getInventory().addItem(new ListGUI().playerHead(p.getName(), false));
                    p.updateInventory();
                    onlineHuntingPlayer.sendMessage("§aDer Kopf wurde dir ins Inventar gelegt.");
                    return;
                }
            }
            Main.hunterWasNotOnline.put(firstHuntingPlayer, new ListGUI().playerHead(p.getName(), false));
        }
    }
}
