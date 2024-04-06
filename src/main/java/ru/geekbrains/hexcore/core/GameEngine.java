package ru.geekbrains.hexcore.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.model.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Game engine. Class that is supposed to be a game judge. Makes players taking turns one by one.
 * Includes MovementEngine and BattleEngine that implement any additional logic for moving and fighting correspondingly.
 */
@Data
@Slf4j
public class GameEngine {
    private final List<Player> players;
    private final Battlefield battlefield;
    private final MovementEngine movementEngine;
    private final BattleEngine battleEngine;

    public GameEngine(List<Player> players, Battlefield battlefield) {
        this.players = new ArrayList<>();
        players.forEach(this::addPlayer);
        this.battlefield = battlefield;
        this.movementEngine = new MovementEngine(battlefield);
        this.battleEngine = new BattleEngine(battlefield);
    }

    /**
     * Add players to the engine
     */
    public void addPlayer(Player player) {
        players.add(player);
        player.setGameEngine(this);
    }

    /**
     * Returns game winner if any. Basic implementation just checks whether there is a looser with no units alive
     *
     * @return Player that still has units while another one has not, or empty Optional if no such Player
     */
    protected Optional<Player> getWinner() {
        if (players.stream().anyMatch(p -> p.getUnits().isEmpty())) {
            return players.stream().filter(p -> !p.getUnits().isEmpty()).findFirst();
        }
        return Optional.empty();
    }

    /**
     * Main method that starts and continues game till it's ended by getWinner method
     */
    public void start() {
        while (getWinner().isEmpty()) {
            for (Player player : players) {
                player.takeTurn();
            }
        }
        log.info("Player {} wins! Congratulations!", getWinner());
    }

    /**
     * Proxies request to MovementEngine
     *
     * @param unit unit to be moved
     * @param path path which must be used to move
     */
    public void moveUnit(Unit unit, Path path) {
        movementEngine.move(unit, path);
    }

    /**
     * Proxies request to BattleEngine
     */
    public void attack(Unit attacker, Unit target) {
        battleEngine.attack(attacker, target);
    }

}
