package game;

import java.awt.*;

/**
 * Game item class
 */
public class Table extends Item {
    private Room room;

    public Table(String name, String description, String inspection, Room room) {
        super(name, description, inspection);
        this.room = room;
    }
    @Override
    public void use() {
        // mostly decorative, doesn't do anything
        System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": It's too heavy.");
    }
}
