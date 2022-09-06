package de.avenuetv.event.main;

import org.bukkit.entity.Player;

public class HuntingPlayer {
    private Player player;
    private int coins;

    public HuntingPlayer (Player player, int coins) {
        this.player = player;
        this.coins = coins;
    }

    public HuntingPlayer (Player player) {
        this.player = player;
    }

    public void addCoins (int coins){
        this.coins += coins;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCoins() {
        return coins;
    }
}
