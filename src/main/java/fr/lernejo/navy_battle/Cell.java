package fr.lernejo.navy_battle;

public enum Cell {
    EMPTY("."),
    MISSED_FIRE("-"),
    SUCCESSFUL_FIRE("X"),
    BOAT("B");

    private final String letter;

    Cell(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }
}
