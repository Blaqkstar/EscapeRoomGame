package org;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(final String[] args) {
        // instantiates room
        Room room = new Room();

        // adds items to each wall. This could prob just be a method later
        room.setItem(Direction.north, new Item("painting", "A painting of an old house surrounded by neatly-trimmed hedges.", "The painter's signature is inscribed in the corner: 'F.L. Romulus'."));
        room.setItem(Direction.south, new Item("bookshelf", "A bookshelf filled with books about the occult.", "A pungent smell gets stronger the closer you get to the shelf."));
        room.setItem(Direction.east, new Item("desk", "A desk with a lamp. Judging by the flickering of the bulb, it's on its last leg.", "When the bulb flicks off, you notice a key hidden inside the bulb."));
        room.setItem(Direction.west, new Item("window", "A window overlooking a garden. It's too foggy to see very far.", "The garden is guarded by a scarecrow with a tattered black hat."));

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
                            System.out.print("You do not see this item.");
                        }
                    }
                    // Handles input of unknown item
                    catch (Exception e) {
                        System.out.println("You do not see this item");
                    }
                } else {
                    System.out.println("Invalid input. Please use the format 'inspect <item>'."); // handles formatting issues
                }
            }
            else if (!input.equalsIgnoreCase("exit")) { // handles incorrect commands
                System.out.println("Unknown input. Please enter 'look <direction>' or 'exit'.");
            }
        } while (!input.equalsIgnoreCase("exit")); // repeats loop until user types 'exit'

        // exit message
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    // TODO: ----------------------------------------------------------- CLASSES AND ENUMS BEGIN HERE
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

        // Detailed description that is given when inspected
        private String inspection;

        // Keeps track of if item has been observed yet
        private boolean observed;

        public Item(String name, String description, String inspection) {
            this.name = name;
            this.description = description;
            this.inspection = inspection;
            this.observed = false;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public void setObserved(boolean observed) {
            this.observed = observed;
        }

        public boolean isObserved() {
            return observed;
        }

        public String getInspection() {
            return inspection;
        }
        @Override
        public String toString() {
            return name + ": " + description;
        }
    }

    public static class Room {
        // this is the logical representation of the "walls" of each room
        private EnumMap<Direction, Item> walls; // map of directions to items. syntax: <key, value>

        // Keeps track of items in separate map not associated with direction
        // This way player can inspect item without specifying direction
        // TODO: Are we adding items to this map after player "discovers" them via look?
        private Map<String, Item> items = new HashMap<>();

        public Room() {
            walls = new EnumMap<>(Direction.class); // initializes map
        }

        public void setItem(Direction direction, Item item) {
            walls.put(direction, item); // adds item to specified direction
            items.put(item.name, item); // adds item to item list
        }

        public Item getItemAtDirection(Direction direction) {
            return walls.get(direction); // gets item at the specified direction
        }

        public Map<String, Item> getItems() {
            return items;
        }
    }
}
