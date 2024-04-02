package ru.geekbrains.hexcore.model.interfaces;

import ru.geekbrains.hexcore.Path;

public interface Movable {
    void move(Path path);
    void stop();
}
