package ru.geekbrains.hexcore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.core.Player;
import ru.geekbrains.hexcore.model.interfaces.Attacking;
import ru.geekbrains.hexcore.model.interfaces.Damageable;
import ru.geekbrains.hexcore.model.interfaces.Destroyable;
import ru.geekbrains.hexcore.model.interfaces.Movable;
import ru.geekbrains.hexcore.utils.Hex;

import java.awt.*;

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
        reduceMovementPoints(1);
        getBattlefield().moveTile(this, hexDelta);
        hex = hex.add(hexDelta);
    }

    /**
     * Restore movement points up to its maximum
     */
    public void restoreMovementPoint() {
        movementPoints = movementRange;
    }

    /**
     * Spend movement when Unit is moved
     *
     * @param value how mn=any movement points been used
     */
    public void reduceMovementPoints(int value) {
        movementPoints -= value;
    }

    @Override
    public Damage attack(Damageable target) {
        log.info("{} is attacking {} by {}", this, target, attack);
        return new Damage(attack.getType(), attack.getDamageAmount());
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
