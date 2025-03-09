package game;

import java.awt.*;

public class Painting extends Item {
    private Room room;

    public Painting(String name, String description, String inspection, Room room) {
        super(name, description, inspection);
        this.room = room;
    }
    @Override
    public void use() {
        if (!isUsed()) {
            System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You straighten the painting on its hangar and a "+ConsoleColors.CYAN+"key"+ConsoleColors.RESET+" tumbles out from behind the frame!");
            // updates description
            this.setDescription("a "+ConsoleColors.CYAN+"painting"+ConsoleColors.RESET+" of an old house surrounded by neatly-trimmed hedges");
            // updates inspection
            this.setInspection(ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The painting is a haunting depiction of an old house, its weathered facade looming against a backdrop of stormy skies. The house is surrounded by neatly-trimmed hedges, their precise lines contrasting\n" +
                    "sharply with the chaos of the clouds above. The brushstrokes are meticulous, capturing every detail of the house's cracked windows and sagging porch, but there's something unnerving about the scene - \n" +
                    "something that makes your skin crawl.\n\n" +
                    "The painting hangs straight, thanks to you. As you lean closer, you notice faint details you hadn't seen before: shadows in the windows and a figure standing in the doorway, barely visible but unmistakably there.\n" +
                    "The figure's face is obscured, but you can feel its heavy gaze on you.");
            // creates a new instance of Key
            Item key = new Key("key", "a rusty old "+ConsoleColors.CYAN+"key"+ConsoleColors.RESET, ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": The key appears to be quite old. The shine of the metal has dulled with oxidation.", room);
            key.setObserved(true); // sets key as observed
            room.setItem(Main.Direction.north, key); // adds key to the north
            setUsed(true); // used!
        } else {
            System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": Nothing else happens.");
        }

    }
}
