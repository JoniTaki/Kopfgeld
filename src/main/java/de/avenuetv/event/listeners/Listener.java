package de.avenuetv.event.listeners;

import de.avenuetv.event.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player) {
            if (e.getDamager() instanceof Player && !Main.ispvp) {
                Player p = (Player)e.getDamager();
                if(Main.playerList.contains(p)) {
                    e.setCancelled(true);
                }
            }
            if (e.getDamager() instanceof Player) {
                Player p = (Player)e.getDamager();
                if(!Main.playerList.contains(p) || !Main.kopfgeldPlayersName.contains(e.getEntity().getName())) {
                    e.setCancelled(true);
                }
            }
        }

        if (e.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile)e.getDamager();
            if (projectile.getShooter() instanceof Player && !Main.ispvp) {
                Player p = (Player)projectile.getShooter();
                if(Main.playerList.contains(p)) {
                    e.setCancelled(true);
                }
            }
            if (projectile.getShooter() instanceof Player) {
                Player p = (Player)projectile.getShooter();
                if(!Main.playerList.contains(p) || !Main.kopfgeldPlayersName.contains(e.getEntity().getName())) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
