package fr.lernejo.navy_battle;

public class Game {
        private final CoMap localMap;
        private final CoMap remoteMap;
        private final Option<Status> status = new Option<>(Status.ONGOING);

        public Game() {
            localMap = new CoMap(true);
            remoteMap = new CoMap(false);
        }

        public void wonGame() {
            status.set(Status.WON);

            System.out.println("Hourray we won the game!!! Pierre is the best!!!");
            System.out.println("The play is over!!!!");
            System.out.println("Adversary map:");
            remoteMap.printMap();

            System.out.println("Our map:");
            localMap.printMap();
        }

        public Coordinates getNextPlaceToHit() {
            return remoteMap.getNextPlaceToHit();
        }

        public void setFireResult(Coordinates coordinates, Result result) {
            if (result == Result.MISS)
                remoteMap.setCell(coordinates, Cell.MISSED_FIRE);
            else
                remoteMap.setCell(coordinates, Cell.SUCCESSFUL_FIRE);
        }

        public boolean localMapShipLeft() {
            return localMap.hasShipLeft();
        }

        public Result hit(Coordinates coordinates) {
            return localMap.hit(coordinates);
        }

        public Status getStatus() {
            if (!localMap.hasShipLeft())
                status.set(Status.LOST);
            return status.get();
        }
}
