package de.avenuetv.event.kopfgeld;

import de.avenuetv.event.main.HuntingPlayer;
import de.avenuetv.event.main.KopfgeldPlayer;
import de.avenuetv.event.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class KopfgeldLoader {

    public void load() {
        List<String> alleKopfgelder = Main.database.getListWithoutSpecs("wantedPlayer");
        for (String name : alleKopfgelder) {
            new KopfgeldPlayer(getHunters(name), name);
        }
    }

    public List<HuntingPlayer> getHunters(String wantedName) {
        List<HuntingPlayer> huntingPlayers = new ArrayList<>();
        List<String> names = Main.database.getListWithSpecs("huntingPlayer", "wantedPlayer", wantedName);
        for (String hunterName : names) {
            HuntingPlayer huntingPlayer = new HuntingPlayer(hunterName, getCoins(hunterName, wantedName));
            huntingPlayers.add(huntingPlayer);
        }
        return huntingPlayers;
    }

    public int getCoins(String hunterName, String wantedName) {
        return Main.database.getInt("Coins", wantedName, hunterName);
    }

}
