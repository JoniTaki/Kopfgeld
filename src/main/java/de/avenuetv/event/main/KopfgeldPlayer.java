package de.avenuetv.event.main;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KopfgeldPlayer {
    private Player wantedPlayer;
    private List<HuntingPlayer> huntingPlayers;

    public KopfgeldPlayer (Player wantedPlayer, HuntingPlayer huntingPlayer){
        huntingPlayers = new ArrayList<>();
        this.wantedPlayer = wantedPlayer;
        this.addHunter(huntingPlayer);
        Main.wantedPlayers.add(wantedPlayer);
    }

    public void addHunter(HuntingPlayer huntingPlayer){
        if (this.isInList(huntingPlayer)) {
            this.find(huntingPlayer).addCoins(huntingPlayer.getCoins());
        } else {
            this.huntingPlayers.add(huntingPlayer);
        }
    }

    public HuntingPlayer find (HuntingPlayer player){
        if(huntingPlayers != null && huntingPlayers.size() > 0) {
            for (HuntingPlayer p : huntingPlayers){
                if(p.getPlayer().equals(player.getPlayer())){
                    return p;
                }
            }
        }
        return null;
    }

    public boolean isInList(HuntingPlayer huntingPlayer){
        if(huntingPlayers != null && huntingPlayers.size() > 0) {
            for (HuntingPlayer p : huntingPlayers){
                if(p.getPlayer().equals(huntingPlayer.getPlayer())){
                    return true;
                }
            }
        }
        return false;
    }

    public void cancelHunting(HuntingPlayer huntingPlayer){
        //Coins zurückgeben (75%)
        if(this.isInList(huntingPlayer)) {
            huntingPlayers.remove(this.find(huntingPlayer));
        }
    }

    public Player getWantedPlayer() {
        return wantedPlayer;
    }

    public List<HuntingPlayer> getHuntingPlayers() {
        return huntingPlayers;
    }

    public int getCoins() {
        int coins = 0;
        for(HuntingPlayer p : huntingPlayers) {
            coins += p.getCoins();
        }
        return coins;
    }
}
