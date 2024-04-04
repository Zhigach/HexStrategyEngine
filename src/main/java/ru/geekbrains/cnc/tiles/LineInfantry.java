package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.game.Player;
import ru.geekbrains.hexcore.model.Attack;
import ru.geekbrains.hexcore.model.AttackDices;
import ru.geekbrains.hexcore.model.AttackType;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.tiles.Unit;

public class LineInfantry extends Unit {
    
    public LineInfantry(Player owner, Hex hex) {
        super(owner, 5, new Attack(AttackType.PHYSICAL, new AttackDices(2, 3), 2), 2, hex);
    }

    private LineInfantry(Player owner, int maxHealth, Attack attack, int movementRange, Hex hex) {
        super(owner, maxHealth, attack, movementRange, hex);
    }
}
