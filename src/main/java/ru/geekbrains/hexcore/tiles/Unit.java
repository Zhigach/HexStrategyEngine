package ru.geekbrains.hexcore.tiles;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.game.Player;
import ru.geekbrains.hexcore.model.Attack;
import ru.geekbrains.hexcore.model.Damage;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Tile;
import ru.geekbrains.hexcore.model.interfaces.Attacking;
import ru.geekbrains.hexcore.model.interfaces.Damageable;
import ru.geekbrains.hexcore.model.interfaces.Movable;

import java.awt.*;

@Data
@Slf4j
public abstract class Unit extends Tile implements Movable, Attacking, Damageable {

    Player owner;
    protected int maxHealth;
    protected int currentHealth = maxHealth;
    protected int movementRange;
    protected int movementPoints = 0;
    protected Attack attack;

    protected Unit(Player owner, int maxHealth, int movementRange, Attack attack, Hex hex) {
        super(hex);
        this.owner = owner;
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
        this.movementRange = movementRange;
        this.attack = attack;
    }


    /**
     * Method to move Unit. Touched Terrains effects triggered automatically.
     * Unit attaches and detaches from Terrains while moving.
     *
     * @param path list of hexes
     */
    @Override
    public void move(Path path) {
        movementPoints = movementRange;
        if (validatePath(path)) {
            for (Hex delta : path.getHexList()) {
                getBattlefield().moveTile(this, delta);
                hex = hex.add(delta);
                movementPoints--;
                if (movementPoints == 0) {
                    this.stop();
                    break;
                }
            }
        }
        log.debug("{} moved using path: {}. New coordinate is {}", this, path, hex);
    }

    /**
     * @param target
     */
    @Override
    public void attack(Damageable target) {
        log.info("{} is attacking {} by {}", this, target, attack);
        target.getDamage(new Damage(attack.getType(), attack.getDamageAmount()));
    }

    /**
     *
     */
    @Override
    public void getDamage(Damage damage) {
        this.currentHealth -= damage.getDamage();
        log.info("{} received {} damage", this, damage);
    }

    /**
     *
     */
    @Override
    public void stop() {

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

}
