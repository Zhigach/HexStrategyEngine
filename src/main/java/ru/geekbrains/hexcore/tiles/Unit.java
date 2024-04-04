package ru.geekbrains.hexcore.tiles;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.core.Player;
import ru.geekbrains.hexcore.model.Attack;
import ru.geekbrains.hexcore.model.Damage;
import ru.geekbrains.hexcore.model.Tile;
import ru.geekbrains.hexcore.model.interfaces.Attacking;
import ru.geekbrains.hexcore.model.interfaces.Damageable;
import ru.geekbrains.hexcore.model.interfaces.Movable;
import ru.geekbrains.hexcore.utils.Hex;

import java.awt.*;

@Getter
@Setter
@Slf4j
public abstract class Unit extends Tile implements Movable, Attacking, Damageable {

    Player owner;
    protected int maxHealth;
    protected int currentHealth = maxHealth;
    protected int movementRange;
    protected int movementPoints;
    protected Attack attack;

    protected Unit(Player owner, int maxHealth, int movementRange, Attack attack, Hex hex) {
        super(hex);
        this.owner = owner;
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
        this.movementRange = movementRange;
        this.attack = attack;
        restoreMovementPoint();
    }


    /**
     * Method to move Unit. Touched Terrains effects triggered automatically.
     * Unit attaches and detaches from Terrains while moving.
     *
     * @param hexDelta list of hexes
     */
    @Override
    public void move(Hex hexDelta) {
        getBattlefield().moveTile(this, hexDelta);
        hex = hex.add(hexDelta);
        reduceMovementPoints(1);
    }

    public void restoreMovementPoint() {
        movementPoints = movementRange;
    }

    public void reduceMovementPoints(int value) {
        movementPoints -= value;
    }

    @Override
    public int getDistanceTo(Hex target) {
        return hex.findDistance(target);
    }

    /**
     * @param target
     * @return
     */
    @Override
    public Damage attack(Damageable target) {
        log.info("{} is attacking {} by {}", this, target, attack);
        return new Damage(attack.getType(), attack.getDamageAmount());
    }


    @Override
    public Hex getDamageableHex() {
        return hex;
    }

    /**
     *
     */
    @Override
    public void getDamage(Damage damage) {
        this.currentHealth -= damage.getDamage();
        log.info("{} received {} damage", this, damage);

    }

    @Override
    public void destroy() {
        log.debug("{} dies", this);
        //battlefield.removeTile(this);
    }

    /**
     *
     */
    @Override
    public void stop() {
        reduceMovementPoints(getMovementPoints());
    }


    @Override
    public void draw(Graphics2D g2, int size, Point centerPoint) {
        g2.setColor(Color.BLACK);
        String shortName = getClass().getSimpleName().chars().filter(Character::isUpperCase).map(c -> ((char) c))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        g2.drawString(String.format(shortName + "(%s/%s)", currentHealth, maxHealth),
                centerPoint.x, centerPoint.y);
    }

    @Override
    public String toString() {
        return String.format("%s: {Owner:%s, HP: %s/%s, Movement: %s/%s, Attack: %s}", getClass().getSimpleName(),
                owner, currentHealth, maxHealth, movementPoints, movementRange, attack);
    }
}
