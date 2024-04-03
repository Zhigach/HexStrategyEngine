package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.TileTypes.Unit;
import ru.geekbrains.hexcore.model.Attack;
import ru.geekbrains.hexcore.model.AttackDices;
import ru.geekbrains.hexcore.model.AttackType;

public class LineInfantry extends Unit {
    {
        this.setAttack(new Attack(AttackType.PHYSICAL, new AttackDices(2, 3), 2));
    }

    public LineInfantry(int s, int q, int r) {
        super(s, q, r);
    }

    @Override
    public void stop() {

    }
}
