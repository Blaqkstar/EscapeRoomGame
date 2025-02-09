package org;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LoggingException;

public class Main {
    public static void main(final String[] args) {

        //Need this logger to work again pls!
        final Logger log = LogManager.getLogger(Main.class.getName());

        log.info("instantiating room...");
        // instantiates room
        Room curreentRoom = new Room();

        Door newDoor = new Door(curreentRoom, 2);

        boolean hasKey = false;

        log.debug("adding items to room...");
        // adds items to each wall. This could prob just be a method later
        curreentRoom.setItem(Direction.north, new Item("painting", "A painting of an old house surrounded by neatly-trimmed hedges.", "The painter's signature is inscribed in the corner: 'F.L. Romulus'."));
        curreentRoom.setItem(Direction.south, new Item("bookshelf", "A bookshelf filled with books about the occult.", "A pungent smell gets stronger the closer you get to the shelf."));
        curreentRoom.setItem(Direction.east, new Item("desk", "A desk with a lamp. Judging by the flickering of the bulb, it's on its last leg.", "When the bulb flicks off, you notice a key hidden inside the bulb."));
        curreentRoom.setItem(Direction.west, new Item("window", "A window overlooking a garden. It's too foggy to see very far.", "The garden is guarded by a scarecrow with a tattered black hat."));

        // this is going to read user input
        Scanner scanner = new Scanner(System.in);
        String input;

        // Welcome message
        System.out.println("You awaken, groggily, to find yourself in a strange room. Along each wall are items.");

        // this continues until the user types 'exit'
        do {
            System.out.print("Enter input (look <direction>, exit): ");
            input = scanner.nextLine(); // user input
            log.info("user input received...");

            // processes user input
            log.info("user selected look...");
            if (input.startsWith("look ")) {
                final String[] parts = input.split(" "); // splits input into parts, storing in an array
                if (parts.length == 2) { // ensures that input consists of two parts
                    try {
                        final Direction direction = Direction.valueOf(parts[1].toLowerCase()); // gets direction
                        curreentRoom.getItemAtDirection(direction).setObserved(true);
                        System.out.println("You look " + direction.getDescription() + " and see: " + curreentRoom.getItemAtDirection(direction).getDescription()); // displays item in chosen direction
                        if(direction == Direction.east) {
                            System.out.println("You find a key in the " + curreentRoom.getItemAtDirection(direction).getName() + ", and now a door appears on the north wall");
                            hasKey = true;
                            newDoor.unlockDoor();
                        }
                        else if(direction == Direction.north && hasKey) {
                            Room newRoom = new Room();
                            newDoor.OpenDoor(newRoom);
                        }
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
                        Item item = curreentRoom.getItems().get(parts[1].toLowerCase());
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
        log.info("exiting game...");
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






}
