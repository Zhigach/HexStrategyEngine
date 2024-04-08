package ru.geekbrains.hexcore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.core.Player;
import ru.geekbrains.hexcore.model.interfaces.Attacking;
import ru.geekbrains.hexcore.model.interfaces.Damageable;
import ru.geekbrains.hexcore.model.interfaces.Destroyable;
import ru.geekbrains.hexcore.model.interfaces.Movable;

@Getter
@Setter
@Slf4j
public abstract class Unit extends Tile implements Movable, Attacking, Damageable, Destroyable {
    Player owner;
    protected int maxHealth;
    protected int currentHealth = maxHealth;
    protected int movementRange;
    protected int movementPoints;
    protected Attack attack;

    protected Unit(Player owner, int maxHealth, int movementRange, Attack attack, Hex hex) {
        super(hex);
        passable = false;
        this.owner = owner;
        owner.addUnit(this);
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
        this.movementRange = movementRange;
        this.attack = attack;
        restoreMovementPoint();
    }

    @Override
    public void move(Hex hexDelta) {
        reduceMovementPoints(1);
        getBattlefield().moveTile(this, hexDelta);
        hex = hex.add(hexDelta);
    }

    @Override
    public void restoreMovementPoint() {
        movementPoints = movementRange;
    }

    @Override
    public void reduceMovementPoints(int value) {
        movementPoints -= value;
    }

    @Override
    public Damage attack(Damageable target) {
        log.info("{} is attacking {} by {}", this, target, attack);
        return new Damage(attack.getType(), attack.getDamageAmount());
    }

    @Override
    public void getDamage(Damage damage) {
        this.currentHealth -= damage.getDamage();
        log.info("{} received {} damage", this, damage);

    }

    @Override
    public void destroy() {
        log.debug("{} dies", this);
    }

    @Override
    public void stop() {
        reduceMovementPoints(getMovementPoints());
    }

    @Override
    public String toString() {
        return String.format("%s: {Owner:%s, HP: %s/%s, Movement: %s/%s, Attack: %s}", getClass().getSimpleName(),
                owner, currentHealth, maxHealth, movementPoints, movementRange, attack);
    }
}
