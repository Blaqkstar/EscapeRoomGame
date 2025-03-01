package game;

import java.awt.*;

public class Telescope extends Item {
    private Room room;

    public Telescope(String name, String description, String inspection, Room room) {
        super(name, description, inspection);
        this.room = room;
    }
    @Override
    public void use() {
        System.out.println("You peer through the telescope but it's too foggy to see anything.");
    }
}
