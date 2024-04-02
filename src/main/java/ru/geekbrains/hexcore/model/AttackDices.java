package ru.geekbrains.hexcore.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public record AttackDices(int number, int d) {
}
