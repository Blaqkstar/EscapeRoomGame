package game;

import java.awt.*;
import java.util.Random;

/**
 * Game item class
 */
public class Corpse extends Item {

    public Corpse(String name, String description, String inspection) {
        super(name, description, inspection);
    }

    @Override
    public void use() {
        System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You're a real freak, aren't ya?");
    }
}
