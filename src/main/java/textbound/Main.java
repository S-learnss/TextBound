package textbound;

public class Main {

    public static void main(String[] args) {

        Room village = new Room(
            "Village Square",
            "You stand in the center of a quiet village."
        );

        Room forest = new Room(
            "Dark Forest",
            "Tall trees surround you on every side."
        );

        village.addExit("north", forest);
        forest.addExit("south", village);

        Player player = new Player("Adventurer", village);

        System.out.println("Player: " + player.getName());
        System.out.println("Current room: "
                + player.getCurrentRoom().getName());

        player.move("north");

        System.out.println("After moving north:");
        System.out.println("Current room: "
                + player.getCurrentRoom().getName());

        boolean moved = player.move("west");

        System.out.println("Could move west? " + moved);
    }
}