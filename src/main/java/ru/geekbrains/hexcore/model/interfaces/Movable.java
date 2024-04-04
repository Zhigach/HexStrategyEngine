package ru.geekbrains.hexcore.model.interfaces;

import ru.geekbrains.hexcore.utils.Hex;

public interface Movable {
    void move(Hex delta);

    void stop();
}
