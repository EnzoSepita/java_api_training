package fr.lernejo.navy_battle;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MapGame extends Map {
    public MapGame(boolean fill) {
        super(fill);

        if (fill)
            printMap();
    }

    public void printMap() {
        System.out.println(" .... ");
        for (Cell[] row : getMap()) {
            System.out.println(Arrays.stream(row).map(Cell::getLetter).collect(Collectors.joining(" ")));
        }
        System.out.println(" .... ");
    }

    public boolean hasShipLeft() {
        for (var row : getMap()) {
            if (Arrays.stream(row).anyMatch(s -> s == Cell.BOAT))
                return true;
        }
        return false;
    }

    public void setCell(Coordinates coordinates, Cell newStatus) {
        getMap()[coordinates.getX()][coordinates.getY()] = newStatus;
    }
}
