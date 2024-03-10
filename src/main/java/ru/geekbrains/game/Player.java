package ru.geekbrains.game;

import ru.geekbrains.hexcore.TileTypes.Unit;

import java.util.List;

public class Player {
    PlayerType playerType;
    String name;
    List<Unit> unitList;
    int victoryPoints = 0;




    public Player createAIPlayer(String name, List<Unit> unitList) {
        return new Player(PlayerType.AI, name, unitList);
    }
    public Player createHumanPlayer(String name, List<Unit> unitList) {
        return new Player(PlayerType.Human, name, unitList);
    }
    public Player(String playerType, String name, List<Unit> unitList) {
        if (playerType.equals("AI")) {
            new Player(PlayerType.AI, name, unitList);
        } else if (playerType.equals("Human")) {
            new Player(PlayerType.Human, name, unitList);
        } else {
            throw new IllegalArgumentException("Only <AI> and <Human> can be used as arguments");
        }
    }

    private Player(PlayerType playerType, String name, List<Unit> unitList) {
        this.playerType = playerType;
        this.name = name;
        this.unitList = unitList;
    }
}

enum PlayerType {
    AI, Human
}


