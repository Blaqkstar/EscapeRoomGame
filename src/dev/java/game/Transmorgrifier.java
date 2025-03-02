package game;

import java.awt.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Transmorgrifier extends Item {
    private Room room;

    private Item itemToUse;

    public Transmorgrifier(String name, String description, String inspection, Room room) {
        super(name, description, inspection);
        this.room = room;
    }
    @Override
    public void use() {
        setUsed(true); // used!

        Consumer<Item> itemConsumer = Item::use;
                itemConsumer.accept(this.itemToUse);

    }

    public void printMenu(List<Item> items)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please choose the item you want to use in the machine");
        for (Item item : items) {
            System.out.println("use " + item.getName());
        }

        String input;
        boolean isValid;
        do {
            System.out.print("Please enter item name: ");
            input = scanner.nextLine();
             isValid = false;

            for (Item item : items) {
                String finalInput = input;
                ValidItemPredicate<Item> nameTester = n -> n.getName().equals(finalInput);
                if (nameTester.test(item))  {
                    isValid = true;
                    this.itemToUse = item;
                    this.use();
                    break;
                }
                else {
                    System.out.println("Invalid item name");
                }
            }

        }
        while(!isValid || !input.equalsIgnoreCase("stop"));
    }

}
