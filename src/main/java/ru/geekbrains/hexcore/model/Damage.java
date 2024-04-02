package ru.geekbrains.hexcore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Data
public class Damage {
    private AttackType attackType;
    private int damage;
}
