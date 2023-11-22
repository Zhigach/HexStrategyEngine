package ru.geekbrains;

import java.util.Date;

public class Session {
    Date startDate;
    Player[] players;
    //Battlefield battlefield;

    public Session(Date startDate, Player[] players) throws IllegalAccessException {
        if (players.length != 2)
            throw new IllegalAccessException("Number of players must equal to 2");
        this.startDate = startDate;
        this.players = players;
    }
}
