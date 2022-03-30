package fr.lernejo.navy_battle;

import java.util.*;

public class Map {
    private final Integer[] BOATS = {5, 4, 3, 3, 2};
    private final Cell[][] map = new Cell[10][10];
    private final List<List<Coordinates>> boats = new ArrayList<>();

    public Map(boolean fill) {
        for (Cell[] gameCells : map) {
            Arrays.fill(gameCells, Cell.EMPTY);
        }

        if (fill) {
            buildMap();
        }
    }

    public int getHeight() {
        return map[0].length;
    }

    public int getWidth() {
        return map.length;
    }

    private void buildMap() {
        var random = new Random();
        var boats = new ArrayList<>(Arrays.asList(BOATS));
        Collections.shuffle(boats);
        while (!boats.isEmpty()) {
            int boat = boats.get(0);
            int x = Math.abs(random.nextInt()) % (getWidth() - 2);
            int y = Math.abs(random.nextInt()) % (getHeight() - 2);
            var orientation = random.nextBoolean() ? Boat.HORIZONTAL : Boat.VERTICAL;
            if (!canFit(boat, x, y, orientation))
                continue;
            addBoat(boat, x, y, orientation);
            boats.remove(0);
        }
    }

    public void addBoat(int length, int x, int y, Boat orientation) {
        var coordinates = new ArrayList<Coordinates>();

        while (length > 0) {
            map[x][y] = Cell.BOAT;
            length--;
            coordinates.add(new Coordinates(x, y));

            switch (orientation) {
                case HORIZONTAL -> x++;
                case VERTICAL -> y++;
            }
        }
        boats.add(coordinates);
    }

    private boolean canFit(int length, int x, int y, Boat orientation) {
        if (x >= getWidth() || y >= getHeight() || getCell(x, y) != Cell.EMPTY)
            return false;

        if (length == 0)
            return true;

        return switch (orientation) {
            case HORIZONTAL -> canFit(length - 1, x + 1, y, orientation);
            case VERTICAL -> canFit(length - 1, x, y + 1, orientation);
        };
    }

    public Cell getCell(Coordinates coordinates) {
        return getCell(coordinates.getX(), coordinates.getY());
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= 10 || y >= 10)
            throw new RuntimeException("Invalidate coordinates!");

        return map[x][y];
    }

    protected Cell[][] getMap() {
        return map;
    }

    public List<List<Coordinates>> getBoats() {
        return boats;
    }
}
