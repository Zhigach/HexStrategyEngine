package ru.geekbrains.hexcore.tiles.unit;

import ru.geekbrains.hexcore.core.Player;
import ru.geekbrains.hexcore.model.Attack;
import ru.geekbrains.hexcore.model.AttackType;
import ru.geekbrains.hexcore.model.Unit;
import ru.geekbrains.hexcore.utils.DiceSet;
import ru.geekbrains.hexcore.utils.Dices;
import ru.geekbrains.hexcore.utils.Hex;

import java.util.Set;

public class LineInfantry extends Unit {
    private static final int HP = 5;
    private static final int MOVEMENT = 2;
    private static Attack BASIC_ATTACK = new Attack(AttackType.PHYSICAL, new DiceSet(Set.of(new Dices(2, 3))), 2);

    public LineInfantry(Player owner, Hex hex) {
        super(owner, HP, MOVEMENT, BASIC_ATTACK, hex);
    }
}
