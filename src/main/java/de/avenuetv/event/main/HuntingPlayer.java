package de.avenuetv.event.main;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class HuntingPlayer {
    private OfflinePlayer player;
    private int coins;

    public HuntingPlayer (OfflinePlayer player, int coins) {
        this.player = player;
        this.coins = coins;
    }

    public HuntingPlayer (Player player) {
        this.player = player;
    }

    public void addCoins (int coins){
        this.coins += coins;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public int getCoins() {
        return coins;
    }
}
