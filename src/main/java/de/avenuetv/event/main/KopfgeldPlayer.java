package de.avenuetv.event.main;

import Coinsystem.Selector;
import Coinsystem.Spieler;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class KopfgeldPlayer extends Main{
    private OfflinePlayer wantedPlayer;
    private String wantedPlayerName;
    private List<HuntingPlayer> huntingPlayers;

    public KopfgeldPlayer (OfflinePlayer wantedPlayer, HuntingPlayer huntingPlayer){
        huntingPlayers = new ArrayList<>();
        this.wantedPlayer = wantedPlayer;
        this.addHunter(huntingPlayer);
        this.wantedPlayerName = wantedPlayer.getName();
        Main.wantedPlayers.add(wantedPlayer);
        addToConfig();
    }

    //Wird genutzt wenn beim laden des Plugins alle HuntingPlayers aus der Config genommen werden.
    public KopfgeldPlayer (OfflinePlayer wantedPlayer, List<HuntingPlayer> huntingPlayers) {
        this.wantedPlayer = wantedPlayer;
        this.huntingPlayers = huntingPlayers;
        Main.kopfgeldPlayers.add(this);
        addToConfig();
    }

    public void addHunter(HuntingPlayer huntingPlayer){
        if (isInList(huntingPlayer)) {
            find(huntingPlayer).addCoins(huntingPlayer.getCoins());
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
        if(isInList(huntingPlayer)) {
            Spieler spieler = new Selector().selectSpieler(huntingPlayer.getPlayer().getName());
            spieler.addCoins((int)(huntingPlayer.getCoins() * 0.75));
            huntingPlayers.remove(find(huntingPlayer));
        }
    }

    public void addToConfig() {
        List<String> wantedPlayers = new ArrayList<>();
        for (KopfgeldPlayer kopfgeldPlayer : kopfgeldPlayers) {
            wantedPlayers.add(kopfgeldPlayer.getWantedPlayerName());
        }
        config.set("wantedPlayers", wantedPlayers);
        List<String> huntingPlayers = new ArrayList<>();
        for (HuntingPlayer huntingPlayer : this.huntingPlayers) {
            huntingPlayers.add(huntingPlayer.getPlayer().getName());
            config.set(huntingPlayer.getPlayer().getName(), wantedPlayerName);
        }
        config.set("huntersList."+wantedPlayerName, huntingPlayers);
        saveConfig();
    }

    public OfflinePlayer getWantedPlayer() {
        return wantedPlayer;
    }

    public String getWantedPlayerName() {
        return wantedPlayerName;
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
