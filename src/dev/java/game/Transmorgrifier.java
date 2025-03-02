package game;

import java.awt.*;

public class Transmorgrifier extends Item {
    private Room room;



    public Transmorgrifier(String name, String description, String inspection, Room room) {
        super(name, description, inspection);
        this.room = room;
    }
    @Override
    public void use() {
        setUsed(true); // used!

    }
}
