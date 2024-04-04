package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.core.Player;
import ru.geekbrains.hexcore.model.Attack;
import ru.geekbrains.hexcore.model.AttackDices;
import ru.geekbrains.hexcore.model.AttackType;
import ru.geekbrains.hexcore.tiles.Unit;
import ru.geekbrains.hexcore.utils.Hex;

public class LineInfantry extends Unit {
    private static final int HP = 5;
    private static final int MOVEMENT = 5;
    private static Attack BASIC_ATTACK = new Attack(AttackType.PHYSICAL, new AttackDices(2, 3), 2);

    public LineInfantry(Player owner, Hex hex) {
        super(owner, HP, MOVEMENT, BASIC_ATTACK, hex);
    }
}
