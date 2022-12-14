package de.avenuetv.event.main;

public class HuntingPlayer {
    private String huntingPlayerName;
    private int coins;

    public HuntingPlayer (String huntingPlayerName, int coins) {
        System.out.println(coins);
        this.coins = coins;
        this.huntingPlayerName = huntingPlayerName;
    }
    public HuntingPlayer (String huntingPlayerName) {
        this.huntingPlayerName = huntingPlayerName;
    }

    public String getHuntingPlayerName() {
        return huntingPlayerName;
    }

    public void setHuntingPlayerName(String huntingPlayerName) {
        this.huntingPlayerName = huntingPlayerName;
    }

    public void addCoins (int coins){
        this.coins += coins;
    }

    public int getCoins() {
        return coins;
    }
}
