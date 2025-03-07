package game;

import java.awt.*;

public class Lantern extends Item {
    private Room room;

    public Lantern(String name, String description, String inspection, Room room) {
        super(name, description, inspection);
        this.room = room;
    }
    @Override
    public void use() {
        // idk yet, probably just decorative. lantern should emit a strange light
    }
}
