package game;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static void main(final String[] args) {

        final Logger log = LogManager.getLogger(Main.class.getName());

        printTitle(); // prints game title

        // instantiates room
        Room room = new Room();
        room.setName("Tutorial Room");
        log.info("instantiating " + room.getName());
        log.debug("adding items to " + room.getName());
        System.out.println();
        // adds items to each wall. This could prob just be a method later
        room.setItem(Direction.north, new Painting("painting",
                "A painting of an old house surrounded by neatly-trimmed hedges.",
                "The painter's signature is inscribed in the corner: 'F.L. Romulus'.",
                room));
        room.setItem(Direction.south, new Bookshelf("bookshelf",
                "A bookshelf filled with books about the occult.",
                "A pungent smell gets stronger the closer you get to the shelf."));
        room.setItem(Direction.east, new Desk("desk",
                "A desk with a lamp. Judging by the flickering of the bulb, it's on its last leg.",
                "When the bulb flicks off, you notice a key hidden inside the bulb."));
        room.setItem(Direction.west, new Window("window",
                "A window overlooking a garden. It's too foggy to see very far.",
                "The garden is guarded by a scarecrow with a tattered black hat."));

        // this is going to read user input
        Scanner scanner = new Scanner(System.in);
        String input;

        // Welcome message
        System.out.println("You awaken, groggily, to find yourself in a strange room. Along each wall are items.");

        // this continues until the user types 'exit'
        do {
            System.out.println();
            System.out.print(ConsoleColors.YELLOW+"Enter input (or 'help' for a list of available commands): "+ConsoleColors.RESET);
            input = scanner.nextLine(); // user input
            log.info("user input received");
            System.out.println();

            // processes user input
            if (input.startsWith("look ")) {
                log.info("player selected look");
                final String[] parts = input.split(" "); // splits input into parts, storing in an array
                if (parts.length == 2) { // ensures that input consists of two parts
                    try {
                        final Direction direction = Direction.valueOf(parts[1].toLowerCase()); // gets direction
                        room.getItemAtDirection(direction).setObserved(true);
                        System.out.println("You look " + direction.getDescription() + " and see: " + room.getItemAtDirection(direction)); // displays item in chosen direction
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid direction. Please enter one of the following: north, south, east, west."); // handles input issues
                    }
                } else {
                    System.out.println("Invalid input. Please use the format 'look <direction>'."); // handles formatting issues
                }
            } else if(input.startsWith("inspect ")) {
                final String[] parts = input.split(" "); // splits input into parts, storing in an array
                if (parts.length == 2) { // ensures that input consists of two parts
                    try {
                        // declare item that is being inspected
                        Item item = room.getItems().get(parts[1].toLowerCase());
                        // Check if player has observed the item yet
                        if (item.isObserved()) {
                            System.out.print(item.getInspection());
                        }
                        else{
                            System.out.println("You do not see any " + parts[1]);
                        }
                    }
                    // Handles input of unknown item
                    catch (Exception e) {
                        System.out.println("You do not see any " + parts[1]);
                    }
                }
                else {
                    System.out.println("Invalid input. Please use the format 'inspect <item>'."); // handles formatting issues
                }
            }
            else if (input.startsWith("use ")) {
                final String[] parts = input.split(" "); // splits input into parts, storing in an array
                if (parts.length == 2) { // ensures that input consists of two parts
                    try {
                        // declare item that is being inspected
                        Item item = room.getItems().get(parts[1].toLowerCase());
                        // Check if player has observed the item yet
                        if (item.isObserved()) {
                            item.use();
                        }
                        else{
                            System.out.println("You do not see any " + parts[1]);
                        }
                    }
                    // Handles input of unknown item
                    catch (Exception e) {
                        System.out.println("You do not see any " + parts[1]);
                    }
                }
                else {
                    System.out.println("Invalid input. Please use the format 'use <item>'."); // handles formatting issues
                }
            }
            else if (input.equalsIgnoreCase("help") || input.equalsIgnoreCase("?")) {
                printCommands();
            }
            else if (!input.equalsIgnoreCase("exit")) { // handles incorrect commands
                System.out.println("Unknown input. Please enter 'look <direction>' or 'exit'.");
            }
        } while (!input.equalsIgnoreCase("exit")); // repeats loop until user types 'exit'

        // exit message
        log.info("exiting game...");
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private static void printCommands() {
        // list of available commands
        System.out.println("AVAILABLE COMMANDS:");
        System.out.println("--------------------");
        System.out.println(ConsoleColors.YELLOW+"1. look <direction>"+ConsoleColors.RESET+": looks at the specified direction");
        System.out.println(ConsoleColors.YELLOW+"2. inspect <item>"+ConsoleColors.RESET+": inspects the specified item");
        System.out.println(ConsoleColors.YELLOW+"2. use <item>"+ConsoleColors.RESET+": attempts to use the specified item");
        System.out.println(ConsoleColors.YELLOW+"3. help"+ConsoleColors.RESET+": prints this message");
        System.out.println(ConsoleColors.YELLOW+"4. exit"+ConsoleColors.RESET+": exits the game");
    }
    private static void printTitle(){
        // text generated via https://patorjk.com/software/taag. This is the "Slant" font
        final String title = ConsoleColors.RED+"\n" +
                "    ___________ _________    ____  ______   ____  ____  ____  __  ___\n" +
                "   / ____/ ___// ____/   |  / __ \\/ ____/  / __ \\/ __ \\/ __ \\/  |/  /\n" +
                "  / __/  \\__ \\/ /   / /| | / /_/ / __/    / /_/ / / / / / / / /|_/ / \n" +
                " / /___ ___/ / /___/ ___ |/ ____/ /___   / _, _/ /_/ / /_/ / /  / /  \n" +
                "/_____//____/\\____/_/  |_/_/   /_____/  /_/ |_|\\____/\\____/_/  /_/   \n" +
                "====================================================================\n"+ConsoleColors.RESET;
        System.out.println(title);
    }

    // TODO: ---------------------------------------[ INLINE CLASSES AND ENUMS BEGIN HERE ]--------------------------------------------------------
    // enum representing four cardinal directions
    public enum Direction {
        north("to the north"),
        south("to the south"),
        east("to the east"),
        west("to the west");

        private final String description; //

        Direction(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description; // returns the direction's description
        }
    }

}
