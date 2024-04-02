package ru.geekbrains.hexcore.TileTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.model.Attack;
import ru.geekbrains.hexcore.model.Damage;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Tile;
import ru.geekbrains.hexcore.model.interfaces.Attacking;
import ru.geekbrains.hexcore.model.interfaces.Damageable;
import ru.geekbrains.hexcore.model.interfaces.DrawableTile;
import ru.geekbrains.hexcore.model.interfaces.Movable;

import java.awt.*;

@Data
@AllArgsConstructor
public abstract class Unit extends Tile implements Movable, DrawableTile, Attacking, Damageable {

    final int maxHealth = 10;

    public int currentHealth = maxHealth;

    private Attack attack;

    final private int movementRange = 0;

    private int movementPoints = 0;

    protected Unit(int s, int q, int r) {
        super(s, q, r);
    }

    protected Unit(Hex hex) {
        super(hex);
    }

    Unit() {
        super();
    }


    /**
     * Method to move Unit. Touched Terrains effects triggered automatically.
     * Unit attaches and detaches from Terrains while moving.
     *
     * @param path list of hexes
     */
    public void move(Path path) {
        movementPoints = movementRange;
        if (validatePath(path)) {
            for (Hex delta : path.getHexList()) {
                hex.add(delta);
                movementPoints--;
                if (movementPoints == 0) {
                    this.stop();
                    break;
                }
            }
        }
    }

    /**
     * @param attack
     */
    @Override
    public void attack(Damageable target) {
        target.getDamage(new Damage(attack.getType(), attack.getDamageAmount()));
    }

    /**
     *
     */
    @Override
    public void getDamage(Damage damage) {
        this.currentHealth -= damage.getDamage();
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
        g2.drawString(shortName, centerPoint.x, centerPoint.y);
    }

}
