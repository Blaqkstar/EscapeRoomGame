package game;

import java.awt.*;
import java.util.Random;

public class Lantern extends Item {

    public Lantern(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    boolean powered = true;

    @Override
    public void use() {
        // first tests to see if lamp has been destroyed yet
        if (powered) {
            this.setUsed(true); // used!
            System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+
                    ": You twist the lantern's dial and its light dims, the impossible color fading like a dying breath. The room feels darker now and the shadows feel thicker, hungrier, than before.");
            powered = false; // lamp is off
            this.setDescription("a lantern, powered off.");
        } else {
            this.setUsed(true); // used!
            System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+
                    ": You twist the lantern's dial and it sputters to life with a faint click. The light that spills from it is wrong - a color you can't quite name; something between blue and violet but\n" +
                    "not quite either, as if it exists outside of any spectrum you know. The haze it casts makes the room feel colder, the shadows sharper, and the air heavier. The light itself feels alive.");
            powered = true; // lamp is on
            this.setDescription("a "+ConsoleColors.CYAN+"lantern"+ConsoleColors.RESET+", flickering faintly as its ethereal glow casts sharp, unnatural shadows.");
        }
    }
}
