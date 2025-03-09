package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Transmorgrifier extends Item {
    private Room room;

    private Item itemToUse;

    private Boolean usable=false;

    private ArrayList<Item> itemsToTransmorgrify = new ArrayList<>();

    public Transmorgrifier(String name, String description, String inspection, Room room) {
        super(name, description, inspection);
        this.room = room;
    }

    public ArrayList<Item> getItemsToTransmorgrify() {
        return itemsToTransmorgrify;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }

    public Boolean isUsable() {
        return usable;
    }

    public void setItemsToTransmorgrify(ArrayList<Item> itemsToTransmorgrify) {
            this.itemsToTransmorgrify = itemsToTransmorgrify;
    }


    @Override
    public void use() {
        setUsed(true); // used!

        if (usable) {
            printMenu(itemsToTransmorgrify);
        }
        else{
            System.out.println("You don't even know where to begin using such a strange machine.");
        }
    }

    public void Transmorgrify() {
        Consumer<Item> itemConsumer = Item::use;
        itemConsumer.accept(this.itemToUse);
    }

    public void printMenu(List<Item> items)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Items to use in the transmorgrifier:");
        // prints a list of available items to use
        for (Item item : items) {
            if (item.isObserved()) {
                System.out.println(ConsoleColors.YELLOW + "AVAILABLE ITEM" + ConsoleColors.RESET + ": " + item.getName());
            }
        }
        String input;
        boolean isValid = false;
        do {
            System.out.print("Please enter item name (or 'stop' to leave interaction menu): ");
            input = scanner.nextLine();
            System.out.println();

            // checks for stop first to allow skipping of the code below if player wants to leave menu
            if (input.equalsIgnoreCase("stop")) {
                break;
            }
            // player has entered something
            final String finalInput = input; // copies input as a final String for use in Predicate Functional Interface
            // iterates through item list and compares to nearby available valid usable objects
            for (Item item : items) {
                if (item.isObserved()) {
                    ValidItemPredicate<Item> nameTester = n -> n.getName().equals(finalInput); // tests that player input matches the name of the currently item in iteration
                    // if the item name matches the name of the current item in items list
                    if (nameTester.test(item)) {
                        isValid = true;
                        this.itemToUse = item;
                        this.Transmorgrify();
                        break;
                    }
                }
            }
            // if not a valid item
            if (!isValid) {
                System.out.println("Invalid item name");
            }
        } while(!input.equalsIgnoreCase("stop") && !isValid);
    }

}
