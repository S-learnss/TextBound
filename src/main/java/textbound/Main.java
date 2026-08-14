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

        Room destination = village.getExit("north");

        System.out.println("Current room: " + village.getName());
        System.out.println("Going north...");
        System.out.println("You arrive at: " + destination.getName());
    }
}