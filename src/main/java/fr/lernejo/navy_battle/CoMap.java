package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class CoMap extends MapGame {
    private final List<Coordinates> positionsToTest = new ArrayList<>();
    private final List<Coordinates> lightHitPositions = new ArrayList<>();

    public CoMap(boolean fill) {
        super(fill);
        for (int i = 0; i < getWidth(); i++) {
            for (int j = i % 2; j < getHeight(); j += 2) {
                lightHitPositions.add(new Coordinates(i, j));
            }
        }
    }

    public void setCell(Coordinates coordinates, Cell newStatus) {
        super.setCell(coordinates, newStatus);

        if (newStatus == Cell.SUCCESSFUL_FIRE) {
            positionsToTest.addAll(List.of(
                coordinates.plus(-1, 0),
                coordinates.plus(0, -1),
                coordinates.plus(1, 0),
                coordinates.plus(0, 1)
            ));
        }
    }


    public Coordinates getNextPlaceToHit() {
        Coordinates coordinates = null;
        if (!positionsToTest.isEmpty())
            coordinates = fireAroundSuccessfulHit();

        if (coordinates == null && !lightHitPositions.isEmpty())
            coordinates = lightHit();

        if (coordinates == null)
            coordinates = bruteForceHit();

        return coordinates;
    }

    private Coordinates fireAroundSuccessfulHit() {
        while (positionsToTest.size() > 0) {
            var c = positionsToTest.remove(0);
            if (getCell(c) == Cell.EMPTY) return c;
        }
        return null;
    }

    private Coordinates lightHit() {
        while (lightHitPositions.size() > 0) {
            var c = lightHitPositions.remove(0);
            if (getCell(c) == Cell.EMPTY) return c;
        }
        return null;
    }

    private Coordinates bruteForceHit() {
        // System.err.println("Brute force required!");
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (getCell(i, j) == Cell.EMPTY)
                    return new Coordinates(i, j);
            }
        }

        throw new RuntimeException("The other player is a cheater and / or a liar!");
    }

    public Result hit(Coordinates coordinates) {
        if (getCell(coordinates) != Cell.BOAT)
            return Result.MISS;

        var first = getBoats().stream().filter(s -> s.contains(coordinates)).findFirst();
        assert (first.isPresent());
        first.get().remove(coordinates);

        setCell(coordinates, Cell.SUCCESSFUL_FIRE);

        return first.get().isEmpty() ? Result.SUNK : Result.HIT;
    }
}
