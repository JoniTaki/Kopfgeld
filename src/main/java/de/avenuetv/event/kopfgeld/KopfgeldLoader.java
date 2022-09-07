package de.avenuetv.event.kopfgeld;

import de.avenuetv.event.main.HuntingPlayer;
import de.avenuetv.event.main.KopfgeldPlayer;
import de.avenuetv.event.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class KopfgeldLoader {

    public FileConfiguration config = Main.getPlugin().getConfig();

    public void load() {
        List<String> alleKopfgelder = config.getStringList("wantedPlayers");
        for (String name : alleKopfgelder) {
            new KopfgeldPlayer(Bukkit.getOfflinePlayer(name), getHunters(name));
        }
    }

    public List<HuntingPlayer> getHunters(String wantedName) {
        List<HuntingPlayer> huntingPlayers = new ArrayList<>();
        List<String> names = config.getStringList("huntersList."+wantedName);
        for (String hunterName : names) {
            new HuntingPlayer(hunterName, getCoins(hunterName, wantedName));
        }
        return huntingPlayers;
    }

    public int getCoins(String hunterName, String wantedName) {
        return config.getInt(hunterName+"."+wantedName);
    }

}
