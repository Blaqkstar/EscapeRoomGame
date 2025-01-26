package org;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type 'look <direction>' to look in a direction (north, south, east, west), or type 'exit' to quit:");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.startsWith("look ")) {
                String directionInput = input.substring(5).toLowerCase();

                try {
                    Direction direction = Direction.valueOf(directionInput);
                    System.out.println("You are looking " + direction.getDescription());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid direction. Please use north, south, east, or west.");
                }
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting");
                break;
            } else {
                System.out.println("Use 'look <north, south, east, or west>' or type 'exit' to quit.");
            }
        }

        scanner.close();
    }

    public enum Direction {
        north("to the north"),
        south("to the south"),
        east("to the east"),
        west("to the west");

        private final String description;

        Direction(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
