package ru.geekbrains.hexcore.core;

import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.tiles.Unit;

@Slf4j
public class DamageEngine {
    Battlefield battlefield;

    public DamageEngine(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public void attack(Unit attacker, Unit target) {

    }
}
