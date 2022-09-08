package de.avenuetv.event.main;

import Coinsystem.Selector;
import Coinsystem.Spieler;
import de.avenuetv.event.kopfgeld.KopfgeldLoader;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class KopfgeldPlayer extends KopfgeldLoader {
    private String wantedPlayerName;
    private List<HuntingPlayer> huntingPlayers;

    public KopfgeldPlayer (OfflinePlayer wantedPlayer, HuntingPlayer huntingPlayer){
        huntingPlayers = new ArrayList<>();
        addHunter(huntingPlayer);
        wantedPlayerName = wantedPlayer.getName();
        Main.wantedPlayers.add(wantedPlayer);
        Main.kopfgeldPlayersName.add(wantedPlayer.getName());
        Main.kopfgeldPlayers.add(this);
        addToDatabase();
    }

    //Wird genutzt wenn beim laden des Plugins alle HuntingPlayers aus der Config genommen werden.
    public KopfgeldPlayer (List<HuntingPlayer> huntingPlayers, String wantedPlayerName) {
        this.huntingPlayers = huntingPlayers;
        this.wantedPlayerName = wantedPlayerName;
        Main.wantedPlayers.add(Bukkit.getOfflinePlayer(wantedPlayerName));
        Main.kopfgeldPlayersName.add(wantedPlayerName);
        Main.kopfgeldPlayers.add(this);
    }

    public void addHunter(HuntingPlayer huntingPlayer){
        if (isInList(huntingPlayer)) {
            find(huntingPlayer).addCoins(huntingPlayer.getCoins());
        } else {
            huntingPlayers.add(huntingPlayer);
        }
        addToDatabase();
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
            Main.database.setData("DELETE FROM `Kopfgelder` WHERE `wantedPlayer` = '"+wantedPlayerName+"' AND `huntingPlayer` = '"+huntingPlayer.getHuntingPlayerName()+"'");
        }
    }

    public List<String> getHuntingPlayersName() {
        List<String> list = new ArrayList<>();
        for (HuntingPlayer huntingPlayer : huntingPlayers) {
            list.add(huntingPlayer.getHuntingPlayerName());
        }
        return list;
    }

    public void addToDatabase() {
        Main.database.setData("DELETE FROM `Kopfgelder` WHERE `wantedPlayer` = '"+wantedPlayerName+"'");
        for (HuntingPlayer huntingPlayer : huntingPlayers) {
            Main.database.setData("INSERT INTO `Kopfgelder`(`wantedPlayer`, `huntingPlayer`, `Coins`) VALUES ('"+wantedPlayerName+"','"+huntingPlayer.getHuntingPlayerName()+"','"+huntingPlayer.getCoins()+"')");
        }
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
