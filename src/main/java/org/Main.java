package org;
import java.util.EnumMap;
import java.util.Scanner;

public class Main {
    public static void main(final String[] args) {
        // instantiates room
        Room room = new Room();

        // adds items to each wall. This could prob just be a method later
        room.setItem(Direction.north, new Item("Painting", "A painting of an old house surrounded by neatly-trimmed hedges."));
        room.setItem(Direction.south, new Item("Bookshelf", "A bookshelf filled with books about the occult."));
        room.setItem(Direction.east, new Item("Desk", "A desk with a lamp. Judging by the flickering of the bulb, it's on its last leg."));
        room.setItem(Direction.west, new Item("Window", "A window overlooking a garden. It's too foggy to see very far."));

        // this is going to read user input
        Scanner scanner = new Scanner(System.in);
        String input;

        // Welcome message
        System.out.println("You awaken, groggily, to find yourself in a strange room. Along each wall are items.");

        // this continues until the user types 'exit'
        do {
            System.out.print("Enter input (look <direction>, exit): ");
            input = scanner.nextLine(); // user input

            // processes user input
            if (input.startsWith("look ")) {
                final String[] parts = input.split(" "); // splits input into parts, storing in an array
                if (parts.length == 2) { // ensures that input consists of two parts
                    try {
                        final Direction direction = Direction.valueOf(parts[1].toLowerCase()); // gets direction
                        System.out.println("You look " + direction.getDescription() + " and see: " + room.getItemAtDirection(direction)); // displays item in chosen direction
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid direction. Please enter one of the following: north, south, east, west."); // handles input issues
                    }
                } else {
                    System.out.println("Invalid input. Please use the format 'look <direction>'."); // handles formatting issues
                }
            } else if (!input.equalsIgnoreCase("exit")) { // handles incorrect commands
                System.out.println("Unknown input. Please enter 'look <direction>' or 'exit'.");
            }
        } while (!input.equalsIgnoreCase("exit")); // repeats loop until user types 'quit'

        // exit message
        System.out.println("Thanks for playing!");
        scanner.close();
    }

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

    public static class Item {
        private String name;
        private String description;

        public Item(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return name + ": " + description;
        }
    }

    // Class representing the room
    public static class Room {
        private EnumMap<Direction, Item> walls; // map of directions to items

        public Room() {
            walls = new EnumMap<>(Direction.class); // initializes map
        }

        public void setItem(Direction direction, Item item) {
            walls.put(direction, item); // adds item to specified direction
        }

        public Item getItemAtDirection(Direction direction) {
            return walls.get(direction); // gets item at the specified direction
        }
    }
}
