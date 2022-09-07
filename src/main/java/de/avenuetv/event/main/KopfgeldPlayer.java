package de.avenuetv.event.main;

import Coinsystem.Selector;
import Coinsystem.Spieler;
import de.avenuetv.event.kopfgeld.KopfgeldLoader;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class KopfgeldPlayer extends KopfgeldLoader {
    private String wantedPlayerName;
    private List<HuntingPlayer> huntingPlayers;

    public KopfgeldPlayer (OfflinePlayer wantedPlayer, HuntingPlayer huntingPlayer){
        huntingPlayers = new ArrayList<>();
        this.addHunter(huntingPlayer);
        this.wantedPlayerName = wantedPlayer.getName();
        Main.wantedPlayers.add(wantedPlayer);
        addToConfig();
    }

    //Wird genutzt wenn beim laden des Plugins alle HuntingPlayers aus der Config genommen werden.
    public KopfgeldPlayer (OfflinePlayer wantedPlayer, List<HuntingPlayer> huntingPlayers) {
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
            for (HuntingPlayer huntingPlayer : huntingPlayers){
                if(huntingPlayer.getHuntingPlayerName().equals(player.getHuntingPlayerName())){
                    return huntingPlayer;
                }
            }
        }
        return null;
    }

    public boolean isInList(HuntingPlayer player){
        if(huntingPlayers != null && huntingPlayers.size() > 0) {
            for (HuntingPlayer huntingPlayer : huntingPlayers){
                if(huntingPlayer.getHuntingPlayerName().equals(player.getHuntingPlayerName())){
                    return true;
                }
            }
        }
        return false;
    }

    public void cancelHunting(HuntingPlayer huntingPlayer){
        if(isInList(huntingPlayer)) {
            Spieler spieler = new Selector().selectSpieler(huntingPlayer.getHuntingPlayerName());
            spieler.addCoins((int)(huntingPlayer.getCoins() * 0.75));
            huntingPlayers.remove(find(huntingPlayer));
        }
    }

    public void addToConfig() {
        List<String> wantedPlayers = new ArrayList<>();
        for (KopfgeldPlayer kopfgeldPlayer : Main.kopfgeldPlayers) {
            wantedPlayers.add(kopfgeldPlayer.getWantedPlayerName());
        }
        config.set("wantedPlayers", wantedPlayers);
        List<String> huntingPlayers = new ArrayList<>();
        for (HuntingPlayer huntingPlayer : this.huntingPlayers) {
            huntingPlayers.add(huntingPlayer.getHuntingPlayerName());
            config.set(huntingPlayer.getHuntingPlayerName(), wantedPlayerName);
        }
        config.set("huntersList."+wantedPlayerName, huntingPlayers);
        Main.getPlugin().saveConfig();
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
